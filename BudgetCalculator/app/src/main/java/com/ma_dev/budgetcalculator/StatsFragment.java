package com.ma_dev.budgetcalculator;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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
        pie = root.findViewById(R.id.statsPie);
        dbh = new DataBaseHandler(getContext());
        totals = dbh.getCategoriesStats();

        setupPie();
        loadPieData();

        return root;
    }

    private void setupPie() {
        pie.setDrawHoleEnabled(true);
        pie.setUsePercentValues(true);
        pie.setEntryLabelTextSize(12);
        pie.setEntryLabelColor(Color.BLACK);
        pie.setCenterText("REPORT");
        pie.setCenterTextSize(24);
        pie.getDescription().setEnabled(false);

        Legend legend = pie.getLegend();
        legend.setEnabled(false);
    }

    private void loadPieData() {
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        for (int i = 0; i< categories.length; i++){
            entries.add(new PieEntry(totals.get(i), categories[i]));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Expense Category");
        dataSet.setColors(colorCodes);

        PieData data = new PieData(dataSet);
        data.setDrawValues(false);
        //data.setValueFormatter(new PercentFormatter(pie));
        //data.setValueTextSize(12f);
        //data.setValueTextColor(R.attr.text1_color);

        pie.setData(data);
        pie.invalidate();

        pie.animateY(1400, Easing.EaseInOutQuad);
    }
}