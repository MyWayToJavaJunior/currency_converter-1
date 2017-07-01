package ru.dezork.currenciesconverter.presentation.presenter;

import android.os.AsyncTask;

import java.util.List;

import ru.dezork.currenciesconverter.domain.CurrencyInteractor;
import ru.dezork.currenciesconverter.domain.ICurrencyInteractor;
import ru.dezork.currenciesconverter.entities.NetworkCurrency;
import ru.dezork.currenciesconverter.presentation.view.ICurrencyView;


public class CurrencyPresenter implements ICurrencyPresenter {
    private ICurrencyInteractor mInteractor;
    private NewCurrencyAsync mAsyncTask;
    private ICurrencyView mView;
    private List<String> mCurrencies;

    public CurrencyPresenter(){
        mInteractor = new CurrencyInteractor();
    }

    @Override
    public void onAttachView(ICurrencyView currencyView) {
        mView = currencyView;
        mCurrencies = mInteractor.getCurrenciesCodes();
        mView.populateSpinners(mCurrencies);
    }


    @Override
    public void calculateResult(String initCurrencyCode, String resultCurrencyCode, String valueToConvert) {
        float result = mInteractor.calculateResult(initCurrencyCode,resultCurrencyCode,Float.parseFloat(valueToConvert));
        mView.setResult(String.valueOf(result));
    }

    @Override
    public void updateCachedCurrencies() {
        mAsyncTask = new NewCurrencyAsync();
        mAsyncTask.execute();
    }

    @Override
    public void onDetachView() {
        if (mAsyncTask!=null && !mAsyncTask.isCancelled()){
            mAsyncTask.cancel(true);
        }
        mView = null;
    }

    /**
     * async task class to perform network actions in background
     */
    class NewCurrencyAsync extends AsyncTask<Void,Void,List<NetworkCurrency>> {
        @Override
        protected List<NetworkCurrency> doInBackground(Void... params) {
            return mInteractor.updateCachedCurrencies();
        }

        @Override
        protected void onPostExecute(List<NetworkCurrency> list) {
            if (list==null){
                // only if there wasn't any cached mCurrencies, show an error
                if (mCurrencies.isEmpty()){
                    mView.showError();
                }

            } else {
                // if there wasn't any cache before preload, get changed mCurrencies again
                if (mCurrencies.isEmpty()){
                    mCurrencies = mInteractor.getCurrenciesCodes();
                    mView.populateSpinners(mCurrencies);
                }
            }
        }
    }
}
