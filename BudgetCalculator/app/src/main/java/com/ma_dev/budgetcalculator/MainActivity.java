package com.ma_dev.budgetcalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.ma_dev.budgetcalculator.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFrame(new HomeFragment());

        binding.navBar.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.homeMenu:
                    replaceFrame(new HomeFragment());
                    break;
                case R.id.historyMenu:
                    replaceFrame(new HistoryFragment());
                    break;
                case R.id.statsMenu:
                    replaceFrame(new StatsFragment());
                    break;
                case R.id.settingsMenu:
                    replaceFrame(new SettingsFragment());
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