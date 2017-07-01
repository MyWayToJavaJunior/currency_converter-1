package ru.dezork.currenciesconverter.data;

import java.util.List;

import ru.dezork.currenciesconverter.entities.NetworkCurrency;
import ru.dezork.currenciesconverter.entities.StorageCurrency;


public interface ICurrencyRepository {
    /**
     * get currencies code with currencies values
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
     * updated cached currencies values
     * @return
     */
    List<NetworkCurrency> updateCachedCurrencies();
}
