package ba.unsa.etf.rma.rma20babicamina92.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ba.unsa.etf.rma.rma20babicamina92.R;
import ba.unsa.etf.rma.rma20babicamina92.presenters.BudgetPresenter;


public class BudgetFragment extends Fragment {

    private TextView budgetField;
    private EditText monthly, total;
    private Button save;

    private BudgetPresenter presenter;

    public BudgetFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_budget, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        budgetField = view.findViewById(R.id.budgetField);
        total = view.findViewById(R.id.totalLimitTextField);
        monthly = view.findViewById(R.id.monthlyLimitTextField);
        save = view.findViewById(R.id.saveLimitButton);

        presenter = BudgetPresenter.getInstance();
        presenter.init(this);

        save.setOnClickListener(event->{
            presenter.updateLimits(monthly.getText().toString(),total.getText().toString());
        });;
    }


    public void setBudgetFieldContent(String budget) {
        budgetField.setText(budget);
    }

    public void setMonthlyFieldContent(String monthly) {
        this.monthly.setText(monthly);
    }

    public void setTotalFieldContent(String total) {
        this.total.setText(total);
    }




}
