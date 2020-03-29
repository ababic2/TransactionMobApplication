package ba.unsa.etf.rma.rma20babicamina92.utils;

import ba.unsa.etf.rma.rma20babicamina92.models.Transaction;

public interface Filter {
    boolean test(Transaction transaction);
}
