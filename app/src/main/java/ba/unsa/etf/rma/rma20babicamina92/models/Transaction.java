package ba.unsa.etf.rma.rma20babicamina92.models;

import java.util.Date;

public class Transaction {
    private Date date;
    private int amount;
    private String title;
    private enum type {INDIVIDUALPAYMENT,  REGULARPAYMENT, PURCHASE, INDIVIDUALINCOME, REGULARINCOME};
    private String itemDescription;
    private int transactionInterval;
    private Date endDate;
}
