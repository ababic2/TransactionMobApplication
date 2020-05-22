package ba.unsa.etf.rma.rma20babicamina92.interactor;

import android.os.AsyncTask;

import ba.unsa.etf.rma.rma20babicamina92.MainActivity;
import ba.unsa.etf.rma.rma20babicamina92.R;
import ba.unsa.etf.rma.rma20babicamina92.models.Account;
import ba.unsa.etf.rma.rma20babicamina92.presenters.BudgetPresenter;

public class AccountPostInteractor extends AsyncTask<Account, Integer, String> {
    private MainActivity mainActivity;
    private BudgetPresenter listFragmentPresenter;

    public AccountPostInteractor(MainActivity mainActivity, BudgetPresenter presenter) {
        this.mainActivity = mainActivity;
        this.listFragmentPresenter = presenter;
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
        MainActivity.loadingOn(mainActivity, "Posting account data to web service. Please wait.");
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        MainActivity.loadingOff();
    }
}
