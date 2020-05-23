package ba.unsa.etf.rma.rma20babicamina92.interactor;

import android.os.AsyncTask;

import ba.unsa.etf.rma.rma20babicamina92.MainActivity;
import ba.unsa.etf.rma.rma20babicamina92.R;
import ba.unsa.etf.rma.rma20babicamina92.models.Transaction;

public class TransactionUpdateInteractor extends AsyncTask<Transaction, Integer, String> {

    private MainActivity mainActivity;

    public TransactionUpdateInteractor(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        MainActivity.loadingOn(mainActivity, "Ažuriranje transakcije. Molimo sačekajte.");
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        MainActivity.loadingOff();
    }

    @Override
    protected String doInBackground(Transaction... params) {
        String query = "/account/" + mainActivity.getResources().getString(R.string.api_id) + "/transactions/"+params[0].getId();
        String data = JsonEncoder.encodeTransaction(params[0]);
        System.out.println(data);
        return Util.postResultToWeb(query,mainActivity,data);
    }
}
