package ba.unsa.etf.rma.rma20babicamina92.presenters;

import java.util.ArrayList;
import java.util.Date;

import ba.unsa.etf.rma.rma20babicamina92.R;
import ba.unsa.etf.rma.rma20babicamina92.contracts.MainContract;
import ba.unsa.etf.rma.rma20babicamina92.models.FilterItem;

public class MainPresenter implements MainContract.MainPresenter {
    private Date date;
    private MainContract.MainView mainActivity;
    private ArrayList<FilterItem>filterItems;
    private ArrayList<String> sortSpinnerItems;

    public MainPresenter(MainContract.MainView mainActivity) {
        date = new Date();
        this.mainActivity = mainActivity;
    }

    public void initialize(){
        mainActivity.setMonthForTransactions(date);
        getFilterItems();
        mainActivity.setFilterBySpinnerItems(filterItems);

        getSortItems();
        mainActivity.setSortBySpinnerItems(sortSpinnerItems);
    }

    private void getSortItems() {
        sortSpinnerItems = new ArrayList<>();
        sortSpinnerItems.add("Sort by");
        sortSpinnerItems.add("Price - Ascending");
        sortSpinnerItems.add("Price - Descending");
        sortSpinnerItems.add("Title - Ascending");
        sortSpinnerItems.add("Title - Descending");
        sortSpinnerItems.add("Date - Ascending");
        sortSpinnerItems.add("Date - Descending");
    }

    private void getFilterItems() {
        filterItems = new ArrayList<FilterItem>();
        filterItems.add(new FilterItem("INDIVIDUALPAYMENT", R.drawable.individualpay));
        filterItems.add(new FilterItem("REGULARPAYMENT",R.drawable.regularpayment));
        filterItems.add(new FilterItem("PURCHASE",R.drawable.pursache));
        filterItems.add(new FilterItem("INDIVIDUALINCOME",R.drawable.individualpay)); // dodaj slikicuuu
        filterItems.add(new FilterItem("REGULARINCOME",R.drawable.individualpay)); //addd phootoooo
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
