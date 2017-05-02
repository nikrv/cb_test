package dexsys.ru.currencyconverter.data.remote;

import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.transform.RegistryMatcher;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import dexsys.ru.currencyconverter.data.DataManager;
import dexsys.ru.currencyconverter.data.local.DatabaseHelper;
import dexsys.ru.currencyconverter.data.model.CurrencyList;

/**
 * Created by blood on 01.05.2017.
 */

public class ConverterData {

    private static ConverterData instance ;

    public static ConverterData getInstance() {
        if (instance == null) {
            instance = new ConverterData();
        }

        return instance;
    }

    public CurrencyList convertToObject(InputStream stream) throws Exception {
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");

        RegistryMatcher m = new RegistryMatcher();
        m.bind(Date.class, new DateFormatTransformer(format));
        m.bind(Double.class, new NumberFormatTransformer(Locale.FRANCE));

        Persister serializer = new Persister(m);

        return serializer.read(CurrencyList.class, stream);
    }
}
