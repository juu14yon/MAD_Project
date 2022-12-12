package com.ma_dev.budgetcalculator;

import android.app.AlertDialog;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HistoryFragment extends Fragment {
    View rootView;
    Context c;
    DataBaseHandler dbh;
    Integer currentID;

    Button button;
    Spinner daySpinner, monthSpinner, yearSpinner;
    ListView lv;

    String year = "All";
    String month = "All";
    String day = "All";

    List<String> yearItems = new ArrayList<>();
    List<String> monthItems = new ArrayList<>();
    List<String> dayItems = new ArrayList<>();

    ArrayList<Integer> ids = new ArrayList<>();

    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_history, container, false);
        c = getContext();
        dbh = new DataBaseHandler(c);

        TextView header = getActivity().findViewById(R.id.headerText);
        header.setText("History");

        daySpinner = rootView.findViewById(R.id.daySpinner);
        monthSpinner = rootView.findViewById(R.id.monthSpinner);
        yearSpinner = rootView.findViewById(R.id.yearSpinner);

        daySpinner.setEnabled(false);
        yearItems.add("All");
        monthItems.add("All");

        for (int i = 1; i<=12; i++){
            monthItems.add(""+i);
        }
        ArrayAdapter<String> monthAdapter = new ArrayAdapter<>(c, android.R.layout.simple_spinner_dropdown_item, monthItems);
        monthSpinner.setAdapter(monthAdapter);

        for (int i = dbh.getMinYear(); i<=dbh.getMaxYear(); i++){
            yearItems.add(""+i);
        }
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(c, android.R.layout.simple_spinner_dropdown_item, yearItems);
        yearSpinner.setAdapter(yearAdapter);

        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                year = yearSpinner.getSelectedItem().toString();
                fillHistory(rootView, c);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                month = monthSpinner.getSelectedItem().toString();
                dayItems.clear();
                day = "All";
                fillHistory(rootView, c);

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

                ArrayAdapter<String> dayAdapter = new ArrayAdapter<>(c, android.R.layout.simple_spinner_dropdown_item, dayItems);
                daySpinner.setAdapter(dayAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        daySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                day = daySpinner.getSelectedItem().toString();
                fillHistory(rootView, c);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                day = "All";
            }
        });

        fillHistory(rootView, c);

        lv.setOnItemLongClickListener((parent, view, position, id) -> {
            currentID = ids.get(position);
            showEditOptions(currentID);
            return true;
        });

        button = rootView.findViewById(R.id.newRecordButton);
        button.setOnClickListener(v -> {
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.add(R.id.frameLayout, new newRecordFragment());
            transaction.commit();
        });

        return rootView;
    }

    private void showEditOptions(Integer currentID) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("Edit Records");
        builder.setMessage("");
        builder.setCancelable(true);

        builder.setPositiveButton("Update", (dialog, which) -> {
            Bundle bundle = new Bundle();
            bundle.putInt("id", currentID);

            updateRecordsFragment updateRecordsFragment = new updateRecordsFragment();
            updateRecordsFragment.setArguments(bundle);

            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.add(R.id.frameLayout, updateRecordsFragment);
            transaction.commit();
        });

        builder.setNegativeButton("Delete", (dialog, which) -> {
            if (dbh.deleteRecord(currentID)){
                Toast.makeText(c, "Record deleted successfully", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(c, "Could not delete this record", Toast.LENGTH_SHORT).show();
            }
            fillHistory(rootView, c);
        });

        builder.setNeutralButton("Cancel", (dialog, which) -> builder.create().dismiss());

        builder.show();
    }

    public void fillHistory(View root, Context c){
        ids.clear();
        
        ArrayList<HashMap<String, String>> recordList = dbh.getRecords(year, month, day);
        for (HashMap<String, String> items : recordList){
            ids.add(Integer.parseInt(items.get("ID")));
        }

        lv = root.findViewById(R.id.recordsListView);

        ListAdapter adapter = new SimpleAdapter(c,
                recordList, R.layout.listrow,
                new String[]{"Name", "Category", "Amount", "ID"},
                new int[]{R.id.nameText, R.id.categoryText, R.id.amountText, R.id.idText});
        lv.setAdapter(adapter);
    }

}