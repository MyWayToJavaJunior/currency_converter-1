package ru.dezork.currenciesconverter.data;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import ru.dezork.currenciesconverter.entities.NetworkCurrencyList;

public class NetworkData implements INetworkData {
    private static final String URL = "http://www.cbr.ru/scripts/XML_daily.asp";

    @Override
    public NetworkCurrencyList getCurrencyList() {
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(URL);
            urlConnection = (HttpURLConnection) url.openConnection();
            int code = urlConnection.getResponseCode();
            if (code == 200){
                Serializer serializer = new Persister();
                return serializer.read(NetworkCurrencyList.class,urlConnection.getInputStream());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (urlConnection!=null)
                urlConnection.disconnect();
        }
        return null;
    }
}
