package ba.unsa.etf.rma.rma20babicamina92.interactor;

import java.text.SimpleDateFormat;
import java.util.Locale;

import ba.unsa.etf.rma.rma20babicamina92.models.Account;
import ba.unsa.etf.rma.rma20babicamina92.models.Transaction;

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

    public static String encodeTransaction(Transaction param) {
        String endDate = param.getEndDate() == null?null:new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS,", Locale.getDefault()).format(param.getEndDate()).replace(" ","T").replace(",","Z");
        String date = param.getDate() == null?null:new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS,", Locale.getDefault()).format(param.getDate()).replace(" ","T").replace(",","Z");
        String result = "{" +
                "\"title\":\""+param.getTitle()+"\"," +
                "\"itemDescription\":"+(param.getItemDescription()==null?"":"\"")+param.getItemDescription()+(param.getItemDescription()==null?"":"\"")+"," +
                "\"amount\":"+param.getAmount().toBigInteger().intValue()+"," +
                "\"date\":"+(date==null?"":"\"")+date+(date==null?"":"\"")+"," +
                "\"endDate\":"+(endDate==null?"":"\"")+endDate+(endDate==null?"":"\"")+"," +
                "\"transactionInterval\":"+param.getTransactionInterval()+"," +
                "\"typeId\":"+param.getTransactionType().getId()+"" +
                "}";
        return result;
    }
}
