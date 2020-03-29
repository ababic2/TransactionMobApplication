package ba.unsa.etf.rma.rma20babicamina92.models;

import java.util.Date;

public class Transaction {
    private enum type {INDIVIDUALPAYMENT,  REGULARPAYMENT, PURCHASE, INDIVIDUALINCOME, REGULARINCOME};
    private Date date;
    private int amount;
    private String title;
    private String itemDescription;
    private int transactionInterval;
    private Date endDate;
    private type transactionType;

    public Transaction(Date date, int amount, String title, String itemDescription, int transactionInterval, Date endDate, String transactionType) {
        this.date = date;
        this.amount = amount;
        this.title = title;
        this.itemDescription = itemDescription;
        this.transactionInterval = transactionInterval;
        this.endDate = endDate;
        this.transactionType =  Enum.valueOf(type.class, transactionType);
    }

    public Date getDate() {
        return date;
    }

    public String getType() {
        return transactionType.toString();
    }

    public void setTransactionType(type transactionType) {
        this.transactionType = transactionType;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public int getTransactionInterval() {
        return transactionInterval;
    }

    public void setTransactionInterval(int transactionInterval) {
        this.transactionInterval = transactionInterval;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
