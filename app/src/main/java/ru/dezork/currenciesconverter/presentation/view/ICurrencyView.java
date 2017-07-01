package ru.dezork.currenciesconverter.presentation.view;

import java.util.List;


public interface ICurrencyView {
    /**
     * set result amount of calculated currency
     * @param result
     */
    void setResult(String  result);

    /**
     * populate spinners with currencies codes
     * @param currencies
     */
    void populateSpinners(List<String> currencies);

    /**
     * show error on network fail
     */
    void showError();
}
