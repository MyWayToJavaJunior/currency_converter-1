package ru.dezork.currenciesconverter.entities;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * class to parse data from xml
 */
@Root(name = "Valute",strict = false)
public class NetworkCurrency {
    @Element(name = "CharCode")
    private String code;

    @Element(name = "Value")
    private String value;

    @Element(name = "Nominal")
    private int nominal;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getNominal() {
        return nominal;
    }

    public void setNominal(int nominal) {
        this.nominal = nominal;
    }
}
