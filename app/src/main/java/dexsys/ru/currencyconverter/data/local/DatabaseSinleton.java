package dexsys.ru.currencyconverter.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import dexsys.ru.currencyconverter.data.model.CurrencyList;
import dexsys.ru.currencyconverter.data.model.CurrencyReaderContract;
import dexsys.ru.currencyconverter.data.remote.ConverterData;
import dexsys.ru.currencyconverter.data.remote.ServiceResult;

/**
 * Created by blood on 01.05.2017.
 */

public class DatabaseSinleton extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Currency.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTER_TYPE = " INTEGER";
    private static final String NUMBER_TYPE = " NUMERIC";
    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + CurrencyReaderContract.CurrencyEntry.TABLE_NAME + " ( " +
                    CurrencyReaderContract.CurrencyEntry._ID + " INTEGER PRIMARY KEY," +
                    CurrencyReaderContract.CurrencyEntry.COLUMN_NAME_ID + TEXT_TYPE + COMMA_SEP +
                    CurrencyReaderContract.CurrencyEntry.COLUMN_NAME_CHARCODE + TEXT_TYPE + COMMA_SEP +
                    CurrencyReaderContract.CurrencyEntry.COLUMN_NAME_NOMINAL + INTER_TYPE + COMMA_SEP +
                    CurrencyReaderContract.CurrencyEntry.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    CurrencyReaderContract.CurrencyEntry.COLUMN_NAME_VALUE + NUMBER_TYPE +
                    " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + CurrencyReaderContract.CurrencyEntry.TABLE_NAME;
    private static DatabaseSinleton instance;

    public DatabaseSinleton(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
