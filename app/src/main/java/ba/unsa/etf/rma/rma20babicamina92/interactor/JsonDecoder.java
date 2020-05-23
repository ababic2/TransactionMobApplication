package ba.unsa.etf.rma.rma20babicamina92.interactor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import ba.unsa.etf.rma.rma20babicamina92.R;
import ba.unsa.etf.rma.rma20babicamina92.models.Account;
import ba.unsa.etf.rma.rma20babicamina92.models.Transaction;
import ba.unsa.etf.rma.rma20babicamina92.models.TransactionType;
import ba.unsa.etf.rma.rma20babicamina92.presenters.ListFragmentPresenter;

class JsonDecoder {
    public static ArrayList<TransactionType> decodeTransactionTypes(String result) {
        ArrayList<Integer> icons = new ArrayList<>(Arrays.asList(R.mipmap.ic_one, R.mipmap.ic_two, R.mipmap.ic_three, R.mipmap.ic_four, R.mipmap.ic_five, R.mipmap.ic_six));
        ArrayList<TransactionType> transactionTypes = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray rows = jsonObject.getJSONArray("rows");
            for (int i = 0; i < jsonObject.getInt("count"); i++) {
                transactionTypes.add(new TransactionType(rows.getJSONObject(i).getInt("id"),rows.getJSONObject(i).getString("name"), icons.get(i)));
            }
        } catch (JSONException e) {

        }
        return transactionTypes;
    }

    public static Account decodeAccount(String result) {
        try {
            JSONObject root = new JSONObject(result);
            return new Account(
                    root.getInt("id"),
                    new BigDecimal(root.getInt("budget")),
                    new BigDecimal(root.getInt("totalLimit")),
                    new BigDecimal(root.getInt("monthLimit"))
                    );
        } catch (JSONException e) {
            System.out.println("JSON not ok! --- Account decode");
        }
        return null;
    }

    public static ArrayList<Transaction> decodeTransactions(String data) {
        ArrayList<Transaction> result = new ArrayList<>();
        ArrayList<TransactionType> types = ListFragmentPresenter.getInstance().getFilterItems();
        try {
            JSONObject root = new JSONObject(data);
            JSONArray transactions = root.getJSONArray("transactions");
            for (int i = 0; i < transactions.length(); i++) {
                JSONObject transaction = transactions.getJSONObject(i);
                TransactionType chosenOne = null;
                for (TransactionType type : types) {
                    if (type.getId() == transaction.getInt("TransactionTypeId")) {
                        chosenOne = type;
                    }
                }
                String date = transaction.getString("date").replace("T", " ").replace("Z", "");
                String endDate = transaction.getString("endDate").replace("T", " ").replace("Z", "");
                Date fixDate = !transaction.isNull("endDate") ? new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault()).parse(endDate) : null;
//                Date fix2Date = !transaction.isNull("date") ? new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault()).parse(date) : null;
                int interval = !transaction.isNull("transactionInterval") ? transaction.getInt("transactionInterval") : 0;
                String description = !transaction.isNull("itemDescription") ? transaction.getString("itemDescription") : null;
                result.add(new Transaction(
                    transaction.getLong("id"),
                    transaction.getString("title"),
                    description,
                    new BigDecimal(transaction.getInt("amount")),
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault()).parse(date),
                    fixDate,
                    interval,
                    chosenOne
                ));
            }
        } catch (JSONException | ParseException ignored) {
            System.out.println("IZUZETAK: " + ignored.getMessage());
        }
        return result;
    }
}
