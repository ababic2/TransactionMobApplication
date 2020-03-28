//package ba.unsa.etf.rma.rma20babicamina92.adapters;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//
//import org.w3c.dom.Text;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import ba.unsa.etf.rma.rma20babicamina92.R;
//import ba.unsa.etf.rma.rma20babicamina92.models.FilterItem;
//import ba.unsa.etf.rma.rma20babicamina92.models.TransactionListItem;
//
//public class TransactionListAdapter extends ArrayAdapter<TransactionListItem> {
//    private int resource;
//
//    public TransactionListAdapter(@NonNull Context context, int resource, @NonNull List<TransactionListItem> elements) {
//        super(context, 0, elements);
//        this.resource = resource;
//    }
//    @NonNull
//    @Override
//    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//       return initView(position,convertView,parent);
//    }
//    private View initView(int position, View convertView, ViewGroup parent) {
//        if(convertView == null) {
//            convertView = LayoutInflater.from(getContext()).inflate(
//                    R.layout.transaction_list,parent,false
//            );
//        }
//        ImageView imageView = convertView.findViewById(R.id.transactionImageView);
//        TextView textView1 = convertView.findViewById(R.id.titleText);
//        TextView textView2 = convertView.findViewById(R.id.amountText);
//
//
//        TransactionListItem currentItem = getItem(position);
//
//        if(currentItem != null) {
//            imageView.setImageResource(currentItem.gettImage());
//            textView1.setText(currentItem.getTitle());
//            textView2.setText(currentItem.getAmount());
//        }
//        return convertView;
//    }
//}
