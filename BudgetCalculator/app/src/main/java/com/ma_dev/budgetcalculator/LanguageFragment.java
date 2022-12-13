package com.ma_dev.budgetcalculator;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Locale;

public class LanguageFragment extends Fragment {
    Button enLanguage, ruLanguage, kyLanguage;
    SharedPreferences sharedPreferences;
    String currentLanguage;

    public LanguageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_language, container, false);
        enLanguage = rootView.findViewById(R.id.enLanguage);
        ruLanguage = rootView.findViewById(R.id.ruLanguage);
        kyLanguage = rootView.findViewById(R.id.kyLanguage);

        sharedPreferences = getActivity().getSharedPreferences("ADM-prefs", Context.MODE_PRIVATE);
        currentLanguage = sharedPreferences.getString("myLang", "en");

        enLanguage.setOnClickListener(v -> {
            currentLanguage = "en";
            commit(getActivity(), sharedPreferences, currentLanguage);
        });

        ruLanguage.setOnClickListener(v -> {
            currentLanguage = "ru";
            commit(getActivity(), sharedPreferences, currentLanguage);
        });

        kyLanguage.setOnClickListener(v -> {
            currentLanguage = "ky";
            commit(getActivity(), sharedPreferences, currentLanguage);
        });

        return rootView;
    }

    private void commit(Activity activity, SharedPreferences sharedPreferences, String currentLanguage) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("myLang", currentLanguage);
        setLocale(activity, currentLanguage);
        editor.apply();
        activity.recreate();
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.add(R.id.settingsFrameLayout, new SettingsFragment());
        transaction.commit();
    }

    public static void setLocale(Activity activity, String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Resources resources = activity.getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }
}