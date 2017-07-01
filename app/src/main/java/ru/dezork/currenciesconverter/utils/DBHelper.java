package ru.dezork.currenciesconverter.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import ru.dezork.currenciesconverter.entities.NetworkCurrency;
import ru.dezork.currenciesconverter.entities.StorageCurrency;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "currencies_converter";

    private static final String TABLE_NAME = "currencies_values";

    private static final String ID = "id";
    private static final String CODE = "code";
    private static final String VALUE = "value";
    private static final String NOMINAL = "nominal";

    private static final String CREATE_CURRENCIES_VALUES_TABLE = "CREATE TABLE " + TABLE_NAME + "("
            + ID + " INTEGER PRIMARY KEY," + CODE + " TEXT, "
            + VALUE+ " REAL, "+NOMINAL+" INTEGER)";
    private static DBHelper instance;

    /**
     * get instance of DBHelper
     * @param context
     * @return DBHelper
     */
    public static synchronized DBHelper getHelper(Context context){
        if (instance == null){
            instance = new DBHelper(context);
        }
        return instance;
    }

    private DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CURRENCIES_VALUES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * get list with currencies codes from DB
     * @return
     */
    public List<String> getCurrencies(){
        SQLiteDatabase db = getReadableDatabase();
        List<String> currencies = new ArrayList<>();
        Cursor cursor = db.query(TABLE_NAME,new String[]{CODE,VALUE},null,null,null,null,null);
        if (cursor!=null){
            if (cursor.moveToFirst()){
                currencies = currenciesCodesToList(cursor);
            }
            cursor.close();
        }
        db.close();
        return currencies;
    }

    /**
     * get storage currency by code
     * @param code
     * @return
     */
    public StorageCurrency getCurrencyByCode(String code){
        SQLiteDatabase db = getReadableDatabase();
        StorageCurrency currency = null;
        Cursor cursor = db.query(TABLE_NAME,new String[]{CODE,VALUE,NOMINAL},CODE+"=?",new String[]{code},null,null,null);
        if (cursor!=null) {
            if (cursor.moveToFirst()) {
                currency = getCurrency(cursor);
            }
        }
        return currency;
    }

    /**
     * get storage currency from cursor
     * @param cursor
     * @return
     */
    private StorageCurrency getCurrency(Cursor cursor) {
        StorageCurrency currency = new StorageCurrency();
        currency.setCode(cursor.getString(cursor.getColumnIndex(CODE)));
        currency.setValue(cursor.getFloat(cursor.getColumnIndex(VALUE)));
        currency.setNominal(cursor.getInt(cursor.getColumnIndex(NOMINAL)));
        return currency;
    }

    /**
     * update or insert (if there wasn't such code yet) currencies in DB
     * @param currencies
     */
    public void updateOrInsertCachedCurrencies(List<NetworkCurrency> currencies){
        SQLiteDatabase db = getWritableDatabase();
        for (int i=0;i<currencies.size();i++){
            ContentValues cv = new ContentValues();
            cv.put(CODE,currencies.get(i).getCode());
            cv.put(VALUE,currencies.get(i).getValue());
            cv.put(NOMINAL,currencies.get(i).getNominal());
            int result = db.update(TABLE_NAME,cv,CODE+"=?",new String[]{currencies.get(i).getCode()});
            if (result==0){
                db.insert(TABLE_NAME,CODE,cv);
            }
        }
        db.close();
    }

    /**
     * put values from cursor to List<String>
     * @param cursor
     * @return
     */
    private List<String> currenciesCodesToList(Cursor cursor){
        List<String> currencies = new ArrayList<>();
        do {
            currencies.add(cursor.getString(cursor.getColumnIndex(CODE)));
        } while (cursor.moveToNext());
        return currencies;
    }

}
