package com.ma_dev.budgetcalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ma_dev.budgetcalculator.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    TextView header;
    Double available, income, expences;

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
    }

    private void setAvailable(Double a){
        available = a;
    }
    private void setIncome(Double a){
        income = a;
    }
    private void setExpences(Double a){
        expences = a;
    }

    private Double getAvailable(){
        return available;
    }
    private Double getIncome(){
        return income;
    }
    private Double getExpences(){
        return expences;
    }
}