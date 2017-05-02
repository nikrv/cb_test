package dexsys.ru.currencyconverter.ui.main;

import java.util.List;

import dexsys.ru.currencyconverter.data.model.Currency;
import dexsys.ru.currencyconverter.ui.base.MvpView;

public interface MainMvpView extends MvpView {
    public void showLoading();
    public void hideLoading();
    public void setListValute(List<Currency> listCurrency);
    public void setResultConver(String message);
    public void showValidateError(String string);
}
