package dexsys.ru.currencyconverter.app;

import android.app.Application;

import dexsys.ru.currencyconverter.data.local.DatabaseSinleton;

/**
 * Created by blood on 01.05.2017.
 */

public class CustomApplication extends Application {
    private static CustomApplication mInstance  ;
    private DatabaseSinleton databaseHelper ;

    @SuppressWarnings("unchecked")
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        initSingleton();
    }

    private void initSingleton() {
        databaseHelper = new DatabaseSinleton(this);
    }

    public static synchronized CustomApplication getInstance() {
        return mInstance;
    }

    public DatabaseSinleton getDatabaseHelper() {
        return databaseHelper;
    }
}
