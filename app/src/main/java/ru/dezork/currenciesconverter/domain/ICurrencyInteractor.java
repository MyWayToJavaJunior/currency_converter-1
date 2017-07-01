package ru.dezork.currenciesconverter.domain;

import java.util.List;

import ru.dezork.currenciesconverter.entities.NetworkCurrency;

public interface ICurrencyInteractor {
    /**
     * get currencies code with currencies values
     * @return
     */
    List<String> getCurrenciesCodes();
    /**
     * updated cached currencies values
     * @return
     */
    List<NetworkCurrency> updateCachedCurrencies();

    /**
     * calculate result amount by given values
     * @param initCurrencyCode
     * @param resultCurrencyCode
     * @param valueToConvert
     * @return
     */
    float calculateResult(String initCurrencyCode,String resultCurrencyCode,float valueToConvert);
}
