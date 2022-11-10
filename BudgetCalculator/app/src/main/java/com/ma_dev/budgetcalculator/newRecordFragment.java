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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class newRecordFragment extends Fragment {
    Spinner dropdown;

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

    TextView warningMessage;

    public newRecordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_new_record, container, false);
        TextView textview = (TextView)getActivity().findViewById(R.id.headerText);
        textview.setText("New Record");

        dropdown = rootView.findViewById(R.id.categoryDropdown);
        Context c = getContext();
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(c,
                R.array.categories_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);

        dbh = new DataBaseHandler(c);

        dateButton = (Button) rootView.findViewById(R.id.buttonDate);
        cancelButton = (Button) rootView.findViewById(R.id.cancelButton);
        saveButton = (Button) rootView.findViewById(R.id.saveButton);

        nameInput = (EditText) rootView.findViewById(R.id.nameInput);
        amountInput = (EditText) rootView.findViewById(R.id.amountInput);
        descriptionInput = (EditText) rootView.findViewById(R.id.descriptionInput);
        categoryInput = (Spinner) rootView.findViewById(R.id.categoryDropdown);
        warningMessage = (TextView) rootView.findViewById(R.id.warningMessage);

        initDatePicker(c);

        dateButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                datePickerDialog.show();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.add(R.id.frameLayout, new HistoryFragment());
                transaction.commit();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String n = nameInput.getText().toString();
                String a = amountInput.getText().toString();
                String d = descriptionInput.getText().toString();
                String c = categoryInput.getSelectedItem().toString();
                String dt = dateButton.getText().toString();

                if (n.isEmpty() || a.isEmpty() || c.isEmpty() || dt.isEmpty()) {
                    Toast.makeText(getContext(), "Please enter all the data..", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (d.isEmpty()){
                    d = "-";
                }

                if (c.equals("Income")){
                    a = "+" + Double.parseDouble(a);
                }
                else{
                    a = "-" + Double.parseDouble(a);
                }

                if(dbh.addNewRecord(n, a, d, c, dt)) {
                    Toast.makeText(getContext(), "Saved Successfully", Toast.LENGTH_SHORT).show();
                }

                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.add(R.id.frameLayout, new HistoryFragment());
                transaction.commit();
            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }

    private void initDatePicker(Context c){
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month+1;
                date = makeDateString(day, month, year);
                dateButton.setText(date);
            }
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


    private void saveRecordToExcel(){

    }
}