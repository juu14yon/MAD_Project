package com.ma_dev.budgetcalculator;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class HistoryFragment extends Fragment {
    Button button;
    DataBaseHandler dbh;

    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_history, container, false);
        Context c = getContext();
        dbh = new DataBaseHandler(c);

        ArrayList<HashMap<String, String>> recordList = dbh.GetRecords();
        ListView lv = (ListView) root.findViewById(R.id.recordsListView);
        ListAdapter adapter = new SimpleAdapter(c,
                recordList, R.layout.listrow,
                new String[]{"Name", "Category", "Amount", "ID"},
                new int[]{R.id.nameText, R.id.categoryText, R.id.amountText, R.id.idText});
        lv.setAdapter(adapter);
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
}
