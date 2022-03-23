package task.getinto;

import task.getinto.utils.FileCSVWriter;
import task.getinto.utils.FileXMLWriter;
import task.getinto.utils.ZipUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;

@Controller
public class UploadCSVController {

    private  Map<String, ArrayList<Invoice>> invoices;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/upload-file-csv")
    public String uploadCSVFile(@RequestParam("upload-file") MultipartFile file,
                                @RequestParam("delimiter") String delimiter,
                                Model model) {

        if(file.isEmpty()) {
            model.addAttribute("message", "Please select a CSV file to upload.");
            model.addAttribute("status", false);

            return "file-upload-status";
        }

        if(delimiter.isEmpty()) {
            model.addAttribute("message", "Please set a delimiter symbol.");
            model.addAttribute("status", false);

            return "file-upload-status";
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            int count = 0;

            Map<String, ArrayList<Invoice>> mappedData = new HashMap<>();
            while ((line = br.readLine()) != null) {
                if(count != 0) {
                    String[] columns = line.split(delimiter);
                    Invoice invoice = new Invoice(columns[0], columns[1], columns[2], columns[3], columns[4], columns[5], columns[6]);

                    if(!mappedData.containsKey(invoice.getBuyer())) {
                        ArrayList<Invoice> al = new ArrayList<>();
                        al.add(invoice);
                        mappedData.put(invoice.getBuyer(), al);
                        continue;
                    }

                    mappedData.get(invoice.getBuyer()).add(invoice);
                }
                count++;
            }

            this.invoices = mappedData;
            model.addAttribute("status", true);

        } catch (Exception ex) {
            model.addAttribute("message", "An error occured while processing the CSV file.");
            model.addAttribute("status", false);
        }

        return "file-upload-status";
    }

    @GetMapping("/generate-csvs")
    public void downloadCsv(HttpServletResponse response) throws IOException {
        if(!this.invoices.isEmpty()) {
            FileCSVWriter.createCSVFiles(this.invoices);
            ZipEntry zipEntry = ZipUtils.downloadFiles("csv", response.getOutputStream());

            response.setContentLength((int) (zipEntry != null ? zipEntry.getSize() : 0));
            response.setContentType("application/zip");
            response.setHeader("Content-Disposition", "attachment; filename=archived-csvs.zip");
            response.addHeader("Pragma", "no-cache");
            response.addHeader("Expires", "0");
        }
    }

    @GetMapping("/generate-xmls")
    public void downloadXmls(HttpServletResponse response) throws IOException{
        if(!this.invoices.isEmpty()) {
            FileXMLWriter.createXMLFiles(this.invoices);
            ZipEntry zipEntry = ZipUtils.downloadFiles("xml", response.getOutputStream());

            response.setContentLength((int) (zipEntry != null ? zipEntry.getSize() : 0));
            response.setContentType("application/zip");
            response.setHeader("Content-Disposition", "attachment; filename=archived-xmls.zip");
            response.addHeader("Pragma", "no-cache");
            response.addHeader("Expires", "0");
        }
    }
}
