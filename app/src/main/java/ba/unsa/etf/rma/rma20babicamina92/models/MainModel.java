package ba.unsa.etf.rma.rma20babicamina92.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import ba.unsa.etf.rma.rma20babicamina92.interactor.AccountPostInteractor;
import ba.unsa.etf.rma.rma20babicamina92.interactor.TransactionCreateInteractor;
import ba.unsa.etf.rma.rma20babicamina92.interactor.TransactionDeleteInteractor;
import ba.unsa.etf.rma.rma20babicamina92.interactor.TransactionUpdateInteractor;
import ba.unsa.etf.rma.rma20babicamina92.presenters.ListFragmentPresenter;

public class MainModel {
    private static class Payment {

        private int amount;
        private Date date;
        Payment(int amount, Date date) {
            this.amount = amount;
            this.date = date;
        }

        int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

    }
    public static long millisecondsInADay = 86400000;

    private static MainModel instance;
    private Account account;

    private ArrayList<Transaction> transactions;
    public static MainModel getInstance() {
        if (instance == null) {
            instance = new MainModel();
        }
        return instance;
    }


    private MainModel() {
        account = new Account(
                19270,
                15000,
                1000
        );
        initializeTransactions();
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    private void initializeTransactions() {
        transactions = new ArrayList<Transaction>();
    }

    public Account getAccount() {
        return account;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public boolean isOverMonthlyLimit(Transaction oldTransaction, Transaction transaction) {
        ArrayList<Transaction> testTransactions = new ArrayList<>(transactions);
        int index = indexOf(oldTransaction);
        testTransactions.set(index, transaction);
        return testIfOverMonthlyLimit(testTransactions);
    }

    private boolean testIfOverMonthlyLimit(ArrayList<Transaction> testTransactions) {
        List<Payment> payments = new ArrayList<>();
        for (int i = 0; i < testTransactions.size(); i++) {
            Transaction element = testTransactions.get(i);
            System.out.println(element);
            if (element.getTransactionType().getName().toUpperCase().contains("REGULAR")) {
                payments.addAll(convertRegularToIndividual(element));
            } else {
                int sign = (1);
                if (element.getTransactionType().getName().toUpperCase().contains("INCOME")) {
                    sign = (-1);
                }
                payments.add(new Payment(element.getAmount()*(sign), element.getDate()));
            }
        }
        Collections.sort(payments, (a, b) -> a.getDate().compareTo(b.getDate()));
        int status = (0);
        if (payments.size() == 0) {
            return false;
        }
        Date current = payments.get(0).getDate();
        for (int i = 0; i < payments.size(); i++) {
            Payment element = payments.get(i);
            if (current.getMonth() == element.getDate().getMonth()
                    && current.getYear() == element.getDate().getYear()) {
                status = status + (element.getAmount());
                if (status > account.getMonthLimit()) {
                    return true;
                }
            } else {
                current = element.getDate();
                status = element.getAmount();
                if (status > account.getMonthLimit()) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isOverTotalLimit(Transaction oldTransaction, Transaction transaction) {
        ArrayList<Transaction> testTransactions = new ArrayList<>(transactions);
        int index = indexOf(oldTransaction);
        testTransactions.set(index, transaction);

        return testIfOverTotalLimit(testTransactions);
    }

    private boolean testIfOverTotalLimit(ArrayList<Transaction> testTransactions) {
        int status = (0);

        for (int i = 0; i < testTransactions.size(); i++) {
            Transaction element = testTransactions.get(i);
            if (element.getTransactionType().getName().toUpperCase().contains("INCOME")) {
                if (element.getTransactionType().getName().toUpperCase().replace(" ", "").equals("REGULARINCOME")) {
                    long difference = element.getEndDate().getTime() - element.getDate().getTime();
                    long days = difference / millisecondsInADay;
                    int times = (int) (days / element.getTransactionInterval());
                    status = status - (element.getAmount() * (times));
                } else {
                    status = status - (element.getAmount());
                }
            } else {
                if (element.getTransactionType().getName().toUpperCase().replace(" ","").equals("REGULARPAYMENT")) {
                    long difference = element.getEndDate().getTime() - element.getDate().getTime();
                    long days = difference / millisecondsInADay;
                    int times = (int) (days / element.getTransactionInterval());
                    status = status + (element.getAmount()*(times));
                } else {
                    status = status + (element.getAmount());
                }
            }
        }
        //status veci od limita
        return status > account.getTotalLimit();
    }

    public boolean isOverMonthlyLimit(Transaction transaction) {
        ArrayList<Transaction> testTransactions = new ArrayList<>(transactions);
        testTransactions.add(transaction);
        return testIfOverMonthlyLimit(testTransactions);
    }

    public boolean isOverTotalLimit(Transaction transaction) {
        ArrayList<Transaction> testTransactions = new ArrayList<>(transactions);
        testTransactions.add(transaction);
        return testIfOverTotalLimit(testTransactions);
    }

    public void addTransactionsFromWeb(ArrayList<Transaction> transactions) {
        this.transactions.addAll(transactions);
    }

    public void addTransaction(Transaction transaction) {
        int saldo = 0;
        if (transaction.getTransactionType().getName().toLowerCase().contains("regular")) {
            List<Payment> payments = convertRegularToIndividual(transaction);
            int old = 0;
            for (Payment payment : payments) {
                old -= payment.amount;
            }
            saldo = old;
        } else {
            if (transaction.getTransactionType().getName().toLowerCase().contains("income")) {
                saldo = transaction.getAmount();
            } else {
                saldo = -transaction.getAmount();
            }
        }
        account.setBudget(account.getBudget() + (saldo));
        new TransactionCreateInteractor(ListFragmentPresenter.getInstance().getView().getMainActivity()).execute(transaction);
        new AccountPostInteractor(ListFragmentPresenter.getInstance().getView().getMainActivity()).execute(account);
        transactions.add(transaction);
    }

    public void updateTransaction(Transaction oldTransaction, Transaction newTransaction) {
        int index = indexOf(oldTransaction);
        int before = 0;
        int after = 0;
        if (oldTransaction.getTransactionType().getName().toLowerCase().contains("regular")) {
            List<Payment> payments = convertRegularToIndividual(oldTransaction);
            int old = 0;
            for (Payment payment : payments) {
                old -= payment.amount;
            }
            before = old;
        } else {
            if (oldTransaction.getTransactionType().getName().toLowerCase().contains("income")) {
                before = oldTransaction.getAmount();
            } else {
                before = -oldTransaction.getAmount();
            }
        }
        if (newTransaction.getTransactionType().getName().toLowerCase().contains("regular")) {
            List<Payment> payments = convertRegularToIndividual(newTransaction);
            int old = 0;
            for (Payment payment : payments) {
                old -= payment.amount;
            }
            after = old;
        } else {
            if (newTransaction.getTransactionType().getName().toLowerCase().contains("income")) {
                after = newTransaction.getAmount();
            } else {
                after = -newTransaction.getAmount();
            }
        }
        account.setBudget(account.getBudget()+(after-before));
        newTransaction.setId(oldTransaction.getId());
        new TransactionUpdateInteractor(ListFragmentPresenter.getInstance().getView().getMainActivity()).execute(newTransaction);
        new AccountPostInteractor(ListFragmentPresenter.getInstance().getView().getMainActivity()).execute(account);
        transactions.set(index, newTransaction);
    }

    private int indexOf(Transaction that) {
        for (int i = 0; i < transactions.size(); i++) {
            Transaction transaction = transactions.get(i);
            if (!that.getTransactionType().equals(transaction.getTransactionType())) {
                continue;
            }
            if (!that.getTitle().equals(transaction.getTitle())) {
                continue;
            }
            if (xOrNull(that.getItemDescription(), transaction.getItemDescription())) {
                continue;
            }
            if (that.getItemDescription()!=null && !that.getItemDescription().equals(transaction.getItemDescription())) {
                continue;
            }
            if (!(that.getAmount()==(transaction.getAmount()))) {
                continue;
            }
            if (!that.getDate().equals(transaction.getDate())) {
                continue;
            }
            if (xOrNull(that.getEndDate(), transaction.getEndDate())) {
                continue;
            }
            if (that.getEndDate()!=null && !that.getEndDate().equals(transaction.getEndDate())) {
                continue;
            }
            if (xOrNull(that.getTransactionInterval(), transaction.getTransactionInterval())) {
                continue;
            }
            if (that.getTransactionInterval()!=null && !that.getTransactionInterval().equals(transaction.getTransactionInterval())) {
                continue;
            }
            return i;
        }
        return -1;
    }

    private boolean xOrNull(Object a, Object b) {
        return (a == null && b !=null)
                || (a != null && b ==null);
    }


    public void deleteTransaction(Transaction transaction) {
        new TransactionDeleteInteractor(ListFragmentPresenter.getInstance().getView().getMainActivity()).execute(transaction);
        transactions.remove(transaction);
    }

    private List<Payment> convertRegularToIndividual(Transaction transaction){
        int sign = (1);
        if (transaction.getTransactionType().getName().toUpperCase().contains("INCOME")) {
            sign = (-1);
        }
        List<Payment> payments = new ArrayList<>();

        // petlja koja prolazi kroz "dane" kada trebaju biti transakcije
        for (Date date = transaction.getDate();
             date.before(transaction.getEndDate())
                     || date.getTime() == transaction.getEndDate().getTime();
             date.setTime(date.getTime() + transaction.getTransactionInterval() * millisecondsInADay)) {
            payments.add(new Payment(transaction.getAmount()*(sign), date));
        }

        return payments;
    }
}
