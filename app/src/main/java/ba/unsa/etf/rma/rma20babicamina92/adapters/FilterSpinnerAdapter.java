package ba.unsa.etf.rma.rma20babicamina92.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import ba.unsa.etf.rma.rma20babicamina92.R;
import ba.unsa.etf.rma.rma20babicamina92.models.FilterItem;

public class FilterSpinnerAdapter extends ArrayAdapter<FilterItem>{

    public FilterSpinnerAdapter(Context context, ArrayList<FilterItem> elements) {
        super(context, 0, elements);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (position == 0) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.filter_by_default_layout,parent,false
            );
        } else {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.filter_spinner,parent,false
            );
        }
        return initView(position,convertView,parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (position == 0) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.filter_by_default_layout,parent,false
            );
        } else {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.filter_spinner,parent,false
            );
        }
        return initView(position,convertView,parent);
    }

    private View initView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            if (position == 0) {
                convertView = LayoutInflater.from(getContext()).inflate(
                        R.layout.filter_by_default_layout,parent,false
                );
            } else {
                convertView = LayoutInflater.from(getContext()).inflate(
                        R.layout.filter_spinner,parent,false
                );
            }

        }

        if (position == 0) {
            TextView textView = convertView.findViewById(R.id.listItemTextView);
            textView.setText("Filter by");

        }else{
            ImageView imageView = convertView.findViewById(R.id.imageView);
            TextView textView = convertView.findViewById(R.id.listItemTextView);
            FilterItem currentItem = getItem(position);

            if(currentItem != null) {
                imageView.setImageResource(currentItem.getfImage());
                textView.setText(currentItem.getFilterName());
            }
        }

        return convertView;
    }
}
