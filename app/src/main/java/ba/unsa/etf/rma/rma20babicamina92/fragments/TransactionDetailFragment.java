package ba.unsa.etf.rma.rma20babicamina92.fragments;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import ba.unsa.etf.rma.rma20babicamina92.MainActivity;
import ba.unsa.etf.rma.rma20babicamina92.R;
import ba.unsa.etf.rma.rma20babicamina92.adapters.FilterSpinnerAdapter;
import ba.unsa.etf.rma.rma20babicamina92.exceptions.InvalidFieldValueException;
import ba.unsa.etf.rma.rma20babicamina92.models.FilterItem;
import ba.unsa.etf.rma.rma20babicamina92.models.MainModel;
import ba.unsa.etf.rma.rma20babicamina92.models.Transaction;
import ba.unsa.etf.rma.rma20babicamina92.models.TransactionType;
import ba.unsa.etf.rma.rma20babicamina92.presenters.DetailFragmentPresenter;
import ba.unsa.etf.rma.rma20babicamina92.presenters.ListFragmentPresenter;


public class TransactionDetailFragment extends Fragment {
    private Drawable defaultBackground;

    private Transaction oldTransaction, transaction;
    private EditText titleTextView, descriptionTextView, amountTextView,
            dateTextView, endDateTextView, transactionIntervalTextView;
    private Spinner typeSpinner;
    private Button saveButton, deleteButton;

    private FilterSpinnerAdapter filterSpinnerAdapter;
    private ArrayList<TransactionType> filterBySpinnerItems;

    private DetailFragmentPresenter presenter;
    private MainActivity activity;


