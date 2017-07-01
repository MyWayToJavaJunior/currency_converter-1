package ru.dezork.currenciesconverter.data;

import java.util.List;

import ru.dezork.currenciesconverter.CurrencyApplication;
import ru.dezork.currenciesconverter.entities.NetworkCurrency;
import ru.dezork.currenciesconverter.entities.StorageCurrency;
import ru.dezork.currenciesconverter.utils.DBHelper;

public class StorageData implements IStorageData {

    private DBHelper mDBHelper;

    public StorageData(){
        mDBHelper = CurrencyApplication.getDBHelper();
    }
    @Override
    public List<String> getCurrenciesCodes() {
        return mDBHelper.getCurrencies();
    }

    @Override
    public StorageCurrency getCurrencyByCode(String code) {
        return mDBHelper.getCurrencyByCode(code);
    }

    @Override
    public void updateCachedCurrencies(List<NetworkCurrency> currencies) {
        mDBHelper.updateOrInsertCachedCurrencies(currencies);
    }
}
