package com.chandrachud.bubble.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.chandrachud.bubble.Adapters.appUsageAdapter;
import com.chandrachud.bubble.Adapters.customGoalListAdapter;
import com.chandrachud.bubble.Adapters.goalBubbleWalletAdapter;
import com.chandrachud.bubble.Adapters.todayProgressAdapter;
import com.chandrachud.bubble.Items.customGoalListItem;
import com.chandrachud.bubble.Items.goalBubbleWalletItem;
import com.chandrachud.bubble.Items.todayProgressItem;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.chandrachud.bubble.Constants;
import com.chandrachud.bubble.HelperClasses.RoundedBarChartRenderer;
import com.chandrachud.bubble.HelperClasses.SideNavClass;
import com.chandrachud.bubble.R;
import com.robinhood.ticker.TickerView;
import com.shunan.circularprogressbar.CircularProgressBar;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class GoalCenter extends AppCompatActivity {

    //Heading
    private ImageButton menuButton;
    private SideNavClass mSideNavClass;
    private ImageView goalsAddButton;

    //Chart
    private BarChart dateChart;
    private int currentIndex;
    private int todayNumber;
    private List<Integer> valueColors;
    private BarData data;
    private ArrayList<BarEntry> values;
    private int pColor, nColor, zeroColor;

    //Circular Overview Bar
    private CircularProgressBar overallBar;
    private CircularProgressBar positiveBar;
    private CircularProgressBar negativeBar;
    private TickerView overallPercent;
    private TickerView negativePercent;
    private TickerView positivePercent;

    //Today's Progress
    private RecyclerView todayProgressRecycler;
    private com.chandrachud.bubble.Adapters.todayProgressAdapter todayProgressAdapter;
    private LinearLayoutManager HorizontalLayout;
    private List<todayProgressItem> todayProgressItems;

    //Custom Goals
    private RecyclerView customGoalsRecycler;
    private com.chandrachud.bubble.Adapters.customGoalListAdapter customGoalListAdapter;
    private LinearLayoutManager VerticalCustomGoalLayout;
    private List<customGoalListItem> customGoalListItems;

    //Bubble Wallet
    private RecyclerView bubbleWalletRecycler;
    private com.chandrachud.bubble.Adapters.goalBubbleWalletAdapter goalBubbleWalletAdapter;
    private LinearLayoutManager VerticalBubbleWalletLayout;
    private List<goalBubbleWalletItem> goalBubbleWalletItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_center);

        Constants.setupBottomNav(savedInstanceState, GoalCenter.this);
        findViewsById(savedInstanceState);
        setOnClickListeners();
        setupBarChart();
        setUpOverviewLayout();
        setUpTodayProgressRecycler();
        setUpCustomGoalsRecycler();
        setUpBubbleWalletRecycler();
    }

    private void findViewsById(Bundle savedInstanceState){
        //Heading
        menuButton = findViewById(R.id.menuButton);
        goalsAddButton = findViewById(R.id.addGoalButton);
        //Chart
        dateChart = findViewById(R.id.goalsDateChart);
        //Circular Overview Bar
        overallBar = findViewById(R.id.goalCenterOverallBar);
        negativeBar = findViewById(R.id.goalCenterNegativeBar);
        positiveBar = findViewById(R.id.goalCenterPositiveBar);
        overallPercent = findViewById(R.id.overallOverviewPercent);
        negativePercent = findViewById(R.id.negativeOverviewPercent);
        positivePercent = findViewById(R.id.positiveOverviewPercent);
        //Today's Progress
        todayProgressRecycler = findViewById(R.id.todayProgressRecycler);
        //Custom Goals
        customGoalsRecycler = findViewById(R.id.customGoalsRecycler);
        //Bubble Wallet
        bubbleWalletRecycler = findViewById(R.id.goalsBubbleWalletRecycler);

        final Typeface mFont = ResourcesCompat.getFont(GoalCenter.this, R.font.montserrat);
        overallPercent.setTypeface(mFont);
        positivePercent.setTypeface(mFont);
        negativePercent.setTypeface(mFont);

        todayProgressItems = new ArrayList<>();
        customGoalListItems = new ArrayList<>();
        goalBubbleWalletItems = new ArrayList<>();

        mSideNavClass = new SideNavClass(GoalCenter.this, savedInstanceState, Constants.goalsActivity);
        mSideNavClass.setUpSideNav();
    }

    private void setOnClickListeners() {

        PushDownAnim.setPushDownAnimTo(goalsAddButton)
                .setScale(PushDownAnim.MODE_SCALE, 0.8f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(GoalCenter.this, AddGoalActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });

    }

    private void setupBarChart(){
        Calendar calendar = Calendar.getInstance();
        todayNumber = calendar.get(Calendar.DAY_OF_WEEK)-1;
        currentIndex = todayNumber;
        pColor = Color.parseColor("#21e8c7");
        nColor = Color.parseColor("#f03145");
        zeroColor = Color.parseColor("#A9A9A9");

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
        xAxis.setTypeface(ResourcesCompat.getFont(this, R.font.montserrat));
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

        valueColors = new ArrayList<>();
        valueColors.add(Color.TRANSPARENT);
        valueColors.add(Color.TRANSPARENT);
        valueColors.add(Color.TRANSPARENT);
        valueColors.add(Color.TRANSPARENT);
        valueColors.add(Color.TRANSPARENT);
        valueColors.add(Color.TRANSPARENT);
        valueColors.add(Color.TRANSPARENT);

        values = new ArrayList<>();
        List<Integer> colors = new ArrayList<>();

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
            set.setValueTypeface(ResourcesCompat.getFont(this, R.font.montserrat));
            data = new BarData(set);
            data.setValueTextSize(13f);
            data.setValueFormatter(new Formatter());
            data.setBarWidth(0.4f);
            if (values.get(currentIndex).getY()>0){
                valueColors.set(currentIndex, pColor);
            }
            else {
                valueColors.set(currentIndex, nColor);
            }
            data.setValueTextColors(valueColors);
            set.setBarShadowColor(Color.parseColor("#1A1F3D"));
            dateChart.setData(data);
        }

        RoundedBarChartRenderer roundedBarChartRenderer= new RoundedBarChartRenderer(dateChart,dateChart.getAnimator(),dateChart.getViewPortHandler());
        roundedBarChartRenderer.setmRadius(15f);
        dateChart.setRenderer(roundedBarChartRenderer);


        dateChart.animateXY(0, 1000);
        dateChart.invalidate();

        dateChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener()
        {
            @Override
            public void onValueSelected(Entry e, Highlight h)
            {
                setupSelectedBar((int) e.getX());
            }

            @Override
            public void onNothingSelected()
            {
                setupSelectedBar(currentIndex);
            }
        });
    }

    private void setupSelectedBar(int index){
        Log.d("TAG", "setupSelectedBar: Index selected: "+index);
        valueColors.set(currentIndex, Color.TRANSPARENT);
        if (values.get(index).getY()>0){
            valueColors.set(index, pColor);
        }
        else if (values.get(index).getY()<0){
            valueColors.set(index, nColor);
        }
        else {
            valueColors.set(index, nColor);
        }
        data.setValueTextColors(valueColors);
        dateChart.getBarData().notifyDataChanged();
        currentIndex = index;
    }

    private void setUpOverviewLayout(){
        int overallProgress = 70;
        int negativeProgress = 80;
        int positiveProgress = 40;
        overallBar.setProgress(overallProgress);
        negativeBar.setProgress(negativeProgress);
        positiveBar.setProgress(positiveProgress);
        overallPercent.setText(overallProgress+"%", true);
        positivePercent.setText(positiveProgress+"%", true);
        negativePercent.setText(negativeProgress+"%", true);
    }

    private void setUpTodayProgressRecycler(){
        HorizontalLayout
                = new LinearLayoutManager(
                GoalCenter.this,
                LinearLayoutManager.HORIZONTAL,
                false);
        todayProgressItems.add(new todayProgressItem(true, "Google\nClassroom", R.drawable.icons8_classroom, 2.5f, 3f, "End of Day", "hr"));
        todayProgressItems.add(new todayProgressItem(false, "Instagram", R.drawable.icons8_instagram_goal, 1, 4f, "Till 7:00PM", "hr"));
        todayProgressItems.add(new todayProgressItem(true, "Gmail", R.drawable.icons8_gmail_type, 45f, 45f, "Till 3:00PM", "min"));
        todayProgressItems.add(new todayProgressItem(true, "Excel", R.drawable.icons8_excel_type, 30f, 45f, "End of Day", "min"));
        todayProgressItems.add(new todayProgressItem(false, "Spotify", R.drawable.icons8_spotify, 2.9f, 3f, "Till 10:00PM", "hr"));
        todayProgressItems.add(new todayProgressItem(false, "Facebook", R.drawable.icons8_facebook, 1.5f, 1.5f, "End of Day", "hr"));
        todayProgressItems.add(new todayProgressItem(true, "Khan\nAcademy", R.drawable.icons8_khan_academy_timeline, 2.7f, 3f, "Till 12:00PM", "hr"));

        todayProgressAdapter = new todayProgressAdapter(todayProgressItems, GoalCenter.this);

        todayProgressRecycler.setLayoutManager(HorizontalLayout);
        todayProgressRecycler.setAdapter(todayProgressAdapter);

    }

    private void setUpCustomGoalsRecycler(){
        VerticalCustomGoalLayout
                = new LinearLayoutManager(
                GoalCenter.this,
                LinearLayoutManager.VERTICAL,
                false);

        customGoalListItems.add(new customGoalListItem(R.drawable.icons8_classroom, true, "Google Classroom", 2f, 4f, "hr"));
        customGoalListItems.add(new customGoalListItem(R.drawable.icons8_instagram_goal, false, "Instagram", 40, 60, "min"));
        customGoalListItems.add(new customGoalListItem(R.drawable.icons8_word, true, "Word", 3.5f, 3.5f, "hr"));
        customGoalListItems.add(new customGoalListItem(R.drawable.icons8_youtube, false, "YouTube", 30, 45, "min"));
        customGoalListItems.add(new customGoalListItem(R.drawable.icons8_khan_academy_timeline, true, "Khan Academy", 1f, 4f, "hr"));

        customGoalListAdapter = new customGoalListAdapter(customGoalListItems, GoalCenter.this);

        customGoalsRecycler.setLayoutManager(VerticalCustomGoalLayout);
        customGoalsRecycler.setAdapter(customGoalListAdapter);
    }

    private void setUpBubbleWalletRecycler(){
        VerticalBubbleWalletLayout
                = new LinearLayoutManager(
                GoalCenter.this,
                LinearLayoutManager.VERTICAL,
                false);

        goalBubbleWalletItems.add(new goalBubbleWalletItem(R.drawable.icons8_spotify_medium, "Spotify", "2hr", false, true, false, 70));
        goalBubbleWalletItems.add(new goalBubbleWalletItem(R.drawable.icons8_khan_academy_timeline, "Khan Academy", "5hr", true, false, false, 2));
        goalBubbleWalletItems.add(new goalBubbleWalletItem(R.drawable.icons8_facebook, "Facebook", "7hr", false, true, true, 100));
        goalBubbleWalletItems.add(new goalBubbleWalletItem(R.drawable.icons8_youtube, "YouTube", "4hr", false, false, false, 15));
        goalBubbleWalletItems.add(new goalBubbleWalletItem(R.drawable.icons8_excel_type, "Excel", "1hr", true, true, false, 100));

        goalBubbleWalletAdapter = new goalBubbleWalletAdapter(goalBubbleWalletItems, GoalCenter.this);

        bubbleWalletRecycler.setLayoutManager(VerticalBubbleWalletLayout);
        bubbleWalletRecycler.setAdapter(goalBubbleWalletAdapter);
    }

    private int randomGenerator(int min, int max){
        int rand = new Random().nextInt(max - min + 1) + min;
        if (rand==0){
            return  randomGenerator(min, max);
        }
        return rand;
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
            mFormat = new DecimalFormat("###### points");
        }

        @Override
        public String getFormattedValue(float value) {

            if (value>0){
                return "+"+mFormat.format(value);
            }
            return mFormat.format(value);


        }
    }
}