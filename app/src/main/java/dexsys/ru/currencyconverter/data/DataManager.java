package dexsys.ru.currencyconverter.data;


import android.os.AsyncTask;

import java.io.IOException;
import java.util.List;

import dexsys.ru.currencyconverter.data.local.DatabaseHelper;
import dexsys.ru.currencyconverter.data.model.Currency;
import dexsys.ru.currencyconverter.data.model.CurrencyList;
import dexsys.ru.currencyconverter.data.remote.CurrencyService;
import dexsys.ru.currencyconverter.data.remote.ServiceResult;

public class DataManager {

    private final CurrencyService currencyService;
    private final DatabaseHelper databaseHelper;

    private static DataManager instance;


    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager(new CurrencyService(), new DatabaseHelper());
        }
        return instance;
    }

    public DataManager(CurrencyService currencyService, DatabaseHelper databaseHelper) {
        this.currencyService = currencyService;
        this.databaseHelper = databaseHelper;
    }


    public ServiceResult<List<Currency>> loadCurrency() {

        ServiceResult<List<Currency>> result = new ServiceResult<>();

        try {
            ServiceResult<CurrencyList> listFromNetwork = currencyService.loadCurrencysFromNetwork();

            if (listFromNetwork.isSuccessfully()) {
                databaseHelper.saveCurrency(listFromNetwork.getResult());
                result.setResult(listFromNetwork.getResult().getList());
            } else {
                result.setResult(databaseHelper.loadCurrencysFromDb());
            }

            result.setSuccessfully(true);
        } catch (IOException e) {
            result.setSuccessfully(false);
        }

        return result;
    }
}
