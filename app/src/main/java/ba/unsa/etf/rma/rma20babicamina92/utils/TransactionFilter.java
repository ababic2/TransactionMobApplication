package ba.unsa.etf.rma.rma20babicamina92.utils;

import ba.unsa.etf.rma.rma20babicamina92.models.Transaction;
import ba.unsa.etf.rma.rma20babicamina92.models.TransactionType;

public interface TransactionFilter {
    boolean test(TransactionType transactionType, Transaction transaction);
}
