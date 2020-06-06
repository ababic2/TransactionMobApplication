package ba.unsa.etf.rma.rma20babicamina92.interactor;

import android.os.AsyncTask;

import ba.unsa.etf.rma.rma20babicamina92.MainActivity;
import ba.unsa.etf.rma.rma20babicamina92.R;
import ba.unsa.etf.rma.rma20babicamina92.models.Transaction;

public class TransactionCreateInteractor extends AsyncTask<Transaction, Integer, String> {

    private MainActivity mainActivity;

    public TransactionCreateInteractor(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        MainActivity.loadingOn(mainActivity, "TRANSACTION_CREATE", "Kreiranje transakcije. Molimo sačekajte.");
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        MainActivity.loadingOff("TRANSACTION_CREATE");
    }

    @Override
    protected String doInBackground(Transaction... params) {
        String query = "/account/" + mainActivity.getResources().getString(R.string.api_id) + "/transactions" ;
        String data = JsonEncoder.encodeTransaction(params[0]);
        return Util.postResultToWeb(query,mainActivity,data);
    }
}
