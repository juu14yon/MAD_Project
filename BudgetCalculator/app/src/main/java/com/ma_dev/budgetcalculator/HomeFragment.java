package com.ma_dev.budgetcalculator;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class HomeFragment extends Fragment {
    TextView availableText, incomeText, expensesText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        Context c = getContext();
        DataBaseHandler dbh = new DataBaseHandler(c);

        availableText = rootView.findViewById(R.id.availableValue);
        incomeText = rootView.findViewById(R.id.incomeValue);
        expensesText = rootView.findViewById(R.id.expencesValue);

        dbh.updateTotals();

        availableText.setText("" + dbh.getAvailable());
        incomeText.setText("+" + dbh.getIncome());
        expensesText.setText("-" + dbh.getExpences());

        return rootView;
    }
}