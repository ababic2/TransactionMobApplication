package ba.unsa.etf.rma.rma20babicamina92.presenters;

import ba.unsa.etf.rma.rma20babicamina92.models.Transaction;

public class DetailFragmentPresenter {
    private static DetailFragmentPresenter instance;

    private ListFragmentPresenter listFragmentPresenter;

    private DetailFragmentPresenter() {
        listFragmentPresenter = ListFragmentPresenter.getInstance();
    }

    public static DetailFragmentPresenter getInstance() {
        if (instance == null) {
            instance = new DetailFragmentPresenter();
        }
        return instance;
    }

    public Transaction getTransaction() {
        return listFragmentPresenter.getCurrentlySelectedTransaction();
    }


}
