package ba.unsa.etf.rma.rma20babicamina92.fragments;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import ba.unsa.etf.rma.rma20babicamina92.R;
import ba.unsa.etf.rma.rma20babicamina92.adapters.FilterSpinnerAdapter;
import ba.unsa.etf.rma.rma20babicamina92.exceptions.InvalidFieldValueException;
import ba.unsa.etf.rma.rma20babicamina92.models.FilterItem;
import ba.unsa.etf.rma.rma20babicamina92.models.Transaction;
import ba.unsa.etf.rma.rma20babicamina92.presenters.DetailFragmentPresenter;


public class TransactionDetailFragment extends Fragment {
    private Drawable defaultBackground;

    private Transaction oldTransaction, transaction;
    private EditText titleTextView, descriptionTextView, amountTextView,
            dateTextView, endDateTextView, transactionIntervalTextView;
    private Spinner typeSpinner;
    private Button saveButton, deleteButton;

    private FilterSpinnerAdapter filterSpinnerAdapter;
    private ArrayList<FilterItem> filterBySpinnerItems;

    private DetailFragmentPresenter presenter;


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
        presenter = DetailFragmentPresenter.getInstance();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_transaction_detail, container, false);
        findViewsById(view);
        presenter.init(this);

        filterBySpinnerItems = new ArrayList<>();
        filterBySpinnerItems.add(new FilterItem("ALL",android.R.drawable.gallery_thumb));
        filterBySpinnerItems.add(new FilterItem("INDIVIDUALPAYMENT", R.drawable.regularpayment));
        filterBySpinnerItems.add(new FilterItem("REGULARPAYMENT",R.drawable.regularpayment));
        filterBySpinnerItems.add(new FilterItem("PURCHASE",R.drawable.purchase));
        filterBySpinnerItems.add(new FilterItem("INDIVIDUALINCOME",R.drawable.individualpay));
        filterBySpinnerItems.add(new FilterItem("REGULARINCOME",R.drawable.individualpay));
        filterSpinnerAdapter = new FilterSpinnerAdapter(getActivity(), filterBySpinnerItems);
        typeSpinner.setAdapter(filterSpinnerAdapter);
        if (presenter.getTransaction() != null) {
            setInitialState(presenter.getTransaction());
        } else {
            deleteButton.setEnabled(false);
        }

        deleteButton.setOnClickListener(v -> {
            presenter.deleteTransaction(oldTransaction);
        });

        return view;
    }

    public void refresh() {
        Transaction transaction = presenter.getTransaction();
        if (transaction != null) {
            setInitialState(transaction);
            deleteButton.setEnabled(true);
        } else {
            deleteButton.setEnabled(false);
            titleTextView.setText(null);
            titleTextView.setBackground(defaultBackground);
            descriptionTextView.setText(null);
            descriptionTextView.setBackground(defaultBackground);
            amountTextView.setText(null);
            amountTextView.setBackground(defaultBackground);
            dateTextView.setText(null);
            dateTextView.setBackground(defaultBackground);
            endDateTextView.setText(null);
            endDateTextView.setBackground(defaultBackground);
            transactionIntervalTextView.setText(null);
            transactionIntervalTextView.setBackground(defaultBackground);
            amountTextView.setText(null);
            amountTextView.setBackground(defaultBackground);
            typeSpinner.setSelection(0);
        }
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
        defaultBackground = titleTextView.getBackground();
    }

    private void setInitialState(Transaction transaction) {
        oldTransaction = transaction;
        titleTextView.setText(oldTransaction.getTitle());
        descriptionTextView.setText(oldTransaction.getItemDescription());
        amountTextView.setText(String.format(Locale.getDefault(),"%.2f",oldTransaction.getAmount()));
        String displayDate = new SimpleDateFormat("MMMM, yyyy", Locale.getDefault()).format(oldTransaction.getDate());
        dateTextView.setText(displayDate);
        String displayEndDate = new SimpleDateFormat("MMMM, yyyy", Locale.getDefault()).format(oldTransaction.getEndDate());
        endDateTextView.setText(displayEndDate);
        transactionIntervalTextView.setText(String.format(Locale.getDefault(),"%d",oldTransaction.getTransactionInterval()));
        typeSpinner.setSelection(
                filterBySpinnerItems.indexOf(
                        new FilterItem(oldTransaction.getTransactionType().toString(), R.drawable.individualpay)
                )
        );
        titleTextView.addTextChangedListener(generateTextWatcher(titleTextView));
        descriptionTextView.addTextChangedListener(generateTextWatcher(descriptionTextView));
        amountTextView.addTextChangedListener(generateTextWatcher(amountTextView));
        dateTextView.addTextChangedListener(generateTextWatcher(dateTextView));
        endDateTextView.addTextChangedListener(generateTextWatcher(endDateTextView));
        transactionIntervalTextView.addTextChangedListener(generateTextWatcher(transactionIntervalTextView));

    }

    private TextWatcher generateTextWatcher(EditText field) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                field.setBackgroundColor(Color.GREEN);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
    }


    public void validateFields() throws InvalidFieldValueException {
        FilterItem filterItem = (FilterItem) typeSpinner.getSelectedItem();
        if (filterItem.getFilterName().contains("ALL")) {
            throw new InvalidFieldValueException("Type is not set");
        }
        if (titleTextView.getText().toString().length() == 0) {
            titleTextView.setBackgroundColor(Color.RED);
            throw new InvalidFieldValueException("Title is not set");
        }

        try {
            new BigDecimal(amountTextView.getText().toString());
        }catch(Exception ignored){
            amountTextView.setBackgroundColor(Color.RED);
            throw new InvalidFieldValueException("Amount is not valid");
        }
        try{
            new SimpleDateFormat("MMMM, yyyy", Locale.getDefault()).parse(dateTextView.getText().toString());
        } catch (ParseException e) {
            dateTextView.setBackgroundColor(Color.RED);
            throw new InvalidFieldValueException("Date is not valid");
        }

        if (!filterItem.getFilterName().contains("INDIVIDUAL")
                && !filterItem.getFilterName().contains("PURCHASE")) {
            try{
                new SimpleDateFormat("MMMM, yyyy", Locale.getDefault()).parse(endDateTextView.getText().toString());
            } catch (ParseException e) {
                endDateTextView.setBackgroundColor(Color.RED);
                throw new InvalidFieldValueException("End date is not valid");
            }
            try {
                Integer.parseInt(transactionIntervalTextView.getText().toString());
            } catch (Exception ignored) {
                transactionIntervalTextView.setBackgroundColor(Color.RED);
                throw new InvalidFieldValueException("Transaction interval is not valid");
            }
        }

        if (!filterItem.getFilterName().contains("INCOME")) {
            if (descriptionTextView.getText().toString().length() == 0) {
                descriptionTextView.setBackgroundColor(Color.RED);
                throw new InvalidFieldValueException("Description is not set");
            }
        }

    }
}
