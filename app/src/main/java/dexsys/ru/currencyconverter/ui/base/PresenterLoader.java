package dexsys.ru.currencyconverter.ui.base;

import android.content.Context;
import android.support.v4.content.Loader;


final class PresenterLoader<T extends Presenter> extends Loader<T> {

    private final PresenterFactory<T> factory;
    private final String tag;
    private T presenter;

    PresenterLoader(Context context, PresenterFactory<T> factory, String tag) {
        super(context);
        this.factory = factory;
        this.tag = tag;
    }


    @Override
    protected void onStartLoading() {
        // if we already own a presenter instance, simply deliver it.
        if (presenter != null) {
            deliverResult(presenter);
            return;
        }

        forceLoad();
    }


    @Override
    protected void onForceLoad() {
        // Create the Presenter using the Factory
        presenter = factory.create();
        // Deliver the result
        deliverResult(presenter);
    }


    @Override
    public void deliverResult(T data) {
        super.deliverResult(data);
    }


    @Override
    protected void onStopLoading() {
    }


    @Override

    protected void onReset() {
        if (presenter != null) {
            presenter.onDestroyed();
            presenter = null;
        }
    }


    public T getPresenter() {
        return presenter;
    }

}
