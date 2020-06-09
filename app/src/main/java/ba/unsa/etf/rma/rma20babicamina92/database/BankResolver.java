package ba.unsa.etf.rma.rma20babicamina92.database;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;

import ba.unsa.etf.rma.rma20babicamina92.models.AccountAction;
import ba.unsa.etf.rma.rma20babicamina92.models.TransactionAction;

import static ba.unsa.etf.rma.rma20babicamina92.database.DatabaseAdapter.COLUMN_ACCOUNT_BUDGET;
import static ba.unsa.etf.rma.rma20babicamina92.database.DatabaseAdapter.COLUMN_ACCOUNT_ID;
import static ba.unsa.etf.rma.rma20babicamina92.database.DatabaseAdapter.COLUMN_ACCOUNT_MONTHLY;
import static ba.unsa.etf.rma.rma20babicamina92.database.DatabaseAdapter.COLUMN_ACCOUNT_TOTAL;
import static ba.unsa.etf.rma.rma20babicamina92.database.DatabaseAdapter.COLUMN_ACTION_ID;
import static ba.unsa.etf.rma.rma20babicamina92.database.DatabaseAdapter.COLUMN_ACTION_NAME;
import static ba.unsa.etf.rma.rma20babicamina92.database.DatabaseAdapter.COLUMN_TRANSACTION_AMOUNT;
import static ba.unsa.etf.rma.rma20babicamina92.database.DatabaseAdapter.COLUMN_TRANSACTION_DATE;
import static ba.unsa.etf.rma.rma20babicamina92.database.DatabaseAdapter.COLUMN_TRANSACTION_DESCRIPTION;
import static ba.unsa.etf.rma.rma20babicamina92.database.DatabaseAdapter.COLUMN_TRANSACTION_END_DATE;
import static ba.unsa.etf.rma.rma20babicamina92.database.DatabaseAdapter.COLUMN_TRANSACTION_ID;
import static ba.unsa.etf.rma.rma20babicamina92.database.DatabaseAdapter.COLUMN_TRANSACTION_INTERVAL;
import static ba.unsa.etf.rma.rma20babicamina92.database.DatabaseAdapter.COLUMN_TRANSACTION_TITLE;
import static ba.unsa.etf.rma.rma20babicamina92.database.DatabaseAdapter.COLUMN_TRANSACTION_TYPE;

public class BankResolver {

    Uri ACCOUNT_ACTIONS = Uri.parse
            ("content://"+BankingProvider.AUTHORITY+"/"+BankingProvider.PATH_ACCOUNT_ACTIONS);

    Uri TRANSACTION_ACTIONS = Uri.parse
            ("content://"+BankingProvider.AUTHORITY+"/"+BankingProvider.PATH_TRANSACTION_ACTIONS);

    private ContentResolver contentResolver;

    public BankResolver(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    public boolean insertAccountAction(AccountAction accountAction) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ACTION_NAME,accountAction.getName());
        values.put(COLUMN_ACCOUNT_ID,accountAction.getAccount().getId());
        values.put(COLUMN_ACCOUNT_BUDGET,accountAction.getAccount().getBudget());
        values.put(COLUMN_ACCOUNT_MONTHLY,accountAction.getAccount().getMonthLimit());
        values.put(COLUMN_ACCOUNT_TOTAL,accountAction.getAccount().getTotalLimit());
        Uri result = contentResolver.insert(ACCOUNT_ACTIONS, values);
        System.out.println(result != null ? result.toString() : "null");
        return true;
    }

    public boolean insertTransactionAction(TransactionAction transactionAction) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ACTION_NAME,transactionAction.getName());
        values.put(COLUMN_TRANSACTION_ID,transactionAction.getTransaction().getId());
        values.put(COLUMN_TRANSACTION_TITLE,transactionAction.getTransaction().getTitle());
        values.put(COLUMN_TRANSACTION_DESCRIPTION,transactionAction.getTransaction().getItemDescription());
        values.put(COLUMN_TRANSACTION_AMOUNT,transactionAction.getTransaction().getAmount());
        values.put(COLUMN_TRANSACTION_DATE, transactionAction.getTransaction().getDate().getTime());
        values.put(COLUMN_TRANSACTION_END_DATE, transactionAction.getTransaction().getEndDate().getTime());
        values.put(COLUMN_TRANSACTION_INTERVAL, transactionAction.getTransaction().getTransactionInterval());
        values.put(COLUMN_TRANSACTION_TYPE, transactionAction.getTransaction().getTransactionType().getId());
        Uri result = contentResolver.insert(TRANSACTION_ACTIONS, values);
        System.out.println(result != null ? result.toString() : "null");
        return true;
    }

    public boolean updateAccoutnAction(AccountAction accountAction) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ACTION_NAME,accountAction.getName());
        values.put(COLUMN_ACCOUNT_BUDGET,accountAction.getAccount().getBudget());
        values.put(COLUMN_ACCOUNT_MONTHLY,accountAction.getAccount().getMonthLimit());
        values.put(COLUMN_ACCOUNT_TOTAL,accountAction.getAccount().getTotalLimit());
        contentResolver.update(ACCOUNT_ACTIONS, values, " " + COLUMN_ACTION_ID + "=" + accountAction.getId(), null);
        return true;
    }

    public boolean updateTransactionAction(TransactionAction transactionAction) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ACTION_NAME,transactionAction.getName());
        values.put(COLUMN_TRANSACTION_TITLE,transactionAction.getTransaction().getTitle());
        values.put(COLUMN_TRANSACTION_DESCRIPTION,transactionAction.getTransaction().getItemDescription());
        values.put(COLUMN_TRANSACTION_AMOUNT,transactionAction.getTransaction().getAmount());
        values.put(COLUMN_TRANSACTION_DATE, transactionAction.getTransaction().getDate().getTime());
        values.put(COLUMN_TRANSACTION_END_DATE, transactionAction.getTransaction().getEndDate().getTime());
        values.put(COLUMN_TRANSACTION_INTERVAL, transactionAction.getTransaction().getTransactionInterval());
        values.put(COLUMN_TRANSACTION_TYPE, transactionAction.getTransaction().getTransactionType().getId());
        contentResolver.update(TRANSACTION_ACTIONS, values, " " + COLUMN_ACTION_ID + "=" + transactionAction.getId(), null);
        return true;
    }

    public boolean deleteAccountAction(long id) {
        int result = contentResolver.delete(ACCOUNT_ACTIONS, " " + COLUMN_ACTION_ID + "=" + id, null);
        System.out.println(result);
        return true;
    }

    public boolean deleteTransactionAction(long id) {
        int result = contentResolver.delete(TRANSACTION_ACTIONS, " " + COLUMN_ACTION_ID + "=" + id, null);
        System.out.println(result);
        return true;
    }

}
