package ba.unsa.etf.rma.rma20babicamina92;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

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

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import ba.unsa.etf.rma.rma20babicamina92.adapters.FilterSpinnerAdapter;
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
        filterBySpinnerItems.add(new FilterItem("ALL",R.drawable.individualpay));
        filterBySpinnerItems.add(new FilterItem("INDIVIDUALPAYMENT", R.drawable.individualpay));
        filterBySpinnerItems.add(new FilterItem("REGULARPAYMENT",R.drawable.regularpayment));
        filterBySpinnerItems.add(new FilterItem("PURCHASE",R.drawable.purchase));
        filterBySpinnerItems.add(new FilterItem("INDIVIDUALINCOME",R.drawable.individualpay));
        filterBySpinnerItems.add(new FilterItem("REGULARINCOME",R.drawable.individualpay));
        filterBySpinnerItems.add(new FilterItem("ALL",android.R.drawable.gallery_thumb));
        filterSpinnerAdapter = new FilterSpinnerAdapter(this, filterBySpinnerItems);
        typeSpinner.setAdapter(filterSpinnerAdapter);

        if (intent.getExtras() != null) {
            setInitialState(intent.getExtras().getSerializable("transaction"));
        } else {
            deleteButton.setEnabled(false);
        }

        deleteButton.setOnClickListener(event -> {
            model.deleteTransaction(oldTransaction);
            finish();
        });


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


}

