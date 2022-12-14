package com.ma_dev.budgetcalculator;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.util.Calendar;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DatabaseHandlerTests {
    private DataBaseHandler dbh;
    private String name = "Name";
    private String amountPos =  "+9210";
    private String amountNeg =  "-9210";
    private String description = "Description";
    private String category = "Income";
    private String date;
    private int year;
    boolean flag;

    @Before
    public void A_createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        dbh = new DataBaseHandler(context);

        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        date = "" + calendar.get(Calendar.DAY_OF_MONTH) + "." + (calendar.get(Calendar.MONTH) + 1) + "." + year;
    }

    @Test
    public void B_addNewRecordTest() throws Exception {
        flag =  dbh.addNewRecord(name, amountPos, description, category, date);
        assertTrue(flag);
    }

    @Test
    public void C_getMinYearTest() throws Exception {
        assertEquals(year, dbh.getMinYear());
    }

    @Test
    public void D_getMaxYearTest() throws Exception {
        assertEquals(year, dbh.getMaxYear());
    }

    @Test
    public void E_getRecordTest() throws Exception {
        HashMap<String,String> record = dbh.getARecord(1);
        String gotName = record.get("Name");
        String gotAmount = record.get("Amount");
        String gotDesc = record.get("Description");
        String gotCat = record.get("Category");
        String gotDate = record.get("Date");
        flag = false;

        if (gotName.equals(name) & gotAmount.equals(amountPos) & gotDesc.equals(description) & gotCat.equals(category) & gotDate.equals(date)){
            flag = true;
        }

        assertTrue(flag);
    }

    @Test
    public void F_updateRecordATest() throws Exception {
        dbh.updateRecordDetails("Noname", amountPos, description, category, date, 1);
        HashMap<String,String> recordNew = dbh.getARecord(1);

        String gotName = recordNew.get("Name");
        String gotAmount = recordNew.get("Amount");
        String gotDesc = recordNew.get("Description");
        String gotCat = recordNew.get("Category");
        String gotDate = recordNew.get("Date");
        flag = false;

        if (gotName.equals("Noname") & gotAmount.equals(amountPos) & gotDesc.equals(description) & gotCat.equals(category) & gotDate.equals(date)){
            flag = true;
        }

        assertTrue(flag);
    }

    @Test
    public void G_getTotalsTest() throws Exception {
        dbh.addNewRecord(name, amountNeg, description, "Food", date);
        dbh.updateTotals();
        Double available = dbh.getAvailable();
        Double income = dbh.getIncome();
        Double expenses = dbh.getExpences();

        assertEquals(""+available, "0.0");
        assertEquals(""+income, "9210.0");
        assertEquals(""+expenses, "9210.0");
    }

//    @Test
//    public void Z_deleteRecordTest() throws Exception {
//        assertTrue(dbh.deleteRecord(1));
//    }

}
