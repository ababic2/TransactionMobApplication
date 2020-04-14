package ba.unsa.etf.rma.rma20babicamina92.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import ba.unsa.etf.rma.rma20babicamina92.R;
import ba.unsa.etf.rma.rma20babicamina92.models.Transaction;


public class TransactionDetailFragment extends Fragment {
    private Transaction oldTransaction, transaction;
    private EditText titleTextView, descriptionTextView, amountTextView,
            dateTextView, endDateTextView, transactionIntervalTextView;
    private Spinner typeSpinner;
    private Button saveButton, deleteButton;


    public TransactionDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_transaction_detail, container, false);
        findViewsById(view);
        return view;
    }



    private void findViewsById(View view) {
        typeSpinner = view.findViewById(R.id.spinner);
        titleTextView = view.findViewById(R.id.titleTextView);
        descriptionTextView = view.findViewById(R.id.descriptionTextView);
        amountTextView = view.findViewById(R.id.amountTextView);
        dateTextView = view.findViewById(R.id.dateTextView);
        endDateTextView = view.findViewById(R.id.endDateTextView);
        transactionIntervalTextView = view.findViewById(R.id.transactionIntervalTextView);
        saveButton = view.findViewById(R.id.saveButton);
        deleteButton = view.findViewById(R.id.deleteButton);
    }
}
