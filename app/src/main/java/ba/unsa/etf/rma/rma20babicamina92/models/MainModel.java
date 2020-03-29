package ba.unsa.etf.rma.rma20babicamina92.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class MainModel {
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

    private void initializeTransactions() {
        transactions = new ArrayList<Transaction>(
                Arrays.asList(
                        new Transaction(
                                new GregorianCalendar(
                                        2020,
                                        Calendar.FEBRUARY,
                                        11
                                ).getTime(),
                                186,
                                "lijekovi",
                                "description",
                                12,
                                new GregorianCalendar(
                                        2020,
                                        Calendar.FEBRUARY,
                                        11
                                ).getTime(),
                                "INDIVIDUALPAYMENT"),
                        new Transaction(
                                new GregorianCalendar(
                                        2020,
                                        Calendar.FEBRUARY,
                                        22
                                ).getTime(),
                                200,
                                "kompjuteri",
                                "description",
                                12,
                                new GregorianCalendar(
                                        2020,
                                        Calendar.FEBRUARY,
                                        11
                                ).getTime(),
                                "REGULARPAYMENT"),
                        new Transaction(
                                new GregorianCalendar(
                                        2020,
                                        Calendar.FEBRUARY,
                                        11
                                ).getTime(),
                                186,
                                "lijekovi",
                                "description",
                                12,
                                new GregorianCalendar(
                                        2020,
                                        Calendar.FEBRUARY,
                                        11
                                ).getTime(),
                                "INDIVIDUALPAYMENT"),
                        new Transaction(
                                new GregorianCalendar(
                                        2020,
                                        Calendar.FEBRUARY,
                                        22
                                ).getTime(),
                                200,
                                "kompjuteri",
                                "description",
                                12,
                                new GregorianCalendar(
                                        2020,
                                        Calendar.FEBRUARY,
                                        11
                                ).getTime(),
                                "REGULARPAYMENT"),
                        new Transaction(
                                new GregorianCalendar(
                                        2020,
                                        Calendar.FEBRUARY,
                                        11
                                ).getTime(),
                                186,
                                "lijekovi",
                                "description",
                                12,
                                new GregorianCalendar(
                                        2020,
                                        Calendar.FEBRUARY,
                                        11
                                ).getTime(),
                                "INDIVIDUALPAYMENT"),
                        new Transaction(
                                new GregorianCalendar(
                                        2020,
                                        Calendar.FEBRUARY,
                                        22
                                ).getTime(),
                                200,
                                "kompjuteri",
                                "description",
                                12,
                                new GregorianCalendar(
                                        2020,
                                        Calendar.FEBRUARY,
                                        11
                                ).getTime(),
                                "REGULARPAYMENT"),
                        new Transaction(
                                new GregorianCalendar(
                                        2020,
                                        Calendar.FEBRUARY,
                                        11
                                ).getTime(),
                                186,
                                "lijekovi",
                                "description",
                                12,
                                new GregorianCalendar(
                                        2020,
                                        Calendar.FEBRUARY,
                                        11
                                ).getTime(),
                                "INDIVIDUALPAYMENT"),
                        new Transaction(
                                new GregorianCalendar(
                                        2020,
                                        Calendar.FEBRUARY,
                                        22
                                ).getTime(),
                                200,
                                "kompjuteri",
                                "description",
                                12,
                                new GregorianCalendar(
                                        2020,
                                        Calendar.FEBRUARY,
                                        11
                                ).getTime(),
                                "REGULARPAYMENT"),
                        new Transaction(
                                new GregorianCalendar(
                                        2020,
                                        Calendar.FEBRUARY,
                                        11
                                ).getTime(),
                                186,
                                "lijekovi",
                                "description",
                                12,
                                new GregorianCalendar(
                                        2020,
                                        Calendar.FEBRUARY,
                                        11
                                ).getTime(),
                                "INDIVIDUALPAYMENT"),
                        new Transaction(
                                new GregorianCalendar(
                                        2020,
                                        Calendar.FEBRUARY,
                                        22
                                ).getTime(),
                                200,
                                "kompjuteri",
                                "description",
                                12,
                                new GregorianCalendar(
                                        2020,
                                        Calendar.FEBRUARY,
                                        11
                                ).getTime(),
                                "REGULARPAYMENT"),
                        new Transaction(
                                new GregorianCalendar(
                                        2020,
                                        Calendar.FEBRUARY,
                                        11
                                ).getTime(),
                                186,
                                "lijekovi",
                                "description",
                                12,
                                new GregorianCalendar(
                                        2020,
                                        Calendar.FEBRUARY,
                                        11
                                ).getTime(),
                                "INDIVIDUALPAYMENT"),
                        new Transaction(
                                new GregorianCalendar(
                                        2020,
                                        Calendar.FEBRUARY,
                                        22
                                ).getTime(),
                                200,
                                "kompjuteri",
                                "description",
                                12,
                                new GregorianCalendar(
                                        2020,
                                        Calendar.FEBRUARY,
                                        11
                                ).getTime(),
                                "REGULARPAYMENT"),
                        new Transaction(
                                new GregorianCalendar(
                                        2020,
                                        Calendar.FEBRUARY,
                                        11
                                ).getTime(),
                                186,
                                "lijekovi",
                                "description",
                                12,
                                new GregorianCalendar(
                                        2020,
                                        Calendar.FEBRUARY,
                                        11
                                ).getTime(),
                                "INDIVIDUALPAYMENT"),
                        new Transaction(
                                new GregorianCalendar(
                                        2020,
                                        Calendar.FEBRUARY,
                                        22
                                ).getTime(),
                                200,
                                "kompjuteri",
                                "description",
                                12,
                                new GregorianCalendar(
                                        2020,
                                        Calendar.FEBRUARY,
                                        11
                                ).getTime(),
                                "REGULARPAYMENT"),
                        new Transaction(
                                new GregorianCalendar(
                                        2020,
                                        Calendar.FEBRUARY,
                                        11
                                ).getTime(),
                                186,
                                "lijekovi",
                                "description",
                                12,
                                new GregorianCalendar(
                                        2020,
                                        Calendar.FEBRUARY,
                                        11
                                ).getTime(),
                                "INDIVIDUALPAYMENT"),
                        new Transaction(
                                new GregorianCalendar(
                                        2020,
                                        Calendar.FEBRUARY,
                                        22
                                ).getTime(),
                                200,
                                "kompjuteri",
                                "description",
                                12,
                                new GregorianCalendar(
                                        2020,
                                        Calendar.FEBRUARY,
                                        11
                                ).getTime(),
                                "REGULARPAYMENT"),
                        new Transaction(
                                new GregorianCalendar(
                                        2020,
                                        Calendar.FEBRUARY,
                                        11
                                ).getTime(),
                                186,
                                "lijekovi",
                                "description",
                                12,
                                new GregorianCalendar(
                                        2020,
                                        Calendar.FEBRUARY,
                                        11
                                ).getTime(),
                                "INDIVIDUALPAYMENT"),
                        new Transaction(
                                new GregorianCalendar(
                                        2020,
                                        Calendar.FEBRUARY,
                                        22
                                ).getTime(),
                                200,
                                "kompjuteri",
                                "description",
                                12,
                                new GregorianCalendar(
                                        2020,
                                        Calendar.FEBRUARY,
                                        11
                                ).getTime(),
                                "REGULARPAYMENT"),
                        new Transaction(
                                new GregorianCalendar(
                                        2020,
                                        Calendar.FEBRUARY,
                                        22
                                ).getTime(),
                                200,
                                "kompjuteri",
                                "description",
                                12,
                                new GregorianCalendar(
                                        2020,
                                        Calendar.FEBRUARY,
                                        11
                                ).getTime(),
                                "REGULARPAYMENT"),
                        new Transaction(
                                new GregorianCalendar(
                                        2020,
                                        Calendar.APRIL,
                                        11
                                ).getTime(),
                                1225,
                                "treca",
                                "description",
                                12,
                                new GregorianCalendar(
                                        2020,
                                        Calendar.MAY,
                                        11
                                ).getTime(),
                                "PURCHASE")
                )
        );
    }

    public Account getAccount() {
        return account;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public boolean isOverMonthlyLimit(BigDecimal transactionAmount) {
        return false;
    }

    public boolean isOverTotalLimit(BigDecimal transactionAmount) {
        return false;
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public void updateTransaction(Transaction oldTransaction, Transaction newTransaction) {
        int index = transactions.indexOf(oldTransaction);
        transactions.set(index, newTransaction);
    }

    public void deleteTransaction(Transaction transaction) {
        transactions.remove(transaction);
    }
}
