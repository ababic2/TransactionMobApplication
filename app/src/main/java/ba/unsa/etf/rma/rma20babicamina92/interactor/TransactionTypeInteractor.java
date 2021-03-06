package ba.unsa.etf.rma.rma20babicamina92.interactor;


import android.os.AsyncTask;

import java.util.ArrayList;

import ba.unsa.etf.rma.rma20babicamina92.MainActivity;
import ba.unsa.etf.rma.rma20babicamina92.R;
import ba.unsa.etf.rma.rma20babicamina92.models.TransactionType;
import ba.unsa.etf.rma.rma20babicamina92.presenters.ListFragmentPresenter;

public class TransactionTypeInteractor extends AsyncTask<String,Integer,String> {
    private MainActivity context;
    private ListFragmentPresenter listFragmentPresenter;

    public TransactionTypeInteractor() {
        super();
    }

    public TransactionTypeInteractor(MainActivity context) {
        this.context = context;
    }

    public TransactionTypeInteractor(MainActivity activity, ListFragmentPresenter presenter) {
        this.context = activity;
        this.listFragmentPresenter = presenter;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        MainActivity.loadingOn(context, "TRANSACTION_TYPE_GET", "Dohvatanje tipova transakcija. Molimo sačekajte.");
    }

    @Override
    protected String doInBackground(String... strings) {
        String query = "/transactionTypes";
        return Util.getResultFromWeb(query, context);
    }

    @Override
    protected void onPostExecute(String result) {
        ArrayList<TransactionType> transactionTypes = JsonDecoder.decodeTransactionTypes(result);
        transactionTypes.add(0, new TransactionType(0,"All", R.mipmap.ic_six));
        transactionTypes.add(new TransactionType(0,"All", R.mipmap.ic_six));
        listFragmentPresenter.setFilterItems(transactionTypes);
        MainActivity.loadingOff("TRANSACTION_TYPE_GET");

    }
}
