package ba.unsa.etf.rma.rma20babicamina92.interactor;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ba.unsa.etf.rma.rma20babicamina92.MainActivity;
import ba.unsa.etf.rma.rma20babicamina92.R;
import ba.unsa.etf.rma.rma20babicamina92.models.MainModel;
import ba.unsa.etf.rma.rma20babicamina92.models.Transaction;

public class TransactionInteractor extends AsyncTask<String, Integer, String> {

    private MainActivity mainActivity;

    public TransactionInteractor(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        MainActivity.loadingOn(mainActivity, "Dohvatanje transakcija. Molimo sačekajte.");
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        int offset = 0;
        try {
            JSONObject root = new JSONObject(result);
            offset = root.getInt("offset");
        } catch (JSONException e) {
            System.out.println("Problems parsing json.");
        }
        offset = offset/5 + 1;
        ArrayList<Transaction> transactions = JsonDecoder.decodeTransactions(result);
        for (Transaction transaction : transactions) {
            MainModel.getInstance().addTransaction(transaction);
        }
        if (transactions.size() == 5) {
            new TransactionInteractor(mainActivity).execute("page="+offset);
        }
        MainActivity.loadingOff();
    }

    @Override
    protected String doInBackground(String... strings) {
        String query = "/account/" + mainActivity.getResources().getString(R.string.api_id) + "/transactions?" + strings[0];
        return Util.getResultFromWeb(query,mainActivity);
    }
}