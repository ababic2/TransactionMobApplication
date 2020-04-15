package ba.unsa.etf.rma.rma20babicamina92.presenters;

import ba.unsa.etf.rma.rma20babicamina92.fragments.TransactionDetailFragment;
import ba.unsa.etf.rma.rma20babicamina92.models.Transaction;

public class DetailFragmentPresenter {
    private static DetailFragmentPresenter instance;

    private ListFragmentPresenter listFragmentPresenter;
    private TransactionDetailFragment fragment;

    private DetailFragmentPresenter() {

    }

    public static DetailFragmentPresenter getInstance() {
        if (instance == null) {
            instance = new DetailFragmentPresenter();
        }
        return instance;
    }

    public void init(TransactionDetailFragment fragment) {
        listFragmentPresenter = ListFragmentPresenter.getInstance();
        this.fragment = fragment;
    }

    public Transaction getTransaction() {
        return listFragmentPresenter.getCurrentlySelectedTransaction();
    }


    void refresh() {
        if (fragment != null) {
            fragment.refresh();
        }
    }

    public void deleteTransaction(Transaction transaction) {
        listFragmentPresenter.deleteTransaction(transaction);
    }

    public void createTransaction(Transaction transaction) {
        listFragmentPresenter.createTransaction(transaction);
    }

    public void updateTransaction(Transaction oldTransaction, Transaction transaction) {
        listFragmentPresenter.updateTransaction(oldTransaction, transaction);
    }
}
