package ba.unsa.etf.rma.rma20babicamina92.contracts;

import java.util.ArrayList;
import java.util.Date;

import ba.unsa.etf.rma.rma20babicamina92.MainActivity;
import ba.unsa.etf.rma.rma20babicamina92.models.Account;
import ba.unsa.etf.rma.rma20babicamina92.models.FilterItem;
import ba.unsa.etf.rma.rma20babicamina92.models.Transaction;

public interface ListFragmentInterface {
    void setMonthForTransactions(Date date);

    void setTransactionListItems(ArrayList<Transaction> transactions);

    void setFilterItems(ArrayList<FilterItem> filterItems);

    void setSortItems(ArrayList<String> sortItems);

    void setAccountData(Account account);

    MainActivity getMainActivity();
}
