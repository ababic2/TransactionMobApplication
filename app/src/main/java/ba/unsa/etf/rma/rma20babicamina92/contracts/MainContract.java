package ba.unsa.etf.rma.rma20babicamina92.contracts;

import java.util.Date;

public class MainContract {
    public interface MainView {
        void setMonthForTransactions(Date date);
    }

    public interface MainPresenter {
        void datePickerClickedRight();

        void datePickerCLickedLeft();
    }
}
