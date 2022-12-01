package com.ma_dev.budgetcalculator;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public class StatsFragment extends Fragment {
    PieChart pie;
    DataBaseHandler dbh;
    ArrayList<Float> totals;
    float expencesSum = 0;
    TextView transPrcntg, foodPrcntg, utilityPrcntg, necessaryPrcntg, entertPrcntg, healthPrcntg, lifePrcntg;

    final String[] categories = new String[]{"Transportation", "Food", "Utilities", "Necessary Payments",
            "Entertainment", "Health and medical", "Lifestyle"};
    final int[] colorCodes = new int[]{R.color.Transportation, R.color.Food, R.color.Utilities, R.color.Necessary,
            R.color.Entertainment, R.color.Health, R.color.Lifestyle};

    public StatsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_stats, container, false);
        Context c = getContext();

        pie = root.findViewById(R.id.statsPie);

        dbh = new DataBaseHandler(c);
        totals = dbh.getCategoriesStats();
        for (float value : totals){
            expencesSum += value;
        }

        transPrcntg = root.findViewById(R.id.transportationPercentage);
        foodPrcntg = root.findViewById(R.id.foodPercentage);
        utilityPrcntg = root.findViewById(R.id.utilitiesPercentage);
        necessaryPrcntg = root.findViewById(R.id.necessaryPercentage);
        entertPrcntg = root.findViewById(R.id.entertainmentPercentage);
        healthPrcntg = root.findViewById(R.id.healthPercentage);
        lifePrcntg = root.findViewById(R.id.lifestylePercentage);

        transPrcntg.setText(toPercentage(totals.get(0)));
        foodPrcntg.setText(toPercentage(totals.get(1)));
        utilityPrcntg.setText(toPercentage(totals.get(2)));
        necessaryPrcntg.setText(toPercentage(totals.get(3)));
        entertPrcntg.setText(toPercentage(totals.get(4)));
        healthPrcntg.setText(toPercentage(totals.get(5)));
        lifePrcntg.setText(toPercentage(totals.get(6)));

        setupPie();
        loadPieData(c);

        return root;
    }

    private void setupPie() {
        pie.setDrawHoleEnabled(true);
        pie.setUsePercentValues(true);
        pie.setCenterText("Total:\n" + expencesSum);
        pie.setCenterTextSize(16);
        pie.getDescription().setEnabled(false);
        pie.setDrawEntryLabels(false);

        Legend legend = pie.getLegend();
        legend.setEnabled(false);
    }

    private void loadPieData(Context c) {
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        for (int i = 0; i< categories.length; i++){
            entries.add(new PieEntry(totals.get(i), categories[i]));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Expense Category");
        dataSet.setColors(colorCodes, c);

        PieData data = new PieData(dataSet);
        data.setDrawValues(false);
        //data.setValueFormatter(new PercentFormatter(pie));
        //data.setValueTextSize(12f);
        //data.setValueTextColor(R.attr.text1_color);

        pie.setData(data);
        pie.invalidate();

        pie.animateY(1400, Easing.EaseInOutQuad);
    }

    public String toPercentage(float num){
        String out = "";
        try {
            num /= expencesSum;
        } catch (Exception e) {
        }

        double roundOff = (double) Math.round((num) * 10000) / 100;

        return out + roundOff + "%";
    }
}