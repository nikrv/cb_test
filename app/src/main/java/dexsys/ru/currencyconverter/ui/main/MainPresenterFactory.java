package dexsys.ru.currencyconverter.ui.main;

import dexsys.ru.currencyconverter.ui.base.PresenterFactory;

/**
 * Created by blood on 01.05.2017.
 */
public class MainPresenterFactory implements PresenterFactory<MainPresenter> {
    @Override
    public MainPresenter create() {
        return new MainPresenter();
    }
}
