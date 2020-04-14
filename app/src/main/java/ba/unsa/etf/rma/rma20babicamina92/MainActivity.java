package ba.unsa.etf.rma.rma20babicamina92;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import ba.unsa.etf.rma.rma20babicamina92.fragments.TransactionListFragment;

public class MainActivity extends FragmentActivity {

    private Fragment masterFragment;

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fragmentManager = getSupportFragmentManager();
        masterFragment = fragmentManager.findFragmentById(R.id.transaction_list);
        if (masterFragment == null) {
            masterFragment = TransactionListFragment.getInstance();
            fragmentManager
                    .beginTransaction()
                    .add(R.id.transaction_list, masterFragment)
                    .commit();
        } else {
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }
}