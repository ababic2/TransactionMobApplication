package ba.unsa.etf.rma.rma20babicamina92.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
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

    private void initializeTransactions() {
        transactions = new ArrayList<Transaction>(
                Arrays.asList(
                        new Transaction(
                                new GregorianCalendar(
                                        2020,
                                        Calendar.JANUARY,
                                        10
                                ).getTime(),
                                new BigDecimal(10),
                                "prva",
                                "description od prve",
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
                                        Calendar.JANUARY,
                                        13
                                ).getTime(),
                                new BigDecimal(18),
                                "druga",
                                "description od druge",
                                5,
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
                                new BigDecimal(20),
                                "treca",
                                null,
                                12,
                                new GregorianCalendar(
                                        2020,
                                        Calendar.FEBRUARY,
                                        28
                                ).getTime(),
                                "INDIVIDUALINCOME"),
                        new Transaction(
                                new GregorianCalendar(
                                        2020,
                                        Calendar.MARCH,
                                        2
                                ).getTime(),
                                new BigDecimal(55),
                                "cetvrta",
                                "description",
                                12,
                                new GregorianCalendar(
                                        2020,
                                        Calendar.MARCH,
                                        11
                                ).getTime(),
                                "PURCHASE"),
                        new Transaction(
                                new GregorianCalendar(
                                        2020,
                                        Calendar.MARCH,
                                        17
                                ).getTime(),
                                new BigDecimal(20),
                                "peta",
                                "description od pete",
                                12,
                                new GregorianCalendar(
                                        2020,
                                        Calendar.MARCH,
                                        11
                                ).getTime(),
                                "INDIVIDUALPAYMENT"),
                        new Transaction(
                                new GregorianCalendar(
                                        2020,
                                        Calendar.MARCH,
                                        29
                                ).getTime(),
                                new BigDecimal(33),
                                "sesta",
                                null,
                                1,
                                new GregorianCalendar(
                                        2020,
                                        Calendar.APRIL,
                                        22
                                ).getTime(),
                                "INDIVIDUALINCOME"),
                        new Transaction(
                                new GregorianCalendar(
                                        2020,
                                        Calendar.APRIL,
                                        15
                                ).getTime(),
                                new BigDecimal(15),
                                "sedma",
                                null,
                                7,
                                new GregorianCalendar(
                                        2020,
                                        Calendar.APRIL,
                                        30
                                ).getTime(),
                                "REGULARINCOME"),
                        new Transaction(
                                new GregorianCalendar(
                                        2020,
                                        Calendar.MAY,
                                        2
                                ).getTime(),
                                new BigDecimal(18),
                                "osma",
                                "description od osme",
                                4,
                                new GregorianCalendar(
                                        2020,
                                        Calendar.MAY,
                                        11
                                ).getTime(),
                                "REGULARPAYMENT"),
                        new Transaction(
                                new GregorianCalendar(
                                        2020,
                                        Calendar.MARCH,
                                        10
                                ).getTime(),
                                new BigDecimal(12),
                                "deveta",
                                "description od devete",
                                22,
                                new GregorianCalendar(
                                        2020,
                                        Calendar.MAY,
                                        11
                                ).getTime(),
                                "PURCHASE"),
                        new Transaction(
                                new GregorianCalendar(
                                        2020,
                                        Calendar.APRIL,
                                        22
                                ).getTime(),
                                new BigDecimal(20),
                                "deseta",
                                "description",
                                5,
                                new GregorianCalendar(
                                        2020,
                                        Calendar.MAY,
                                        11
                                ).getTime(),
                                "REGULARPAYMENT"),
                        new Transaction(
                                new GregorianCalendar(
                                        2020,
                                        Calendar.JANUARY,
                                        11
                                ).getTime(),
                                new BigDecimal(10),
                                "jedanaesta",
                                "description od jedanaeste",
                                12,
                                new GregorianCalendar(
                                        2020,
                                        Calendar.JANUARY,
                                        11
                                ).getTime(),
                                "INDIVIDUALPAYMENT"),
                        new Transaction(
                                new GregorianCalendar(
                                        2020,
                                        Calendar.FEBRUARY,
                                        15
                                ).getTime(),
                                new BigDecimal(100),
                                "dvanaesta",
                                null,
                                15,
                                new GregorianCalendar(
                                        2020,
                                        Calendar.MAY,
                                        11
                                ).getTime(),
                                "REGULARINCOME"),
                        new Transaction(
                                new GregorianCalendar(
                                        2020,
                                        Calendar.MARCH,
                                        11
                                ).getTime(),
                                new BigDecimal(190),
                                "trinaesta",
                                null,
                                12,
                                new GregorianCalendar(
                                        2020,
                                        Calendar.MARCH,
                                        11
                                ).getTime(),
                                "INDIVIDUALINCOME"),
                        new Transaction(
                                new GregorianCalendar(
                                        2020,
                                        Calendar.JANUARY,
                                        22
                                ).getTime(),
                                new BigDecimal(15),
                                "cetrnaesta",
                                "description od cetrnaeste",
                                12,
                                new GregorianCalendar(
                                        2020,
                                        Calendar.MAY,
                                        11
                                ).getTime(),
                                "REGULARPAYMENT"),
                        new Transaction(
                                new GregorianCalendar(
                                        2020,
                                        Calendar.APRIL,
                                        11
                                ).getTime(),
                                new BigDecimal(10),
                                "petnaesta",
                                "description od petnaeste",
                                12,
                                new GregorianCalendar(
                                        2020,
                                        Calendar.APRIL,
                                        11
                                ).getTime(),
                                "INDIVIDUALPAYMENT"),
                        new Transaction(
                                new GregorianCalendar(
                                        2020,
                                        Calendar.FEBRUARY,
                                        22
                                ).getTime(),
                                new BigDecimal(2),
                                "SESNAESTA",
                                null,
                                12,
                                new GregorianCalendar(
                                        2020,
                                        Calendar.APRIL,
                                        11
                                ).getTime(),
                                "REGULARINCOME"),
                        new Transaction(
                                new GregorianCalendar(
                                        2020,
                                        Calendar.MARCH,
                                        11
                                ).getTime(),
                                new BigDecimal(22),
                                "SEDAMNAESTAAAAA",
                                "description od sedamnaesteeee",
                                12,
                                new GregorianCalendar(
                                        2020,
                                        Calendar.MARCH,
                                        11
                                ).getTime(),
                                "INDIVIDUALPAYMENT"),
                        new Transaction(
                                new GregorianCalendar(
                                        2020,
                                        Calendar.MAY,
                                        24
                                ).getTime(),
                                new BigDecimal(1),
                                "osamnaesta",
                                "Idemo negdje na veceru. Na kolace i prohodati...",
                                12,
                                new GregorianCalendar(
                                        2020,
                                        Calendar.MAY,
                                        24
                                ).getTime(),
                                "INDIVIDUALPAYMENT"),
                        new Transaction(
                                new GregorianCalendar(
                                        2020,
                                        Calendar.JANUARY,
                                        22
                                ).getTime(),
                                new BigDecimal(9),
                                "devetnaesta",
                                "description of 19",
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
                                new BigDecimal(18),
                                "dvadeseta",
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
            if (element.getTransactionType().toString().contains("REGULAR")) {
                payments.addAll(convertRegularToIndividual(element));
            } else {
                BigDecimal sign = new BigDecimal(1);
                if (element.getTransactionType().toString().contains("INCOME")) {
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
            if (element.getTransactionType().toString().contains("INCOME")) {
                if (element.getTransactionType().toString().equals("REGULARINCOME")) {
                    long difference = element.getEndDate().getTime() - element.getDate().getTime();
                    long days = difference / millisecondsInADay;
                    long times = days / element.getTransactionInterval();
                    status = status.subtract(element.getAmount().multiply(new BigDecimal(times)));
                } else {
                    status = status.subtract(element.getAmount());
                }
            } else {
                if (element.getTransactionType().toString().equals("REGULARPAYMENT")) {
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
        System.out.println(transactions);
        System.out.println(oldTransaction);
        int index = indexOf(oldTransaction);
        transactions.set(index, newTransaction);
    }

    private int indexOf(Transaction that) {
        for (int i = 0; i < transactions.size(); i++) {
            Transaction transaction = transactions.get(i);
            if (!that.getTransactionType().toString().equals(transaction.getTransactionType().toString())) {
                System.out.println("TYPE");
                continue;
            }
            if (!that.getTitle().equals(transaction.getTitle())) {
                System.out.println("Title");
                continue;
            }
            if (!that.getItemDescription().equals(transaction.getItemDescription())) {
                System.out.println("Description");
                continue;
            }
            if (!that.getAmount().equals(transaction.getAmount())) {
                System.out.println("AMOUNT");
                continue;
            }
            if (!that.getDate().equals(transaction.getDate())) {
                System.out.println("DAte");
                continue;
            }
            if (!that.getEndDate().equals(transaction.getEndDate())) {
                System.out.println("ENDDATE");
                continue;
            }
            if (!that.getTransactionInterval().equals(transaction.getTransactionInterval())) {
                System.out.println("INTERVAL");
                continue;
            }
            return i;
        }
        return -1;
    }


    public void deleteTransaction(Transaction transaction) {
        transactions.remove(transaction);
    }

    private List<Payment> convertRegularToIndividual(Transaction transaction){
        BigDecimal sign = new BigDecimal(1);
        if (transaction.getTransactionType().toString().contains("INCOME")) {
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
