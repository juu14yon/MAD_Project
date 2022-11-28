package com.ma_dev.budgetcalculator;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FAQFragment extends Fragment {
    public FAQFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_f_a_q, container, false);
        TextView textview = (TextView)getActivity().findViewById(R.id.settingsHeader);
        textview.setText("FAQ");

        return root;
    }


}