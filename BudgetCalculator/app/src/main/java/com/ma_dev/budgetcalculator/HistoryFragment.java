package com.ma_dev.budgetcalculator;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HistoryFragment extends Fragment {
    Button button;
    DataBaseHandler dbh;
    Spinner daySpinner, monthSpinner, yearSpinner;
    String year = "All";
    String month = "All";
    String day = "All";
    List<String> yearItems = new ArrayList<String>();
    List<String> monthItems = new ArrayList<String>();
    List<String> dayItems = new ArrayList<String>();

    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_history, container, false);
        Context c = getContext();
        dbh = new DataBaseHandler(c);

        daySpinner = root.findViewById(R.id.daySpinner);
        monthSpinner = root.findViewById(R.id.monthSpinner);
        yearSpinner = root.findViewById(R.id.yearSpinner);

        daySpinner.setEnabled(false);

        // FILL IN YEAR ARRAY
        // THE MONTH IS WRONG: CHOOSE 10, DISPLAYS 11
        yearItems.add("All");
        monthItems.add("All");
        for (int i = 1; i<=12; i++){
            monthItems.add(""+i);
        }
        for (int i = dbh.minYear(); i<=dbh.maxYear(); i++){
            yearItems.add(""+i);
        }

        ArrayAdapter<String> yearAdapter = new ArrayAdapter<String>(c, android.R.layout.simple_spinner_dropdown_item, yearItems);
        yearSpinner.setAdapter(yearAdapter);
        ArrayAdapter<String> monthAdapter = new ArrayAdapter<String>(c, android.R.layout.simple_spinner_dropdown_item, monthItems);
        monthSpinner.setAdapter(monthAdapter);

        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                year = yearSpinner.getSelectedItem().toString();
                fillHistory(root, c);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                month = monthSpinner.getSelectedItem().toString();
                dayItems.clear();
                day = "All";
                fillHistory(root, c);

                if (!month.equals("All")) {
                    daySpinner.setEnabled(true);

                    dayItems.add("All");

                    for (int i = 1; i <= 30; i++) {
                        dayItems.add("" + i);
                    }
                }
                else{
                    daySpinner.setEnabled(false);
                    dayItems.clear();
                }
                ArrayAdapter<String> dayAdapter = new ArrayAdapter<String>(c, android.R.layout.simple_spinner_dropdown_item, dayItems);
                daySpinner.setAdapter(dayAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        daySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                day = daySpinner.getSelectedItem().toString();
                fillHistory(root, c);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                day = "All";
            }
        });


/*
Refer to this link to create context menu:
https://stackoverflow.com/questions/17207366/creating-a-menu-after-a-long-click-event-on-a-list-view

        lv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                TextView idView = v.findViewById(R.id.idText);
                int id = Integer.parseInt(idView.getText().toString());

                if(dbh.DeleteRecord(id)){
                    Toast.makeText(c, "Deleted successfully", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(c, "Could not delete record", Toast.LENGTH_SHORT).show();
                }

                return true;
            }
        });

 */

        TextView textview = (TextView)getActivity().findViewById(R.id.headerText);
        textview.setText("History");

        button = (Button) root.findViewById(R.id.newRecordButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.add(R.id.frameLayout, new newRecordFragment());
                transaction.commit();
            }
        });

        return root;
    }

    public void fillHistory(View root, Context c){
        /*if (!month.equals("All")){
            month = "" + (Integer.parseInt(month) - 1);
        }

         */
        ArrayList<HashMap<String, String>> recordList = dbh.GetRecords(year, month, day);
        ListView lv = (ListView) root.findViewById(R.id.recordsListView);
        ListAdapter adapter = new SimpleAdapter(c,
                recordList, R.layout.listrow,
                new String[]{"Name", "Category", "Amount", "ID"},
                new int[]{R.id.nameText, R.id.categoryText, R.id.amountText, R.id.idText});
        lv.setAdapter(adapter);
    }
}