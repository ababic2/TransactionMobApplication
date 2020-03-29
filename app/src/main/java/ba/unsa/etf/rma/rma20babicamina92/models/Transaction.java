package ba.unsa.etf.rma.rma20babicamina92.models;

import java.util.Date;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transaction that = (Transaction) o;

        if (amount != that.amount) return false;
        if (transactionInterval != that.transactionInterval) return false;
        if (!Objects.equals(date, that.date)) return false;
        if (!Objects.equals(title, that.title)) return false;
        if (!Objects.equals(itemDescription, that.itemDescription))
            return false;
        if (!Objects.equals(endDate, that.endDate)) return false;
        return transactionType == that.transactionType;
    }

    @Override
    public int hashCode() {
        int result = date != null ? date.hashCode() : 0;
        result = 31 * result + amount;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (itemDescription != null ? itemDescription.hashCode() : 0);
        result = 31 * result + transactionInterval;
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (transactionType != null ? transactionType.hashCode() : 0);
        return result;
    }
}
