package dexsys.ru.currencyconverter.data.remote;

import org.simpleframework.xml.transform.Transform;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by blood on 01.05.2017.
 */
public class NumberFormatTransformer implements Transform<Double> {

    private Locale locale;

    public NumberFormatTransformer(Locale locale) {
        this.locale = locale;
    }

    @Override
    public Double read(String value) throws Exception {
        NumberFormat format = NumberFormat.getInstance(locale);
        Number number = format.parse(value);
        return number.doubleValue();
    }

    @Override
    public String write(Double value) throws Exception {
        return String.valueOf(value);
    }
}
