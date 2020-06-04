package ba.unsa.etf.rma.rma20babicamina92.presenters;

import java.math.BigDecimal;
import java.util.Locale;

import ba.unsa.etf.rma.rma20babicamina92.MainActivity;
import ba.unsa.etf.rma.rma20babicamina92.fragments.BudgetFragment;
import ba.unsa.etf.rma.rma20babicamina92.interactor.AccountPostInteractor;
import ba.unsa.etf.rma.rma20babicamina92.models.MainModel;

public class BudgetPresenter {
    private static BudgetPresenter instance;
    private MainModel model;
    private BudgetFragment fragment;

    private BudgetPresenter() {
        model = MainModel.getInstance();
    }

    public static BudgetPresenter getInstance() {
        if (instance == null) {
            instance = new BudgetPresenter();
        }
        return instance;
    }

    public void init(BudgetFragment fragment) {
        this.fragment = fragment;
        updateFragment(fragment);
    }

    private void updateFragment(BudgetFragment fragment) {
        int budget = model.getAccount().getBudget();
        int monthly = model.getAccount().getMonthLimit();
        int total = model.getAccount().getTotalLimit();
        fragment.setBudgetFieldContent(String.format(Locale.getDefault(),"Budget: %d",budget));
        fragment.setMonthlyFieldContent(String.format(Locale.getDefault(),"%d",monthly));
        fragment.setTotalFieldContent(String.format(Locale.getDefault(),"%d",total));
    }

    public void updateLimits(String monthly, String total) {
        int monthlyLimit = new BigDecimal(monthly).intValue();
        int totalLimit = new BigDecimal(total).intValue();
        model.getAccount().setMonthLimit(monthlyLimit);
        model.getAccount().setTotalLimit(totalLimit);
        updateFragment(fragment);
        new AccountPostInteractor((MainActivity) fragment.getActivity()).execute(model.getAccount());
    }

}
