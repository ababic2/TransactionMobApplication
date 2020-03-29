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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import ba.unsa.etf.rma.rma20babicamina92.adapters.FilterSpinnerAdapter;
//import ba.unsa.etf.rma.rma20babicamina92.adapters.TransactionListAdapter;
import ba.unsa.etf.rma.rma20babicamina92.adapters.TransactionListAdapter;
import ba.unsa.etf.rma.rma20babicamina92.contracts.MainContract;
import ba.unsa.etf.rma.rma20babicamina92.models.FilterItem;
import ba.unsa.etf.rma.rma20babicamina92.models.Transaction;
import ba.unsa.etf.rma.rma20babicamina92.presenters.MainPresenter;

    public class MainActivity extends AppCompatActivity implements MainContract.MainView{
        private Button leftDatePickerButton, rightDatePickerButton, addTransactionButton;
        private ListView listView;
        private Spinner filterSpinner;
        private Spinner sortSpinner;
        private TextView dateTextView;

        private ArrayList<FilterItem> filterBySpinnerItems = new ArrayList<FilterItem>();
        private ArrayList<String> sortBySpinnerItems = new ArrayList<String>();
        private ArrayList<Transaction> transactionArrayList = new ArrayList<Transaction>();

        private FilterSpinnerAdapter filterSpinnerAdapter;
        private ArrayAdapter<String> sortSpinnerAdapter;
        private MainPresenter mainPresenter;
        private TransactionListAdapter transactionListAdapter;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            leftDatePickerButton = findViewById(R.id.leftDatePickerButton);
            dateTextView = findViewById(R.id.dateTextView);
            rightDatePickerButton = findViewById(R.id.rightDatePickerButton);
            addTransactionButton = findViewById(R.id.addTransactionButton);
            sortSpinner = (Spinner) findViewById(R.id.sortSpinner);
            filterSpinner = (Spinner) findViewById(R.id.filterSpinner);
            listView = (ListView) findViewById(R.id.transactionListView);

            filterSpinnerAdapter = new FilterSpinnerAdapter(this, filterBySpinnerItems);
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

            sortSpinnerAdapter =new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, sortBySpinnerItems) {
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
            mainPresenter.initialize();

            transactionListAdapter = new TransactionListAdapter(this,R.layout.transaction_list,transactionArrayList);
            listView.setAdapter(transactionListAdapter);

        }

        public void setMonthForTransactions(Date date) {
            String displayDate = new SimpleDateFormat("MMMM, yyyy", Locale.getDefault()).format(date);
            dateTextView.setText(displayDate);
        }

        @Override
        public void setFilterBySpinnerItems(ArrayList<FilterItem> filterItems) {
            filterBySpinnerItems.clear();
            filterBySpinnerItems.addAll(filterItems);
        }

        @Override
        public void setSortBySpinnerItems(ArrayList<String> sortSpinnerItems) {
            this.sortBySpinnerItems.clear();
            this.sortBySpinnerItems.addAll(sortSpinnerItems);
        }

        @Override
        public void setTransactionListItems(ArrayList<Transaction> transactionArrayList) {
            this.transactionArrayList.clear();
            System.out.println(transactionArrayList.size());
//            for(int i = 0; i < transactionArrayList.size(); i++) {
//                this.transactionArrayList.add(transactionArrayList.get(i));
//                transactionListAdapter.notifyDataSetChanged();
            //}
        }

        @Override
        public void filterTransactionListByType(ArrayList<Transaction> arrayOfTransactionsByDate) {
            ArrayList<Transaction> arrayOfTransactionsByType = new ArrayList<>();
            String typeFromSpinner = filterSpinner.getSelectedItem().toString();

            for(Transaction transaction : transactionArrayList) {
                String transactionType = transaction.getType();
                if(transactionType.equals(typeFromSpinner)) {
                    arrayOfTransactionsByType.add(transaction);
                }
            }
            //filterTransactionArrayList.addAll(arrayOfTransactionsByType);
        }

        @Override
        public void notifyAdapter() {
            transactionListAdapter.notifyDataSetChanged();
        }
    }