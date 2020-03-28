package ba.unsa.etf.rma.rma20babicamina92.contracts;

import java.util.ArrayList;
import java.util.Date;

import ba.unsa.etf.rma.rma20babicamina92.models.FilterItem;

public class MainContract {
    public interface MainView {
        void setMonthForTransactions(Date date);
        void setFilterBySpinnerItems(ArrayList<FilterItem> filterItems);

        void setSortBySpinnerItems(ArrayList<String> sortSpinnerItems);
    }

    public interface MainPresenter {
        void initialize();

        void datePickerClickedRight();

        void datePickerCLickedLeft();
    }
}
