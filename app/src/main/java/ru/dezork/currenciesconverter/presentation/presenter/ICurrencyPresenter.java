package ru.dezork.currenciesconverter.presentation.presenter;

import ru.dezork.currenciesconverter.presentation.view.ICurrencyView;

public interface ICurrencyPresenter {
    /**
     * attach view to presenter
     * @param currencyView
     */
    void onAttachView(ICurrencyView currencyView);

    /**
     * detach view from presenter (to not have a memory leak)
     */
    void onDetachView();

    /**
     * calculate result amount by given values
     * @param initCurrencyCode
     * @param resultCurrencyCode
     * @param valueToConvert
     */
    void calculateResult(String initCurrencyCode,String resultCurrencyCode,String valueToConvert);

    /**
     * updated cached currencies values
     */
    void updateCachedCurrencies();
}
