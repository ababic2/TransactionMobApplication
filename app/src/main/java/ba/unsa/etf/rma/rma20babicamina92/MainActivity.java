package ba.unsa.etf.rma.rma20babicamina92;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import ba.unsa.etf.rma.rma20babicamina92.adapters.FilterSpinnerAdapter;
//import ba.unsa.etf.rma.rma20babicamina92.adapters.TransactionListAdapter;
import ba.unsa.etf.rma.rma20babicamina92.contracts.MainContract;
import ba.unsa.etf.rma.rma20babicamina92.models.FilterItem;
import ba.unsa.etf.rma.rma20babicamina92.models.TransactionListItem;
import ba.unsa.etf.rma.rma20babicamina92.presenters.MainPresenter;

public class MainActivity extends AppCompatActivity implements MainContract.MainView{
    private Button leftDatePickerButton, rightDatePickerButton, addTransactionButton;
    private ListView listView;
    private Spinner filterSpinner;
    private Spinner sortSpinner;
    private TextView dateTextView;

    private ArrayList<FilterItem> entriesSpinner1 = new ArrayList<FilterItem>();
    private ArrayList<TransactionListItem> transactionListItems = new ArrayList<TransactionListItem>();
    private ArrayList<String> entriesSpinner2 = new ArrayList<String>();

    private FilterSpinnerAdapter filterSpinnerAdapter;
    private ArrayAdapter<String> sortSpinnerAdapter;
    private MainPresenter mainPresenter;
   // private TransactionListAdapter transactionListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initList();

        leftDatePickerButton = findViewById(R.id.leftDatePickerButton);
        dateTextView = findViewById(R.id.dateTextView);
        rightDatePickerButton = findViewById(R.id.rightDatePickerButton);
        addTransactionButton = findViewById(R.id.addTransactionButton);
        sortSpinner = (Spinner) findViewById(R.id.sortSpinner);
        filterSpinner = (Spinner) findViewById(R.id.filterSpinner);


        filterSpinnerAdapter = new FilterSpinnerAdapter(this, entriesSpinner1);
        filterSpinner.setAdapter(filterSpinnerAdapter);

        filterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                FilterItem filterItem = (FilterItem)parent.getItemAtPosition(position);
                String clickedName = filterItem.getFilterName();
                if(position != 0) {
                    Toast.makeText(MainActivity.this, clickedName + " selected", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        sortSpinnerAdapter =new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, entriesSpinner2) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        sortSpinner.setAdapter(sortSpinnerAdapter);
        mainPresenter = new MainPresenter(this);
        rightDatePickerButton.setOnClickListener(event -> mainPresenter.datePickerClickedRight());
        leftDatePickerButton.setOnClickListener(event -> mainPresenter.datePickerCLickedLeft());
    }

    public void setMonthForTransactions(Date date) {
        String displayDate = new SimpleDateFormat("MMMM, yyyy", Locale.getDefault()).format(date);
        dateTextView.setText(displayDate);
    }

    private void initList() {
        entriesSpinner1.add(new FilterItem("INDIVIDUALPAYMENT",R.drawable.individualpay));
        entriesSpinner1.add(new FilterItem("REGULARPAYMENT",R.drawable.regularpayment));
        entriesSpinner1.add(new FilterItem("PURCHASE",R.drawable.pursache));
        entriesSpinner1.add(new FilterItem("INDIVIDUALINCOME",R.drawable.individualpay)); // dodaj slikicuuu
        entriesSpinner1.add(new FilterItem("REGULARINCOME",R.drawable.individualpay)); //addd phootoooo

        entriesSpinner1.add(new FilterItem("INDIVIDUALPAYMENT",R.drawable.individualpay));
        entriesSpinner1.add(new FilterItem("REGULARPAYMENT",R.drawable.regularpayment));
        entriesSpinner1.add(new FilterItem("PURCHASE",R.drawable.pursache));
        entriesSpinner1.add(new FilterItem("INDIVIDUALINCOME",R.drawable.individualpay)); // dodaj slikicuuu
        entriesSpinner1.add(new FilterItem("REGULARINCOME",R.drawable.individualpay)); //addd phootoooo

        entriesSpinner1.add(new FilterItem("INDIVIDUALPAYMENT",R.drawable.individualpay));
        entriesSpinner1.add(new FilterItem("REGULARPAYMENT",R.drawable.regularpayment));
        entriesSpinner1.add(new FilterItem("PURCHASE",R.drawable.pursache));
        entriesSpinner1.add(new FilterItem("INDIVIDUALINCOME",R.drawable.individualpay)); // dodaj slikicuuu
        entriesSpinner1.add(new FilterItem("REGULARINCOME",R.drawable.individualpay)); //addd phootoooo

        entriesSpinner2.add("Sort by");
        entriesSpinner2.add("Price - Ascending");
        entriesSpinner2.add("Price - Descending");
        entriesSpinner2.add("Title - Ascending");
        entriesSpinner2.add("Title - Descending");
        entriesSpinner2.add("Date - Ascending");
        entriesSpinner2.add("Date - Descending");
        entriesSpinner2.add("Price - Descending");
        entriesSpinner2.add("Title - Ascending");
        entriesSpinner2.add("Title - Descending");
        entriesSpinner2.add("Date - Ascending");
        entriesSpinner2.add("Date - Descending");

       // transactionListItems.add()
    }

}
