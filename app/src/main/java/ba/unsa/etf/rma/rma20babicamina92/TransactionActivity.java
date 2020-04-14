package ba.unsa.etf.rma.rma20babicamina92;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import ba.unsa.etf.rma.rma20babicamina92.adapters.FilterSpinnerAdapter;
import ba.unsa.etf.rma.rma20babicamina92.exceptions.InvalidFieldValueException;
import ba.unsa.etf.rma.rma20babicamina92.models.FilterItem;
import ba.unsa.etf.rma.rma20babicamina92.models.MainModel;
import ba.unsa.etf.rma.rma20babicamina92.models.Transaction;


public class TransactionActivity extends AppCompatActivity {

    private Transaction oldTransaction, transaction;
    private EditText titleTextView, descriptionTextView, amountTextView,
            dateTextView, endDateTextView, transactionIntervalTextView;
    private Spinner typeSpinner;
    private Button saveButton, deleteButton;
    private FilterSpinnerAdapter filterSpinnerAdapter;
    private ArrayList<FilterItem> filterBySpinnerItems;
    private MainModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        model = MainModel.getInstance();

        findViewsById();
        Intent intent = getIntent();
        filterBySpinnerItems = new ArrayList<FilterItem>();
        filterBySpinnerItems.add(new FilterItem("ALL",android.R.drawable.gallery_thumb));
        filterBySpinnerItems.add(new FilterItem("INDIVIDUALPAYMENT", R.drawable.regularpayment));
        filterBySpinnerItems.add(new FilterItem("REGULARPAYMENT",R.drawable.regularpayment));
        filterBySpinnerItems.add(new FilterItem("PURCHASE",R.drawable.purchase));
        filterBySpinnerItems.add(new FilterItem("INDIVIDUALINCOME",R.drawable.individualpay));
        filterBySpinnerItems.add(new FilterItem("REGULARINCOME",R.drawable.individualpay));
        filterSpinnerAdapter = new FilterSpinnerAdapter(this, filterBySpinnerItems);
        typeSpinner.setAdapter(filterSpinnerAdapter);

        if (intent.getExtras() != null) {
            setInitialState(intent.getExtras().getSerializable("transaction"));
            saveButton.setOnClickListener(provideUpdateListener());
        } else {
            deleteButton.setEnabled(false);
            saveButton.setOnClickListener(provideSaveListener());
        }

        deleteButton.setOnClickListener(event -> {
            model.deleteTransaction(oldTransaction);
            finish();
        });


    }

    private View.OnClickListener provideSaveListener() {
        return event -> {
            String[] poruke = {
                    "Over monthly limit. Continue?",
                    "Over total limit. Continue?",
                    "Over monthly and total limit. Continue?"
            };
            int kojaPoruka = -1;
            try {
                validateFields();
                extractTransaction();
                if (model.isOverMonthlyLimit(transaction)) {
                    kojaPoruka = 0;
                }
                if (model.isOverTotalLimit(transaction)) {
                    if (kojaPoruka == 0) {
                        kojaPoruka = 2;
                    } else {
                        kojaPoruka = 1;
                    }
                }
                if (kojaPoruka != -1) {
                    new AlertDialog
                            .Builder(this)
                            .setTitle("Warning")
                            .setMessage(poruke[kojaPoruka])
                            .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                                model.addTransaction(transaction);
                                finish();
                            })
                            .setNegativeButton(android.R.string.cancel, null).show();
                } else {
                    model.addTransaction(transaction);
                    finish();
                }
            } catch (InvalidFieldValueException e) {
                Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
            }
        };
    }

    private View.OnClickListener provideUpdateListener() {
        return event -> {
            String[] poruke = {
                    "Over monthly limit. Continue?",
                    "Over total limit. Continue?",
                    "Over monthly and total limit. Continue?"
            };
            int kojaPoruka = -1;
            try {
                validateFields();
                extractTransaction();
                if (model.isOverMonthlyLimit(oldTransaction,transaction)) {
                    kojaPoruka = 0;
                }
                if (model.isOverTotalLimit(oldTransaction,transaction)) {
                    if (kojaPoruka == 0) {
                        kojaPoruka = 2;
                    } else {
                        kojaPoruka = 1;
                    }
                }
                if (kojaPoruka != -1) {
                    new AlertDialog
                            .Builder(this)
                            .setTitle("Warning")
                            .setMessage(poruke[kojaPoruka])
                            .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                                model.updateTransaction(oldTransaction,transaction);
                                oldTransaction = transaction;
                            })
                            .setNegativeButton(android.R.string.cancel, null).show();
                } else {
                    model.updateTransaction(oldTransaction,transaction);
                    oldTransaction = transaction;
                }
            } catch (InvalidFieldValueException e) {
                Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
            }
        };
    }

    private void extractTransaction() {
        try {
            transaction = new Transaction(
                    new SimpleDateFormat("MMMM, yyyy", Locale.getDefault()).parse(dateTextView.getText().toString()),
                    new BigDecimal(amountTextView.getText().toString()),
                    titleTextView.getText().toString(),
                    descriptionTextView.getText().toString(),
                    Integer.parseInt(transactionIntervalTextView.getText().toString()),
                    new SimpleDateFormat("MMMM, yyyy", Locale.getDefault()).parse(endDateTextView.getText().toString()),
                    ((FilterItem)typeSpinner.getSelectedItem()).getFilterName());
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private void findViewsById() {
        typeSpinner = findViewById(R.id.spinner);
        titleTextView = findViewById(R.id.titleTextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        amountTextView = findViewById(R.id.amountTextView);
        dateTextView = findViewById(R.id.dateTextView);
        endDateTextView = findViewById(R.id.endDateTextView);
        transactionIntervalTextView = findViewById(R.id.transactionIntervalTextView);
        saveButton = findViewById(R.id.saveButton);
        deleteButton = findViewById(R.id.deleteButton);
    }

    private void setInitialState(Serializable transaction) {
        oldTransaction = (Transaction) transaction;
        titleTextView.setText(oldTransaction.getTitle());
        descriptionTextView.setText(oldTransaction.getItemDescription());
        amountTextView.setText(oldTransaction.getAmount().toString());
        String displayDate = new SimpleDateFormat("MMMM, yyyy", Locale.getDefault()).format(oldTransaction.getDate());
        dateTextView.setText(displayDate);
        String displayEndDate = new SimpleDateFormat("MMMM, yyyy", Locale.getDefault()).format(oldTransaction.getEndDate());
        endDateTextView.setText(displayEndDate);
        transactionIntervalTextView.setText(oldTransaction.getTransactionInterval().toString());
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

