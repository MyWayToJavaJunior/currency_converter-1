package ru.dezork.currenciesconverter.presentation.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ru.dezork.currenciesconverter.R;
import ru.dezork.currenciesconverter.presentation.presenter.CurrencyPresenter;
import ru.dezork.currenciesconverter.presentation.presenter.ICurrencyPresenter;

public class CurrencyConverterActivity extends AppCompatActivity implements ICurrencyView{

    private ICurrencyPresenter mPresenter;
    private Spinner mInitCurrency,mResultCurrency;
    private EditText mInitValue;
    private TextView mResultValue;
    private ArrayAdapter<String> currenciesAdapter;
    private int mCurrentInitPosition, mCurrentResultPosition;
    private boolean mCanUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_converter);
        mPresenter = new CurrencyPresenter();
        bindViews();
        initializeCurrenciesPositions();
        setListeners();
        if (savedInstanceState==null){
            mPresenter.updateCachedCurrencies();
        } else {
            actionsIfRecreated(savedInstanceState);
        }
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        mCanUpdate = true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.onAttachView(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("init_value",mInitValue.getText().toString());
        outState.putString("result_value",mResultValue.getText().toString());
        super.onSaveInstanceState(outState);
    }
    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.onDetachView();
    }
    @Override
    public void setResult(String result) {
        mResultValue.setText(result);
    }

    @Override
    public void populateSpinners(List<String> currencies) {
        currenciesAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,currencies);
        mInitCurrency.setAdapter(currenciesAdapter);
        mInitCurrency.setSelection(mCurrentInitPosition);
        mResultCurrency.setAdapter(currenciesAdapter);
        mResultCurrency.setSelection(mCurrentResultPosition);
    }

    @Override
    public void showError() {
        Toast.makeText(this,R.string.error_message,Toast.LENGTH_SHORT).show();
    }


    private void initializeCurrenciesPositions() {
        mCurrentInitPosition = 0;
        mCurrentResultPosition = 1;
    }
    /**
     * set listeners to edit text with init amount and spinners with currencies codes
     */
    private void setListeners() {
        mInitValue.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (!v.getText().toString().equals("")) {
                        showKeyboard(false);
                        mPresenter.calculateResult(mInitCurrency.getSelectedItem().toString(),
                                mResultCurrency.getSelectedItem().toString(), v.getText().toString());
                        return true;
                    }
                }
                return false;
            }
        });
        mInitCurrency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == mResultCurrency.getSelectedItemPosition()) {
                    mInitCurrency.setSelection(mResultCurrency.getSelectedItemPosition());
                    mResultCurrency.setSelection(mCurrentInitPosition);
                }
                if(mCanUpdate){
                    mResultValue.setText("");
                }
                mCurrentInitPosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mResultCurrency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == mInitCurrency.getSelectedItemPosition()) {
                    mResultCurrency.setSelection(mInitCurrency.getSelectedItemPosition());
                    mInitCurrency.setSelection(mCurrentResultPosition);
                }
                 if(mCanUpdate){
                    mResultValue.setText("");
                }
                mCurrentResultPosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    /**
     * if view was recreated set previous values
     * @param savedInstanceState
     */
    private void actionsIfRecreated(Bundle savedInstanceState) {
        mCanUpdate = false;
        String initValue = savedInstanceState.getString("init_value");
        String resultValue = savedInstanceState.getString("result_value");
        if (initValue!=null && !initValue.isEmpty()){
            mInitValue.setText(initValue);
        }
        if (resultValue!=null && !resultValue.isEmpty()){
            mResultValue.setText(resultValue);
        }
    }
    /**
     * bind views to variables
     */
    private void bindViews() {
        mInitCurrency = (Spinner) findViewById(R.id.spinner_init_valute);
        mResultCurrency = (Spinner) findViewById(R.id.spinner_result_valute);
        mInitValue = (EditText) findViewById(R.id.initial_currency);
        mResultValue = (TextView) findViewById(R.id.result_currency);
    }
    /**
     * show or hide keyboard depends on given parameter
     * @param showKeyboard show if true and hide if false
     */
    public void showKeyboard(boolean showKeyboard) {
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (showKeyboard){
            inputManager.hideSoftInputFromWindow(mInitValue.getWindowToken(),
                    InputMethodManager.SHOW_IMPLICIT);
        }else {
            inputManager.hideSoftInputFromWindow(mInitValue.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
