package com.randc.productivityapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageButton;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.randc.productivityapp.Constants;
import com.randc.productivityapp.HelperClasses.RoundedBarChartRenderer;
import com.randc.productivityapp.HelperClasses.SideNavClass;
import com.randc.productivityapp.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GoalCenter extends AppCompatActivity {

    //Heading
    private ImageButton menuButton;
    private SideNavClass mSideNavClass;

    //Chart
    private BarChart dateChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_center);

        findViewsById(savedInstanceState);
        setOnClickListeners();
        setupBarChart();
    }

    private void findViewsById(Bundle savedInstanceState){
        menuButton = findViewById(R.id.menuButton);
        dateChart = findViewById(R.id.goalsDateChart);

        mSideNavClass = new SideNavClass(GoalCenter.this, savedInstanceState, Constants.goalsActivity);
        mSideNavClass.setUpSideNav();
    }

    private void setOnClickListeners(){

    }

    private void setupBarChart(){
        dateChart.setBackgroundColor(Color.TRANSPARENT);
        dateChart.setExtraTopOffset(-30f);
        dateChart.setExtraBottomOffset(10f);
        //dateChart.setExtraLeftOffset(10f);
        //dateChart.setExtraRightOffset(10f);
        dateChart.setDrawBarShadow(true);
        dateChart.setDrawValueAboveBar(true);
        dateChart.getDescription().setEnabled(false);

        // scaling can now only be done on x- and y-axis separately
        dateChart.setPinchZoom(false);

        dateChart.setDrawGridBackground(false);

        XAxis xAxis = dateChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setTextColor(Color.LTGRAY);
        xAxis.setTextSize(12f);
        xAxis.setCenterAxisLabels(false);
        xAxis.setLabelCount(7);
        xAxis.setGranularity(1f);

        YAxis left = dateChart.getAxisLeft();
        left.setDrawLabels(false);
        //left.setSpaceTop(25f);
        //left.setSpaceBottom(25f);
        left.setDrawAxisLine(false);
        left.setDrawGridLines(false);
        left.setDrawZeroLine(false); // draw a zero line
        //left.setZeroLineColor(Color.TRANSPARENT);
        //left.setZeroLineWidth(0.7f);
        dateChart.getAxisRight().setEnabled(false);
        dateChart.getLegend().setEnabled(false);


        // THIS IS THE ORIGINAL DATA YOU WANT TO PLOT
        final List<Data> data = new ArrayList<>();
        data.add(new Data(0f, randomGenerator(-10,10), "Sun"));
        data.add(new Data(1f, randomGenerator(-10,10), "Mon"));
        data.add(new Data(2f, randomGenerator(-10,10), "Tue"));
        data.add(new Data(3f, randomGenerator(-10,10), "Wed"));
        data.add(new Data(4f, randomGenerator(-10,10), "Thurs"));
        data.add(new Data(5f, randomGenerator(-10,10), "Fri"));
        data.add(new Data(6f, randomGenerator(-10,10), "Sat"));

        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return data.get(Math.min(Math.max((int) value, 0), data.size()-1)).xAxisValue;
            }
        });

        setBarData(data);
    }

    private void setBarData(List<Data> dataList) {

        ArrayList<BarEntry> values = new ArrayList<>();
        List<Integer> colors = new ArrayList<>();

        int pColor = Color.parseColor("#21e8c7");
        int nColor = Color.parseColor("#f03145");

        for (int i = 0; i < dataList.size(); i++) {

            Data d = dataList.get(i);
            BarEntry entry = new BarEntry(d.xValue, d.yValue);
            values.add(entry);

            // specific colors
            if (d.yValue >= 0)
                colors.add(pColor);
            else
                colors.add(nColor);
        }

        BarDataSet set;

        if (dateChart.getData() != null &&
                dateChart.getData().getDataSetCount() > 0) {
            set = (BarDataSet) dateChart.getData().getDataSetByIndex(0);
            set.setValues(values);
            //set.setDrawValues(false);
            set.setBarShadowColor(Color.parseColor("#1A1F3D"));
            dateChart.getData().notifyDataChanged();
            dateChart.notifyDataSetChanged();
        } else {
            set = new BarDataSet(values, "Values");
            set.setColors(colors);
            //set.setDrawValues(false);
            set.setValueTextColors(colors);
            BarData data = new BarData(set);
            data.setValueTextSize(13f);
            List<Integer> valueColors = new ArrayList<>();
            valueColors.add(Color.TRANSPARENT);
            valueColors.add(Color.TRANSPARENT);
            if (values.get(2).getY()>0) {
                valueColors.add(pColor);
            }
            else {
                valueColors.add(nColor);
            }
            valueColors.add(Color.TRANSPARENT);
            valueColors.add(Color.TRANSPARENT);
            valueColors.add(Color.TRANSPARENT);
            valueColors.add(Color.TRANSPARENT);
            data.setValueTextColors(valueColors);
            data.setValueFormatter(new Formatter());
            data.setBarWidth(0.4f);
            set.setBarShadowColor(Color.parseColor("#1A1F3D"));
            dateChart.setData(data);
        }

        RoundedBarChartRenderer roundedBarChartRenderer= new RoundedBarChartRenderer(dateChart,dateChart.getAnimator(),dateChart.getViewPortHandler());
        roundedBarChartRenderer.setmRadius(15f);
        dateChart.setRenderer(roundedBarChartRenderer);


        dateChart.animateXY(0, 1000);
        dateChart.invalidate();
    }

    private int randomGenerator(int min, int max){
        return new Random().nextInt(max - min + 1) + min;
    }

    private static class Data {

        final String xAxisValue;
        final float yValue;
        final float xValue;

        Data(float xValue, float yValue, String xAxisValue) {
            this.xAxisValue = xAxisValue;
            this.yValue = yValue;
            this.xValue = xValue;
        }
    }

    private static class Formatter extends ValueFormatter
    {

        private final DecimalFormat mFormat;

        Formatter() {
            mFormat = new DecimalFormat("###### goals");
        }

        @Override
        public String getFormattedValue(float value) {

            if (value<0){
                return mFormat.format(-value);
            }
            else {
                return mFormat.format(value);
            }
        }
    }
}