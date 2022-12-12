package com.ma_dev.budgetcalculator;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

public class SettingsFragment extends Fragment {
    DataBaseHandler dbh;

    Switch themeSwitch, notifSwitch;
    Button themeButton, notifButton, faqButton, exportButton;
    SharedPreferences sharedPreferences;
    String currentTheme;
    boolean currentNotif;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        Context c = getContext();
        dbh = new DataBaseHandler(c);

        Button aboutButton = rootView.findViewById(R.id.aboutButton);

        themeSwitch = rootView.findViewById(R.id.themeSwitch);
        themeButton = rootView.findViewById(R.id.themeButton);
        notifSwitch = rootView.findViewById(R.id.notificationSwitch);
        notifButton = rootView.findViewById(R.id.notificationButton);
        faqButton = rootView.findViewById(R.id.faqButton);
        exportButton = rootView.findViewById(R.id.exportButton);

        sharedPreferences = getActivity().getSharedPreferences("ADM-prefs", Context.MODE_PRIVATE);
        currentTheme = sharedPreferences.getString("myTheme", "Light");
        currentNotif = sharedPreferences.getBoolean("myNotifs", true);

        aboutButton.setOnClickListener(v -> {
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.add(R.id.settingsFrameLayout, new AboutUsFragment());
            transaction.commit();
        });

        themeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            if (isChecked){
                editor.putString("myTheme", "Dark");
                currentTheme = "Dark";
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
            else{
                editor.putString("myTheme", "Light");
                currentTheme = "Light";
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
            editor.apply();
        });

        if (currentTheme.equals("Dark")){
            themeSwitch.setChecked(true);
        }else{
            themeSwitch.setChecked(false);
        }
        themeButton.setOnClickListener(v -> themeSwitch.setChecked(!themeSwitch.isChecked()));

        notifSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("myNotifs", isChecked);
            currentNotif = isChecked;
            editor.apply();
        });
        notifSwitch.setChecked(currentNotif);
        notifButton.setOnClickListener(v -> notifSwitch.setChecked(!notifSwitch.isChecked()));

        faqButton.setOnClickListener(v -> {
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.add(R.id.settingsFrameLayout, new FAQFragment());
            transaction.commit();
        });

        exportButton.setOnClickListener(v -> {
             if(dbh.exportDatabase()){
                 Toast.makeText(getContext(), R.string.export_successful, Toast.LENGTH_SHORT).show();
             }
        });

        return rootView;
    }
}