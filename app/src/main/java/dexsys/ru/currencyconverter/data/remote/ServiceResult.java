package dexsys.ru.currencyconverter.data.remote;

/**
 * Created by blood on 01.05.2017.
 */

public class ServiceResult<T>{

    private T result;
    private boolean isSuccessfully = false;

    /**
     * Получить результат
     *
     * @return
     */
    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public boolean isSuccessfully() {
        return isSuccessfully;
    }

    public void setSuccessfully(boolean successfully) {
        isSuccessfully = successfully;
    }
}
