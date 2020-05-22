package ba.unsa.etf.rma.rma20babicamina92;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import java.util.Objects;

import ba.unsa.etf.rma.rma20babicamina92.fragments.BudgetFragment;
import ba.unsa.etf.rma.rma20babicamina92.fragments.GraphsFragment;
import ba.unsa.etf.rma.rma20babicamina92.fragments.TransactionDetailFragment;
import ba.unsa.etf.rma.rma20babicamina92.fragments.TransactionListFragment;
import ba.unsa.etf.rma.rma20babicamina92.interactor.TransactionTypeInteractor;
import ba.unsa.etf.rma.rma20babicamina92.utils.SimpleGestureFilter;

public class MainActivity extends FragmentActivity implements
        SimpleGestureFilter.SimpleGestureListener {

    private static ProgressDialog dialog;
    private SimpleGestureFilter detector;

    private TransactionListFragment masterFragment;
    private TransactionDetailFragment detailFragment;

    public static int slider = 0;

    public static boolean twoPane;

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    public static void loadingOn(MainActivity mainActivity) {
        if (dialog != null) {
            dialog.dismiss();
        }
        dialog = ProgressDialog.show(mainActivity, "",
                "Loading. Please wait...", true);
    }

    public static void loadingOff(MainActivity mainActivity) {
        if (dialog == null) {
            return;
        }
        dialog.dismiss();
        dialog = null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        twoPane = false;
        setContentView(R.layout.activity_main);
        cleanStartFragments();

        // Detect touched area
        if (!twoPane) {
            detector = new SimpleGestureFilter(MainActivity.this, this);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent me) {
        // Call onTouchEvent of SimpleGestureFilter class
        if (!twoPane) {
            this.detector.onTouchEvent(me);
        }
        return super.dispatchTouchEvent(me);
    }



    private void cleanStartFragments() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (slider == 0) {
            if (fragmentManager.findFragmentById(R.id.transaction_list) != null) {
                fragmentManager.beginTransaction()
                        .remove(Objects.requireNonNull(fragmentManager.findFragmentById(R.id.transaction_list)));
            }
            if (fragmentManager.findFragmentById(R.id.transaction_detail) != null) {
                fragmentManager.beginTransaction()
                        .remove(Objects.requireNonNull(fragmentManager.findFragmentById(R.id.transaction_detail)));
            }
            Fragment fragment = fragmentManager.findFragmentById(R.id.transaction_list);
            if(fragment instanceof BudgetFragment || fragment instanceof GraphsFragment){
                fragmentManager.beginTransaction().remove(fragment).commit();
                fragment = null;
            }
            masterFragment = (TransactionListFragment) fragment;
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
        } else if (slider == 1) {
            Fragment fragment = fragmentManager.findFragmentById(R.id.transaction_list);
            if (fragment != null) {
                fragmentManager.beginTransaction().remove(fragment).commit();
            }
            fragmentManager.beginTransaction().add(R.id.transaction_list, new BudgetFragment()).commit();
        } else if (slider == 2) {
            Fragment fragment = fragmentManager.findFragmentById(R.id.transaction_list);
            if (fragment != null) {
                fragmentManager.beginTransaction().remove(fragment).commit();
            }
            fragmentManager.beginTransaction().add(R.id.transaction_list, new GraphsFragment()).commit();
        }
    }

    public void clickedOnTransaction() {
        if (detailFragment == null) {
            detailFragment = new TransactionDetailFragment();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.transaction_list, detailFragment)
                .addToBackStack(null)
        .commit();
    }

    public void afterSubmitActionOnDetailFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
        fragmentManager.beginTransaction()
                .replace(R.id.transaction_list, masterFragment)
                .commit();
    }

    @Override
    public void onSwipe(int direction) {
        //Detect the swipe gestures and display toast
        switch (direction) {
            case SimpleGestureFilter.SWIPE_RIGHT:
                slider = (slider == 0) ? 2 : slider - 1;
                cleanStartFragments();
                break;
            case SimpleGestureFilter.SWIPE_LEFT:
                slider = (slider+1)%3;
                cleanStartFragments();
                break;
            case SimpleGestureFilter.SWIPE_DOWN:
            case SimpleGestureFilter.SWIPE_UP:
                break;
        }
    }

    @Override
    public void onDoubleTap() {
    }
}