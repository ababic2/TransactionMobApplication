package ba.unsa.etf.rma.rma20babicamina92.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

public class Transaction implements Comparable<Transaction>, Serializable, Parcelable {
    public static final Creator<Transaction> CREATOR = new Creator<Transaction>() {
        @Override
        public Transaction createFromParcel(Parcel in) {
            return new Transaction(in);
        }

        @Override
        public Transaction[] newArray(int size) {
            return new Transaction[size];
        }
    };

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(itemDescription);
        dest.writeSerializable(amount);
        dest.writeSerializable(date);
        dest.writeSerializable(endDate);
        if (transactionInterval == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(transactionInterval);
        }
        dest.writeSerializable(transactionType);
    }

    public Transaction(Parcel parcel) {

        title = parcel.readString();
        itemDescription = parcel.readString();
        amount = (BigDecimal) parcel.readSerializable();
        date = (Date) parcel.readSerializable();
        endDate = (Date) parcel.readSerializable();
        if (parcel.readByte() == 0) {
            transactionInterval = null;
        } else {
            transactionInterval = parcel.readInt();
        }
        transactionType = (TransactionType) parcel.readSerializable();
    }

    private Long id;
    private String title;
    private String itemDescription;
    private BigDecimal amount;
    private Date date;
    private Date endDate;
    private Integer transactionInterval;
    private TransactionType transactionType;

    public Transaction(Long id, String title, String itemDescription, BigDecimal amount, Date date, Date endDate, Integer transactionInterval, TransactionType transactionType) {
        this.id = id;
        this.title = title;
        this.itemDescription = itemDescription;
        this.amount = amount;
        this.date = date;
        this.endDate = endDate;
        this.transactionInterval = transactionInterval;
        this.transactionType = transactionType;
    }

    public Transaction(Date date, BigDecimal amount, String title, String itemDescription, Integer transactionInterval, Date endDate, TransactionType transactionType) {
        this.date = date;
        this.amount = amount;
        this.title = title;
        this.itemDescription = itemDescription;
        this.transactionInterval = transactionInterval;
        this.endDate = endDate;
        this.transactionType = transactionType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public String getType() {
        return transactionType.toString();
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
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

    public Integer getTransactionInterval() {
        return transactionInterval;
    }

    public void setTransactionInterval(Integer transactionInterval) {
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
        return "Transaction{" +
                "date=" + date +
                ", amount=" + amount +
                ", title='" + title + '\'' +
                ", itemDescription='" + itemDescription + '\'' +
                ", transactionInterval=" + transactionInterval +
                ", endDate=" + endDate +
                ", transactionType=" + transactionType +
                '}'+'\n';
    }

    @Override
    public int compareTo(Transaction o) {
        return amount.compareTo(o.amount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transaction that = (Transaction) o;

        if (amount.equals(that.amount)) return false;
        if (transactionInterval.equals(that.transactionInterval)) return false;
        if(date.getYear() != that.getDate().getYear()) return false;
        if(date.getMonth() != that.getDate().getMonth()) return false;
        if (!Objects.equals(title, that.title)) return false;
        if (!Objects.equals(itemDescription, that.itemDescription))
            return false;
        if(endDate.getYear() != that.getEndDate().getYear()) return false;
        if(endDate.getMonth() != that.getEndDate().getMonth()) return false;
        return transactionType.equals(that.transactionType);
    }

    @Override
    public int hashCode() {
        int result = date != null ? date.hashCode() : 0;
        result = 31 * result + amount.hashCode();
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (itemDescription != null ? itemDescription.hashCode() : 0);
        result = 31 * result + transactionInterval;
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (transactionType != null ? transactionType.hashCode() : 0);
        return result;
    }
}
