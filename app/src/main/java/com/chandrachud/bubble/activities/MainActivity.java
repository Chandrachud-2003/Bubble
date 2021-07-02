package com.chandrachud.bubble.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Bundle;
import android.os.Process;
import android.provider.Settings;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.gelitenight.waveview.library.WaveView;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.chandrachud.bubble.Adapters.appUsageAdapter;
import com.chandrachud.bubble.Adapters.goalMainAdapter;
import com.chandrachud.bubble.HelperClasses.RoundedBarChartRenderer;
import com.chandrachud.bubble.HelperClasses.SideNavClass;
import com.chandrachud.bubble.HelperClasses.WaveHelper;
import com.chandrachud.bubble.Items.appUsageItem;
import com.chandrachud.bubble.Items.goalMainItem;
import com.chandrachud.bubble.R;
import com.chandrachud.bubble.Services.DetectAppService;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private WaveHelper pWaveHelper;
    private WaveHelper nWaveHelper;

    private WaveHelper positiveHelper;
    private WaveHelper negativeHelper;

    private RecyclerView distributionRecycler;

    private WaveView pWave;
    private WaveView nWave;
    private WaveView positiveWave;
    private WaveView negativeWave;

    private int mBorderColor = Color.parseColor("#44FFFFFF");
    private int mBorderWidth = 3;



    private int mBorderColor2=Color.TRANSPARENT;

    private LineChart pLineChart;
    private LineChart nLineChart;

    private LineChart timeLineChart;

    private ArrayList<appUsageItem> mAppUsageItems;

    private RecyclerView.LayoutManager RecyclerViewLayoutManager;

    private LinearLayoutManager HorizontalLayout;

    private appUsageAdapter mAppUsageAdapter;

    private ToggleButton todayButton;
    private ToggleButton yesterdayButton;
    private ToggleButton lastWeekButton;
    private ImageButton moreButton;
    private View todayDivider;
    private View yesterdayDivider;
    private View lastWeekDivider;

    private RelativeLayout todayLayout;
    private RelativeLayout yesterdayLayout;
    private RelativeLayout lastWeekLayout;

    private LinearLayout positiveLinear;
    private LinearLayout negativeLinear;

    private BarChart distributionChart;

    private RecyclerView goalRecycler;
    private ArrayList<goalMainItem> goalMainItemsList;
    private goalMainAdapter mGoalMainAdapter;
    private LinearLayoutManager goalMainLayout;

    private int statsRequestCode = 23123;
    private int start=0;

    private float pProgress;
    private float nProgress;

    private SideNavClass mSideNavClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.main_activity_xml);

        requestPermission();

        findViewFunc();
        setClickListeners();

        Intent intent = getIntent();

        //mSideNavClass = new SideNavClass(MainActivity.this, savedInstanceState, Constants.mainActivity);
        //mSideNavClass.setUpSideNav();

        pLineChart.setViewPortOffsets(0, 0, 0, 0);
        pLineChart.setBackgroundColor(Color.TRANSPARENT);

        pLineChart.getDescription().setEnabled(false);
        pLineChart.setTouchEnabled(false);


        pLineChart.setDragEnabled(true);
        pLineChart.setScaleEnabled(true);
        pLineChart.setPinchZoom(false);
        pLineChart.setDrawGridBackground(false);
        pLineChart.setMaxHighlightDistance(300);

        XAxis x = pLineChart.getXAxis();
        x.setEnabled(false);

        YAxis y = pLineChart.getAxisLeft();
        y.setEnabled(false);

        pLineChart.getAxisRight().setEnabled(false);

        pLineChart.getLegend().setEnabled(false);

        setPData(5, 100);

        pLineChart.animateXY(500, 1500);


        nLineChart.setViewPortOffsets(0, 0, 0, 0);
        nLineChart.setBackgroundColor(Color.TRANSPARENT);


        nLineChart.getDescription().setEnabled(false);
        nLineChart.setTouchEnabled(false);



        nLineChart.setDragEnabled(true);
        nLineChart.setScaleEnabled(true);
        nLineChart.setPinchZoom(false);
        nLineChart.setDrawGridBackground(false);
        nLineChart.setMaxHighlightDistance(300);

        XAxis x2 = nLineChart.getXAxis();
        x2.setEnabled(false);

        YAxis y2 = nLineChart.getAxisLeft();
        y2.setEnabled(false);

        nLineChart.getAxisRight().setEnabled(false);

        nLineChart.getLegend().setEnabled(false);

        setNData(5, 100);

        nLineChart.animateXY(500, 1500);

        /*timeLineChart.setViewPortOffsets(0, 0, 0, 0);
        //timeLineChart.setBackgroundColor(Color.parseColor("#00FFFFFF"));

        timeLineChart.getDescription().setEnabled(false);
        timeLineChart.setTouchEnabled(false);


        timeLineChart.setDragEnabled(true);
        timeLineChart.setScaleEnabled(true);
        timeLineChart.setPinchZoom(false);
        timeLineChart.setDrawGridBackground(false);
        //timeLineChart.setMaxHighlightDistance(300);

        XAxis x3 = timeLineChart.getXAxis();
        x3.setEnabled(false);

        YAxis y3 = timeLineChart.getAxisLeft();
        y3.setEnabled(false);

        timeLineChart.getAxisRight().setEnabled(false);
        timeLineChart.getAxisLeft().setEnabled(false);

        timeLineChart.getAxisRight().setDrawAxisLine(false);
        timeLineChart.getAxisLeft().setDrawAxisLine(false);

        timeLineChart.getLegend().setEnabled(false);

        setTimeLineData(10, 100);

        timeLineChart.animateXY(500, 1500);
        */


        //INVALIDATE
        pLineChart.invalidate();
        nLineChart.invalidate();

        //timeLineChart.invalidate();







        pWave.setShapeType(WaveView.ShapeType.CIRCLE);
        pWave.setBackgroundResource(R.drawable.main_bubble_bg);

        pWave.setWaveColor(Color.parseColor("#2D6A6B"), Color.parseColor("#21e8c7"));

        nWave.setShapeType(WaveView.ShapeType.CIRCLE);
        nWave.setWaveColor(Color.parseColor("#953F53"), Color.parseColor("#f03145"));

        mBorderColor = Color.parseColor("#FFFFFF");




        pProgress = 0.5f;
        nProgress = 0.25f;
        pWaveHelper = new WaveHelper(pWave, pProgress, 3000, 5000);
        nWaveHelper = new WaveHelper(nWave, nProgress, 3000, 5000);


        pWaveHelper.start();
        nWaveHelper.start();

        setEnergyBubble();










        setUpDistributionRecycler();

        setupDistributionChart();

        setUpGoalRecycler();

        //startService();









    }

    public void startService()
    {
        Intent serviceIntent = new Intent(this, DetectAppService.class);
        startService(serviceIntent);

    }

    public void stopService()
    {
        Intent serviceIntent = new Intent(this, DetectAppService.class);
        stopService(serviceIntent);

    }

    private void requestPermission()
    {
        AppOpsManager appOps = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);

        int mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                Process.myUid(), getPackageName());

        if (mode!=AppOpsManager.MODE_ALLOWED)
        {
            startActivityForResult(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS), statsRequestCode);

        }

        else
        {
           startService();

        }

    }

    private void setTimeLineData(int count, float range) {

        ArrayList<Entry> values = new ArrayList<>();



        for (int i = 0; i < count; i++) {
            float val = (float) (Math.random() * (range + 1)) + 20;
            values.add(new Entry(i, val));
        }




        LineDataSet set1;


        if (timeLineChart.getData() != null &&
                timeLineChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) timeLineChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            timeLineChart.getData().notifyDataChanged();
            timeLineChart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values, "DataSet 1");

            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            set1.setCubicIntensity(0.2f);
            set1.setDrawFilled(false);
            set1.setDrawCircles(false);
            set1.setLineWidth(3f);
            set1.setCircleRadius(4f);
            set1.setCircleColor(Color.WHITE);
            set1.setHighLightColor(Color.rgb(244, 117, 117));
            set1.setDrawHorizontalHighlightIndicator(false);
            set1.setFillAlpha(255);
            set1.setDrawHorizontalHighlightIndicator(false);
            set1.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return timeLineChart.getAxisLeft().getAxisMinimum();
                }
            });

            timeLineChart.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    Paint paint = timeLineChart.getRenderer().getPaintRender();
                    int height = timeLineChart.getHeight();

                    LinearGradient linGrad = new LinearGradient(0, 0, 0, height,
                            getResources().getColor(R.color.timeLineChartHigh),
                            getResources().getColor(R.color.timeLineChartLow),
                            Shader.TileMode.REPEAT);
                    paint.setShader(linGrad);
                }
            });






            // create a data object with the data sets
            LineData data = new LineData();
            data.addDataSet(set1);

            data.setValueTextSize(9f);
            data.setDrawValues(false);

            // set data
            timeLineChart.setData(data);
        }
    }

    private void setPData(int count, float range) {

        ArrayList<Entry> values = new ArrayList<>();
        ArrayList<Entry> values2 = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            float val = (float) (Math.random() * (range + 1)) + 20;
            values.add(new Entry(i, val));
        }

        for (int i = 0; i < count; i++) {
            float val = (float) (Math.random() * (range + 1)) + 20;
            values2.add(new Entry(i, val));
        }

        LineDataSet set1;
        LineDataSet set2;

        if (pLineChart.getData() != null &&
                pLineChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) pLineChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            pLineChart.getData().notifyDataChanged();
            pLineChart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values, "DataSet 1");

            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            set1.setCubicIntensity(0.2f);
            set1.setDrawFilled(true);
            set1.setDrawCircles(false);
            set1.setLineWidth(0.5f);
            set1.setCircleRadius(4f);
            set1.setCircleColor(Color.WHITE);
            set1.setHighLightColor(Color.rgb(244, 117, 117));
            set1.setColor(Color.parseColor("#21e8c7"));
            set1.setFillColor(Color.parseColor("#21e8c7"));
            set1.setFillAlpha(255);
            set1.setDrawHorizontalHighlightIndicator(false);
            set1.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return pLineChart.getAxisLeft().getAxisMinimum();
                }
            });

            set2 = new LineDataSet(values2, "DataSet 1");

            set2.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            set2.setCubicIntensity(0.2f);
            set2.setDrawFilled(true);
            set2.setDrawCircles(false);
            set2.setLineWidth(0f);
            set2.setCircleRadius(4f);
            set2.setCircleColor(Color.WHITE);
            set2.setHighLightColor(Color.rgb(244, 117, 117));
            set2.setColor(Color.parseColor("#2D6A6B"));
            set2.setFillColor(Color.parseColor("#2D6A6B"));
            set2.setFillAlpha(255);
            set2.setDrawHorizontalHighlightIndicator(false);
            set2.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return pLineChart.getAxisLeft().getAxisMinimum();
                }
            });

            // create a data object with the data sets
            LineData data = new LineData();
            data.addDataSet(set2);
            data.addDataSet(set1);
            data.setValueTextSize(9f);
            data.setDrawValues(false);

            // set data
            pLineChart.setData(data);
        }
    }

    private void setNData(int count, float range) {

        ArrayList<Entry> values = new ArrayList<>();
        ArrayList<Entry> values2 = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            float val = (float) (Math.random() * (range + 1)) + 20;
            values.add(new Entry(i, val));
        }

        for (int i = 0; i < count; i++) {
            float val = (float) (Math.random() * (range + 1)) + 20;
            values2.add(new Entry(i, val));
        }

        LineDataSet set1;
        LineDataSet set2;

        if (nLineChart.getData() != null &&
                nLineChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) nLineChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            nLineChart.getData().notifyDataChanged();
            nLineChart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values, "DataSet 1");

            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            set1.setCubicIntensity(0.2f);
            set1.setDrawFilled(true);
            set1.setDrawCircles(false);
            set1.setLineWidth(1.8f);
            set1.setCircleRadius(4f);
            set1.setCircleColor(Color.WHITE);
            set1.setHighLightColor(Color.rgb(244, 117, 117));
            set1.setColor(Color.parseColor("#f03145"));
            set1.setFillColor(Color.parseColor("#f03145"));
            set1.setFillAlpha(255);
            set1.setDrawHorizontalHighlightIndicator(false);
            set1.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return nLineChart.getAxisLeft().getAxisMinimum();
                }
            });

            set2 = new LineDataSet(values2, "DataSet 1");

            set2.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            set2.setCubicIntensity(0.2f);
            set2.setDrawFilled(true);
            set2.setDrawCircles(false);
            set2.setLineWidth(0f);
            set2.setCircleRadius(4f);
            set2.setCircleColor(Color.WHITE);
            set2.setHighLightColor(Color.rgb(244, 117, 117));
            set2.setColor(Color.parseColor("#953F53"));
            set2.setFillColor(Color.parseColor("#953F53"));
            set2.setFillAlpha(255);
            set2.setDrawHorizontalHighlightIndicator(false);
            set2.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return nLineChart.getAxisLeft().getAxisMinimum();
                }
            });



            // create a data object with the data sets
            LineData data = new LineData();
            data.addDataSet(set2);
            data.addDataSet(set1);
            data.setValueTextSize(9f);
            data.setDrawValues(false);

            // set data
            nLineChart.setData(data);
        }
    }



    private void setUpDistributionRecycler()
    {
        mAppUsageItems = new ArrayList<>();

        mAppUsageItems.add(new appUsageItem("WhatsApp", "2 hrs 15 min", "+ 10%", "Since yesterday", R.drawable.icons8_whatsapp, true));
        mAppUsageItems.add(new appUsageItem("Facebook", "1 hrs 30 min", "- 20%", "Since yesterday", R.drawable.icons8_facebook, false));
        mAppUsageItems.add(new appUsageItem("Word", "3hrs", "+ 30%", "Since yesterday", R.drawable.icons8_word, true));
        mAppUsageItems.add(new appUsageItem("Spotify", "45 min", "+ 20%", "Since yesterday", R.drawable.icons8_spotify, true));
        mAppUsageItems.add(new appUsageItem("Youtube", "4 hrs 15 min", "- 10%", "Since yesterday", R.drawable.icons8_youtube, false));

        HorizontalLayout
                = new LinearLayoutManager(
                MainActivity.this,
                LinearLayoutManager.HORIZONTAL,
                false);

        RecyclerViewLayoutManager
                = new LinearLayoutManager(
                getApplicationContext());

        mAppUsageAdapter = new appUsageAdapter(mAppUsageItems);

        distributionRecycler.setLayoutManager(HorizontalLayout);
        distributionRecycler.setAdapter(mAppUsageAdapter);

    }

    private void findViewFunc()
    {
        pWave = (WaveView) findViewById(R.id.wave);
        nWave = (WaveView) findViewById(R.id.wave2);
        positiveWave = findViewById(R.id.positiveEnergyWave);
        negativeWave = findViewById(R.id.negativeEnergyWave);

        positiveLinear = findViewById(R.id.positiveLinearHeading);
        negativeLinear = findViewById(R.id.negativeLinearHeading);


        distributionRecycler = findViewById(R.id.appDistributionRecycler);

        pLineChart = findViewById(R.id.pCubicChart);
        nLineChart = findViewById(R.id.nCubicChart);
        //timeLineChart = findViewById(R.id.timeLineChart);
        distributionChart = findViewById(R.id.distributionChart);

        todayButton = findViewById(R.id.todayButton);
        yesterdayButton = findViewById(R.id.yesterdayButton);
        lastWeekButton = findViewById(R.id.lastWeekButton);
        moreButton = findViewById(R.id.moreRecentButton);

        todayLayout = findViewById(R.id.todayLayout);
        yesterdayLayout = findViewById(R.id.yesterdayLayout);
        lastWeekLayout = findViewById(R.id.lastWeekLayout);

        todayButton.setEnabled(false);
        yesterdayButton.setEnabled(true);
        lastWeekButton.setEnabled(true);
        moreButton.setEnabled(true);

        todayDivider = findViewById(R.id.todayDivider);
        yesterdayDivider = findViewById(R.id.yesterdayDivider);
        lastWeekDivider = findViewById(R.id.lastWeekDivider);

        todayDivider.setBackgroundResource(R.color.toggleColor);


        yesterdayDivider.setBackgroundResource(R.color.transparent);
        lastWeekDivider.setBackgroundResource(R.color.transparent);

        goalRecycler = findViewById(R.id.goalMainRecycler);














    }



    private void setClickListeners()
    {


        PushDownAnim.setPushDownAnimTo( todayButton )
        .setOnClickListener( new View.OnClickListener(){
        @Override
        public void onClick( View view ){


                setChecked(todayButton, true, todayDivider);
                setChecked(yesterdayButton, false, yesterdayDivider);
                setChecked(lastWeekButton, false, lastWeekDivider);



        }

        } );

        PushDownAnim.setPushDownAnimTo( yesterdayButton )

                .setOnClickListener( new View.OnClickListener(){
                    @Override
                    public void onClick( View view ){


                            setChecked(yesterdayButton, true, yesterdayDivider);
                            setChecked(todayButton, false, todayDivider);
                            setChecked(lastWeekButton, false, lastWeekDivider);



                    }

                } );

        PushDownAnim.setPushDownAnimTo( lastWeekButton )
                .setOnClickListener( new View.OnClickListener(){
                    @Override
                    public void onClick( View view ){


                            setChecked(lastWeekButton, true, lastWeekDivider);
                            setChecked(yesterdayButton, false, yesterdayDivider);
                            setChecked(todayButton, false, todayDivider);



                    }

                } );

        PushDownAnim.setPushDownAnimTo(moreButton)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                    }
                });



    }

    private void setChecked(ToggleButton toggleButton, boolean value, View divider)
    {
        if (value)
        {
            toggleButton.setTextSize(20f);
            toggleButton.setTextColor(Color.parseColor("#21e8c7"));
            divider.setVisibility(View.VISIBLE);
            divider.setBackgroundResource(R.color.toggleColor);
            toggleButton.setEnabled(false);
            toggleButton.setChecked(true);


        }

        else
        {
            toggleButton.setTextSize(14f);
            toggleButton.setTextColor(Color.parseColor("#D3D3D3"));
            divider.setVisibility(View.GONE);
            divider.setBackgroundResource(R.color.transparent);
            toggleButton.setEnabled(true);
            toggleButton.setChecked(false);


        }

    }

    private void clickedToast()
    {
        Toast.makeText(MainActivity.this, "Clicked", Toast.LENGTH_SHORT).show();

    }

    private void setEnergyBubble()
    {





        positiveLinear.post(new Runnable(){
            public void run(){
                int height = positiveLinear.getHeight();
                int width = positiveLinear.getWidth();

                positiveWave.setLayoutParams(new RelativeLayout.LayoutParams(width, height));
                positiveWave.setShapeType(WaveView.ShapeType.SQUARE);

                positiveWave.setBorder(0, Color.parseColor("#00FFFFFF"));
                positiveWave.setWaveColor(Color.parseColor("#26978C"), Color.parseColor("#21e8c7"));

                positiveHelper = new WaveHelper(positiveWave, 0.5f, 3000, 5000);

                positiveHelper.start();
            }
        });

        negativeLinear.post(new Runnable(){
            public void run(){
                int height = negativeLinear.getHeight();
                int width = negativeLinear.getWidth();

                negativeWave.setLayoutParams(new RelativeLayout.LayoutParams(width, height));
                negativeWave.setShapeType(WaveView.ShapeType.SQUARE);
                negativeWave.setBorder(0, Color.parseColor("#00FFFFFF"));
                negativeWave.setWaveColor(Color.parseColor("#953F53"), Color.parseColor("#f03145"));


                negativeHelper = new WaveHelper(negativeWave, 0.4f, 3000, 5000);

                negativeHelper.start();
            }
        });





    }

    private void setupDistributionChart()
    {

        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0f, 30f));
        entries.add(new BarEntry(1f, 80f));
        entries.add(new BarEntry(2f, 60f));
        entries.add(new BarEntry(3f, 50f));
        entries.add(new BarEntry(4f, 70f));
        entries.add(new BarEntry(5f, 60f));

        int startColor1 = ContextCompat.getColor(this, R.color.barChartStartColor);

        int endColor1 = ContextCompat.getColor(this, R.color.barChartEndColor);

        List<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#01B075"));
        colors.add(Color.parseColor("#01B075"));
        colors.add(Color.parseColor("#01B075"));
        colors.add(Color.parseColor("#01B075"));
        colors.add(Color.parseColor("#01B075"));
        colors.add(Color.parseColor("#01B075"));


        BarDataSet barDataSet = new BarDataSet(entries, "");
        barDataSet.setColors(colors);
        barDataSet.setBarBorderWidth(0.9f);

        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(0.4f);


        XAxis xAxis = distributionChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        final String[] xValues = new String[]{"WhatsApp", "Facebook", "Word", "Khan Academy", "Powerpoint", "Total"};
        IndexAxisValueFormatter formatter = new IndexAxisValueFormatter(xValues);
        xAxis.setLabelRotationAngle(-45f);
        xAxis.setGranularity(1f);

        xAxis.setValueFormatter(formatter);

        distributionChart.setData(barData);
        distributionChart.setFitBars(true);

        distributionChart.getLegend().setEnabled(false);
        distributionChart.setDrawGridBackground(false);
        distributionChart.getXAxis().setDrawGridLines(false);
        distributionChart.getAxisRight().setDrawGridLines(false);
        distributionChart.getAxisLeft().setDrawGridLines(false);

        distributionChart.getDescription().setEnabled(false);
        distributionChart.getXAxis().setAxisLineColor(Color.TRANSPARENT);
        distributionChart.getAxisRight().setAxisLineColor(Color.TRANSPARENT);
        distributionChart.getAxisRight().setEnabled(false);
        distributionChart.getAxisLeft().setAxisLineColor(Color.TRANSPARENT);

        RoundedBarChartRenderer roundedBarChartRenderer= new RoundedBarChartRenderer(distributionChart,distributionChart.getAnimator(),distributionChart.getViewPortHandler());
        roundedBarChartRenderer.setmRadius(15f);
        distributionChart.setRenderer(roundedBarChartRenderer);


        distributionChart.animateXY(0, 3000);
        distributionChart.invalidate();



    }

    private void setUpGoalRecycler()
    {
        goalMainItemsList = new ArrayList<>();
        goalMainItemsList.add(new goalMainItem(true, "Google Classroom", R.drawable.icons8_classroom_goal, "Till 2:00pm", "1hr",  "/ 2.5hr", 30));
        goalMainItemsList.add(new goalMainItem(false, "WhatsApp", R.drawable.icons8_whatsapp_goal, "All Day", "3.3hr",  "/ 4hr", 80));
        goalMainItemsList.add(new goalMainItem(false, "Twitter", R.drawable.icons8_twitter_goal, "Till 4:00pm", "1.5hr",  "/ 2.5hr", 50));
        goalMainItemsList.add(new goalMainItem(true, "Amazon Kindle", R.drawable.icons8_kindle_goal, "All Day", "3.2hr",  "/ 5hr", 70));
        goalMainItemsList.add(new goalMainItem(true, "Studying App", R.drawable.icons8_studying_goal, "Till 12:00pm", "3.5hr",  "/ 4hr", 90));
        goalMainItemsList.add(new goalMainItem(false, "Super Mario", R.drawable.icons8_mario_goal, "All Day", "1.5hr",  "/ 2.5hr", 60));

        goalMainLayout = new LinearLayoutManager(
            MainActivity.this,
            LinearLayoutManager.HORIZONTAL,
            false);

        mGoalMainAdapter = new goalMainAdapter(goalMainItemsList, this);

        goalRecycler.setLayoutManager(goalMainLayout);
        goalRecycler.setAdapter(mGoalMainAdapter);







    }






}
