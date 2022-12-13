package com.ma_dev.budgetcalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ma_dev.budgetcalculator.databinding.ActivityMainBinding;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    ActivityMainBinding binding;
    TextView header;
    String currentTheme, currentLanguage;
    boolean currentNotif = true;
    static final String myPreference = "ADM-prefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = getSharedPreferences(myPreference,
                Context.MODE_PRIVATE);
        currentTheme = sharedPreferences.getString("myTheme", "Light");
        currentNotif = sharedPreferences.getBoolean("myNotifs", true);
        currentLanguage = sharedPreferences.getString("myLang", "en");

        if (!Locale.getDefault().getLanguage().equals(currentLanguage)){
            Locale locale = new Locale(currentLanguage);
            Locale.setDefault(locale);
            Resources resources = getResources();
            Configuration config = resources.getConfiguration();
            config.setLocale(locale);
            recreate();
        }

        if (!currentTheme.equals("Light") | AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.Theme_Dark);
        }else{
            setTheme(R.style.Theme_Light);
        }
        
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFrame(new HomeFragment());

        header = findViewById(R.id.headerText);

        binding.navBar.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.homeMenu:
                    header.setText(R.string.dashboard);
                    replaceFrame(new HomeFragment());
                    break;
                case R.id.historyMenu:
                    header.setText(R.string.history);
                    replaceFrame(new HistoryFragment());
                    break;
                case R.id.statsMenu:
                    header.setText(R.string.monthly_report);
                    replaceFrame(new StatsFragment());
                    break;
            }

            return true;
        });
    }

    private void replaceFrame(Fragment frag){
        FragmentManager fragManager = getSupportFragmentManager();
        FragmentTransaction fragTransaction = fragManager.beginTransaction();
        fragTransaction.replace(R.id.frameLayout, frag);
        fragTransaction.commit();
    }

    public void openSettings(View view) {
        startActivity(new Intent(MainActivity.this, SettingsActivity.class));
        finish();
    }
}