package ba.unsa.etf.rma.rma20babicamina92.presenters;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.stream.Collectors;

import ba.unsa.etf.rma.rma20babicamina92.R;
import ba.unsa.etf.rma.rma20babicamina92.contracts.MainContract;
import ba.unsa.etf.rma.rma20babicamina92.models.FilterItem;
import ba.unsa.etf.rma.rma20babicamina92.models.Transaction;

public class MainPresenter implements MainContract.MainPresenter {
    private Date date;
    private MainContract.MainView mainActivity;
    private ArrayList<FilterItem>filterItems;
    private ArrayList<String> sortSpinnerItems;
    private ArrayList<Transaction> transactionArrayList;

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

        getTransactionListItems();
        mainActivity.setTransactionListItems(getTransactionsByDate());
    }

    private void getTransactionListItems() {

        transactionArrayList = new ArrayList<Transaction>(
                Arrays.asList(
                        new Transaction( new GregorianCalendar(2020, Calendar.FEBRUARY, 11).getTime(),
                                186, "title","description",12,
                                new GregorianCalendar(2015, Calendar.FEBRUARY, 11).getTime(),"INDIVIDUALPAYMENT"),
                        new Transaction( new GregorianCalendar(2020, Calendar.MAY, 11).getTime(),
                                1225, "title2","description",12,
                                new GregorianCalendar(2015, Calendar.FEBRUARY, 11).getTime(),"PURCHASE")
                )
        );

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

        ArrayList<Transaction> transactions = getTransactionsByDate();
        mainActivity.setTransactionListItems(transactions);
    }

    @Override
    public void datePickerCLickedLeft() {
        date.setMonth(date.getMonth() - 1);
        mainActivity.setMonthForTransactions(date);
        ArrayList<Transaction> transactions = getTransactionsByDate();
        mainActivity.setTransactionListItems(transactions);
    }

    private ArrayList<Transaction> getTransactionsByDate() {
        ArrayList<Transaction> transactions = new ArrayList<>();

        for(int i = 0; i < transactionArrayList.size(); i++) {
            if(transactionArrayList.get(i).getDate().getMonth() == date.getMonth() && transactionArrayList.get(i).getDate().getYear() == date.getYear()) {
                transactions.add(transactionArrayList.get(i));
            }
        }
        return transactions;
    }
}
