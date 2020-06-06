package ba.unsa.etf.rma.rma20babicamina92.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Locale;

import ba.unsa.etf.rma.rma20babicamina92.models.AccountAction;
import ba.unsa.etf.rma.rma20babicamina92.models.TransactionAction;

public class DatabaseAdapter {
    private static final String TAG=DatabaseAdapter.class.getSimpleName();

    public static final String DB_NAME="banking.db";
    public static final int DB_VERSION=1;

    public static final String TABLE_ACCOUNT_ACTIONS="table_account_actions";
    public static long NEXT_ACCOUNT_ID =1;
    public static final String COLUMN_ACTION_ID="action_id";
    public static final String COLUMN_ACTION_NAME="action_name";
    public static final String COLUMN_ACCOUNT_ID="account_id";
    public static final String COLUMN_ACCOUNT_BUDGET="account_budget";
    public static final String COLUMN_ACCOUNT_MONTHLY="account_monthly";
    public static final String COLUMN_ACCOUNT_TOTAL="account_total";

    public static final String TABLE_TRANSACTION_ACTIONS="table_account_actions";
    public static long NEXT_TRANSACTION_ID=1;
    public static final String COLUMN_TRANSACTION_ID="transaction_id";
    public static final String COLUMN_TRANSACTION_TITLE="transaction_title";
    public static final String COLUMN_TRANSACTION_DESCRIPTION="transaction_description";
    public static final String COLUMN_TRANSACTION_AMOUNT="transaction_amount";
    public static final String COLUMN_TRANSACTION_DATE="transaction_date";
    public static final String COLUMN_TRANSACTION_END_DATE="transaction_end_date";
    public static final String COLUMN_TRANSACTION_INTERVAL="transaction_interval";
    public static final String COLUMN_TRANSACTION_TYPE="transaction_type";

    // query strings for database creation
    public static String CREATE_TABLE_ACCOUNT_ACTIONS = "CREATE TABLE " + TABLE_ACCOUNT_ACTIONS + "(" +
            COLUMN_ACTION_ID + " integer primary key," +
            COLUMN_ACTION_NAME + " text," +
            COLUMN_ACCOUNT_ID + " text," +
            COLUMN_ACCOUNT_BUDGET + " integer," +
            COLUMN_ACCOUNT_MONTHLY + " integer," +
            COLUMN_ACCOUNT_TOTAL + " integer )";

    public static String CREATE_TABLE_TRANSACTION_ACTIONS = "CREATE TABLE " + TABLE_TRANSACTION_ACTIONS + "(" +
            COLUMN_ACTION_ID + " integer primary key," +
            COLUMN_ACTION_NAME + " text," +
            COLUMN_TRANSACTION_ID + " text," +
            COLUMN_TRANSACTION_TITLE + " text," +
            COLUMN_TRANSACTION_DESCRIPTION + " text," +
            COLUMN_TRANSACTION_AMOUNT + " integer," +
            COLUMN_TRANSACTION_DATE + " date," +
            COLUMN_TRANSACTION_END_DATE + " date," +
            COLUMN_TRANSACTION_INTERVAL + " integer," +
            COLUMN_TRANSACTION_TYPE + " integer )";

    private Context context;
    private static SQLiteDatabase database;
    private static DatabaseAdapter databaseAdapter;

    private DatabaseAdapter(Context context) {
        this.context = context;
        database = new SQLiteHelper(context, DB_NAME, null, DB_VERSION).getWritableDatabase();

    }

    public static DatabaseAdapter getInstance(Context context) {
        if (databaseAdapter == null) {
            databaseAdapter = new DatabaseAdapter(context);
        }
        NEXT_ACCOUNT_ID = getNextId(database,TABLE_ACCOUNT_ACTIONS)+1;
        NEXT_TRANSACTION_ID = getNextId(database,TABLE_TRANSACTION_ACTIONS)+1;
        return databaseAdapter;
    }

    public int deleteTransaction(String clause, String[] args) {
        return database.delete(TABLE_TRANSACTION_ACTIONS, clause, args);
    }
    //String.format(Locale.getDefault(), "%s=%d", COLUMN_TRANSACTION_ID

    public int deleteAccount(String clause, String[] args) {
        return database.delete(TABLE_ACCOUNT_ACTIONS, clause, args);
    }
    //String.format(Locale.getDefault(), "%s=%d", COLUMN_ACCOUNT_ID, id)
    public Cursor getAccountListCursor() {
        return database.query(TABLE_ACCOUNT_ACTIONS, null, null, null, null, null, null);
    }

    public Cursor getTransactionListCursor() {
        return database.query(TABLE_TRANSACTION_ACTIONS, null, null, null, null, null, null);
    }

    public static long getNextId(SQLiteDatabase database, String table) {
        return DatabaseUtils.queryNumEntries(database, table);
    }

