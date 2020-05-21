package ba.unsa.etf.rma.rma20babicamina92.fragments;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import ba.unsa.etf.rma.rma20babicamina92.MainActivity;
import ba.unsa.etf.rma.rma20babicamina92.R;
import ba.unsa.etf.rma.rma20babicamina92.adapters.FilterSpinnerAdapter;
import ba.unsa.etf.rma.rma20babicamina92.adapters.TransactionListAdapter;
import ba.unsa.etf.rma.rma20babicamina92.contracts.ListFragmentInterface;
import ba.unsa.etf.rma.rma20babicamina92.models.Account;
import ba.unsa.etf.rma.rma20babicamina92.models.FilterItem;
import ba.unsa.etf.rma.rma20babicamina92.models.Transaction;
import ba.unsa.etf.rma.rma20babicamina92.presenters.ListFragmentPresenter;
import ba.unsa.etf.rma.rma20babicamina92.providers.AdapterProvider;


public class TransactionListFragment extends Fragment implements ListFragmentInterface {

    private static final String ARG_SORT_ITEMS = "sortItems";
    private static final String ARG_MONTH = "monthOfTransaction";
    private static final String ARG_TRANSACTIONS = "transactions";
    private static final String ARG_FILTER_ITEMS = "filterItems";
    private static final String ARG_CURRENT_FILTER = "current_filter";


    private Button leftDatePickerButton, rightDatePickerButton, addTransactionButton;
    private ListView listView;
    private Spinner filterSpinner;
    private Spinner sortSpinner;
    private TextView dateTextView;
    private EditText globalAmountTextView,limitTextView;

    private ArrayList<FilterItem> filterBySpinnerItems = new ArrayList<FilterItem>();
    private ArrayList<String> sortBySpinnerItems = new ArrayList<String>();
    private ArrayList<Transaction> transactionArrayList = new ArrayList<Transaction>();
    private String monthOfTransaction;

    private MainActivity activity;
    private FilterSpinnerAdapter filterSpinnerAdapter;
    private ArrayAdapter<String> sortSpinnerAdapter;

    private TransactionListAdapter transactionListAdapter;

    /**
     * drugi dio
     */
    private ListFragmentPresenter listPresenter;

    public TransactionListFragment() {
        // Required empty public constructor
    }

    public static TransactionListFragment getInstance() {
        return new TransactionListFragment();
    }

    public static TransactionListFragment getInstance(
            ArrayList<FilterItem> filterBySpinnerItems, String monthOfTransaction,
            ArrayList<String> sortBySpinnerItems,
            ArrayList<Transaction> transactionArrayList,
            int currentFilterPosition) {
        TransactionListFragment fragment = new TransactionListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_FILTER_ITEMS,filterBySpinnerItems);
        args.putString(ARG_MONTH,monthOfTransaction);
        args.putStringArrayList(ARG_SORT_ITEMS, sortBySpinnerItems);
        args.putParcelableArrayList(ARG_TRANSACTIONS, transactionArrayList);
        args.putInt(ARG_CURRENT_FILTER,currentFilterPosition);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        filterBySpinnerItems = new ArrayList<>();
        transactionArrayList = new ArrayList<>();
        sortBySpinnerItems = new ArrayList<>();
        monthOfTransaction = "";
    }

    @Override
    public void onResume() {
        super.onResume();
        listPresenter.setCurrentlySelectedTransaction(null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_transaction_list, container, false);
        leftDatePickerButton = view.findViewById(R.id.leftDatePickerButton);
        dateTextView = view.findViewById(R.id.dateTextView);
        rightDatePickerButton = view.findViewById(R.id.rightDatePickerButton);
        addTransactionButton = view.findViewById(R.id.addTransactionButton);
        sortSpinner = (Spinner) view.findViewById(R.id.sortSpinner);
        filterSpinner = (Spinner) view.findViewById(R.id.filterSpinner);
        listView = (ListView) view.findViewById(R.id.transactionListView);
        globalAmountTextView = view.findViewById(R.id.globalAmountTextView);
        limitTextView = view.findViewById(R.id.limitTextView);

        activity = (MainActivity) getActivity();
        listPresenter = ListFragmentPresenter.getInstance();
        initViewData();
        listPresenter.init(this);


        return view;
    }

    private void initViewData() {
        sortSpinnerAdapter = AdapterProvider.provideSortSpinnerAdapter(getActivity(), sortBySpinnerItems);
        sortSpinner.setAdapter(sortSpinnerAdapter);
        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                listPresenter.setSortMethod((String) parent.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        filterSpinnerAdapter = new FilterSpinnerAdapter(getActivity(), filterBySpinnerItems);
        filterSpinner.setAdapter(filterSpinnerAdapter);
        filterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                listPresenter.setFilterMethod((FilterItem) parent.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        dateTextView.setText(monthOfTransaction);

        rightDatePickerButton.setOnClickListener(event-> listPresenter.datePickerClickedRight());

        leftDatePickerButton.setOnClickListener(event -> listPresenter.datePickerCLickedLeft());

        transactionListAdapter = new TransactionListAdapter((MainActivity) getActivity(),R.layout.transaction_list,transactionArrayList);
        listView.setAdapter(transactionListAdapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            if (!MainActivity.twoPane) {
                listPresenter.setCurrentlySelectedTransaction((Transaction) parent.getItemAtPosition(position));
                activity.clickedOnTransaction();
                return;
            }
            if (view.getBackground() == null) {
                for (int i = 0; i < listView.getChildCount(); i++) {
                    View child = listView.getChildAt(i);
                    child.setBackground(null);
                }
                view.setBackgroundColor(Color.GREEN);
                listPresenter.setCurrentlySelectedTransaction((Transaction) parent.getItemAtPosition(position));
            } else {
                for (int i = 0; i < listView.getChildCount(); i++) {
                    View child = listView.getChildAt(i);
                    child.setBackground(null);
                }
                listPresenter.setCurrentlySelectedTransaction(null);
            }
        });
        addTransactionButton.setOnClickListener(event -> {
            listPresenter.setCurrentlySelectedTransaction(null);
            activity.clickedOnTransaction();
        });
    }


    @Override
    public void setMonthForTransactions(Date date) {
        monthOfTransaction = new SimpleDateFormat("MMMM, yyyy", Locale.getDefault()).format(date);
        dateTextView.setText(monthOfTransaction);
    }

    @Override
    public void setTransactionListItems(ArrayList<Transaction> transactions) {
        transactionArrayList.clear();
        transactionArrayList.addAll(transactions);
        transactionListAdapter.notifyDataSetChanged();
        for (int i = 0; i < listView.getChildCount(); i++) {
            listView.getChildAt(i).setBackground(null);
        }
    }

    @Override
    public void setFilterItems(ArrayList<FilterItem> filterItems) {
        filterBySpinnerItems.clear();
        filterBySpinnerItems.addAll(filterItems);
        filterSpinnerAdapter.notifyDataSetChanged();
    }

    @Override
    public void setSortItems(ArrayList<String> sortItems) {
        sortBySpinnerItems.clear();
        sortBySpinnerItems.addAll(sortItems);
        sortSpinnerAdapter.notifyDataSetChanged();
    }

    @Override
    public void setAccountData(Account account) {
        globalAmountTextView.setText(String.format(Locale.getDefault(),"%.2f",account.getBudget()));
        limitTextView.setText(String.format(Locale.getDefault(),"%.2f",account.getTotalLimit()));
    }

    @Override
    public MainActivity getMainActivity() {
        return activity;
    }


}
