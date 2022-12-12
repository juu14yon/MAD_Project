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

    private Double available = 0.0, income = 0.0, expences = 0.0;

    public DataBaseHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DATE_COL + " TEXT,"
                + DAY_COL + " INTEGER,"
                + MONTH_COL + " INTEGER,"
                + YEAR_COL + " INTEGER,"
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
        values.put(DAY_COL, calendar.get(Calendar.DAY_OF_MONTH));
        values.put(MONTH_COL, calendar.get(Calendar.MONTH)+1);
        values.put(YEAR_COL, calendar.get(Calendar.YEAR));

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

    @SuppressLint("Range")
    public ArrayList<HashMap<String, String>> getRecords(String year, String month, String day){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> recordList = new ArrayList<>();
        String query = "SELECT name, category, amount, id " +
                "FROM "+ TABLE_NAME +
                whereDateFilter(year, month, day) +
                " ORDER BY year DESC, month DESC, day DESC, id DESC;";

        Cursor cursor = db.rawQuery(query,null);

        while (cursor.moveToNext()){
            HashMap<String,String> record = new HashMap<>();
            record.put("Name", cursor.getString(cursor.getColumnIndex(NAME_COL)));
            record.put("Category", cursor.getString(cursor.getColumnIndex(CATEGORY_COL)));
            record.put("Amount", cursor.getString(cursor.getColumnIndex(AMOUNT_COL)));
            record.put("ID", cursor.getString(cursor.getColumnIndex(ID_COL)));

            recordList.add(record);
        }

        return recordList;
    }

    @SuppressLint("Range")
    public HashMap<String,String> getARecord(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        HashMap<String,String> record = new HashMap<>();
        String query = "SELECT * " +
                "FROM "+ TABLE_NAME +
                " WHERE id = " + id;

        Cursor cursor = db.rawQuery(query,null);

        while (cursor.moveToNext()){
            record.put("Name", cursor.getString(cursor.getColumnIndex(NAME_COL)));
            record.put("Category", cursor.getString(cursor.getColumnIndex(CATEGORY_COL)));
            record.put("Amount", cursor.getString(cursor.getColumnIndex(AMOUNT_COL)));
            record.put("Description", cursor.getString(cursor.getColumnIndex(DESCRIPTION_COL)));
            record.put("Date", cursor.getString(cursor.getColumnIndex(DATE_COL)));
        }

        return record;
    }

    public boolean deleteRecord(int recordid){
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABLE_NAME, ID_COL + " = ?", new String[]{String.valueOf(recordid)});
            db.close();
            return true;
        } catch(Exception e){
            return false;
        }

    }

    public void updateRecordDetails(String name, String amount, String description, String category, String date, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cVals = new ContentValues();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(toDate(date));

        cVals.put(NAME_COL, name);
        cVals.put(AMOUNT_COL, amount);
        cVals.put(DESCRIPTION_COL, description);
        cVals.put(CATEGORY_COL, category);
        cVals.put(DATE_COL, date);
        cVals.put(DAY_COL, calendar.get(Calendar.DAY_OF_MONTH));
        cVals.put(MONTH_COL, calendar.get(Calendar.MONTH)+1);
        cVals.put(YEAR_COL, calendar.get(Calendar.YEAR));

        db.update(TABLE_NAME, cVals, ID_COL+" = ?",new String[]{String.valueOf(id)});
    }

    public String whereDateFilter(String year, String month, String day){
        String out = "";
        boolean y = false;
        boolean m = false;

        if (year.equals("All") && month.equals("All") && day.equals("All")){
            return out;
        }
        else{
            out += " WHERE ";
            if (!year.equals("All")){
                out += "year = '" + year + "'";
                y = true;
            }
            if (!month.equals("All")){
                if (y){
                    out += " AND";
                }
                out += " month = '" + month + "'";
                m = true;
            }
            if (!day.equals("All")){
                if (y || m){
                    out += " AND";
                }
                out += " day = '" + day + "' ";
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

    @SuppressLint("Range")
    public int getMinYear(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT year" +
                " FROM "+ TABLE_NAME +
                " WHERE year = (SELECT MIN(year) FROM " + TABLE_NAME + ");";

        Cursor cursor = db.rawQuery(query,null);

        if(cursor.moveToNext()){
            return Integer.parseInt(cursor.getString(cursor.getColumnIndex(YEAR_COL)));
        }
        else{
            return 2022;
        }
    }
    @SuppressLint("Range")
    public int getMaxYear(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT year" +
                " FROM "+ TABLE_NAME +
                " WHERE year = (SELECT MAX(year) FROM " + TABLE_NAME + ");";

        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToNext()){
            return Integer.parseInt(cursor.getString(cursor.getColumnIndex(YEAR_COL)));
        }
        else{
            return 2022;
        }
    }

    @SuppressLint("Range")
    public void updateTotals(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT amount " +
                "FROM "+ TABLE_NAME;

        Cursor cursor = db.rawQuery(query,null);

        String temp, sign;
        double value;
        while (cursor.moveToNext()){
            temp = cursor.getString(cursor.getColumnIndex(AMOUNT_COL));
            sign = temp.substring(0, 1);
            value = Double.parseDouble(temp.substring(1));
            if(sign.equals("+")){
                income += value;
            }
            else{
                expences += value;
            }
        }

        available = income - expences;

        income = 0.0;
        expences = 0.0;

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;

        query = "SELECT amount " +
                "FROM "+ TABLE_NAME + " WHERE year = " + year + " AND month = " + month;

        cursor = db.rawQuery(query,null);

        while (cursor.moveToNext()){
            temp = cursor.getString(cursor.getColumnIndex(AMOUNT_COL));
            sign = temp.substring(0, 1);
            value = Double.parseDouble(temp.substring(1));
            if(sign.equals("+")){
                income += value;
            }
            else{
                expences += value;
            }
        }
    }

    public Double getAvailable(){
        return available;
    }
    public Double getIncome(){
        return income;
    }
    public Double getExpences(){
        return expences;
    }

    @SuppressLint("Range")
    public ArrayList<Float> getCategoriesStats(){
        final String[] categories = new String[]{"Transportation", "Food", "Utilities", "Necessary Payments", "Entertainment", "Health and medical", "Lifestyle"};
        ArrayList<Float> totals = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;
        double value;
        float total;
        String query;
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;

        for (String ctgr: categories
             ) {
            query = "SELECT amount " +
                    "FROM "+ TABLE_NAME +
                    " WHERE category = \"" + ctgr + "\"" + " AND year = " + year + " AND month = " + month;

            total = 0;

            try{
                cursor = db.rawQuery(query,null);
                while (cursor.moveToNext()){
                    value = Float.parseFloat(cursor.getString(cursor.getColumnIndex(AMOUNT_COL)).substring(1));
                    total += value;
                }
            } catch (NumberFormatException e) {
                total = 0;
            }

            totals.add(total);
        }

        return totals;
    }
}