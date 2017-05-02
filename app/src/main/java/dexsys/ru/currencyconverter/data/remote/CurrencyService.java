package dexsys.ru.currencyconverter.data.remote;

import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.transform.RegistryMatcher;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;

import dexsys.ru.currencyconverter.data.model.Currency;
import dexsys.ru.currencyconverter.data.model.CurrencyList;

/**
 * Сервис для загрузки информации по валюте
 */
public class CurrencyService {

    private final String LIST_CURENCY_URL = "http://www.cbr.ru/scripts/XML_daily.asp";

    public ServiceResult<CurrencyList> loadCurrencysFromNetwork() throws IOException {
        InputStream stream = null;
        ServiceResult<CurrencyList> result = new ServiceResult<>();

        try {
            stream = downloadUrl(LIST_CURENCY_URL);
            CurrencyList currencyList = ConverterData.getInstance().convertToObject(stream);
            result.setResult(currencyList);
            result.setSuccessfully(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccessfully(false);
        } finally {
            if (stream != null) {
                stream.close();
            }
        }

        return result;
    }



    private InputStream downloadUrl(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000 /* milliseconds */);
        conn.setConnectTimeout(15000 /* milliseconds */);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        // Starts the query
        conn.connect();
        return conn.getInputStream();
    }
}
