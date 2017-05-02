package dexsys.ru.currencyconverter.ui.main;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import dexsys.ru.currencyconverter.R;
import dexsys.ru.currencyconverter.data.model.Currency;
import dexsys.ru.currencyconverter.ui.adapters.CurrencyAdapter;
import dexsys.ru.currencyconverter.ui.base.BasePresenterActivity;
import dexsys.ru.currencyconverter.ui.base.PresenterFactory;
import dexsys.ru.currencyconverter.ui.dialog.ProgressDialogFragment;

public class MainActivity extends BasePresenterActivity<MainPresenter, MainMvpView> implements MainMvpView {

    private Spinner autocompleSourceCurrency, autocompleFinalCurrency;
    private EditText textCurrencyAmount;
    private Button buttonCalculation;
    private View spinerLoading, viewMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initControls();
    }

    private void initControls() {
        textCurrencyAmount = (EditText) findViewById(R.id.text_currency_amount);
        autocompleSourceCurrency = (Spinner) findViewById(R.id.autocomple_source_currency);
        autocompleFinalCurrency = (Spinner) findViewById(R.id.autocomple_final_currency);
        buttonCalculation = (Button) findViewById(R.id.button_calculation);
        spinerLoading = findViewById(R.id.spiner_loading);
        viewMain = findViewById(R.id.view_main);

        buttonCalculation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                converCurrency();
            }
        });
    }

    private void converCurrency() {
        Double amount = 0.0;

        try {
            amount = Double.parseDouble(textCurrencyAmount.getText().toString());
        } catch (NumberFormatException ex) {
        }

        Currency sourceCurrency = (Currency) autocompleSourceCurrency.getSelectedItem();
        Currency finalCurrency = (Currency) autocompleFinalCurrency.getSelectedItem();

        getPresenter().сonvertСurrency(amount, sourceCurrency, finalCurrency);
    }

    @NonNull
    @Override
    protected String tag() {
        return "activity";
    }

    @NonNull
    @Override
    protected PresenterFactory<MainPresenter> getPresenterFactory() {
        return new MainPresenterFactory();
    }

    @Override
    protected void onPresenterCreatedOrRestored(@NonNull MainPresenter presenter) {
        presenter.onAttachView(getPresenterView());
        presenter.loadingCurrency();
    }

    @Override
    public void showLoading() {
        spinerLoading.setVisibility(View.VISIBLE);
        viewMain.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        spinerLoading.setVisibility(View.GONE);
        viewMain.setVisibility(View.VISIBLE);
    }

    @Override
    public void setListValute(List<Currency> listCurrency) {
        autocompleSourceCurrency.setAdapter(new CurrencyAdapter(getBaseContext(), listCurrency));
        autocompleFinalCurrency.setAdapter(new CurrencyAdapter(getBaseContext(), listCurrency));
    }

    @Override
    public void setResultConver(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.result);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setMessage(message);
        builder.create().show();
    }

    @Override
    public void showValidateError(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.errors);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setMessage(message);
        builder.create().show();
    }
}
