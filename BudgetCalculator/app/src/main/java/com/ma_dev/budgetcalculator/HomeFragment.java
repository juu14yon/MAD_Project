package com.ma_dev.budgetcalculator;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class HomeFragment extends Fragment {
    TextView availableText, incomeText, expencesText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        Context c = getContext();

        DataBaseHandler dbh = new DataBaseHandler(c);

        availableText = root.findViewById(R.id.availableValue);
        incomeText = root.findViewById(R.id.incomeValue);
        expencesText = root.findViewById(R.id.expencesValue);

        dbh.updateTotals();

        availableText.setText("" + dbh.getAvailable());
        incomeText.setText("+" + dbh.getIncome());
        expencesText.setText("-" + dbh.getExpences());

        return root;
    }
}