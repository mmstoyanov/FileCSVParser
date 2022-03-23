package task.getinto.utils;

import task.getinto.Invoice;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.Map;

public class FileXMLWriter {

    public static void createXMLFiles(Map<String, ArrayList<Invoice>> invoices) {
        try {
            for (String BuyerName : invoices.keySet()) {
                createXMLContent(BuyerName, invoices.get(BuyerName));
            }
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    private static void createXMLContent(String BuyerName, ArrayList<Invoice> invoices) throws ParserConfigurationException, TransformerException {

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement("invoices");
        doc.appendChild(rootElement);

        int counter = 1;
        for(Invoice row : invoices) {

            Element invoice = doc.createElement("invoice");
            rootElement.appendChild(invoice);
            invoice.setAttribute("id", String.valueOf(counter));

            Element buyer = doc.createElement("buyer");
            buyer.setTextContent(row.getBuyer());
            invoice.appendChild(buyer);

            Element invoice_status = doc.createElement("invoice_status");
            invoice_status.setTextContent(row.getInvoice_status());
            invoice.appendChild(invoice_status);

            Element invoice_due_date = doc.createElement("invoice_due_date");
            invoice_due_date.setTextContent(row.getInvoice_due_date());
            invoice.appendChild(invoice_due_date);

            Element invoice_number = doc.createElement("invoice_number");
            invoice_number.setTextContent(row.getInvoice_number());
            invoice.appendChild(invoice_number);

            Element invoice_amount = doc.createElement("invoice_amount");
            invoice_amount.setTextContent(row.getInvoice_amount());
            invoice.appendChild(invoice_amount);

            Element invoice_currency = doc.createElement("invoice_currency");
            invoice_currency.setTextContent(row.getInvoice_currency());
            invoice.appendChild(invoice_currency);

            Element supplier = doc.createElement("supplier");
            supplier.setTextContent(row.getSupplier());
            invoice.appendChild(supplier);

            counter++;
        }

        writeXml(doc, BuyerName);
    }

    private static void writeXml(Document doc, String BuyerName) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File("tmp/xml/" +BuyerName+".xml"));
        transformer.transform(source, result);
    }
}
