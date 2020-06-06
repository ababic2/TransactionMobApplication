package ba.unsa.etf.rma.rma20babicamina92.database;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class BankingProvider extends ContentProvider {

    public static final String AUTHORITY = "ba.unsa.etf.rma.rma20babicamina92";
    public static final String PATH_ACCOUNT_ACTIONS = "ACCOUNT_ACTIONS";
    public static final String PATH_TRANSACTION_ACTIONS = "TRANSACTION_ACTIONS";

    public static final int ACCOUNT_ACTIONS = 1;
    public static final int TRANSACTION_ACTIONS = 2;

    private static final UriMatcher MATCHER=new UriMatcher(UriMatcher.NO_MATCH);

    static {
        MATCHER.addURI(AUTHORITY, PATH_ACCOUNT_ACTIONS, ACCOUNT_ACTIONS);
        MATCHER.addURI(AUTHORITY, PATH_TRANSACTION_ACTIONS,TRANSACTION_ACTIONS);
    }

    public static final String MIME_TYPE_1 = ContentResolver.CURSOR_DIR_BASE_TYPE+"/"+ "vnd.ba.unsa.etf.rma.rma20babicamina92.accounts";
    public static final String MIME_TYPE_4 = ContentResolver.CURSOR_DIR_BASE_TYPE+"/"+ "vnd.ba.unsa.etf.rma.rma20babicamina92.transactions";
    //    public static final String MIME_TYPE_2 = ContentResolver.CURSOR_DIR_BASE_TYPE+"/"+ "vnd.com.codetutore.todos.place";
//    public static final String MIME_TYPE_3 = ContentResolver.CURSOR_ITEM_BASE_TYPE+"/"+ "vnd.com.codetutore.todocount";
    private DatabaseAdapter adapter;

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (MATCHER.match(uri)){
            case ACCOUNT_ACTIONS: return MIME_TYPE_1;
            case TRANSACTION_ACTIONS: return MIME_TYPE_4;
        }
        return null;
    }

    @Override
    public boolean onCreate() {
        adapter = DatabaseAdapter.getInstance(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor=null;
        switch (MATCHER.match(uri)){
            case ACCOUNT_ACTIONS: cursor=adapter.getAccountListCursor();break;
            case TRANSACTION_ACTIONS: cursor=adapter.getTransactionListCursor();break;
            default:
                break;
        }
        return cursor;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) throws UnsupportedOperationException {
        Uri returnUri = null;
        switch (MATCHER.match(uri)){
            case ACCOUNT_ACTIONS: returnUri= insertAccountAction(uri,values);break;
            case TRANSACTION_ACTIONS: returnUri=insertTransactionAction(uri, values);break;
            default: new UnsupportedOperationException("insert operation not supported"); break;
        }

        return returnUri ;
    }

    private Uri insertAccountAction(Uri uri, ContentValues values) {
        long id = adapter.insertAccountAction(values);
        getContext().getContentResolver().notifyChange(uri,null);
        return Uri.parse("content://"+AUTHORITY+"/"+ PATH_ACCOUNT_ACTIONS +"/"+id);
    }

    private Uri insertTransactionAction(Uri uri, ContentValues values) {
        long id = adapter.insertTransactionAction(values);
        getContext().getContentResolver().notifyChange(uri,null);
        return Uri.parse("content://"+AUTHORITY+"/"+ PATH_TRANSACTION_ACTIONS +"/"+id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int deleteCount=-1;
        switch (MATCHER.match(uri)){
            case TRANSACTION_ACTIONS: deleteCount= deleteTransactionActions(selection,selectionArgs);break;
            case ACCOUNT_ACTIONS: deleteCount= deleteAccountActions(selection,selectionArgs);break;
            default:new UnsupportedOperationException("delete operation not supported"); break;
        }
        return deleteCount;
    }

    private int deleteTransactionActions(String selection, String[] selectionArgs) {
        return adapter.deleteTransaction(selection,selectionArgs);
    }

    private int deleteAccountActions(String selection, String[] selectionArgs) {
        return adapter.deleteAccount(selection,selectionArgs);
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int updateCount=-1;
        switch (MATCHER.match(uri)){
            case ACCOUNT_ACTIONS: updateCount=updateAccountAction(values,selection,selectionArgs);break;
            case TRANSACTION_ACTIONS: updateCount=updateTransactionAction(values,selection,selectionArgs);break;
            default:
                new UnsupportedOperationException("insert operation not supported"); break;
        }
        return updateCount;
    }

    private int updateAccountAction(ContentValues values, String selection, String[] selectionArgs) {
        return adapter.updateAccountAction(values,selection,selectionArgs);
    }

    private int updateTransactionAction(ContentValues values, String selection, String[] selectionArgs) {
        return adapter.updateTransactionAction(values,selection,selectionArgs);
    }


}
