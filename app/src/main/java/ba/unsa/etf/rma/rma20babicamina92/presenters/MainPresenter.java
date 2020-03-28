package ba.unsa.etf.rma.rma20babicamina92.presenters;

import java.util.Date;

import ba.unsa.etf.rma.rma20babicamina92.contracts.MainContract;

public class MainPresenter implements MainContract.MainPresenter {
    private Date date;
    private MainContract.MainView mainActivity;

    public MainPresenter(MainContract.MainView mainActivity) {
        date = new Date();
        this.mainActivity = mainActivity;
        mainActivity.setMonthForTransactions(date);
    }

    @Override
    public void datePickerClickedRight() {
        date.setMonth(date.getMonth() + 1);
        mainActivity.setMonthForTransactions(date);
    }

    @Override
    public void datePickerCLickedLeft() {
        date.setMonth(date.getMonth() - 1);
        mainActivity.setMonthForTransactions(date);
    }
}
