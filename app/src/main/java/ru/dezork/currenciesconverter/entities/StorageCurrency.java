package ru.dezork.currenciesconverter.entities;

/**
 * Class to store data from DB storage and uses through app
 */
public class StorageCurrency {
    private String code;
    private float value;
    private int nominal;

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
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
