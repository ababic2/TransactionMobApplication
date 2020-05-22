package ba.unsa.etf.rma.rma20babicamina92.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import ba.unsa.etf.rma.rma20babicamina92.MainActivity;
import ba.unsa.etf.rma.rma20babicamina92.R;
import ba.unsa.etf.rma.rma20babicamina92.models.Transaction;

public class TransactionListAdapter extends ArrayAdapter<Transaction> {
    private int resource;

    public TransactionListAdapter(MainActivity mainActivity, int resource, ArrayList<Transaction> transactionArrayList) {
        super(mainActivity, resource, transactionArrayList);
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LinearLayout newView;
        if(convertView == null) {
            newView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater layoutInflater;
            layoutInflater = (LayoutInflater)getContext().getSystemService(inflater);
            layoutInflater.inflate(resource,newView,true);
        } else {
            newView = (LinearLayout)convertView;
        }
        Transaction transaction = getItem(position);
        if (transaction != null) {
            TextView titleTextView = newView.findViewById(R.id.titleText);
            ImageView imageView = newView.findViewById(R.id.transactionImageView);
            titleTextView.setText(transaction.getTitle() + "\n" + transaction.getAmount());
            imageView.setImageResource(transaction.getTransactionType().getImage());
//            switch (transaction.getType()) {
//                case "INDIVIDUALPAYMENT":
//                case "REGULARPAYMENT":
//                    imageView.setImageResource(R.drawable.regularpayment);
//                    break;
//                case "PURCHASE":
//                    imageView.setImageResource(R.drawable.purchase);
//                    break;
//                case "INDIVIDUALINCOME":
//                case "REGULARINCOME":
//                    imageView.setImageResource(R.drawable.individualpay);
//                    break;
//            }
        }
        return newView;
    }

}
