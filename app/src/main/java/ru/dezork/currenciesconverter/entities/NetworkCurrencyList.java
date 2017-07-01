package ru.dezork.currenciesconverter.entities;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;
/**
 * class to parse data from xml
 */
@Root(name = "ValCurs",strict = false)
public class NetworkCurrencyList {
    @ElementList(inline = true)
    private List<NetworkCurrency> list;

    public List<NetworkCurrency> getList() {
        return list;
    }
}
