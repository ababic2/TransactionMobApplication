package ba.unsa.etf.rma.rma20babicamina92.interactor;

import android.os.AsyncTask;

import ba.unsa.etf.rma.rma20babicamina92.MainActivity;
import ba.unsa.etf.rma.rma20babicamina92.R;
import ba.unsa.etf.rma.rma20babicamina92.models.Account;

public class AccountPostInteractor extends AsyncTask<Account, Integer, String> {
    private MainActivity mainActivity;

    public AccountPostInteractor(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    protected String doInBackground(Account... accounts) {
        String query = "/account/" + mainActivity.getResources().getString(R.string.api_id);
        String data = JsonEncoder.encodeAccount(accounts[0]);
        return Util.postResultToWeb(query,mainActivity,data);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        MainActivity.loadingOn(mainActivity, "ACCOUNT_POST", "Slanje podataka o računu na web servis. Molimo sačekajte.");
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        MainActivity.loadingOff("ACCOUNT_POST");
    }
}
