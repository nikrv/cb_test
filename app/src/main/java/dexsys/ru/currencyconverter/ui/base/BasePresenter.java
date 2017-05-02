package dexsys.ru.currencyconverter.ui.base;

/**
 * Базовый презентер
 */
public class BasePresenter<T extends MvpView> implements Presenter<T> {
    private T mMvpView;

    @Override
    public void onAttachView(T mvpView) {
        mMvpView = mvpView;
    }

    @Override
    public void onDetachView() {
        mMvpView = null;
    }

    public boolean isViewAttached() {
        return mMvpView != null;
    }

    public T getMvpView() {
        return mMvpView;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
    }

    @Override
    public void onDestroyed() {
        // Nothing to clean up
    }

    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call Presenter.attachView(MvpView) before" +
                    " requesting data to the Presenter");
        }
    }
}
