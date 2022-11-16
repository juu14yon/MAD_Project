package com.ma_dev.budgetcalculator;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

public class SettingsFragment extends Fragment {
    Switch themeSwitch;
    Button themeButton;
    Switch notifSwitch;
    Button notifButton;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        Button aboutButton = (Button) rootView.findViewById(R.id.aboutButton);

        themeSwitch = rootView.findViewById(R.id.themeSwitch);
        themeButton = rootView.findViewById(R.id.themeButton);
        notifSwitch = rootView.findViewById(R.id.notificationSwitch);
        notifButton = rootView.findViewById(R.id.notificationButton);

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            themeSwitch.setChecked(true);
        }else{
            themeSwitch.setChecked(false);
        }

        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.add(R.id.settingsFrameLayout, new AboutUsFragment());
                transaction.commit();
            }
        });

        themeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
                else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
        });

        themeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                themeSwitch.setChecked(!themeSwitch.isChecked());
            }
        });

        notifButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifSwitch.setChecked(!notifSwitch.isChecked());
            }
        });


        return rootView;
    }
}