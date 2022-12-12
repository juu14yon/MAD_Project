package com.ma_dev.budgetcalculator;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class AboutUsFragment extends Fragment {
    public AboutUsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_about_us, container, false);
        TextView header = getActivity().findViewById(R.id.settingsHeader);
        header.setText("About Us");

        Button goBack = rootView.findViewById(R.id.goBackButton);

        goBack.setOnClickListener(v -> {
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.add(R.id.settingsFrameLayout, new SettingsFragment());
            transaction.commit();
        });

        return rootView;
    }
}