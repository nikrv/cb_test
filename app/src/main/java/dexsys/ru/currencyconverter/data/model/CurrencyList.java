package dexsys.ru.currencyconverter.data.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Список валют
 */
@Root(name="ValCurs")
public class CurrencyList {
    @ElementList(inline=true)
    private List<Currency> list = new ArrayList<>();

    @Attribute(name="name")
    public String name;

    @Attribute(name="Date")
    public Date date;

    public List<Currency> getList() {
        return list;
    }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }
}
