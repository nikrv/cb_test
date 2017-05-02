package dexsys.ru.currencyconverter.data.model;

import android.provider.BaseColumns;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

/**
 * Created by blood on 01.05.2017.
 */

public class CurrencyReaderContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public CurrencyReaderContract() {}

    /* Inner class that defines the table contents */
    public static abstract class CurrencyEntry implements BaseColumns {
        public static final String TABLE_NAME = "currency";
        public static final String COLUMN_NAME_ID = "currencyid";
        public static final String COLUMN_NAME_CHARCODE = "charcode";
        public static final String COLUMN_NAME_NOMINAL = "nominal";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_VALUE = "value";
        public static final String COLUMN_NAME_NULLABLE = "null";
    }
}
