package ba.unsa.etf.rma.rma20babicamina92.offline_tools;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ConnectivityChangeReceiver extends BroadcastReceiver {
    public Receiver myReceiver;

    public ConnectivityChangeReceiver(){

    }

    public void setMyReceiver(Receiver receiver) {
        myReceiver = receiver;
    }

    @Override
    public void onReceive(final Context context, final Intent intent) {

        int status = ConnectivityGetter.getConnectivityStatusString(context);
        if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
            if (status == ConnectivityGetter.NETWORK_STATUS_NOT_CONNECTED) {
                myReceiver.onReceive(false);
            } else {
                myReceiver.onReceive(true);
            }
        }
    }

    public interface Receiver {
        void onReceive(boolean connected);
    }
}
