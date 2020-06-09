package ba.unsa.etf.rma.rma20babicamina92.interactor;

import android.os.AsyncTask;

import ba.unsa.etf.rma.rma20babicamina92.MainActivity;
import ba.unsa.etf.rma.rma20babicamina92.R;
import ba.unsa.etf.rma.rma20babicamina92.models.Account;
import ba.unsa.etf.rma.rma20babicamina92.models.AccountAction;
import ba.unsa.etf.rma.rma20babicamina92.presenters.ListFragmentPresenter;

public class AccountInteractor extends AsyncTask<String,Integer,String> {
    private MainActivity mainActivity;
    private ListFragmentPresenter listFragmentPresenter;

    public AccountInteractor(MainActivity mainActivity, ListFragmentPresenter presenter) {
        this.mainActivity = mainActivity;
        this.listFragmentPresenter = presenter;
    }

    @Override
    protected String doInBackground(String... strings) {
        String query = "/account/" + mainActivity.getResources().getString(R.string.api_id);
        return Util.getResultFromWeb(query,mainActivity);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        MainActivity.loadingOn(mainActivity,"ACCOUNT_GET", "Dohvatanje podataka o računu. Molimo sačekajte.");
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Account account = JsonDecoder.decodeAccount(result);
        if (listFragmentPresenter != null) {
            listFragmentPresenter.setAccount(account);
        } else {
            MainActivity.bankResolver.insertAccountAction(new AccountAction("DODAVANJE", account));
        }
        MainActivity.loadingOff("ACCOUNT_GET");
    }
}
