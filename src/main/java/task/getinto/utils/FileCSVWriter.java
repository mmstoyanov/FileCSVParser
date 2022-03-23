package task.getinto.utils;

import task.getinto.Invoice;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;

public class FileCSVWriter {

    public static void createCSVFiles(Map<String, ArrayList<Invoice>> invoices) {
        for(String BuyerName : invoices.keySet()) {
            Writer writer = null;
            try {
                writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("tmp/csv/" + BuyerName + ".csv"), "utf-8"));
                writer.write("buyer,invoice_status,invoice_due_date,invoice_number,invoice_amount,invoice_currency,supplier \n");
                for (Invoice invoice : invoices.get(BuyerName)) {
                    writer.write(invoice.getBuyer()
                            + "," + invoice.getInvoice_status()
                            + "," + invoice.getInvoice_due_date()
                            + "," + invoice.getInvoice_number()
                            + "," + invoice.getInvoice_amount()
                            + "," + invoice.getInvoice_currency()
                            + "," + invoice.getSupplier()
                            + "\n");
                }
            } catch (IOException ex) {
                System.out.println(ex);
                // Report
            } finally {
                try {
                    writer.close();
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }
        }
    }
}
