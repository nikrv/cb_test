package dexsys.ru.currencyconverter.ui.base;

/**
 * Created by blood on 01.05.2017.
 */
public interface PresenterFactory<T extends Presenter> {
    T create();
}
