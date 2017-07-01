package ru.dezork.currenciesconverter.data;

import ru.dezork.currenciesconverter.entities.NetworkCurrencyList;


public interface INetworkData {
    /**
     * get currencies from network
     * @return
     */
    NetworkCurrencyList getCurrencyList();
}
