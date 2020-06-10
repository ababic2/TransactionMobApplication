package ba.unsa.etf.rma.rma20babicamina92.presenters;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import ba.unsa.etf.rma.rma20babicamina92.MainActivity;
import ba.unsa.etf.rma.rma20babicamina92.R;
import ba.unsa.etf.rma.rma20babicamina92.contracts.ListFragmentInterface;
import ba.unsa.etf.rma.rma20babicamina92.interactor.AccountInteractor;
import ba.unsa.etf.rma.rma20babicamina92.interactor.TransactionInteractor;
import ba.unsa.etf.rma.rma20babicamina92.interactor.TransactionTypeInteractor;
import ba.unsa.etf.rma.rma20babicamina92.models.Account;
import ba.unsa.etf.rma.rma20babicamina92.models.AccountAction;
import ba.unsa.etf.rma.rma20babicamina92.models.FilterItem;
import ba.unsa.etf.rma.rma20babicamina92.models.MainModel;
import ba.unsa.etf.rma.rma20babicamina92.models.Transaction;
import ba.unsa.etf.rma.rma20babicamina92.models.TransactionAction;
import ba.unsa.etf.rma.rma20babicamina92.models.TransactionType;
import ba.unsa.etf.rma.rma20babicamina92.utils.Filter;
import ba.unsa.etf.rma.rma20babicamina92.utils.TransactionFilter;

public class ListFragmentPresenter {
    private static ListFragmentPresenter instance;

    private MainModel model;
    private ListFragmentInterface view;

    private Map<String, Comparator<Transaction>> comparatorMap;
    private Map<String, Filter> filterMap;
    private Date date;
    private TransactionType type;
    private String sortBy;

    public ArrayList<TransactionType> filterItems;
    private ArrayList<String> sortSpinnerItems;

    private Transaction currentlySelectedTransaction;
    private DetailFragmentPresenter detailFragmentPresenter;

    private TransactionFilter filter;

    public ListFragmentInterface getView() {
        return view;
    }

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
        type = new TransactionType(0, "All", R.mipmap.ic_six);
        sortBy = "Default";
        setMaps();
        getSortItems();
        getFilterItemsFromWeb();
        getAccountFromWeb();
        model = MainModel.getInstance();

        if (MainActivity.isConnected) {
            model.getTransactions().clear();
            new TransactionInteractor(view.getMainActivity(), this).execute("");
        } else {
            if (model.getTransactions().size() == 0) {
                ArrayList<TransactionAction> actions = MainActivity.bankResolver.getTransactionActions();
                for (TransactionAction action : actions) {
                    model.getTransactions().add(action.getTransaction());
                }
            }
            model.setTransactionActions(MainActivity.bankResolver.getTransactionActions());
            model.setAccountActions(MainActivity.bankResolver.getAccountAction());

        }
        view.setAccountData(model.getAccount());
        view.setFilterItems(filterItems);
        view.setSortItems(sortSpinnerItems);
        view.setMonthForTransactions(date);
        view.setTransactionListItems(getTransactions());
    }

    private void getFilterItemsFromWeb() {
        if (filterItems == null) {
            filterItems = new ArrayList<>();
        }
        if (MainActivity.isConnected) {
            new TransactionTypeInteractor(view.getMainActivity(), this).execute();
        } else {
            ArrayList<TransactionType> transactionTypes = new ArrayList<>();
            ArrayList<Integer> icons = new ArrayList<>(Arrays.asList(R.mipmap.ic_one, R.mipmap.ic_two, R.mipmap.ic_three, R.mipmap.ic_four, R.mipmap.ic_five, R.mipmap.ic_six));
            transactionTypes.add(new TransactionType(1, "Regular payment", icons.get(0)));
            transactionTypes.add(new TransactionType(2, "Regular income", icons.get(1)));
            transactionTypes.add(new TransactionType(3, "Purchase", icons.get(2)));
            transactionTypes.add(new TransactionType(4, "Individual income", icons.get(3)));
            transactionTypes.add(new TransactionType(5, "Regular income", icons.get(4)));
            transactionTypes.add(0, new TransactionType(0,"All", R.mipmap.ic_six));
            transactionTypes.add(new TransactionType(0,"All", R.mipmap.ic_six));
            setFilterItems(transactionTypes);
        }
    }

    private void getAccountFromWeb() {
        if (MainActivity.isConnected) {
            new AccountInteractor(view.getMainActivity(), this).execute();
        } else {
            if (model == null) {
                model = MainModel.getInstance();
            }

            if (model.getAccount() == null) {
                AccountAction accountAction = view.getMainActivity().bankResolver.getAccountAction();
                if (accountAction == null) {
                    Toast.makeText(view.getMainActivity(), "Mora se bar jednom konektovati na internet", Toast.LENGTH_LONG).show();
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {

                    }
                    view.getMainActivity().finish();
                } else {
                    model.setAccount(accountAction.getAccount());
                }
            }
        }
    }

    public void setAccount(Account account) {
        model.setAccount(account);
        view.setAccountData(account);
    }

    public ArrayList<TransactionType> getFilterItems() {
        return filterItems;
    }

    public void setFilterItems(ArrayList<TransactionType> filterItems) {
        this.filterItems.clear();
        this.filterItems.addAll(filterItems);
        view.setFilterItems(filterItems);
    }

    private void setMaps() {
        comparatorMap = new HashMap<String, Comparator<Transaction>>() {{
            put("Price - Ascending", Transaction::compareTo);
            put("Price - Descending", (a, b) -> b.compareTo(a));
            put("Title - Ascending", (a, b) -> a.getTitle().compareTo(b.getTitle()));
            put("Title - Descending", (a, b) -> b.getTitle().compareTo(a.getTitle()));
            put("Date - Ascending", (a, b) -> a.getDate().compareTo(b.getDate()));
            put("Date - Descending", (a, b) -> b.getDate().compareTo(a.getDate()));
            put("Default", (a, b) -> 0);
        }};

        filter = (transactionType, transaction) -> isInMonth(transaction) && (transactionType.equals(transaction.getTransactionType()) ^ transactionType.getName().toLowerCase().contains("all"));
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


    public void setFilterMethod(TransactionType filterItem) {
        type = filterItem;
        view.setTransactionListItems(getTransactions());
    }

    private ArrayList<Transaction> getTransactions() {
        ArrayList<Transaction> transactions = new ArrayList<>();
        ArrayList<Transaction> transactionArrayList = model.getTransactions();

        for (int i = 0; i < transactionArrayList.size(); i++) {
            if (filter.test(type, transactionArrayList.get(i))) {
                transactions.add(transactionArrayList.get(i));
            }
        }
        Collections.sort(transactions, comparatorMap.get(sortBy));
        return transactions;
    }

    private boolean isInMonth(Transaction transaction) {
        if (
                transaction.getTransactionType().getName().toLowerCase().contains("regular")
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

    public void createTransaction(Transaction transaction) {
        model.addTransaction(transaction);
    }

    public void updateTransaction(Transaction oldTransaction, Transaction transaction) {
        model.updateTransaction(oldTransaction, transaction);
    }
}
