package ru.dezork.currenciesconverter.domain;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

import ru.dezork.currenciesconverter.data.CurrencyRepository;
import ru.dezork.currenciesconverter.data.ICurrencyRepository;
import ru.dezork.currenciesconverter.entities.NetworkCurrency;
import ru.dezork.currenciesconverter.entities.StorageCurrency;

public class CurrencyInteractor implements ICurrencyInteractor {
    private DecimalFormat mFormatter;
    private ICurrencyRepository mCurrencyRepository;

    public CurrencyInteractor(){
        mCurrencyRepository = new CurrencyRepository();
        mFormatter =new DecimalFormat("#.####",new DecimalFormatSymbols(Locale.US));
    }
    @Override
    public List<String> getCurrenciesCodes() {
        return mCurrencyRepository.getCurrenciesCodes();
    }

    @Override
    public List<NetworkCurrency> updateCachedCurrencies() {
        return mCurrencyRepository.updateCachedCurrencies();
    }

    @Override
    public float calculateResult(String initCurrencyCode, String resultCurrencyCode, float valueToConvert) {
        // if value to convert is zero (no value to convert)
        // or result currency is null
        // or init currency is null
        // just return 0
        StorageCurrency initNetworkCurrency = mCurrencyRepository.getCurrencyByCode(initCurrencyCode);
        StorageCurrency resultNetworkCurrency = mCurrencyRepository.getCurrencyByCode(resultCurrencyCode);
        if (valueToConvert==0 || initNetworkCurrency ==null || resultNetworkCurrency ==null){
            return 0;
        } else {
            float result = valueToConvert * ((initNetworkCurrency.getValue()/ initNetworkCurrency.getNominal())
                    /(resultNetworkCurrency.getValue()/ resultNetworkCurrency.getNominal()));
            return Float.valueOf(mFormatter.format(result));
        }

    }

}
