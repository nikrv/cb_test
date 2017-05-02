package dexsys.ru.currencyconverter.data.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import dexsys.ru.currencyconverter.app.CustomApplication;
import dexsys.ru.currencyconverter.data.model.Currency;
import dexsys.ru.currencyconverter.data.model.CurrencyList;
import dexsys.ru.currencyconverter.data.model.CurrencyReaderContract;
import dexsys.ru.currencyconverter.data.model.CurrencyReaderContract.CurrencyEntry;
import dexsys.ru.currencyconverter.data.remote.ServiceResult;

/**
 * Created by blood on 01.05.2017.
 */

public class DatabaseHelper {

    DatabaseSinleton database;

    public DatabaseHelper() {
        database = CustomApplication.getInstance().getDatabaseHelper();
    }

    public List<Currency> loadCurrencysFromDb() {

        List<Currency> result = new ArrayList<>();

        SQLiteDatabase db = database.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                CurrencyEntry._ID,
                CurrencyEntry.COLUMN_NAME_ID,
                CurrencyEntry.COLUMN_NAME_CHARCODE,
                CurrencyEntry.COLUMN_NAME_NAME,
                CurrencyEntry.COLUMN_NAME_NOMINAL,
                CurrencyEntry.COLUMN_NAME_VALUE,
        };

        Cursor cursor = db.query(
                CurrencyEntry.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

        if (cursor.moveToFirst()) {
            do {
                Currency currency = new Currency();

                currency.setCharCode(cursor.getString(cursor.getColumnIndex(CurrencyEntry.COLUMN_NAME_CHARCODE)));
                currency.setId(cursor.getString(cursor.getColumnIndex(CurrencyEntry.COLUMN_NAME_ID)));
                currency.setName(cursor.getString(cursor.getColumnIndex(CurrencyEntry.COLUMN_NAME_NAME)));
                currency.setNominal(cursor.getInt(cursor.getColumnIndex(CurrencyEntry.COLUMN_NAME_NOMINAL)));
                currency.setValue(cursor.getDouble(cursor.getColumnIndex(CurrencyEntry.COLUMN_NAME_VALUE)));

                result.add(currency);
                // do what ever you want here
            } while (cursor.moveToNext());
        }
        cursor.close();

        return result;
    }

    public void saveCurrency(CurrencyList result) {
        // Gets the data repository in write mode
        SQLiteDatabase dbWrite = database.getWritableDatabase();
        SQLiteDatabase dbRead = database.getReadableDatabase();

        for (Currency cur : result.getList()) {
            if (checkExistCurrency(dbRead, cur)) {
                updateCurrency(dbWrite, cur);
            } else {
                createNewCurrency(dbWrite, cur);
            }
        }
    }

    private boolean checkExistCurrency(SQLiteDatabase db, Currency currency) {
        String selection = "select * from " + CurrencyEntry.TABLE_NAME + " WHERE " + CurrencyEntry.COLUMN_NAME_ID + " = ?";

        // Specify arguments in placeholder order.
        String[] selectionArgs = {currency.getId()};


        Cursor cursor = db.rawQuery(selection, selectionArgs);
        return cursor.getCount() > 0;
    }


    private void updateCurrency(SQLiteDatabase db, Currency cur) {
        // New value for one column
        ContentValues values = new ContentValues();
        values.put(CurrencyEntry.COLUMN_NAME_CHARCODE, cur.getCharCode());
        values.put(CurrencyEntry.COLUMN_NAME_NAME, cur.getName());
        values.put(CurrencyEntry.COLUMN_NAME_NOMINAL, cur.getNominal());
        values.put(CurrencyEntry.COLUMN_NAME_VALUE, cur.getValue());

        // Which row to update, based on the ID
        String selection = CurrencyEntry.COLUMN_NAME_ID + " = ?";
        String[] selectionArgs = {cur.getId()};

        int count = db.update(
                CurrencyEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

    private void createNewCurrency(SQLiteDatabase db, Currency cur) {
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(CurrencyEntry.COLUMN_NAME_ID, cur.getId());
        values.put(CurrencyEntry.COLUMN_NAME_CHARCODE, cur.getCharCode());
        values.put(CurrencyEntry.COLUMN_NAME_NAME, cur.getName());
        values.put(CurrencyEntry.COLUMN_NAME_NOMINAL, cur.getNominal());
        values.put(CurrencyEntry.COLUMN_NAME_VALUE, cur.getValue());

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                CurrencyEntry.TABLE_NAME,
                CurrencyEntry.COLUMN_NAME_NULLABLE,
                values);
    }
}
