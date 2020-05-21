package ba.unsa.etf.rma.rma20babicamina92.interactor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import ba.unsa.etf.rma.rma20babicamina92.R;
import ba.unsa.etf.rma.rma20babicamina92.models.FilterItem;

class JsonDecoder {
    public static ArrayList<FilterItem> decodeTransactionTypes(String result) {
        ArrayList<Integer> icons = new ArrayList<>(Arrays.asList(R.mipmap.ic_one, R.mipmap.ic_two, R.mipmap.ic_three, R.mipmap.ic_four, R.mipmap.ic_five, R.mipmap.ic_six));
        ArrayList<FilterItem> filterItems = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray rows = jsonObject.getJSONArray("rows");
            for (int i = 0; i < jsonObject.getInt("count"); i++) {
                filterItems.add(new FilterItem(rows.getJSONObject(i).getString("name"), icons.get(i)));
            }
        } catch (JSONException e) {

        }
        return filterItems;
    }
}
