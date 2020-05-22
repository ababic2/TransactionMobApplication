package ba.unsa.etf.rma.rma20babicamina92.interactor;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import ba.unsa.etf.rma.rma20babicamina92.MainActivity;
import ba.unsa.etf.rma.rma20babicamina92.R;
import ba.unsa.etf.rma.rma20babicamina92.models.Account;
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
        MainActivity.loadingOn(mainActivity);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Account account = JsonDecoder.decodeAccount(result);
        listFragmentPresenter.setAccount(account);
        MainActivity.loadingOff(mainActivity);
    }
}
