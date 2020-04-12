package ba.unsa.etf.rma.rma20babicamina92;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import ba.unsa.etf.rma.rma20babicamina92.adapters.FilterSpinnerAdapter;
import ba.unsa.etf.rma.rma20babicamina92.models.FilterItem;
import ba.unsa.etf.rma.rma20babicamina92.models.Transaction;
import ba.unsa.etf.rma.rma20babicamina92.providers.AdapterProvider;


public class TransactionListFragment extends Fragment {

    private static final String ARG_SORT_ITEMS = "sortItems";


    private Button leftDatePickerButton, rightDatePickerButton, addTransactionButton;
    private ListView listView;
    private Spinner filterSpinner;
    private Spinner sortSpinner;
    private TextView dateTextView;
    private EditText globalAmountTextView,limitTextView;

    private ArrayList<FilterItem> filterBySpinnerItems = new ArrayList<FilterItem>();
    private ArrayList<String> sortBySpinnerItems = new ArrayList<String>();
    private ArrayList<Transaction> transactionArrayList = new ArrayList<Transaction>();

    private FilterSpinnerAdapter filterSpinnerAdapter;
    private ArrayAdapter<String> sortSpinnerAdapter;

    public TransactionListFragment() {
        // Required empty public constructor
    }


    public static TransactionListFragment newInstance(ArrayList<String> sortBySpinnerItems) {
        TransactionListFragment fragment = new TransactionListFragment();
        Bundle args = new Bundle();
        args.putStringArrayList(ARG_SORT_ITEMS, sortBySpinnerItems);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.sortBySpinnerItems = getArguments().getStringArrayList(ARG_SORT_ITEMS);
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

        sortSpinnerAdapter = AdapterProvider.provideSortSpinnerAdapter(getActivity(), sortBySpinnerItems);
        sortSpinner.setAdapter(sortSpinnerAdapter);

        return view;
    }
}