    public TransactionDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        presenter = DetailFragmentPresenter.getInstance();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_transaction_detail, container, false);
        findViewsById(view);
        presenter.init(this);
        activity = (MainActivity) getActivity();

        filterBySpinnerItems = new ArrayList<>();
        filterBySpinnerItems.addAll(ListFragmentPresenter.getInstance().getFilterItems());
        if (filterBySpinnerItems.size() > 0) {
            filterBySpinnerItems.remove(filterBySpinnerItems.size() - 1);
        }
        filterSpinnerAdapter = new FilterSpinnerAdapter(getActivity(), filterBySpinnerItems);
        typeSpinner.setAdapter(filterSpinnerAdapter);
        if (presenter.getTransaction() != null) {
            oldTransaction = presenter.getTransaction();
            setInitialState(presenter.getTransaction());
            saveButton.setOnClickListener(getUpdateListener());
        } else {
            deleteButton.setEnabled(false);
            saveButton.setOnClickListener(getCreateListener());
        }

        deleteButton.setOnClickListener(v -> {
            presenter.deleteTransaction(oldTransaction);
            if (!MainActivity.twoPane) {
                activity.afterSubmitActionOnDetailFragment();
            }
        });

        return view;
    }

    public void refresh() {
        setBackgroundsToDefault();
        Transaction transaction = presenter.getTransaction();
        if (transaction != null) {
            oldTransaction = transaction;
            setInitialState(transaction);
            setBackgroundsToDefault();
            deleteButton.setEnabled(true);
            saveButton.setOnClickListener(getUpdateListener());
        } else {
            deleteButton.setEnabled(false);
            titleTextView.setText(null);
            descriptionTextView.setText(null);
            amountTextView.setText(null);
            dateTextView.setText(null);
            endDateTextView.setText(null);
            transactionIntervalTextView.setText(null);
            amountTextView.setText(null);
            typeSpinner.setSelection(0);
            saveButton.setOnClickListener(getCreateListener());
            setBackgroundsToDefault();
        }
    }

    private View.OnClickListener getCreateListener() {
        return (event)->{
            try {
                validateFields();
                extractTransaction();
                String[] poruke = {
                        "Over monthly limit. Continue?",
                        "Over total limit. Continue?",
                        "Over monthly and total limit. Continue?"
                };
                int kojaPoruka = -1;
                try {
                    validateFields();
                    extractTransaction();
                    if (MainModel.getInstance().isOverMonthlyLimit(this.transaction)) {
                        kojaPoruka = 0;
                    }
                    if (MainModel.getInstance().isOverTotalLimit(this.transaction)) {
                        if (kojaPoruka == 0) {
                            kojaPoruka = 2;
                        } else {
                            kojaPoruka = 1;
                        }
                    }
                    if (kojaPoruka != -1) {
                        new AlertDialog
                                .Builder(getActivity())
                                .setTitle("Warning")
                                .setMessage(poruke[kojaPoruka])
                                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                                    presenter.createTransaction(this.transaction);
                                    activity.afterSubmitActionOnDetailFragment();
                                })
                                .setNegativeButton(android.R.string.cancel, null).show();
                    } else {
                        presenter.createTransaction(this.transaction);
                        activity.afterSubmitActionOnDetailFragment();
                    }
                } catch (InvalidFieldValueException e) {
                    Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_LONG).show();
                }

            } catch (InvalidFieldValueException e) {

            }
        };
    }

    private View.OnClickListener getUpdateListener() {
        return (event)->{
            try {
                validateFields();
                extractTransaction();
                String[] poruke = {
                        "Over monthly limit. Continue?",
                        "Over total limit. Continue?",
                        "Over monthly and total limit. Continue?"
                };
                int kojaPoruka = -1;
                try {
                    validateFields();
                    extractTransaction();
                    if (MainModel.getInstance().isOverMonthlyLimit(this.oldTransaction,this.transaction)) {
                        kojaPoruka = 0;
                    }
                    if (MainModel.getInstance().isOverTotalLimit(this.oldTransaction,this.transaction)) {
                        if (kojaPoruka == 0) {
                            kojaPoruka = 2;
                        } else {
                            kojaPoruka = 1;
                        }
                    }
                    if (kojaPoruka != -1) {
                        new AlertDialog
                                .Builder(getActivity())
                                .setTitle("Warning")
                                .setMessage(poruke[kojaPoruka])
                                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                                    presenter.updateTransaction(this.oldTransaction,this.transaction);
                                    activity.afterSubmitActionOnDetailFragment();
                                })
                                .setNegativeButton(android.R.string.cancel, null).show();
                    } else {
                        presenter.updateTransaction(this.oldTransaction,this.transaction);
                        activity.afterSubmitActionOnDetailFragment();
                    }
                } catch (InvalidFieldValueException e) {
                    Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_LONG).show();
                }

            } catch (InvalidFieldValueException e) {

            }
        };
    }

    private void setBackgroundsToDefault() {
        titleTextView.setBackground(defaultBackground);
        descriptionTextView.setBackground(defaultBackground);
        amountTextView.setBackground(defaultBackground);
        dateTextView.setBackground(defaultBackground);
        endDateTextView.setBackground(defaultBackground);
        transactionIntervalTextView.setBackground(defaultBackground);
        amountTextView.setBackground(defaultBackground);
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
        String displayDate = new SimpleDateFormat("d.M.y", Locale.getDefault()).format(oldTransaction.getDate());
        dateTextView.setText(displayDate);
        String displayEndDate = new SimpleDateFormat("d.M.y", Locale.getDefault()).format(oldTransaction.getEndDate());
        endDateTextView.setText(displayEndDate);
        transactionIntervalTextView.setText(String.format(Locale.getDefault(),"%d",oldTransaction.getTransactionInterval()));
        typeSpinner.setSelection(
                filterBySpinnerItems.indexOf(
                        oldTransaction.getTransactionType()
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
        TransactionType filterItem = (TransactionType) typeSpinner.getSelectedItem();
        if (filterItem.getName().toLowerCase().contains("all")) {
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
            new SimpleDateFormat("d.M.y", Locale.getDefault()).parse(dateTextView.getText().toString());
        } catch (ParseException e) {
            dateTextView.setBackgroundColor(Color.RED);
            throw new InvalidFieldValueException("Date is not valid");
        }

        if (!filterItem.getName().toLowerCase().contains("individual")
                && !filterItem.getName().toLowerCase().contains("purchase")) {
            try{
                new SimpleDateFormat("d.M.y", Locale.getDefault()).parse(endDateTextView.getText().toString());
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

        if (!filterItem.getName().toLowerCase().contains("income")) {
            if (descriptionTextView.getText().toString().length() == 0) {
                descriptionTextView.setBackgroundColor(Color.RED);
                throw new InvalidFieldValueException("Description is not set");
            }
        }

    }
    private void extractTransaction() {
        try {
            transaction = new Transaction(
                    new SimpleDateFormat("d.M.y", Locale.getDefault()).parse(dateTextView.getText().toString()),
                    new BigDecimal(amountTextView.getText().toString()),
                    titleTextView.getText().toString(),
                    descriptionTextView.getText().toString(),
                    Integer.parseInt(transactionIntervalTextView.getText().toString()),
                    new SimpleDateFormat("d.M.y", Locale.getDefault()).parse(endDateTextView.getText().toString()),
                    (TransactionType) typeSpinner.getSelectedItem());
        } catch (ParseException e) {

        }

    }
}
