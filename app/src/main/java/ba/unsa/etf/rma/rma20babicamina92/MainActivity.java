package ba.unsa.etf.rma.rma20babicamina92;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import java.util.Objects;

import ba.unsa.etf.rma.rma20babicamina92.fragments.TransactionDetailFragment;
import ba.unsa.etf.rma.rma20babicamina92.fragments.TransactionListFragment;

public class MainActivity extends FragmentActivity {

    private TransactionListFragment masterFragment;
    private TransactionDetailFragment detailFragment;
    public static boolean twoPane;

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    protected void onStart() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
        Fragment fragment = fragmentManager.findFragmentById(R.id.transaction_list);
        if (fragment != null) {
//            if (fragment instanceof TransactionListFragment) {
//                fragmentManager
//                        .beginTransaction()
//                        .remove(masterFragment)
//                        .commit();
//            }
            if (fragment instanceof TransactionDetailFragment) {
                fragmentManager
                        .beginTransaction()
                        .remove(detailFragment)
                        .commit();
            }
        }
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        twoPane = false;
        setContentView(R.layout.activity_main);
        cleanStartFragments();
    }

    private void cleanStartFragments() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.findFragmentById(R.id.transaction_list) != null) {
            fragmentManager.beginTransaction()
                    .remove(Objects.requireNonNull(fragmentManager.findFragmentById(R.id.transaction_list)));
        }
        if (fragmentManager.findFragmentById(R.id.transaction_detail) != null) {
            fragmentManager.beginTransaction()
                    .remove(Objects.requireNonNull(fragmentManager.findFragmentById(R.id.transaction_detail)));
        }
        masterFragment = (TransactionListFragment) fragmentManager.findFragmentById(R.id.transaction_list);
        FrameLayout detailFrame = findViewById(R.id.transaction_detail);
        if (masterFragment == null) {
            masterFragment = TransactionListFragment.getInstance();
            fragmentManager
                    .beginTransaction()
                    .add(R.id.transaction_list, masterFragment)
                    .commit();

        } else {
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        if (detailFrame != null) {
            twoPane = true;
            if (detailFragment == null) {
                detailFragment = new TransactionDetailFragment();
            }
            if (fragmentManager.findFragmentById(R.id.transaction_detail) == null) {
                fragmentManager.beginTransaction()
                        .add(R.id.transaction_detail, detailFragment)
                        .commit();
            }
        }
    }

    public void clickedOnTransaction() {
        if (detailFragment == null) {
            detailFragment = new TransactionDetailFragment();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.transaction_list, detailFragment)
//                .addToBackStack(null)
        .commit();
    }

    public void afterSubmitActionOnDetailFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.transaction_list, masterFragment)
                .commit();
    }
}