package com.ma_dev.budgetcalculator;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.HashMap;

public class updateRecordsFragment extends Fragment {
    private DatePickerDialog datePickerDialog;
    private String date;
    private DataBaseHandler dbh;

    Button dateButton;
    Button cancelButton;
    Button saveButton;

    EditText nameInput;
    EditText amountInput;
    EditText descriptionInput;

    Spinner categoryInput;

    Integer id;

    public updateRecordsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_update_records, container, false);
        Context c = getContext();
        dbh = new DataBaseHandler(c);

        TextView header = getActivity().findViewById(R.id.headerText);
        header.setText("New Record");

        categoryInput = rootView.findViewById(R.id.updateCategorySpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(c,
                R.array.categories_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoryInput.setAdapter(adapter);

        dateButton = rootView.findViewById(R.id.updateDate);
        cancelButton = rootView.findViewById(R.id.updateCancelButton);
        saveButton = rootView.findViewById(R.id.updateSaveButton);

        nameInput = rootView.findViewById(R.id.updateName);
        amountInput = rootView.findViewById(R.id.updateAmount);
        descriptionInput = rootView.findViewById(R.id.updateDescription);

        Bundle bundle = this.getArguments();

        if(bundle != null){
            id = bundle.getInt("id");
        }

        initDatePicker(c);

        HashMap<String,String> record = dbh.getARecord(id);
        nameInput.setText(record.get("Name"));
        amountInput.setText(record.get("Amount").substring(1));
        descriptionInput.setText(record.get("Description"));
        dateButton.setText(record.get("Date"));
        categoryInput.setSelection(((ArrayAdapter)categoryInput.getAdapter()).getPosition(record.get("Category")));

        dateButton.setOnClickListener(v -> datePickerDialog.show());

        cancelButton.setOnClickListener(v -> {
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.add(R.id.frameLayout, new HistoryFragment());
            transaction.commit();
        });

        saveButton.setOnClickListener(v -> {
            String n = nameInput.getText().toString();
            String a = amountInput.getText().toString();
            String d = descriptionInput.getText().toString();
            String c1 = categoryInput.getSelectedItem().toString();
            String dt = dateButton.getText().toString();

            if (n.isEmpty() || a.isEmpty() || c1.isEmpty() || dt.isEmpty()) {
                Toast.makeText(getContext(), "Please enter all the data..", Toast.LENGTH_SHORT).show();
                return;
            }

            if (d.isEmpty()){
                d = "-";
            }

            if (c1.equals("Income")){
                a = "+" + Double.parseDouble(a);
            }
            else{
                a = "-" + Double.parseDouble(a);
            }

            dbh.updateRecordDetails(n, a, d, c1, dt, id);

            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.add(R.id.frameLayout, new HistoryFragment());
            transaction.commit();
        });

        return rootView;
    }

    private void initDatePicker(Context c){
        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, day) -> {
            month = month+1;
            date = makeDateString(day, month, year);
            dateButton.setText(date);
        };

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(c, dateSetListener, year, month, day);

    }

    private String makeDateString(int day, int month, int year) {
        return day+"."+month+"."+year;
    }
}