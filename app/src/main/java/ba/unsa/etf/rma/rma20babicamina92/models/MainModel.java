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

        private BigDecimal amount;
        private Date date;
        Payment(BigDecimal amount, Date date) {
            this.amount = amount;
            this.date = date;
        }

        BigDecimal getAmount() {
            return amount;
        }

        public void setAmount(BigDecimal amount) {
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
                new BigDecimal(19270.30),
                new BigDecimal(15000.00),
                new BigDecimal(1000.00)
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
                BigDecimal sign = new BigDecimal(1);
                if (element.getTransactionType().getName().toUpperCase().contains("INCOME")) {
                    sign = new BigDecimal(-1);
                }
                payments.add(new Payment(element.getAmount().multiply(sign), element.getDate()));
            }
        }
        Collections.sort(payments, (a, b) -> a.getDate().compareTo(b.getDate()));
        BigDecimal status = new BigDecimal(0);
        if (payments.size() == 0) {
            return false;
        }
        Date current = payments.get(0).getDate();
        for (int i = 0; i < payments.size(); i++) {
            Payment element = payments.get(i);
            if (current.getMonth() == element.getDate().getMonth()
                    && current.getYear() == element.getDate().getYear()) {
                status = status.add(element.getAmount());
                if (status.compareTo(account.getMonthLimit()) > 0) {
                    return true;
                }
            } else {
                current = element.getDate();
                status = element.getAmount();
                if (status.compareTo(account.getMonthLimit()) > 0) {
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
        BigDecimal status = new BigDecimal(0);

        for (int i = 0; i < testTransactions.size(); i++) {
            Transaction element = testTransactions.get(i);
            if (element.getTransactionType().getName().toUpperCase().contains("INCOME")) {
                if (element.getTransactionType().getName().toUpperCase().replace(" ", "").equals("REGULARINCOME")) {
                    long difference = element.getEndDate().getTime() - element.getDate().getTime();
                    long days = difference / millisecondsInADay;
                    long times = days / element.getTransactionInterval();
                    status = status.subtract(element.getAmount().multiply(new BigDecimal(times)));
                } else {
                    status = status.subtract(element.getAmount());
                }
            } else {
                if (element.getTransactionType().getName().toUpperCase().replace(" ","").equals("REGULARPAYMENT")) {
                    long difference = element.getEndDate().getTime() - element.getDate().getTime();
                    long days = difference / millisecondsInADay;
                    long times = days / element.getTransactionInterval();
                    status = status.add(element.getAmount().multiply(new BigDecimal(times)));
                } else {
                    status = status.add(element.getAmount());
                }
            }
        }
        //status veci od limita
        return status.compareTo(account.getTotalLimit()) > 0;
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
                old -= payment.amount.intValue();
            }
            saldo = old;
        } else {
            if (transaction.getTransactionType().getName().toLowerCase().contains("income")) {
                saldo = transaction.getAmount().intValue();
            } else {
                saldo = -transaction.getAmount().intValue();
            }
        }
        account.setBudget(account.getBudget().add(new BigDecimal(saldo)));
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
                old -= payment.amount.intValue();
            }
            before = old;
        } else {
            if (oldTransaction.getTransactionType().getName().toLowerCase().contains("income")) {
                before = oldTransaction.getAmount().intValue();
            } else {
                before = -oldTransaction.getAmount().intValue();
            }
        }
        if (newTransaction.getTransactionType().getName().toLowerCase().contains("regular")) {
            List<Payment> payments = convertRegularToIndividual(newTransaction);
            int old = 0;
            for (Payment payment : payments) {
                old -= payment.amount.intValue();
            }
            after = old;
        } else {
            if (newTransaction.getTransactionType().getName().toLowerCase().contains("income")) {
                after = newTransaction.getAmount().intValue();
            } else {
                after = -newTransaction.getAmount().intValue();
            }
        }
        account.setBudget(account.getBudget().add(new BigDecimal(after-before)));
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
            if (!that.getAmount().equals(transaction.getAmount())) {
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
        BigDecimal sign = new BigDecimal(1);
        if (transaction.getTransactionType().getName().toUpperCase().contains("INCOME")) {
            sign = new BigDecimal(-1);
        }
        List<Payment> payments = new ArrayList<>();

        // petlja koja prolazi kroz "dane" kada trebaju biti transakcije
        for (Date date = transaction.getDate();
             date.before(transaction.getEndDate())
                     || date.getTime() == transaction.getEndDate().getTime();
             date.setTime(date.getTime() + transaction.getTransactionInterval() * millisecondsInADay)) {
            payments.add(new Payment(transaction.getAmount().multiply(sign), date));
        }

        return payments;
    }
}
