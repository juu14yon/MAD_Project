package com.ma_dev.budgetcalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.TextView;

import com.ma_dev.budgetcalculator.databinding.ActivityMainBinding;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    TextView header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
                    //replaceFrame(new newRecordFragment());
                    break;
                case R.id.statsMenu:
                    header.setText("Monthly Report");
                    replaceFrame(new StatsFragment());
                    break;
                case R.id.settingsMenu:
                    header.setText("Settings");
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