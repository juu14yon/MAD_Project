package com.ma_dev.budgetcalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class SettingsActivity extends AppCompatActivity {
    static final String myPreference = "ADM-prefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = getSharedPreferences(myPreference,
                Context.MODE_PRIVATE);
        String currentTheme = sharedPreferences.getString("myTheme", "Light");

        if (!currentTheme.equals("Light") | AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.Theme_Dark);
        }else{
            setTheme(R.style.Theme_Light);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        replaceFrame(new SettingsFragment());
    }

    private void replaceFrame(Fragment frag){
        FragmentManager fragManager = getSupportFragmentManager();
        FragmentTransaction fragTransaction = fragManager.beginTransaction();
        fragTransaction.replace(R.id.settingsFrameLayout, frag);
        fragTransaction.commit();
    }

    public void backToMain(View v) {
        onBackPressed();
    }

    @Override
    public void onBackPressed(){
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}