package dexsys.ru.currencyconverter.ui.utils;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by blood on 01.05.2017.
 */

public class NumFormatter {

    public static String formatDecPriceRU(double number) {
        Locale fmtLocale = Locale.US;
        NumberFormat formatter = NumberFormat.getInstance(fmtLocale);
        formatter.setMaximumFractionDigits(2);
        formatter.setMinimumFractionDigits(2);
        String result = formatter.format(number);
        result = result.replace(",", " "); // заменили разделение пробелами
        result = result.replace(".", ","); // копейки отделяем запятой.
        if (result != null && result.substring(result.indexOf(",") + 1).equals("00")) {
            result = result.substring(0, result.indexOf(","));
        }
        return result;
    }
}
