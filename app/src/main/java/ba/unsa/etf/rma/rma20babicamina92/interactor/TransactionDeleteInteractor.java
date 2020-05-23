package ba.unsa.etf.rma.rma20babicamina92.interactor;

import android.os.AsyncTask;

import ba.unsa.etf.rma.rma20babicamina92.MainActivity;
import ba.unsa.etf.rma.rma20babicamina92.R;
import ba.unsa.etf.rma.rma20babicamina92.models.Transaction;

public class TransactionDeleteInteractor extends AsyncTask<Transaction,Integer,String> {

    private MainActivity mainActivity;

    public TransactionDeleteInteractor(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        MainActivity.loadingOn(mainActivity, "Brisanje transakcije. Molimo sačekajte.");
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        MainActivity.loadingOff();
    }

    @Override
    protected String doInBackground(Transaction... params) {
        String query = "/account/" + mainActivity.getResources().getString(R.string.api_id) + "/transactions/"+params[0].getId();
        return Util.deleteActionToWeb(query, mainActivity);
    }
}
