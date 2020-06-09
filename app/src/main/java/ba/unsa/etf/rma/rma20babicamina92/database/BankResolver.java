package ba.unsa.etf.rma.rma20babicamina92.database;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import ba.unsa.etf.rma.rma20babicamina92.R;
import ba.unsa.etf.rma.rma20babicamina92.models.Account;
import ba.unsa.etf.rma.rma20babicamina92.models.AccountAction;
import ba.unsa.etf.rma.rma20babicamina92.models.MainModel;
import ba.unsa.etf.rma.rma20babicamina92.models.Transaction;
import ba.unsa.etf.rma.rma20babicamina92.models.TransactionAction;
import ba.unsa.etf.rma.rma20babicamina92.models.TransactionType;
import ba.unsa.etf.rma.rma20babicamina92.presenters.ListFragmentPresenter;

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

    public AccountAction getAccountAction() {
        AccountAction accountAction = null;
        Cursor cursor = contentResolver.query(ACCOUNT_ACTIONS, null, null, null, null);
        if (cursor == null) {
            throw new RuntimeException();
        }
        int numberOfAccounts = cursor.getCount();
        if (numberOfAccounts == 0) {
            return null;
        }
        for (int i = 0; i < numberOfAccounts; i++) {
            accountAction = new AccountAction(cursor.getString(2), new Account(cursor.getInt(3), cursor.getInt(4), cursor.getInt(6), cursor.getInt(5)));
            accountAction.setId(cursor.getInt(1));
        }
        cursor.close();
        return accountAction;
    }

    public ArrayList<TransactionAction> getTransactionActions() {
        Cursor cursor = contentResolver.query(TRANSACTION_ACTIONS, null, null, null, null);
        if (cursor == null) {
            throw new RuntimeException();
        }
        ArrayList<TransactionAction> transactionActions = new ArrayList<>();
        int numberOfTransactions = cursor.getCount();

        ArrayList<TransactionType> transactionTypes = ListFragmentPresenter.getInstance().filterItems;
        if (transactionTypes == null) {
            transactionTypes = new ArrayList<>();
            ArrayList<Integer> icons = new ArrayList<>(Arrays.asList(R.mipmap.ic_one, R.mipmap.ic_two, R.mipmap.ic_three, R.mipmap.ic_four, R.mipmap.ic_five, R.mipmap.ic_six));
            transactionTypes.add(new TransactionType(1, "Regular payment", icons.get(1)));
            transactionTypes.add(new TransactionType(2, "Regular income", icons.get(2)));
            transactionTypes.add(new TransactionType(3, "Purchase", icons.get(3)));
            transactionTypes.add(new TransactionType(4, "Individual income", icons.get(4)));
            transactionTypes.add(new TransactionType(5, "Regular income", icons.get(5)));
            transactionTypes.add(0, new TransactionType(0,"All", R.mipmap.ic_six));
            transactionTypes.add(new TransactionType(0,"All", R.mipmap.ic_six));
        }
        for (int i = 0; i < numberOfTransactions; i++) {
            transactionActions.add(
                    new TransactionAction(
                            cursor.getString(2),
                            new Transaction(
                                    (long) cursor.getInt(3),
                                    cursor.getString(4),
                                    cursor.getString(5),
                                    cursor.getInt(6),
                                    new Date(cursor.getLong(7)),
                                    new Date(cursor.getLong(8)),
                                    cursor.getInt(9),
                                    transactionTypes.get(cursor.getInt(10))
                            )
                    )
            );
        }
        cursor.close();
        return transactionActions;
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
        values.put(COLUMN_TRANSACTION_DESCRIPTION,transactionAction.getTransaction().getItemDescription()==null?"":transactionAction.getTransaction().getItemDescription());
        values.put(COLUMN_TRANSACTION_AMOUNT,transactionAction.getTransaction().getAmount());
        values.put(COLUMN_TRANSACTION_DATE, transactionAction.getTransaction().getDate().getTime());
        values.put(COLUMN_TRANSACTION_END_DATE, transactionAction.getTransaction().getEndDate()==null?0:transactionAction.getTransaction().getEndDate().getTime());
        values.put(COLUMN_TRANSACTION_INTERVAL, transactionAction.getTransaction().getTransactionInterval());
        values.put(COLUMN_TRANSACTION_TYPE, transactionAction.getTransaction().getTransactionType().getId());
        Uri result = contentResolver.insert(TRANSACTION_ACTIONS, values);
        System.out.println(result != null ? result.toString() : "null");
        return true;
    }

    public boolean updateAccountAction(AccountAction accountAction) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ACTION_NAME,accountAction.getName());
        values.put(COLUMN_ACCOUNT_BUDGET,accountAction.getAccount().getBudget());
        values.put(COLUMN_ACCOUNT_MONTHLY,accountAction.getAccount().getMonthLimit());
        values.put(COLUMN_ACCOUNT_TOTAL,accountAction.getAccount().getTotalLimit());
        contentResolver.update(ACCOUNT_ACTIONS, values, " " + COLUMN_ACCOUNT_ID + "=" + accountAction.getAccount().getId(), null);
        return true;
    }

    public boolean updateTransactionAction(TransactionAction transactionAction) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ACTION_NAME,transactionAction.getName());
        values.put(COLUMN_TRANSACTION_TITLE,transactionAction.getTransaction().getTitle());
        values.put(COLUMN_TRANSACTION_DESCRIPTION,transactionAction.getTransaction().getItemDescription()==null?"":transactionAction.getTransaction().getItemDescription());
        values.put(COLUMN_TRANSACTION_AMOUNT,transactionAction.getTransaction().getAmount());
        values.put(COLUMN_TRANSACTION_DATE, transactionAction.getTransaction().getDate().getTime());
        values.put(COLUMN_TRANSACTION_END_DATE, transactionAction.getTransaction().getEndDate()==null?0:transactionAction.getTransaction().getEndDate().getTime());
        values.put(COLUMN_TRANSACTION_INTERVAL, transactionAction.getTransaction().getTransactionInterval());
        values.put(COLUMN_TRANSACTION_TYPE, transactionAction.getTransaction().getTransactionType().getId());
        contentResolver.update(TRANSACTION_ACTIONS, values, " " + COLUMN_TRANSACTION_ID + "=" + transactionAction.getTransaction().getId(), null);
        return true;
    }

    public boolean deleteAccountAction(long id) {
        int result = contentResolver.delete(ACCOUNT_ACTIONS, " " + COLUMN_ACCOUNT_ID + "=" + id, null);
        System.out.println(result);
        return true;
    }

    public boolean deleteTransactionAction(long id) {
        int result = contentResolver.delete(TRANSACTION_ACTIONS, " " + COLUMN_TRANSACTION_ID + "=" + id, null);
        System.out.println(result);
        return true;
    }

}
