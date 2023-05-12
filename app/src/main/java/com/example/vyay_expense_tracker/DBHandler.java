package com.example.vyay_expense_tracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DBHandler extends SQLiteOpenHelper {

    private static final String DB_NAME="expenses";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "dailyExpenseTable";
    private static final String SNO = "sno";
    private static final String DATE = "date";
    private static final String TIME = "time";
    private static final String AMOUNT = "amount";
    private static final String REASON = "reason";

    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + SNO + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DATE + " TEXT,"
                + TIME + " TEXT,"
                + AMOUNT + " TEXT,"
                + REASON + " TEXT)";
        db.execSQL(query);
    }


    public int newPayment(String amount,String date,String time,String reason){
        int f=0;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(DATE, date);
            values.put(TIME, time);
            values.put(AMOUNT, amount);
            values.put(REASON, reason);
            db.insert(TABLE_NAME, null, values);
            f=1;
            db.close();
            f=2;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return f;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
