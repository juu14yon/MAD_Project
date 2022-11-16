package com.ma_dev.budgetcalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.ma_dev.budgetcalculator.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    TextView header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.Theme_Dark);
        }else{
            setTheme(R.style.Theme_Light);
        }
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFrame(new HomeFragment());

        header = (TextView) findViewById(R.id.headerText);

        binding.navBar.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.homeMenu:
                    header.setText("Dashboard");
                    replaceFrame(new HomeFragment());
                    break;
                case R.id.historyMenu:
                    header.setText("History");
                    replaceFrame(new HistoryFragment());
                    break;
                case R.id.statsMenu:
                    header.setText("Monthly Report");
                    replaceFrame(new StatsFragment());
                    break;
                case R.id.settingsMenu:
                    /*
                    header.setText("Settings");
                    replaceFrame(new SettingsFragment());
                     */
                    startActivity(new Intent(MainActivity.this, SettingsActivity.class));
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
}