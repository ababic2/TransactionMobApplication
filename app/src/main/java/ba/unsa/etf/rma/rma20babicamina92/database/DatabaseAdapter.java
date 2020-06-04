package ba.unsa.etf.rma.rma20babicamina92.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ba.unsa.etf.rma.rma20babicamina92.models.AccountAction;

public class DatabaseAdapter {
    private static final String TAG=DatabaseAdapter.class.getSimpleName();

    public static final String DB_NAME="banking.db";
    public static final int DB_VERSION=1;

    public static final String TABLE_ACCOUNT_ACTIONS="table_account_actions";
    public static long NEXT_ID=1;
    public static final String COLUMN_ACTION_ID="action_id";
    public static final String COLUMN_ACTION_NAME="action_name";
    public static final String COLUMN_ACCOUNT_ID="account_id";
    public static final String COLUMN_ACCOUNT_BUDGET="account_budget";
    public static final String COLUMN_ACCOUNT_MONTHLY="account_monthly";
    public static final String COLUMN_ACCOUNT_TOTAL="account_total";

    // query strings for database creation
    public static String CREATE_TABLE_ACCOUNT_ACTIONS = "CREATE TABLE " + TABLE_ACCOUNT_ACTIONS + "(" +
            COLUMN_ACTION_ID + " integer primary key," +
            COLUMN_ACTION_NAME + " text," +
            COLUMN_ACCOUNT_ID + " text," +
            COLUMN_ACCOUNT_BUDGET + " integer," +
            COLUMN_ACCOUNT_MONTHLY + " integer," +
            COLUMN_ACCOUNT_TOTAL + " integer )";


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
        NEXT_ID = getNextAccountActionId(database)+1;
        return databaseAdapter;
    }

    public Cursor getAccountListCursor() {
        return database.query(TABLE_ACCOUNT_ACTIONS, null, null, null, null, null, null);
    }

    public static long getNextAccountActionId(SQLiteDatabase database) {
        return DatabaseUtils.queryNumEntries(database, TABLE_ACCOUNT_ACTIONS);
    }

    public boolean insertAccount(AccountAction accountAction) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ACTION_ID,NEXT_ID);
        values.put(COLUMN_ACTION_NAME,accountAction.getName());
        values.put(COLUMN_ACCOUNT_ID,accountAction.getAccount().getId());
        values.put(COLUMN_ACCOUNT_BUDGET,accountAction.getAccount().getBudget());
        values.put(COLUMN_ACCOUNT_MONTHLY,accountAction.getAccount().getMonthLimit());
        values.put(COLUMN_ACCOUNT_TOTAL,accountAction.getAccount().getTotalLimit());
        boolean result = database.insert(TABLE_ACCOUNT_ACTIONS, null, values)>0;
        NEXT_ID++;
        return result;
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
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase,
                              int oldVersion, int newVersion) {
            //Not implemented now
        }
    }
}
