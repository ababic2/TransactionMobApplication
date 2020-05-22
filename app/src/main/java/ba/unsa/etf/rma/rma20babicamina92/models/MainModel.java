package ba.unsa.etf.rma.rma20babicamina92.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

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
        transactions = new ArrayList<Transaction>(
//                Arrays.asList(
//                        new Transaction(
//                                new Date(120,0,10),
//                                new BigDecimal(10),
//                                "prva",
//                                "description od prve",
//                                12,
//                                new Date(120,1,11),
//                                "INDIVIDUALPAYMENT"),
//                        new Transaction(
//                                new Date(120,0,13),
//                                new BigDecimal(18),
//                                "druga",
//                                "description od druge",
//                                5,
//                                new Date(120,1,11),
//                                "REGULARPAYMENT"),
//                        new Transaction(
//                                new Date(120,1,22),
//                                new BigDecimal(20),
//                                "treca",
//                                null,
//                                12,
//                                new Date(120,1,28),
//                                "INDIVIDUALINCOME"),
//                        new Transaction(
//                                new Date(120,2,2),
//                                new BigDecimal(55),
//                                "cetvrta",
//                                "description",
//                                12,
//                                new Date(120,2,11),
//                                "PURCHASE"),
//                        new Transaction(
//                                new Date(120,2,17),
//                                new BigDecimal(20),
//                                "peta",
//                                "description od pete",
//                                12,
//                                new Date(120,2,11),
//                                "INDIVIDUALPAYMENT"),
//                        new Transaction(
//                                new Date(120,2,29),
//                                new BigDecimal(33),
//                                "sesta",
//                                null,
//                                1,
//                                new Date(120,3,22),
//                                "INDIVIDUALINCOME"),
//                        new Transaction(
//                                new Date(120,3,15),
//                                new BigDecimal(15),
//                                "sedma",
//                                null,
//                                7,
//                                new Date(120,3,30),
//                                "REGULARINCOME"),
//                        new Transaction(
//                                new Date(120,4,2),
//                                new BigDecimal(18),
//                                "osma",
//                                "description od osme",
//                                4,
//                                new Date(120,4,11),
//                                "REGULARPAYMENT"),
//                        new Transaction(
//                                new Date(120,2,10),
//                                new BigDecimal(12),
//                                "deveta",
//                                "description od devete",
//                                22,
//                                new Date(120,4,11),
//                                "PURCHASE"),
//                        new Transaction(
//                                new Date(120,3,22),
//                                new BigDecimal(20),
//                                "deseta",
//                                "description",
//                                5,
//                                new Date(120,4,11),
//                                "REGULARPAYMENT"),
//                        new Transaction(
//                                new Date(120,0,11),
//                                new BigDecimal(10),
//                                "jedanaesta",
//                                "description od jedanaeste",
//                                12,
//                                new Date(120,0,11),
//                                "INDIVIDUALPAYMENT"),
//                        new Transaction(
//                                new Date(120,1,15),
//                                new BigDecimal(100),
//                                "dvanaesta",
//                                null,
//                                15,
//                                new Date(120,4,11),
//                                "REGULARINCOME"),
//                        new Transaction(
//                                new Date(120,2,11),
//                                new BigDecimal(190),
//                                "trinaesta",
//                                null,
//                                12,
//                                new Date(120,2,11),
//                                "INDIVIDUALINCOME"),
//                        new Transaction(
//                                new Date(120,0,22),
//                                new BigDecimal(15),
//                                "cetrnaesta",
//                                "description od cetrnaeste",
//                                12,
//                                new Date(120,4,11),
//                                "REGULARPAYMENT"),
//                        new Transaction(
//                                new Date(120,3,11),
//                                new BigDecimal(10),
//                                "petnaesta",
//                                "description od petnaeste",
//                                12,
//                                new Date(120,3,11),
//                                "INDIVIDUALPAYMENT"),
//                        new Transaction(
//                                new Date(120,1,22),
//                                new BigDecimal(2),
//                                "SESNAESTA",
//                                null,
//                                12,
//                                new Date(120,3,11),
//                                "REGULARINCOME"),
//                        new Transaction(
//                                new Date(120,2,11),
//                                new BigDecimal(22),
//                                "SEDAMNAESTAAAAA",
//                                "description od sedamnaesteeee",
//                                12,
//                                new Date(120,2,11),
//                                "INDIVIDUALPAYMENT"),
//                        new Transaction(
//                                new Date(120,4,24),
//                                new BigDecimal(1),
//                                "osamnaesta",
//                                "Idemo negdje na veceru. Na kolace i prohodati...",
//                                12,
//                                new Date(120,4,24),
//                                "INDIVIDUALPAYMENT"),
//                        new Transaction(
//                                new Date(120,0,22),
//                                new BigDecimal(9),
//                                "devetnaesta",
//                                "description of 19",
//                                12,
//                                new Date(120,1,11),
//                                "REGULARPAYMENT"),
//                        new Transaction(
//                                new Date(120,3,11),
//                                new BigDecimal(18),
//                                "dvadeseta",
//                                "description",
//                                12,
//                                new Date(120,4,11),
//                                "PURCHASE")
//                )
        );
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

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public void updateTransaction(Transaction oldTransaction, Transaction newTransaction) {
        int index = indexOf(oldTransaction);
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
