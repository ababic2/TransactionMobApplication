package ba.unsa.etf.rma.rma20babicamina92.presenters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import ba.unsa.etf.rma.rma20babicamina92.R;
import ba.unsa.etf.rma.rma20babicamina92.contracts.ListFragmentInterface;
import ba.unsa.etf.rma.rma20babicamina92.models.FilterItem;
import ba.unsa.etf.rma.rma20babicamina92.models.MainModel;
import ba.unsa.etf.rma.rma20babicamina92.models.Transaction;
import ba.unsa.etf.rma.rma20babicamina92.utils.Filter;

public class ListFragmentPresenter {
    private static ListFragmentPresenter instance;

    private MainModel model;
    private ListFragmentInterface view;

    private Map<String, Comparator<Transaction>> comparatorMap;
    private Map<String, Filter> filterMap;
    private Date date;
    private Transaction.Type type;
    private String sortBy;

    private ArrayList<FilterItem> filterItems;
    private ArrayList<String> sortSpinnerItems;

    private Transaction currentlySelectedTransaction;
    private DetailFragmentPresenter detailFragmentPresenter;

    private ListFragmentPresenter() {

    }

    public static ListFragmentPresenter getInstance() {
        if (instance == null) {
            instance = new ListFragmentPresenter();
        }
        return instance;
    }

    public Transaction getCurrentlySelectedTransaction() {
        return currentlySelectedTransaction;
    }

    public void setCurrentlySelectedTransaction(Transaction currentlySelectedTransaction) {
        this.currentlySelectedTransaction = currentlySelectedTransaction;
        detailFragmentPresenter.refresh();
    }

    public void init(ListFragmentInterface view) {
        this.view = view;
        detailFragmentPresenter = DetailFragmentPresenter.getInstance();
        date = new Date();
        date.setDate(1);
        type = Transaction.Type.ALL;
        sortBy = "Default";
        setMaps();
        getSortItems();
        getFilterItems();
        model = MainModel.getInstance();
        view.setAccountData(model.getAccount());
        view.setFilterItems(filterItems);
        view.setSortItems(sortSpinnerItems);
        view.setMonthForTransactions(date);
        view.setTransactionListItems(getTransactions());
    }

    private void setMaps() {
        comparatorMap = new HashMap<String, Comparator<Transaction>>() {{
            put("Price - Ascending", Transaction::compareTo);
            put("Price - Descending", (a, b) -> b.compareTo(a));
            put("Title - Ascending", (a, b) -> a.getTitle().compareTo(b.getTitle()));
            put("Title - Descending", (a, b) -> b.getTitle().compareTo(a.getTitle()));
            put("Date - Ascending", (a, b) -> a.getDate().compareTo(b.getDate()));
            put("Date - Descending", (a, b) -> b.getDate().compareTo(a.getDate()));
            put("Default", (a, b) -> 1);
        }};

        filterMap = new HashMap<String, Filter>() {{
            put(
                    Transaction.Type.INDIVIDUALPAYMENT.toString(),
                    (a) -> isInMonth(a) && a.getType().equals(Transaction.Type.INDIVIDUALPAYMENT.toString())
            );
            put(
                    Transaction.Type.REGULARPAYMENT.toString(),
                    (a) -> isInMonth(a) && a.getType().equals(Transaction.Type.REGULARPAYMENT.toString())
            );
            put(
                    Transaction.Type.PURCHASE.toString(),
                    (a) -> isInMonth(a) && a.getType().equals(Transaction.Type.PURCHASE.toString())
            );
            put(
                    Transaction.Type.INDIVIDUALINCOME.toString(),
                    (a) -> isInMonth(a) && a.getType().equals(Transaction.Type.INDIVIDUALINCOME.toString())
            );
            put(
                    Transaction.Type.REGULARINCOME.toString(),
                    (a) -> isInMonth(a) && a.getType().equals(Transaction.Type.REGULARINCOME.toString())
            );
            put(
                    Transaction.Type.ALL.toString(),
                    (a) -> isInMonth(a)
            );

        }};
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
        filterItems.add(new FilterItem("ALL", R.drawable.individualpay));
        filterItems.add(new FilterItem("INDIVIDUALPAYMENT", R.drawable.regularpayment));
        filterItems.add(new FilterItem("REGULARPAYMENT", R.drawable.regularpayment));
        filterItems.add(new FilterItem("PURCHASE", R.drawable.purchase));
        filterItems.add(new FilterItem("INDIVIDUALINCOME", R.drawable.individualpay)); // dodaj slikicuuu
        filterItems.add(new FilterItem("REGULARINCOME", R.drawable.individualpay)); //addd phootoooo
        filterItems.add(new FilterItem("ALL", android.R.drawable.gallery_thumb)); //addd phootoooo
    }


    public void datePickerClickedRight() {
        date.setMonth(date.getMonth() + 1);
        view.setMonthForTransactions(date);

        view.setTransactionListItems(getTransactions());
    }


    public void datePickerCLickedLeft() {
        date.setMonth(date.getMonth() - 1);
        view.setMonthForTransactions(date);
        ArrayList<Transaction> transactions = getTransactions();
        view.setTransactionListItems(transactions);
    }


    public void setSortMethod(String sortMethod) {
        sortBy = sortMethod;
        view.setTransactionListItems(getTransactions());
    }


    public void setFilterMethod(FilterItem filterItem) {
        type = Transaction.Type.valueOf(filterItem.getFilterName());
        view.setTransactionListItems(getTransactions());
    }

    private ArrayList<Transaction> getTransactions() {
        ArrayList<Transaction> transactions = new ArrayList<>();
        ArrayList<Transaction> transactionArrayList = model.getTransactions();
        for (int i = 0; i < transactionArrayList.size(); i++) {
            if (filterMap.get(type.toString()).test(transactionArrayList.get(i))) {
                transactions.add(transactionArrayList.get(i));
            }
        }
        Collections.sort(transactions, comparatorMap.get(sortBy));
        return transactions;
    }

    private boolean isInMonth(Transaction transaction) {
        if (
                transaction.getType().equals(Transaction.Type.REGULARINCOME.toString()) ||
                        transaction.getType().equals(Transaction.Type.REGULARPAYMENT.toString())
        ) {
            return transaction.getDate().before(date) && transaction.getEndDate().after(date) ||
                    (transaction.getDate().getMonth() == date.getMonth() && transaction.getDate().getYear() == date.getYear()) ||
                    (transaction.getEndDate().getMonth() == date.getMonth() && transaction.getEndDate().getYear() == date.getYear());
        } else {
            return transaction.getDate().getMonth() == date.getMonth() && transaction.getDate().getYear() == date.getYear();
        }
    }

    public void deleteTransaction(Transaction transaction) {
        model.deleteTransaction(transaction);
        currentlySelectedTransaction = null;
        view.setTransactionListItems(getTransactions());
        detailFragmentPresenter.refresh();
    }
}
