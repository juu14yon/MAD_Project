package com.ma_dev.budgetcalculator;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;

public class SettingsFragment extends Fragment {
    Switch themeSwitch, notifSwitch;
    Button themeButton, notifButton, faqButton;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        Button aboutButton = rootView.findViewById(R.id.aboutButton);

        themeSwitch = rootView.findViewById(R.id.themeSwitch);
        themeButton = rootView.findViewById(R.id.themeButton);
        notifSwitch = rootView.findViewById(R.id.notificationSwitch);
        notifButton = rootView.findViewById(R.id.notificationButton);
        faqButton = rootView.findViewById(R.id.faqButton);

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            themeSwitch.setChecked(true);
        }else{
            themeSwitch.setChecked(false);
        }

        aboutButton.setOnClickListener(v -> {
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.add(R.id.settingsFrameLayout, new AboutUsFragment());
            transaction.commit();
        });

        themeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
            else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });

        themeButton.setOnClickListener(v -> themeSwitch.setChecked(!themeSwitch.isChecked()));

        notifButton.setOnClickListener(v -> notifSwitch.setChecked(!notifSwitch.isChecked()));

        faqButton.setOnClickListener(v -> {
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.add(R.id.settingsFrameLayout, new FAQFragment());
            transaction.commit();
        });

        return rootView;
    }
}