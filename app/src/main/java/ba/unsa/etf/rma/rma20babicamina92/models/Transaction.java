package ba.unsa.etf.rma.rma20babicamina92.models;

import java.util.Date;

public class Transaction implements Comparable<Transaction>{
    public enum Type {
        INDIVIDUALPAYMENT("INDIVIDUALPAYMENT"),
        REGULARPAYMENT("REGULARPAYMENT"),
        PURCHASE("PURCHASE"),
        INDIVIDUALINCOME("INDIVIDUALINCOME"),
        REGULARINCOME("REGULARINCOME"),
        ALL("ALL");

        Type(String method) {
        }
    };
    private Date date;
    private int amount;
    private String title;
    private String itemDescription;
    private int transactionInterval;
    private Date endDate;
    private Type transactionType;
    public Transaction(Date date, int amount, String title, String itemDescription, int transactionInterval, Date endDate, String transactionType) {
        this.date = date;
        this.amount = amount;
        this.title = title;
        this.itemDescription = itemDescription;
        this.transactionInterval = transactionInterval;
        this.endDate = endDate;
        this.transactionType =  Type.valueOf(transactionType);

    }

    public Date getDate() {
        return date;
    }

    public String getType() {
        return transactionType.toString();
    }

    public void setTransactionType(Type transactionType) {
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

    @Override
    public String toString() {
        return title + " "+date;
    }

    @Override
    public int compareTo(Transaction o) {
        return 0;
    }
}
