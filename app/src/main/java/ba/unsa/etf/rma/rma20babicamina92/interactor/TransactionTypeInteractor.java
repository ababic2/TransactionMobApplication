package ba.unsa.etf.rma.rma20babicamina92.interactor;


import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import ba.unsa.etf.rma.rma20babicamina92.MainActivity;
import ba.unsa.etf.rma.rma20babicamina92.R;
import ba.unsa.etf.rma.rma20babicamina92.models.FilterItem;
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
        MainActivity.loadingOn(context);
    }

    @Override
    protected String doInBackground(String... strings) {

        String result = "";
        try {
            String api = context.getResources().getString(R.string.api_url);
            System.out.println(api);
            URL url = new URL(api + "/transactionTypes");
            System.out.println(url.getPath());
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            System.out.println("here");
            InputStream in = urlConnection.getInputStream();
            System.out.println("Opened connection");
            result = readStream(in);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("IOException: "+e.getMessage());
        }
        return result;
    }

    private String readStream(InputStream in) {
        Scanner scanner = new Scanner(in);
        String result = "";
        while(scanner.hasNextLine()) {
            result += scanner.nextLine();
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        System.out.println(result + "something");
        System.out.println("result");
        ArrayList<FilterItem> filterItems = JsonDecoder.decodeTransactionTypes(result);
        filterItems.add(0, new FilterItem("All", R.mipmap.ic_six));
        filterItems.add(new FilterItem("All", R.mipmap.ic_six));
        listFragmentPresenter.setFilterItems(filterItems);
        MainActivity.loadingOff(context);

    }
}
