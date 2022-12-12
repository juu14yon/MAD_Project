package com.ma_dev.budgetcalculator;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

public class StatsFragment extends Fragment {
    DataBaseHandler dbh;

    ArrayList<Float> totals;
    float expensesSum = 0;

    TextView transPrcntg, foodPrcntg, utilityPrcntg, necessaryPrcntg, entertPrcntg, healthPrcntg, lifePrcntg;
    PieChart pie;

    String[] categories;

    final int[] colorCodes = new int[]{R.color.Transportation, R.color.Food, R.color.Utilities, R.color.Necessary,
            R.color.Entertainment, R.color.Health, R.color.Lifestyle};

    public StatsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_stats, container, false);
        Context c = getContext();
        dbh = new DataBaseHandler(c);

        categories = new String[]{c.getString(R.string.transportation), c.getString(R.string.food),
                c.getString(R.string.utilities), c.getString(R.string.necessary_payments),
                c.getString(R.string.entertainment), c.getString(R.string.health_and_medical),
                c.getString(R.string.lifestyle)};

        pie = rootView.findViewById(R.id.statsPie);

        totals = dbh.getCategoriesStats();
        for (float value : totals){
            expensesSum += value;
        }

        transPrcntg = rootView.findViewById(R.id.transportationPercentage);
        foodPrcntg = rootView.findViewById(R.id.foodPercentage);
        utilityPrcntg = rootView.findViewById(R.id.utilitiesPercentage);
        necessaryPrcntg = rootView.findViewById(R.id.necessaryPercentage);
        entertPrcntg = rootView.findViewById(R.id.entertainmentPercentage);
        healthPrcntg = rootView.findViewById(R.id.healthPercentage);
        lifePrcntg = rootView.findViewById(R.id.lifestylePercentage);

        transPrcntg.setText(toPercentage(totals.get(0)));
        foodPrcntg.setText(toPercentage(totals.get(1)));
        utilityPrcntg.setText(toPercentage(totals.get(2)));
        necessaryPrcntg.setText(toPercentage(totals.get(3)));
        entertPrcntg.setText(toPercentage(totals.get(4)));
        healthPrcntg.setText(toPercentage(totals.get(5)));
        lifePrcntg.setText(toPercentage(totals.get(6)));

        setupPie();
        loadPieData(c);

        return rootView;
    }

    private void setupPie() {
        pie.setDrawHoleEnabled(true);
        pie.setTransparentCircleAlpha(0);
        pie.setUsePercentValues(true);
        pie.setCenterText(getString(R.string.total) + expensesSum);
        pie.setCenterTextSize(16);
        pie.getDescription().setEnabled(false);
        pie.setDrawEntryLabels(false);

        Legend legend = pie.getLegend();
        legend.setEnabled(false);
    }

    private void loadPieData(Context c) {
        ArrayList<PieEntry> entries = new ArrayList<>();

        for (int i = 0; i< categories.length; i++){
            entries.add(new PieEntry(totals.get(i), categories[i]));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Expense Category");
        dataSet.setColors(colorCodes, c);

        PieData data = new PieData(dataSet);
        data.setDrawValues(false);
        pie.setData(data);
        pie.invalidate();

        pie.animateY(1400, Easing.EaseInOutQuad);
    }

    public String toPercentage(float num){
        String out = "";
        try {
            num /= expensesSum;
        } catch (Exception ignored) {
        }

        double roundOff = (double) Math.round((num) * 10000) / 100;

        return out + roundOff + "%";
    }
}