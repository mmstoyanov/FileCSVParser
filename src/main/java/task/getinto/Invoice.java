package task.getinto;

public class Invoice {

    private String buyer;

    private String invoice_status;

    private String invoice_due_date;

    private String invoice_number;

    private String invoice_amount;

    private String invoice_currency;

    private String supplier;

    public Invoice(
            String buyer,
            String invoice_status,
            String invoice_due_date,
            String invoice_number,
            String invoice_amount,
            String invoice_currency,
            String supplier
    ) {
        this.buyer = buyer;
        this.invoice_status = invoice_status;
        this.invoice_due_date =  invoice_due_date;
        this.invoice_number = invoice_number;
        this.invoice_amount = invoice_amount;
        this.invoice_currency = invoice_currency;
        this.supplier = supplier;
    }

    public String getBuyer() {
        return buyer;
    }

    public String getInvoice_status() {
        return invoice_status;
    }

    public String getInvoice_due_date() {
        return invoice_due_date;
    }

    public String getInvoice_number() {
        return invoice_number;
    }

    public String getInvoice_amount() {
        return invoice_amount;
    }

    public String getInvoice_currency() {
        return invoice_currency;
    }

    public String getSupplier() {
        return supplier;
    }
}
