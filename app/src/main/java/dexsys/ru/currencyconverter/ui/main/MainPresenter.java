package dexsys.ru.currencyconverter.ui.main;

import android.os.AsyncTask;

import java.text.NumberFormat;
import java.util.List;

import dexsys.ru.currencyconverter.data.DataManager;
import dexsys.ru.currencyconverter.data.model.Currency;
import dexsys.ru.currencyconverter.data.remote.ServiceResult;
import dexsys.ru.currencyconverter.ui.base.BasePresenter;
import dexsys.ru.currencyconverter.ui.utils.NumFormatter;


public class MainPresenter extends BasePresenter<MainMvpView> {

    /**
     * Установить валюты
     *
     * @param result
     */
    private void setCurrencey(List<Currency> result) {
        getMvpView().setListValute(result);
    }

    /**
     * Конвертировать валюту
     */
    public void сonvertСurrency(double value, Currency sourceCurrency, Currency finalCurrency) {

        if (sourceCurrency == null || finalCurrency == null)
        {
            getMvpView().showValidateError("Заполните валюту");
        }
        else
        {
            double  sorVa = value / sourceCurrency.getNominal() * sourceCurrency.getValue();
            double  finVal = sorVa / finalCurrency.getValue()  * finalCurrency.getNominal();



            getMvpView().setResultConver(String.format("%s %s -> %s = %s %s",
                    NumFormatter.formatDecPriceRU(value),
                    sourceCurrency.getCharCode(),
                    finalCurrency.getCharCode(),
                    NumFormatter.formatDecPriceRU(finVal),
                    finalCurrency.getCharCode()
                    ));
        }

    }

    /**
     * Загрузить валюты
     */
    public void loadingCurrency() {
        new AsyncTask<Void, Void, ServiceResult<List<Currency>>>() {
            @Override
            protected void onPreExecute() {
                getMvpView().showLoading();
                super.onPreExecute();
            }

            @Override
            protected ServiceResult<List<Currency>> doInBackground(Void... params) {
                return DataManager.getInstance().loadCurrency();
            }

            @Override
            protected void onPostExecute(ServiceResult<List<Currency>> result) {
                super.onPostExecute(result);
                getMvpView().hideLoading();
                setCurrencey(result.getResult());
            }
        }.execute();
    }


}
