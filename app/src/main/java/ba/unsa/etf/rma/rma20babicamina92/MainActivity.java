package ba.unsa.etf.rma.rma20babicamina92;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import ba.unsa.etf.rma.rma20babicamina92.adapters.FilterSpinnerAdapter;
//import ba.unsa.etf.rma.rma20babicamina92.adapters.TransactionListAdapter;
import ba.unsa.etf.rma.rma20babicamina92.adapters.TransactionListAdapter;
import ba.unsa.etf.rma.rma20babicamina92.contracts.MainContract;
import ba.unsa.etf.rma.rma20babicamina92.models.Account;
import ba.unsa.etf.rma.rma20babicamina92.models.FilterItem;
import ba.unsa.etf.rma.rma20babicamina92.models.Transaction;
import ba.unsa.etf.rma.rma20babicamina92.presenters.MainPresenter;
import ba.unsa.etf.rma.rma20babicamina92.providers.AdapterProvider;
import ba.unsa.etf.rma.rma20babicamina92.providers.ListenerProvider;

public class MainActivity extends FragmentActivity implements MainContract.MainView{


    private ArrayList<FilterItem> filterBySpinnerItems = new ArrayList<FilterItem>();
    private ArrayList<Transaction> transactionArrayList = new ArrayList<Transaction>();

    private FilterSpinnerAdapter filterSpinnerAdapter;
    private ArrayAdapter<String> sortSpinnerAdapter;
    private MainPresenter mainPresenter;
    private TransactionListAdapter transactionListAdapter;

    private Fragment masterFragment;


    @Override
    protected void onPostResume() {
        super.onPostResume();
//        mainPresenter.initialize();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fragmentManager = getSupportFragmentManager();
        masterFragment = fragmentManager.findFragmentById(R.id.transaction_list);
        if (masterFragment == null) {
            masterFragment = new TransactionListFragment();
            fragmentManager
                    .beginTransaction()
                    .add(R.id.transaction_list, masterFragment)
                    .commit();
        } else {
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }


//
//            addTransactionButton.setOnClickListener(event-> {
//                Intent intent = new Intent(this, TransactionActivity.class);
//                startActivity(intent);
//            });
//
//            filterSpinner.setOnItemSelectedListener(ListenerProvider.provideFilterSpinnerListener(this));
//            sortSpinner.setOnItemSelectedListener(ListenerProvider.provideSortSpinnerListener(this));
//
//            filterSpinnerAdapter = new FilterSpinnerAdapter(this, filterBySpinnerItems);

//            transactionListAdapter = new TransactionListAdapter(this,R.layout.transaction_list,transactionArrayList);
//
//            filterSpinner.setAdapter(filterSpinnerAdapter);

//            listView.setAdapter(transactionListAdapter);
//
//            listView.setOnItemClickListener((parent, view, position, id) -> {
//                Intent intent = new Intent(this, TransactionActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("transaction", transactionArrayList.get(position));
//                intent.putExtras(bundle);
//                startActivity(intent);
//            });
//
            mainPresenter = new MainPresenter(this);
//            rightDatePickerButton.setOnClickListener(event -> mainPresenter.datePickerClickedRight());
//            leftDatePickerButton.setOnClickListener(event -> mainPresenter.datePickerCLickedLeft());
            mainPresenter.initialize();
        }

        public void setMonthForTransactions(Date date) {
//            String displayDate = new SimpleDateFormat("MMMM, yyyy", Locale.getDefault()).format(date);
//            dateTextView.setText(displayDate);
        }

        @Override
        public void setFilterBySpinnerItems(ArrayList<FilterItem> filterItems) {
//            filterBySpinnerItems.clear();
//            filterBySpinnerItems.addAll(filterItems);
//            filterSpinnerAdapter.notifyDataSetChanged();
        }

        @Override
        public void setSortBySpinnerItems(ArrayList<String> sortSpinnerItems) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            masterFragment = TransactionListFragment.newInstance(sortSpinnerItems);
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.transaction_list, masterFragment)
                    .commit();
//            this.sortBySpinnerItems.clear();
//            this.sortBySpinnerItems.addAll(sortSpinnerItems);
//            sortSpinnerAdapter.notifyDataSetChanged();
        }

        @Override
        public void setTransactionListItems(ArrayList<Transaction> transactionArrayList) {
//            this.transactionArrayList.clear();
//            System.out.println(transactionArrayList.size());
//            this.transactionArrayList.addAll(transactionArrayList);
//            transactionListAdapter.notifyDataSetChanged();
        }

    @Override
    public void setAccountData(Account account) {
//        limitTextView.setText(String.format(Locale.getDefault(),"%.2f",account.getTotalLimit()));
//        globalAmountTextView.setText(String.format(Locale.getDefault(),"%.2f",account.getBudget()));
    }


    public void onFilterSelect(FilterItem filterItem) {
//        mainPresenter.setFilterMethod(filterItem);
    }

    public void onSortSelect(String sort) {
//        mainPresenter.setSortMethod(sort);
    }
}