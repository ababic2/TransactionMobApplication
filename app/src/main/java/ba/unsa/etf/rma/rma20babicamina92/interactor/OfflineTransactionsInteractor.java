package ba.unsa.etf.rma.rma20babicamina92.interactor;

import android.os.AsyncTask;

import java.util.ArrayList;

import ba.unsa.etf.rma.rma20babicamina92.MainActivity;
import ba.unsa.etf.rma.rma20babicamina92.R;
import ba.unsa.etf.rma.rma20babicamina92.models.MainModel;
import ba.unsa.etf.rma.rma20babicamina92.models.Transaction;
import ba.unsa.etf.rma.rma20babicamina92.models.TransactionAction;

public class OfflineTransactionsInteractor extends AsyncTask<TransactionAction,Integer,String> {
    private MainActivity mainActivity;
    private int index;

    public OfflineTransactionsInteractor(MainActivity mainActivity,int index) {
        this.mainActivity = mainActivity;
        this.index = index;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        MainActivity.loadingOn(mainActivity, "SYNC", "Azuriranje prema lokalnoj bazi. Molimo sačekajte.");
    }

    @Override
    protected String doInBackground(TransactionAction... actions) {
        String query = "/account/" + mainActivity.getResources().getString(R.string.api_id) + "/transactions";
        if (actions != null) {
            if (actions[0].getName().equals("DODAVANJE")) {
                String data = JsonEncoder.encodeTransaction(actions[0].getTransaction());
                return Util.postResultToWeb(query,mainActivity,data);
            } else if (actions[0].getName().equals("IZMJENA")) {
                query = "/account/" + mainActivity.getResources().getString(R.string.api_id) + "/transactions/"+actions[0].getTransaction().getId();
                String data = JsonEncoder.encodeTransaction(actions[0].getTransaction());
                System.out.println(data);
                return Util.postResultToWeb(query,mainActivity,data);
            } else if (actions[0].getName().equals("BRISANJE")) {
                query = "/account/" + mainActivity.getResources().getString(R.string.api_id) + "/transactions/"+actions[0].getTransaction().getId();
                return Util.deleteActionToWeb(query, mainActivity);
            }
        }
        MainActivity.bankResolver.deleteTransactionAction(actions[0].getTransaction().getId());
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        MainActivity.loadingOff("SYNC");
        if (index + 1 < MainModel.getInstance().getTransactionActions().size()) {
            new OfflineTransactionsInteractor(mainActivity, index + 1).execute(MainModel.getInstance().getTransactionActions().get(index + 1));
        } else {
            if (MainModel.getInstance().getAccountActions() != null) {
                new AccountPostInteractor(mainActivity).execute(MainModel.getInstance().getAccountActions().getAccount());
            }
            MainModel.getInstance().setTransactionActions(new ArrayList<>());
        }
    }
}
