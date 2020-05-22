package ba.unsa.etf.rma.rma20babicamina92.interactor;

import android.text.PrecomputedText;

import ba.unsa.etf.rma.rma20babicamina92.models.Account;

class JsonEncoder {
    static String encodeAccount(Account account) {
        if (account == null) {
            return "";
        }
        return "{\n" +
                "\"budget\": "+account.getBudget()+",\n" +
                "\"monthLimit\": "+account.getMonthLimit()+",\n" +
                "\"totalLimit\": "+account.getTotalLimit()+"\n" +
                "}\n";
    }
}
