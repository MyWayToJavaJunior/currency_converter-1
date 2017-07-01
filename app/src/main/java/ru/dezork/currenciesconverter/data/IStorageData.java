package ru.dezork.currenciesconverter.data;

import java.util.List;

import ru.dezork.currenciesconverter.entities.NetworkCurrency;
import ru.dezork.currenciesconverter.entities.StorageCurrency;

public interface IStorageData {
    /**
     * get currencies from cache
     * @return
     */
    List<String> getCurrenciesCodes();

    /**
     * get currency object by currency code
     * @param code
     * @return
     */
    StorageCurrency getCurrencyByCode(String code);

    /**
     * update cached currencies in cache
     * @param currencies
     */
    void updateCachedCurrencies(List<NetworkCurrency> currencies);
}
