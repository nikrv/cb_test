package dexsys.ru.currencyconverter.ui.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by blood on 01.05.2017.
 */
public abstract class BasePresenterActivity<P extends Presenter<V>, V extends MvpView> extends AppCompatActivity {
    private static final String TAG = "base-activity";
    private static final int LOADER_ID = 101;

    private P presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Loader<P> loader = getSupportLoaderManager().getLoader(loaderId());

        if (loader == null) {
            initLoader();
        } else {
            this.presenter = ((PresenterLoader<P>) loader).getPresenter();
            onPresenterCreatedOrRestored(presenter);
        }
    }

    private void initLoader() {
        getSupportLoaderManager().initLoader(loaderId(), null, new LoaderManager.LoaderCallbacks<P>() {

            @Override
            public final Loader<P> onCreateLoader(int id, Bundle args) {
                return new PresenterLoader<>(BasePresenterActivity.this, getPresenterFactory(), tag());
            }

            @Override
            public final void onLoadFinished(Loader<P> loader, P presenter) {
                BasePresenterActivity.this.presenter = presenter;
                onPresenterCreatedOrRestored(presenter);
            }

            @Override
            public final void onLoaderReset(Loader<P> loader) {
                BasePresenterActivity.this.presenter = null;
            }

        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        presenter.onAttachView(getPresenterView());
    }


    @Override
    protected void onStop() {
        presenter.onDetachView();
        super.onStop();
    }

    @NonNull
    protected abstract String tag();

    @NonNull
    protected abstract PresenterFactory<P> getPresenterFactory();

    protected abstract void onPresenterCreatedOrRestored(@NonNull P presenter);

    @NonNull
    protected V getPresenterView() {
        return (V) this;
    }

    public P getPresenter() {
        return presenter;
    }

    protected int loaderId() {
        return LOADER_ID;
    }
}
