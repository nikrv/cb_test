package dexsys.ru.currencyconverter.ui.base;

/**
 * Интерфейс для работы с презентором
 */
public interface Presenter<V extends MvpView> {
    void onAttachView(V mvpView);
    void onDetachView();
    void onDestroyed();
}