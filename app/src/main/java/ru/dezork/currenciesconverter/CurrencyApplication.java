package ru.dezork.currenciesconverter;

import android.app.Application;

import ru.dezork.currenciesconverter.utils.DBHelper;


public class CurrencyApplication extends Application {
    private static DBHelper dbHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        dbHelper = DBHelper.getHelper(this);
    }

    /**
     * since there is no DI framework, get dbhelper instance from singleton
     * @return
     */
    public static DBHelper getDBHelper() {
        return dbHelper;
    }
}
