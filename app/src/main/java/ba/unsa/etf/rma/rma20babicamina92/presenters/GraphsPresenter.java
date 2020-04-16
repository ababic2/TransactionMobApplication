package ba.unsa.etf.rma.rma20babicamina92.presenters;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

import ba.unsa.etf.rma.rma20babicamina92.fragments.GraphsFragment;
import ba.unsa.etf.rma.rma20babicamina92.models.MainModel;
import ba.unsa.etf.rma.rma20babicamina92.models.Transaction;
import ba.unsa.etf.rma.rma20babicamina92.utils.FloatTest;

public class GraphsPresenter {
    private static GraphsPresenter instance;
    private GraphsFragment fragment;
    private MainModel model;
    private String interval = "Month";


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

        @Override
        public String toString() {
            return "Payment{" +
                    "amount=" + amount +
                    ", date=" + date +
                    '}';
        }
    }


    private GraphsPresenter() {
        model = MainModel.getInstance();
    }

    public static GraphsPresenter getInstance() {
        if (instance == null) {
            instance = new GraphsPresenter();
        }
        return instance;
    }

    public void init(GraphsFragment fragment) {
        this.fragment = fragment;
    }

    public void setInterval(String interval) {
        this.interval = interval;

        BarData costBarData = getBarData(a -> a > 0);
        fragment.setPaymentChartData(costBarData);
        fragment.setIncomeChartData(getBarData( a -> a < 0));
        fragment.setAllChartData(getBarData(a -> true));
    }

    private BarData getBarData(FloatTest floatTest) {
        ArrayList<ArrayList<Payment>> paymentValues =
                getPaymentsByInterval(
                        getPaymentsFromTransactions(
                                new ArrayList<>(model.getTransactions()
                                )
                        )
                );
        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < paymentValues.size(); i++) {
            ArrayList<Payment> payments = paymentValues.get(i);
            if (payments.size() == 0) {
                entries.add(new BarEntry(i+1, 0));
            } else {
                float sum = 0;
                for (Payment payment : payments) {
                    float value = (float) Double.parseDouble(
                            String.format(
                                    Locale.getDefault(),
                                    "%.2f",
                                    payment.getAmount()
                            )
                    );
                    if (floatTest.test(value)) {
                        if (floatTest.test(-1) && floatTest.test(1)) {
                            sum += -value;
                        } else {
                            sum += Math.abs(value);
                        }
                    }
                }
                entries.add(new BarEntry(i+1, sum));
            }
        }
        return
                new BarData(
                        Collections.singletonList(
                                new BarDataSet(
                                        entries,
                                        floatTest.test(-1) &&
                                                floatTest.test(1)
                                                ? "Ukupno stanje" : floatTest.test(-1)
                                ?"Zarada":"Potrošnja")));
    }

    private ArrayList<ArrayList<Payment>> getPaymentsByInterval(ArrayList<Payment> payments) {
        payments = extractPaymentsFromCurrentYear(payments);
        Collections.sort(payments,(a,b)->a.getDate().compareTo(b.getDate()));
        ArrayList<ArrayList<Payment>> result = new ArrayList<>();
        ArrayList<Payment> resultPayments;
        Date date = new Date();

        if (interval.equals("Month")) {
            for (int i = 0; i < 12; i++) {
                int j = 0;
                resultPayments = new ArrayList<>();
                while (j < payments.size()) {
                    Payment payment = payments.get(j);
                    if (payment.getDate().getYear() == date.getYear()) {
                        if (payment.getDate().getMonth() == i) {
                            resultPayments.add(payment);
                        }
                    } else if (payment.getDate().getMonth() > i) {
                        break;
                    }
                    j++;
                }
                result.add(resultPayments);
            }
        } else if (interval.equals("Day")) {
            Date start = new Date();
            start.setDate(1);
            start.setMonth(0);
            for (int i = 1; ; i++) {
                resultPayments = new ArrayList<>();
                int j = 0;
                while (j < payments.size()) {
                    Payment payment = payments.get(j);
                    if (payment.getDate().getMonth() == start.getMonth()
                            && payment.getDate().getDate() == start.getDate()) {
                        resultPayments.add(payment);
                    } else if(start.before(payment.getDate())){
                        result.add(resultPayments);
                        resultPayments = new ArrayList<>();
                        start.setDate(start.getDate()+1);
                        continue;
                    } else {
                        break;
                    }
                    j++;
                }
                result.add(resultPayments);
                if (start.getMonth() == 11 && start.getDate() == 31) {
                    break;
                }
                start.setDate(start.getDate() + 1);
            }
        } else if (interval.equals("Week")) {
            Date start = new Date();
            start.setDate(7);
            start.setMonth(0);
            int j = 0;
            for (int i = 1; ; i++) {
                resultPayments = new ArrayList<>();
                while (j < payments.size()) {
                    Payment payment = payments.get(j);
                    if (payment.getDate().before(start) || payment.getDate().equals(start)) {
                        resultPayments.add(payment);
                    } else if(start.before(payment.getDate())){
                        result.add(resultPayments);
                        resultPayments = new ArrayList<>();
                        start.setDate(start.getDate()+7);
                        continue;
                    } else {
                        break;
                    }
                    j++;
                }
                result.add(resultPayments);
                if (start.getYear()>new Date().getYear()) {
                    break;
                }
                start.setDate(start.getDate() + 7);

            }
        }
        return result;
    }

    private ArrayList<Payment> extractPaymentsFromCurrentYear(ArrayList<Payment> payments) {
        ArrayList<Payment> result = new ArrayList<>();
        for (Payment payment : payments) {
            if (payment.getDate().getYear() == new Date().getYear()) {
                result.add(payment);
            }
        }
        return result;
    }

    private ArrayList<Payment> getPaymentsFromTransactions(ArrayList<Transaction> transactions) {

        ArrayList<Payment> payments = new ArrayList<>();

        for (int i = 0; i < transactions.size(); i++) {
            Transaction element = transactions.get(i);
            if (element.getTransactionType().toString().contains("REGULAR")) {
                payments.addAll(convertRegularToIndividual(element));
            } else {
                BigDecimal sign = new BigDecimal(1);
                if (element.getTransactionType().toString().contains("INCOME")) {
                    sign = new BigDecimal(-1);
                }
                payments.add(
                        new Payment(element.getAmount().multiply(sign),
                                new Date(element.getDate().getTime())));
            }
        }

        Collections.sort(payments, (a, b) -> a.getDate().compareTo(b.getDate()));
        return payments;
    }

    private ArrayList<Payment> convertRegularToIndividual(Transaction transaction){
        BigDecimal sign = new BigDecimal(1);
        if (transaction.getTransactionType().toString().contains("INCOME")) {
            sign = new BigDecimal(-1);
        }
        ArrayList<Payment> payments = new ArrayList<>();
        // petlja koja prolazi kroz "dane" kada trebaju biti transakcije
        for (Date date = new Date(transaction.getDate().getTime());
             date.before(transaction.getEndDate())
                     || date.getTime() == transaction.getEndDate().getTime();
             date.setDate(date.getDate() + transaction.getTransactionInterval())) {

            payments.add(new Payment(
                    transaction.getAmount().multiply(sign),
                    new Date(date.getTime())));
        }
        return new ArrayList<>(payments);
    }

}
