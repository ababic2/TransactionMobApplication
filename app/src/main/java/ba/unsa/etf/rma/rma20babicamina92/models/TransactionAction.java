package ba.unsa.etf.rma.rma20babicamina92.models;

import ba.unsa.etf.rma.rma20babicamina92.models.Transaction;

public class TransactionAction {
    private long id;
    private String name;
    private Transaction transaction;

    public TransactionAction(String name, Transaction transaction) {
        this.name = name;
        this.transaction = transaction;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
}
