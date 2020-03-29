package ba.unsa.etf.rma.rma20babicamina92.contracts;

import java.util.ArrayList;
import java.util.Date;

import ba.unsa.etf.rma.rma20babicamina92.models.FilterItem;
import ba.unsa.etf.rma.rma20babicamina92.models.Transaction;

public class MainContract {
    public interface MainView {
        void setMonthForTransactions(Date date);
        void setFilterBySpinnerItems(ArrayList<FilterItem> filterItems);

        void setSortBySpinnerItems(ArrayList<String> sortSpinnerItems);

        void setTransactionListItems(ArrayList<Transaction> transactionArrayList);

        void filterTransactionListByDate();

        void filterTransactionListByType();
    }

    public interface MainPresenter {
        void initialize();

        void datePickerClickedRight();

        void datePickerCLickedLeft();
    }
}
