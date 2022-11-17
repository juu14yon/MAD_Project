package com.ma_dev.budgetcalculator;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class DataBaseHandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "history";
    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "transactionHistory";

    private static final String ID_COL = "id";
    private static final String NAME_COL = "name";
    private static final String AMOUNT_COL = "amount";
    private static final String DESCRIPTION_COL = "description";
    private static final String CATEGORY_COL = "category";
    private static final String DATE_COL = "date";
    private static final String DAY_COL = "day";
    private static final String MONTH_COL = "month";
    private static final String YEAR_COL = "year";


    public DataBaseHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    // below method is for creating a database by running a sqlite query
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DATE_COL + " TEXT,"
                + DAY_COL + " TEXT,"
                + MONTH_COL + " TEXT,"
                + YEAR_COL + " TEXT,"
                + NAME_COL + " TEXT,"
                + AMOUNT_COL + " TEXT,"
                + DESCRIPTION_COL + " TEXT,"
                + CATEGORY_COL + " TEXT)";
        
        db.execSQL(query);
    }

    public boolean addNewRecord(String name, String amount, String description, String category, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(toDate(date));

        values.put(NAME_COL, name);
        values.put(AMOUNT_COL, amount);
        values.put(DESCRIPTION_COL, description);
        values.put(CATEGORY_COL, category);
        values.put(DATE_COL, date);
        values.put(DAY_COL, ""+calendar.get(Calendar.DAY_OF_MONTH));
        values.put(MONTH_COL, ""+calendar.get(Calendar.MONTH));
        values.put(YEAR_COL, ""+calendar.get(Calendar.YEAR));

        db.insert(TABLE_NAME, null, values);
        db.close();
        return true;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.setVersion(oldVersion);
    }

    // ???
    @SuppressLint("Range")
    public ArrayList<HashMap<String, String>> GetRecords(String year, String month, String day){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> recordList = new ArrayList<>();
        String query = "SELECT name, category, amount, id " +
                "FROM "+ TABLE_NAME +
                where(year, month, day) +
                " ORDER BY date DESC, id DESC;";

        Cursor cursor = db.rawQuery(query,null);

        while (cursor.moveToNext()){
            HashMap<String,String> record = new HashMap<>();
            record.put("Name", cursor.getString(cursor.getColumnIndex(NAME_COL)));
            record.put("Category", cursor.getString(cursor.getColumnIndex(CATEGORY_COL)));
            record.put("Amount", cursor.getString(cursor.getColumnIndex(AMOUNT_COL)));
            record.put("ID", cursor.getString(cursor.getColumnIndex(ID_COL)));

            recordList.add(record);
        }

        return  recordList;
    }

    public boolean DeleteRecord(int recordid){
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABLE_NAME, ID_COL + " = ?", new String[]{String.valueOf(recordid)});
            db.close();
            return true;
        } catch(Exception e){
            return false;
        }

    }

    public int UpdateRecordDetails(String name, Double amount, String description, String category, String date, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cVals = new ContentValues();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(toDate(date));

        cVals.put(NAME_COL, name);
        cVals.put(AMOUNT_COL, amount);
        cVals.put(DESCRIPTION_COL, description);
        cVals.put(CATEGORY_COL, category);
        cVals.put(DATE_COL, date);
        cVals.put(DAY_COL, ""+calendar.get(Calendar.DAY_OF_MONTH));
        cVals.put(MONTH_COL, ""+calendar.get(Calendar.MONTH));
        cVals.put(YEAR_COL, ""+calendar.get(Calendar.YEAR));

        int count = db.update(TABLE_NAME, cVals, ID_COL+" = ?",new String[]{String.valueOf(id)});
        return  count;
    }

    public String where(String year, String month, String day){
        String out = "";
        if (year.equals("All") && month.equals("All") && day.equals("All")){
            return out;
        }
        else{
            out += " WHERE ";
            if (!year.equals("All")){
                out += "year = '" + year + "'";
            }
            if (!month.equals("All")){
                out += "month = '" + month + "'";
            }
            if (!day.equals("All")){
                out += "day = '" + day + "'";
            }
        }
        return out;
    }

    public Date toDate(String s){
        Date wholeDate;
        try {
            wholeDate = new SimpleDateFormat("dd.MM.yyyy").parse(s);
        } catch (ParseException e) {
            wholeDate = new Date();
        }
        return wholeDate;
    }
}