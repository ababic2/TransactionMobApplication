package ba.unsa.etf.rma.rma20babicamina92;

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

import java.util.ArrayList;

import ba.unsa.etf.rma.rma20babicamina92.adapters.FilterSpinnerAdapter;
import ba.unsa.etf.rma.rma20babicamina92.adapters.TransactionListAdapter;
import ba.unsa.etf.rma.rma20babicamina92.models.FilterItem;
import ba.unsa.etf.rma.rma20babicamina92.models.Transaction;
import ba.unsa.etf.rma.rma20babicamina92.providers.AdapterProvider;
import ba.unsa.etf.rma.rma20babicamina92.providers.ListenerProvider;


public class TransactionListFragment extends Fragment {

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
    private int currentFilterPosition=0;

    private FilterSpinnerAdapter filterSpinnerAdapter;
    private ArrayAdapter<String> sortSpinnerAdapter;

    private MainFragmentActivity mainFragmentActivity;
    private TransactionListAdapter transactionListAdapter;

    public TransactionListFragment() {
        // Required empty public constructor
    }


    public static TransactionListFragment newInstance(
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
        if (getArguments() != null) {
            this.filterBySpinnerItems = getArguments().getParcelableArrayList(ARG_FILTER_ITEMS);
            this.monthOfTransaction = getArguments().getString(ARG_MONTH);
            this.sortBySpinnerItems = getArguments().getStringArrayList(ARG_SORT_ITEMS);
            this.transactionArrayList = getArguments().getParcelableArrayList(ARG_TRANSACTIONS);
            this.currentFilterPosition = getArguments().getInt(ARG_CURRENT_FILTER);
        }


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

        mainFragmentActivity = (MainFragmentActivity) getActivity();
        initViewData();


        return view;
    }

    private void initViewData() {
        sortSpinnerAdapter = AdapterProvider.provideSortSpinnerAdapter(getActivity(), sortBySpinnerItems);
        sortSpinner.setAdapter(sortSpinnerAdapter);

        filterSpinnerAdapter = new FilterSpinnerAdapter(getActivity(), filterBySpinnerItems);
        filterSpinner.setAdapter(filterSpinnerAdapter);
//        filterSpinner.setOnItemSelectedListener(ListenerProvider.provideFilterSpinnerListener(getActivity()));
        filterSpinner.setSelection(currentFilterPosition);
        filterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println(position);
                System.out.println(currentFilterPosition);
                System.out.println(parent.getSelectedItem());
                if (position != currentFilterPosition) {
                    currentFilterPosition = position;
                    mainFragmentActivity.onFilterSelect((FilterItem) parent.getSelectedItem());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        dateTextView.setText(monthOfTransaction);
        rightDatePickerButton.setOnClickListener(mainFragmentActivity::onRightClicked);
        leftDatePickerButton.setOnClickListener(mainFragmentActivity::onLeftClicked);

        transactionListAdapter = new TransactionListAdapter((MainActivity) getActivity(),R.layout.transaction_list,transactionArrayList);
        listView.setAdapter(transactionListAdapter);
    }

    public int getCurrentFilterItem() {
        return currentFilterPosition;
    }

    public interface MainFragmentActivity {
        void onRightClicked(View view);

        void onLeftClicked(View view);

        void onFilterSelect(FilterItem filterItem);
    }

}
