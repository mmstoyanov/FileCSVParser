package task.getinto.utils;

import javax.servlet.ServletOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtils {

    private static List<String> getFilenamesToArchive(String filetype){
        List<String> filenames = new ArrayList<>();
        File[] files = new File("tmp/"+filetype).listFiles((dir, name) -> name.endsWith("."+filetype));
        for (File file : files) {
            if (file.isFile()) {
                filenames.add(file.getName());
            }
        }

        return filenames;
    }

    private static void deleteFiles(List<String> filenames, String filetype) {
        for (String path : filenames) {
            File file = new File("tmp/" + filetype + "/" + path);
            if(file.exists()) {
                file.delete();
            }
        }
    }

    public static ZipEntry downloadFiles(String filetype, ServletOutputStream responseOutputStream) throws IOException {
        List<String> filenames = ZipUtils.getFilenamesToArchive(filetype);
        int BUFFER_SIZE = 1024;
        final ZipOutputStream zipOutputStream = new ZipOutputStream(responseOutputStream);
        ZipEntry zipEntry = null;
        InputStream inputStream = null;
        try {
            for (String path : filenames) {
                File file = new File("tmp/"+filetype+"/"+path);
                zipEntry = new ZipEntry(file.getName());

                inputStream = new FileInputStream(file);

                zipOutputStream.putNextEntry(zipEntry);
                byte[] bytes = new byte[BUFFER_SIZE];
                int length;
                while ((length = inputStream.read(bytes)) >= 0) {
                    zipOutputStream.write(bytes, 0, length);
                }
                file.deleteOnExit();
            }

            ZipUtils.deleteFiles(filenames, filetype);
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (zipOutputStream != null) {
                zipOutputStream.close();
            }
        }
        return zipEntry;
    }
}
