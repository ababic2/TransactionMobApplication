package ba.unsa.etf.rma.rma20babicamina92;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import java.util.Objects;

import ba.unsa.etf.rma.rma20babicamina92.fragments.TransactionDetailFragment;
import ba.unsa.etf.rma.rma20babicamina92.fragments.TransactionListFragment;
import ba.unsa.etf.rma.rma20babicamina92.utils.SimpleGestureFilter;

public class MainActivity extends FragmentActivity implements
        SimpleGestureFilter.SimpleGestureListener {

    private SimpleGestureFilter detector;

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

        // Detect touched area
        detector = new SimpleGestureFilter(MainActivity.this, this);

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent me) {
        // Call onTouchEvent of SimpleGestureFilter class
        this.detector.onTouchEvent(me);
        return super.dispatchTouchEvent(me);
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

    @Override
    public void onSwipe(int direction) {
        //Detect the swipe gestures and display toast
        String showToastMessage = "";
        System.out.println("DIRECTION UNKNOWN");
        switch (direction) {

            case SimpleGestureFilter.SWIPE_RIGHT:
                showToastMessage = "You have Swiped Right.";
                break;
            case SimpleGestureFilter.SWIPE_LEFT:
                showToastMessage = "You have Swiped Left.";
                break;
            case SimpleGestureFilter.SWIPE_DOWN:
                showToastMessage = "You have Swiped Down.";
                break;
            case SimpleGestureFilter.SWIPE_UP:
                showToastMessage = "You have Swiped Up.";
                break;

        }
        Toast.makeText(this, showToastMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDoubleTap() {

    }
}