    public Cursor getTransactionByTransactionId(long id) {
        return database.rawQuery("SELECT * FROM "+TABLE_TRANSACTION_ACTIONS+" WHERE "+COLUMN_TRANSACTION_ID+"="+id,null);
    }

    public int updateAccountAction(ContentValues values, String clause, String[] args) {
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_ACTION_NAME,accountAction.getName());
//        values.put(COLUMN_ACCOUNT_BUDGET,accountAction.getAccount().getBudget());
//        values.put(COLUMN_ACCOUNT_MONTHLY,accountAction.getAccount().getMonthLimit());
//        values.put(COLUMN_ACCOUNT_TOTAL,accountAction.getAccount().getTotalLimit());
        return database.update(TABLE_ACCOUNT_ACTIONS, values, clause, args);
    }
    // " " + COLUMN_ACCOUNT_ID + "=" + values.getAsInteger(COLUMN_ACCOUNT_ID)


    public long insertAccountAction(ContentValues values) {
//        ContentValues values = new ContentValues();
        values.put(COLUMN_ACTION_ID, NEXT_ACCOUNT_ID);
//        values.put(COLUMN_ACTION_NAME,accountAction.getName());
//        values.put(COLUMN_ACCOUNT_ID,accountAction.getAccount().getId());
//        values.put(COLUMN_ACCOUNT_BUDGET,accountAction.getAccount().getBudget());
//        values.put(COLUMN_ACCOUNT_MONTHLY,accountAction.getAccount().getMonthLimit());
//        values.put(COLUMN_ACCOUNT_TOTAL,accountAction.getAccount().getTotalLimit());
        boolean result = database.insert(TABLE_ACCOUNT_ACTIONS, null, values)>0;
        NEXT_ACCOUNT_ID++;
        return NEXT_ACCOUNT_ID;
    }

    public int updateTransactionAction(ContentValues values, String clause, String[] args) {
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_ACTION_NAME,transactionAction.getName());
//        values.put(COLUMN_TRANSACTION_TITLE,transactionAction.getTransaction().getTitle());
//        values.put(COLUMN_TRANSACTION_DESCRIPTION,transactionAction.getTransaction().getItemDescription());
//        values.put(COLUMN_TRANSACTION_AMOUNT,transactionAction.getTransaction().getAmount());
//        values.put(COLUMN_TRANSACTION_DATE, transactionAction.getTransaction().getDate().getTime());
//        values.put(COLUMN_TRANSACTION_END_DATE, transactionAction.getTransaction().getEndDate().getTime());
//        values.put(COLUMN_TRANSACTION_INTERVAL, transactionAction.getTransaction().getTransactionInterval());
//        values.put(COLUMN_TRANSACTION_TYPE, transactionAction.getTransaction().getTransactionType().getId());
        return database.update(TABLE_TRANSACTION_ACTIONS, values, clause,args);
        // " "+COLUMN_TRANSACTION_ID+"="+values.get(COLUMN_TRANSACTION_ID)
    }


    public long insertTransactionAction(ContentValues values) {
//        ContentValues values = new ContentValues();
        values.put(COLUMN_ACTION_ID, NEXT_ACCOUNT_ID);
//        values.put(COLUMN_ACTION_NAME,transactionAction.getName());
//        values.put(COLUMN_TRANSACTION_ID,transactionAction.getTransaction().getId());
//        values.put(COLUMN_TRANSACTION_TITLE,transactionAction.getTransaction().getTitle());
//        values.put(COLUMN_TRANSACTION_DESCRIPTION,transactionAction.getTransaction().getItemDescription());
//        values.put(COLUMN_TRANSACTION_AMOUNT,transactionAction.getTransaction().getAmount());
//        values.put(COLUMN_TRANSACTION_DATE, transactionAction.getTransaction().getDate().getTime());
//        values.put(COLUMN_TRANSACTION_END_DATE, transactionAction.getTransaction().getEndDate().getTime());
//        values.put(COLUMN_TRANSACTION_INTERVAL, transactionAction.getTransaction().getTransactionInterval());
//        values.put(COLUMN_TRANSACTION_TYPE, transactionAction.getTransaction().getTransactionType().getId());
        boolean result = database.insert(TABLE_TRANSACTION_ACTIONS, null, values)>0;
        NEXT_TRANSACTION_ID++;
        return NEXT_TRANSACTION_ID-1;
    }

    private static class SQLiteHelper extends SQLiteOpenHelper {
        public SQLiteHelper(Context context, String dbName, SQLiteDatabase.CursorFactory cursorFactory, int dbVersion) {
            super(context, dbName, cursorFactory, dbVersion);
        }

        @Override
        public void onConfigure(SQLiteDatabase database) {
            super.onConfigure(database);
            database.setForeignKeyConstraintsEnabled(true);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(CREATE_TABLE_ACCOUNT_ACTIONS);
            sqLiteDatabase.execSQL(CREATE_TABLE_TRANSACTION_ACTIONS);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase,
                              int oldVersion, int newVersion) {
            //Not implemented now
        }
    }
}
