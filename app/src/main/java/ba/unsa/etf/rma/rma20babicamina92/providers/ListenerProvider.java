package ba.unsa.etf.rma.rma20babicamina92.providers;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import ba.unsa.etf.rma.rma20babicamina92.MainActivity;
import ba.unsa.etf.rma.rma20babicamina92.TransactionActivity;
import ba.unsa.etf.rma.rma20babicamina92.exceptions.InvalidFieldValueException;
import ba.unsa.etf.rma.rma20babicamina92.models.FilterItem;
import ba.unsa.etf.rma.rma20babicamina92.models.MainModel;
import ba.unsa.etf.rma.rma20babicamina92.models.Transaction;

public class ListenerProvider {
    public static AdapterView.OnItemSelectedListener provideFilterSpinnerListener(Context context) {
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                FilterItem filterItem = (FilterItem) parent.getItemAtPosition(position);
                String clickedName = filterItem.getFilterName();
                if (position != 0) {
                    Toast.makeText(context, clickedName + " selected", Toast.LENGTH_SHORT).show();
                }
                MainActivity mainActivity = (MainActivity) context;
                mainActivity.onFilterSelect(filterItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
    }

    public static AdapterView.OnItemSelectedListener provideSortSpinnerListener(MainActivity context) {
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String sort = (String) parent.getItemAtPosition(position);
                context.onSortSelect(sort);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
    }

}
