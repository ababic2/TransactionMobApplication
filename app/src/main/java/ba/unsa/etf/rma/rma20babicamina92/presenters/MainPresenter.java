package ba.unsa.etf.rma.rma20babicamina92.presenters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import ba.unsa.etf.rma.rma20babicamina92.R;
import ba.unsa.etf.rma.rma20babicamina92.contracts.MainContract;
import ba.unsa.etf.rma.rma20babicamina92.models.FilterItem;
import ba.unsa.etf.rma.rma20babicamina92.models.Transaction;
import ba.unsa.etf.rma.rma20babicamina92.models.Transaction.Type;
import ba.unsa.etf.rma.rma20babicamina92.utils.Filter;

public class MainPresenter implements MainContract.MainPresenter {
    private Map<String, Comparator<Transaction>> comparatorMap;
    private Map<String, Filter> filterMap;
    private Date date;
    private Type type;
    private String sortBy;

    private MainContract.MainView mainActivity;
    private ArrayList<FilterItem>filterItems;
    private ArrayList<String> sortSpinnerItems;
    private ArrayList<Transaction> transactionArrayList;

    public MainPresenter(MainContract.MainView mainActivity) {
        date = new Date();
        date.setDate(1);
        sortBy = "Default";
        type = Type.ALL;
        comparatorMap = new HashMap<String, Comparator<Transaction>>() {{
            put("Price - Ascending", (a,b)->Integer.compare(a.getAmount(),b.getAmount()));
            put("Price - Descending", (a,b)->Integer.compare(b.getAmount(),a.getAmount()));
            put("Title - Ascending", (a,b)->a.getTitle().compareTo(b.getTitle()));
            put("Title - Descending", (a,b)->b.getTitle().compareTo(a.getTitle()));
            put("Date - Ascending", (a,b)->a.getDate().compareTo(b.getDate()));
            put("Date - Descending", (a,b)->b.getDate().compareTo(a.getDate()));
            put("Default", (a,b)->1);
        }};

        filterMap =  new HashMap<String, Filter>() {{
            put(
                    Type.INDIVIDUALPAYMENT.toString(),
                    (a)->isInMonth(a) && a.getType().equals(Type.INDIVIDUALPAYMENT.toString())
            );
            put(
                    Type.REGULARPAYMENT.toString(),
                    (a)->isInMonth(a) && a.getType().equals(Type.REGULARPAYMENT.toString())
            );
            put(
                    Type.PURCHASE.toString(),
                    (a)->isInMonth(a) && a.getType().equals(Type.PURCHASE.toString())
            );
            put(
                    Type.INDIVIDUALINCOME.toString(),
                    (a) -> isInMonth(a) && a.getType().equals(Type.INDIVIDUALINCOME.toString())
            );
            put(
                    Type.REGULARINCOME.toString(),
                    (a)->isInMonth(a) && a.getType().equals(Type.REGULARINCOME.toString())
            );
            put(
                    Type.ALL.toString(),
                    (a)->isInMonth(a)
            );

        }};

        this.mainActivity = mainActivity;
    }

    public void initialize(){

        mainActivity.setMonthForTransactions(date);
        getFilterItems();
        mainActivity.setFilterBySpinnerItems(filterItems);

        getSortItems();
        mainActivity.setSortBySpinnerItems(sortSpinnerItems);

        getTransactionListItems();
        mainActivity.setTransactionListItems(getTransactions());
    }

    private void getTransactionListItems() {

        transactionArrayList = new ArrayList<Transaction>(
                Arrays.asList(
                        new Transaction(
                                new GregorianCalendar(
                                        2020,
                                        Calendar.FEBRUARY,
                                        11
                                ).getTime(),
                                186,
                                "lijekovi",
                                "description",
                                12,
                                new GregorianCalendar(
                                        2020,
                                        Calendar.FEBRUARY,
                                        11
                                ).getTime(),
                                "INDIVIDUALPAYMENT"),
                        new Transaction(
                                new GregorianCalendar(
                                        2020,
                                        Calendar.FEBRUARY,
                                        22
                                ).getTime(),
                                200,
                                "kompjuteri",
                                "description",
                                12,
                                new GregorianCalendar(
                                        2020,
                                        Calendar.FEBRUARY,
                                        11
                                ).getTime(),
                                "REGULARPAYMENT"),
                        new Transaction(
                                new GregorianCalendar(
                                        2020,
                                        Calendar.APRIL,
                                        11
                                ).getTime(),
                                1225,
                                "title2",
                                "description",
                                12,
                                new GregorianCalendar(
                                        2020,
                                        Calendar.MAY,
                                        11
                                ).getTime(),
                                "PURCHASE")
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
        filterItems.add(new FilterItem("ALL",R.drawable.individualpay));
        filterItems.add(new FilterItem("INDIVIDUALPAYMENT", R.drawable.individualpay));
        filterItems.add(new FilterItem("REGULARPAYMENT",R.drawable.regularpayment));
        filterItems.add(new FilterItem("PURCHASE",R.drawable.purchase));
        filterItems.add(new FilterItem("INDIVIDUALINCOME",R.drawable.individualpay)); // dodaj slikicuuu
        filterItems.add(new FilterItem("REGULARINCOME",R.drawable.individualpay)); //addd phootoooo
        filterItems.add(new FilterItem("ALL",android.R.drawable.gallery_thumb)); //addd phootoooo
    }

    @Override
    public void datePickerClickedRight() {
        date.setMonth(date.getMonth() + 1);
        mainActivity.setMonthForTransactions(date);

        ArrayList<Transaction> transactions = getTransactions();
        mainActivity.setTransactionListItems(transactions);
    }

    @Override
    public void datePickerCLickedLeft() {
        date.setMonth(date.getMonth() - 1);
        mainActivity.setMonthForTransactions(date);
        ArrayList<Transaction> transactions = getTransactions();
        mainActivity.setTransactionListItems(transactions);
    }

    @Override
    public void setSortMethod(String sortMethod) {
        sortBy = sortMethod;
        mainActivity.setTransactionListItems(getTransactions());
    }

    @Override
    public void setFilterMethod(FilterItem filterItem) {
        type = Type.valueOf(filterItem.getFilterName());
        mainActivity.setTransactionListItems(getTransactions());
    }

    private ArrayList<Transaction> getTransactions() {
        ArrayList<Transaction> transactions = new ArrayList<>();
        System.out.println(this.transactionArrayList);

        for(int i = 0; i < transactionArrayList.size(); i++) {
            if(filterMap.get(type.toString()).test(transactionArrayList.get(i))) {
                transactions.add(transactionArrayList.get(i));
            }
        }
        Collections.sort(transactions, comparatorMap.get(sortBy));
        System.out.println(transactions);
        return transactions;
    }

    private boolean isInMonth(Transaction transaction) {
        if (
                transaction.getType().equals(Type.REGULARINCOME.toString()) ||
                        transaction.getType().equals(Type.REGULARPAYMENT.toString())
        ) {
            return transaction.getDate().before(date) && transaction.getEndDate().after(date) ||
                    (transaction.getDate().getMonth() == date.getMonth() && transaction.getDate().getYear() == date.getYear()) ||
                    (transaction.getEndDate().getMonth() == date.getMonth() && transaction.getEndDate().getYear() == date.getYear());
        } else {
            return transaction.getDate().getMonth() == date.getMonth() && transaction.getDate().getYear() == date.getYear();
        }
    }
}
