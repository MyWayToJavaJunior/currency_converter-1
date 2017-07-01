package ru.dezork.currenciesconverter.data;

import java.util.List;

import ru.dezork.currenciesconverter.entities.NetworkCurrency;
import ru.dezork.currenciesconverter.entities.NetworkCurrencyList;
import ru.dezork.currenciesconverter.entities.StorageCurrency;

public class CurrencyRepository implements  ICurrencyRepository {
    private INetworkData mNetworkData;
    private IStorageData mStorageData;
    public CurrencyRepository(){
        mNetworkData = new NetworkData();
        mStorageData = new StorageData();
    }
    @Override
    public List<String> getCurrenciesCodes() {
        return mStorageData.getCurrenciesCodes();
    }

    @Override
    public StorageCurrency getCurrencyByCode(String code) {
        return mStorageData.getCurrencyByCode(code);
    }

    @Override
    public List<NetworkCurrency> updateCachedCurrencies() {
        NetworkCurrencyList currencyList = mNetworkData.getCurrencyList();
        if (currencyList!=null){
            mStorageData.updateCachedCurrencies(mNetworkData.getCurrencyList().getList());
            return currencyList.getList();
        } else {
            return null;
        }
    }
}
