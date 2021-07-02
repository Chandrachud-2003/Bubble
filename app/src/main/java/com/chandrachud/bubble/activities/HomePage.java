package com.chandrachud.bubble.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.gelitenight.waveview.library.WaveView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.chandrachud.bubble.Adapters.customTaskViewpagerAdapter;
import com.chandrachud.bubble.Adapters.homeDateAdapter;
import com.chandrachud.bubble.Constants;
import com.chandrachud.bubble.HelperClasses.SideNavClass;
import com.chandrachud.bubble.HelperClasses.WaveHelper;
import com.chandrachud.bubble.Items.customTaskItem;
import com.chandrachud.bubble.Items.goalPreviewItem;
import com.chandrachud.bubble.Items.homeDateItem;
import com.chandrachud.bubble.Items.yourBubblePreviewItem;
import com.chandrachud.bubble.R;
import com.shunan.circularprogressbar.CircularProgressBar;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.armcha.elasticview.ElasticView;
import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

public class HomePage extends AppCompatActivity {

    private String testAutomation = "I am testing the github automation";

    // Top Bar
    private ImageButton menuButton;
    private ImageButton statisticsButton;
    private SideNavClass mSideNavClass;
    private WaveSwipeRefreshLayout waveSwipeRefreshLayout;

    //Date Recycler
    private RecyclerView dateRecycler;
    private LinearLayoutManager HorizontalLayout;
    private homeDateAdapter mHomeDateAdapter;
    private List<homeDateItem> mHomeDateItems;

    private ElasticView yourBubbleCard;
    private TextView nameView;
    //Positive and Negative Bubble
    private WaveView pBubble;
    private WaveHelper pHelper;
    private WaveView nBubble;
    private WaveHelper nHelper;
    private float pProgress;
    private float nProgress;
    //Positive and Negative Bubble Preview
    private ConstraintLayout positivePreviewLayout;
    private ConstraintLayout negativePreviewLayout;
    private ImageView positivePreviewImage;
    private ImageView negativePreviewImage;
    private TextView positivePreviewName;
    private TextView negativePreviewName;
    private TextView positivePreviewTime;
    private TextView negativePreviewTime;
    private TextView positivePreviewRank;
    private TextView negativePreviewRank;
    private WaveView positivePreviewBubble;
    private WaveView negativePreviewBubble;
    private WaveHelper positivePreviewHelper;
    private WaveHelper negativePreviewHelper;
    private List<yourBubblePreviewItem> mYourBubblePositivePreviewItems;
    private List<yourBubblePreviewItem> mYourBubbleNegativePreviewItems;
    private int prevPosIndex = 0;
    private int prevNegIndex = 0;
    private int posIndex;
    private int negIndex;
    //Cubic Graphs
    private TextView cubicNPercent;
    private TextView cubicPPercent;
    private TextView cubicNRef;
    private TextView cubicPRef;
    private LineChart cubicNChart;
    private LineChart cubicPChart;

    //Goal Center preview
    private TextView goalCount;
    private TextView goodJobText;
    private TextView goalDescription;
    private TextView allGoalsButton;
    private CircularProgressBar goalsPreviewBar;
    private ImageView goalsPreviewImage;
    private TextView goalsPreviewName;
    private TextView goalsPreviewTime;
    private TextView goalsPreviewType;
    private TextView goalsPreviewPercent;
    private TextView goalsPreviewPercentDescription;
    private ConstraintLayout goalPreviewLayout;
    private ConstraintLayout goalPercentLayout;
    private List<goalPreviewItem> goalPreviewItems;
    private int prevGoalIndex;
    private int goalIndex;

    //Handpicked Tasks preview
    private ViewPager2 customTaskPager;
    private com.chandrachud.bubble.Adapters.customTaskViewpagerAdapter customTaskViewpagerAdapter;
    private ArrayList<customTaskItem> customTaskItems;
    private TabLayout customTaskTabLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        findViewsById(savedInstanceState);
        setOnClickListeners();
        setupSwipeToRefreshLayout();
        setupDateRecycler();
        setupYourBubbleCard();
        setupLineCharts();
        setupGoalsCard();
        setupCustomTasksCard();
    }

    private void findViewsById(Bundle savedInstanceState){
        menuButton = findViewById(R.id.menuButton);
        statisticsButton = findViewById(R.id.statisticsButton);
        dateRecycler = findViewById(R.id.dateRecycler);
        nameView = findViewById(R.id.mainNameView);
        //yourBubbleCard = findViewById(R.id.yourBubbleElasticCard);
        pBubble = findViewById(R.id.positiveBubble);
        nBubble = findViewById(R.id.negativeBubble);
        positivePreviewLayout = findViewById(R.id.positiveBubblePreviewLayout);
        negativePreviewLayout = findViewById(R.id.negativeBubblePreviewLayout);
        positivePreviewBubble = findViewById(R.id.positiveBubblePreviewBubble);
        negativePreviewBubble = findViewById(R.id.negativeBubblePreviewBubble);
        positivePreviewName = findViewById(R.id.positiveBubblePreviewName);
        negativePreviewName = findViewById(R.id.negativeBubblePreviewName);
        positivePreviewTime = findViewById(R.id.positiveBubblePreviewTime);
        negativePreviewTime = findViewById(R.id.negativeBubblePreviewTime);
        positivePreviewRank = findViewById(R.id.positiveBubblePreviewRanking);
        negativePreviewRank = findViewById(R.id.negativeBubblePreviewRanking);
        positivePreviewImage = findViewById(R.id.positiveBubblePreviewImage);
        negativePreviewImage = findViewById(R.id.negativeBubblePreviewImage);
        cubicNPercent = findViewById(R.id.cubicNPercent);
        cubicPPercent = findViewById(R.id.cubicPPercent);
        cubicNRef = findViewById(R.id.cubicNRef);
        cubicPRef = findViewById(R.id.cubicPRef);
        cubicNChart = findViewById(R.id.nCubicChart);
        cubicPChart = findViewById(R.id.pCubicChart);
        goalCount = findViewById(R.id.goalcenterCountText);
        goodJobText = findViewById(R.id.goodjobText);
        goalDescription = findViewById(R.id.goalcenterCountDescription);
        allGoalsButton = findViewById(R.id.allGoalsButton);
        goalsPreviewBar = findViewById(R.id.goalPreviewSeekBar);
        goalsPreviewImage = findViewById(R.id.goalPreviewIcon);
        goalsPreviewName = findViewById(R.id.goalPreviewName);
        goalsPreviewTime = findViewById(R.id.goalPreviewTime);
        goalsPreviewType = findViewById(R.id.goalPreviewType);
        goalsPreviewPercent = findViewById(R.id.goalPreviewPercent);
        goalsPreviewPercentDescription = findViewById(R.id.goalPreviewPercentText);
        goalPreviewLayout = findViewById(R.id.rightGoalCenterLayout);
        goalPercentLayout = findViewById(R.id.goalPreviewPercentLayout);
        customTaskPager = findViewById(R.id.customTaskViewpager);
        customTaskTabLayout = findViewById(R.id.customTaskViewpagerTabLayout);
        waveSwipeRefreshLayout = findViewById(R.id.main_swipe);

        mSideNavClass = new SideNavClass(HomePage.this, savedInstanceState, Constants.homePageActivity);
        mSideNavClass.setUpSideNav();

        /*positivePreviewBubble.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                positivePreviewBubble.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int w = positivePreviewBubble.getWidth();
                int h = positivePreviewBubble.getHeight();

                positivePreviewBubble.setRotation(90.0f);


                ViewGroup.LayoutParams lp = positivePreviewBubble.getLayoutParams();
                lp.height = h;
                lp.width = h;
                positivePreviewBubble.requestLayout();
            }
        });*/



    }

    private void setOnClickListeners(){

        PushDownAnim.setPushDownAnimTo(allGoalsButton)
                .setScale(PushDownAnim.MODE_SCALE, 0.8f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(HomePage.this, GoalCenter.class);
                        startActivity(intent);

                    }
                });
    }

    private void setupSwipeToRefreshLayout(){
        waveSwipeRefreshLayout.setWaveColor(Color.parseColor("#121c54"));
        waveSwipeRefreshLayout.setColorSchemeColors(Color.parseColor("#FFFFFF"));

        waveSwipeRefreshLayout.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        waveSwipeRefreshLayout.setRefreshing(false);

                    }
                }, 2500); //Timer is in ms here.

            }
        });
    }

    private void setupDateRecycler(){
        mHomeDateItems = new ArrayList<>();
        mHomeDateItems.add(new homeDateItem("16", "Fri", true));
        mHomeDateItems.add(new homeDateItem("15", "Thur", false));
        mHomeDateItems.add(new homeDateItem("14", "Wed", false));
        mHomeDateItems.add(new homeDateItem("13", "Tue", false));
        mHomeDateItems.add(new homeDateItem("12", "Mon", false));
        mHomeDateItems.add(new homeDateItem("11", "Sun", false));
        mHomeDateItems.add(new homeDateItem("10", "Sat", false));

        HorizontalLayout
                = new LinearLayoutManager(
                HomePage.this,
                LinearLayoutManager.HORIZONTAL,
                false);

        mHomeDateAdapter = new homeDateAdapter(mHomeDateItems);
        dateRecycler.setLayoutManager(HorizontalLayout);
        dateRecycler.setAdapter(mHomeDateAdapter);
    }

    private void setupYourBubbleCard(){

        //Name Text
        nameView.setText("Chandrachud "+getEmojiByUnicode(0x1F44B));

        //Elastic Card

        //Positive and Negative Bubble
        pBubble.setShapeType(WaveView.ShapeType.CIRCLE);
        pBubble.setBackgroundResource(R.drawable.main_bubble_bg);
        pBubble.setWaveColor(Color.parseColor("#2D6A6B"), Color.parseColor("#21e8c7"));
        nBubble.setShapeType(WaveView.ShapeType.CIRCLE);
        nBubble.setWaveColor(Color.parseColor("#953F53"), Color.parseColor("#f03145"));
        pProgress = 0.5f;
        nProgress = 0.25f;
        pHelper = new WaveHelper(pBubble, pProgress, 3000, 5000);
        nHelper = new WaveHelper(nBubble, nProgress, 3000, 5000);
        pHelper.start();
        nHelper.start();

        //Bubble Emitter
        emitBubbles();

        //Positive and Negative preview
        positivePreviewBubble.setShapeType(WaveView.ShapeType.SQUARE);
        positivePreviewBubble.setWaveColor(Color.parseColor("#2D6A6B"), Color.parseColor("#21e8c7"));
        negativePreviewBubble.setShapeType(WaveView.ShapeType.SQUARE);
        negativePreviewBubble.setWaveColor(Color.parseColor("#953F53"), Color.parseColor("#f03145"));

        mYourBubblePositivePreviewItems = new ArrayList<>();
        mYourBubblePositivePreviewItems.add(new yourBubblePreviewItem("Google Classroom", "5hr 15min", 0.40f, R.drawable.icons8_google_classroom_timeline));
        mYourBubblePositivePreviewItems.add(new yourBubblePreviewItem("Khan Academy", "4hr 30min", 0.20f, R.drawable.icons8_khan_academy_timeline));
        mYourBubblePositivePreviewItems.add(new yourBubblePreviewItem("Kindle", "3hr 45min", 0.20f, R.drawable.icons8_kindle_timeline));
        mYourBubblePositivePreviewItems.add(new yourBubblePreviewItem("Forest", "2hr", 0.10f, R.drawable.icons8_forest_timeline));
        mYourBubblePositivePreviewItems.add(new yourBubblePreviewItem("Microsoft Word", "1hr 30min", 0.10f, R.drawable.icons8_word_timeline));


        mYourBubbleNegativePreviewItems = new ArrayList<>();
        mYourBubbleNegativePreviewItems.add(new yourBubblePreviewItem("Instagram", "5hr 15min", 0.40f, R.drawable.icons8_instagram_type));
        mYourBubbleNegativePreviewItems.add(new yourBubblePreviewItem("Netflix", "4hr 30min", 0.20f, R.drawable.icons8_netflix_timeline));
        mYourBubbleNegativePreviewItems.add(new yourBubblePreviewItem("WhatsApp", "3hr 45min", 0.20f, R.drawable.icons8_whatsapp_timeline));
        mYourBubbleNegativePreviewItems.add(new yourBubblePreviewItem("Facebook", "2hr", 0.10f, R.drawable.icons8_facebook_timeline));
        mYourBubbleNegativePreviewItems.add(new yourBubblePreviewItem("Call of Duty - Mobile", "1hr 30min", 0.10f, R.drawable.cod_timeline));

        posIndex = 0;
        negIndex = 0;
        prevPosIndex = 0;
        prevNegIndex = 0;
        final int min = 0;
        final int max = mYourBubblePositivePreviewItems.size()-1;
        playAnimationOnPreview(mYourBubblePositivePreviewItems.get(0), true, true, posIndex+1);
        playAnimationOnPreview(mYourBubbleNegativePreviewItems.get(0), false, true, negIndex+1);

        Handler handler = new Handler();
        Runnable runnableCode = new Runnable() {
            @Override
            public void run() {
                posIndex = new Random().nextInt((max - min) + 1) + min;
                while (posIndex==prevPosIndex){
                    posIndex = new Random().nextInt((max - min) + 1) + min;
                }
                negIndex = new Random().nextInt((max - min) + 1) + min;
                while (negIndex==prevNegIndex){
                    negIndex = new Random().nextInt((max - min) + 1) + min;
                }
                //playAnimationOnPreview(mYourBubblePositivePreviewItems.get(posIndex), true, false, posIndex+1);
                //playAnimationOnPreview(mYourBubbleNegativePreviewItems.get(negIndex), false, false, negIndex+1);

                playAnimationOnPreview(mYourBubblePositivePreviewItems.get(posIndex), true, true, posIndex+1);
                playAnimationOnPreview(mYourBubbleNegativePreviewItems.get(negIndex), false, true, negIndex+1);
                prevPosIndex = posIndex;
                prevNegIndex = negIndex;

                handler.postDelayed(this, 4000);
            }
        };
        handler.post(runnableCode);
    }

    private void setupLineCharts(){
        //Positive Line Chart
        cubicPChart.setViewPortOffsets(0, 0, 0, 0);
        cubicPChart.setBackgroundColor(Color.TRANSPARENT);
        cubicPChart.getDescription().setEnabled(false);
        cubicPChart.setTouchEnabled(false);
        cubicPChart.setDragEnabled(true);
        cubicPChart.setScaleEnabled(true);
        cubicPChart.setPinchZoom(false);
        cubicPChart.setDrawGridBackground(false);
        cubicPChart.setMaxHighlightDistance(300);
        XAxis x = cubicPChart.getXAxis();
        x.setEnabled(false);
        YAxis y = cubicPChart.getAxisLeft();
        y.setEnabled(false);
        cubicPChart.getAxisRight().setEnabled(false);
        cubicPChart.getLegend().setEnabled(false);
        setCubicGraphData(5, 100, cubicPChart, true);
        cubicPChart.animateXY(500, 1500);

        //Negative Line Chart
        cubicNChart.setViewPortOffsets(0, 0, 0, 0);
        cubicNChart.setBackgroundColor(Color.TRANSPARENT);
        cubicNChart.getDescription().setEnabled(false);
        cubicNChart.setTouchEnabled(false);
        cubicNChart.setDragEnabled(true);
        cubicNChart.setScaleEnabled(true);
        cubicNChart.setPinchZoom(false);
        cubicNChart.setDrawGridBackground(false);
        cubicNChart.setMaxHighlightDistance(300);
        XAxis x2 = cubicNChart.getXAxis();
        x2.setEnabled(false);
        YAxis y2 = cubicNChart.getAxisLeft();
        y2.setEnabled(false);
        cubicNChart.getAxisRight().setEnabled(false);
        cubicNChart.getLegend().setEnabled(false);
        setCubicGraphData(5, 100, cubicNChart, false);
        cubicNChart.animateXY(500, 1500);

        cubicPChart.invalidate();
        cubicNChart.invalidate();
    }

    private void setCubicGraphData(int count, float range, LineChart v, boolean positive) {
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

        if (v.getData() != null &&
                v.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) v.getData().getDataSetByIndex(0);
            set1.setValues(values);
            v.getData().notifyDataChanged();
            v.notifyDataSetChanged();
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
            if (positive) {
                set1.setHighLightColor(Color.rgb(244, 117, 117));
                set1.setColor(Color.parseColor("#21e8c7"));
                set1.setFillColor(Color.parseColor("#21e8c7"));
            } else {
                set1.setHighLightColor(Color.rgb(244, 117, 117));
                set1.setColor(Color.parseColor("#f03145"));
                set1.setFillColor(Color.parseColor("#f03145"));
            }
            set1.setFillAlpha(255);
            set1.setDrawHorizontalHighlightIndicator(false);
            set1.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return cubicPChart.getAxisLeft().getAxisMinimum();
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
            if (positive) {
                set2.setHighLightColor(Color.rgb(244, 117, 117));
                set2.setColor(Color.parseColor("#2D6A6B"));
                set2.setFillColor(Color.parseColor("#2D6A6B"));
            } else {
                set2.setHighLightColor(Color.rgb(244, 117, 117));
                set2.setColor(Color.parseColor("#953F53"));
                set2.setFillColor(Color.parseColor("#953F53"));
            }
            set2.setFillAlpha(255);
            set2.setDrawHorizontalHighlightIndicator(false);
            set2.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return v.getAxisLeft().getAxisMinimum();
                }
            });

            // create a data object with the data sets
            LineData data = new LineData();
            data.addDataSet(set2);
            data.addDataSet(set1);
            data.setValueTextSize(9f);
            data.setDrawValues(false);

            // set data
            v.setData(data);
        }
    }

    private void setupGoalsCard(){
        goalsPreviewBar.setDashLineLength(5);
        goalsPreviewBar.setDashLineOffset(5);

        goalPreviewItems = new ArrayList<>();
        goalPreviewItems.add(new goalPreviewItem(true, "Kindle", 77, "3hr 10min", 77, R.drawable.icons8_kindle_timeline));
        goalPreviewItems.add(new goalPreviewItem(false, "Instagram", 50, "4hr", 50, R.drawable.insta_timeline));
        goalPreviewItems.add(new goalPreviewItem(true, "Word", 90, "2hr 15min", 90, R.drawable.word_timeline));
        goalPreviewItems.add(new goalPreviewItem(false, "Super Mario", 30, "1hr", 30, R.drawable.mario_timeline));
        goalPreviewItems.add(new goalPreviewItem(true, "Khan Academy", 100, "3hr", 100, R.drawable.icons8_khan_academy_timeline));

        prevGoalIndex = 0;
        final int min = 0;
        final int max = goalPreviewItems.size()-1;
        goalsPreviewBar.setAnimationDuration(1000);
        playAnimationOnGoalPreview(goalPreviewItems.get(0));

        Handler handler = new Handler();
        Runnable runnableCode = new Runnable() {
            @Override
            public void run() {
                goalIndex = new Random().nextInt((max - min) + 1) + min;
                while (goalIndex==prevGoalIndex){
                    goalIndex = new Random().nextInt((max - min) + 1) + min;
                }

                playAnimationOnGoalPreview(goalPreviewItems.get(goalIndex));
                prevGoalIndex = goalIndex;

                handler.postDelayed(this, 4000);
            }
        };
        handler.post(runnableCode);


    }

    private void setupCustomTasksCard(){
        customTaskItems = new ArrayList<>();
        customTaskItem[] taskList = Constants.customTasks;
        for(int i=1;i<=3;i++){
            int rand = randomGenerator(0, taskList.length-1);
            customTaskItem item = taskList[rand];
            if (item!=null){
                int duration = randomGenerator(15, 45);
                if (duration%5!=0){
                    int rem = duration%5;
                    duration-=rem;
                    duration+=5;
                }
                item.setDuration(duration);
                item.setName(item.getName()+duration+"min");
                customTaskItems.add(item);
                taskList[rand] = null;

            }
            else {
                i-=1;
            }
        }

        customTaskViewpagerAdapter = new customTaskViewpagerAdapter(customTaskItems);
        customTaskPager.setOffscreenPageLimit(ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT);
        customTaskPager.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);
        customTaskPager.setAdapter(customTaskViewpagerAdapter);

        new TabLayoutMediator(customTaskTabLayout, customTaskPager,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {

                    }
                }).attach();
    }

    private void emitBubbles() {

    }

    public static void customView(View v, int backgroundColor, int cornerRadii) {
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setCornerRadii(new float[] { cornerRadii, cornerRadii, cornerRadii, cornerRadii, 0, 0, 0, 0 });
        shape.setColor(backgroundColor);
        v.setBackground(shape);
    }

    public String getEmojiByUnicode(int unicode){
        return new String(Character.toChars(unicode));
    }

    private void playAnimation(Techniques techniques, int duration, View v){
        YoYo.with(techniques)
                .duration(duration)
                .playOn(v);
    }

    private void playAnimationOnPreview(yourBubblePreviewItem yourBubblePreviewItem, boolean isPositive, boolean enter, int rank){

        Techniques techniques;
        //if (enter){
            if (isPositive){
                techniques = Techniques.FadeInLeft;
            }
            else{
                techniques = Techniques.FadeInRight;
            }
        //}

        /*else{
            if (isPositive){
                techniques = Techniques.FadeOutRight;
            }
            else{
                techniques = Techniques.FadeOutLeft;
            }
        }*/

        techniques = Techniques.Landing;

        if (isPositive){
            playAnimation(techniques, 1000, positivePreviewImage);
            playAnimation(techniques, 1000, positivePreviewName);
            playAnimation(techniques, 1000, positivePreviewTime);
            playAnimation(techniques, 1000, positivePreviewRank);
            if (enter){
                positivePreviewImage.setImageResource(yourBubblePreviewItem.getResId());
                positivePreviewName.setText(yourBubblePreviewItem.getName());
                positivePreviewTime.setText(yourBubblePreviewItem.getTime());
                positivePreviewRank.setText("#"+rank);
                //positivePreviewHelper = new WaveHelper(positivePreviewBubble, yourBubblePreviewItem.getPercentage(), 3000, 5000);
                //positivePreviewHelper.start();
            }
            /*else {

                positivePreviewHelper = new WaveHelper(positivePreviewBubble, 0, 0, 0);
                positivePreviewHelper.start();
            }*/
        }
        else {
            playAnimation(techniques, 1000, negativePreviewImage);
            playAnimation(techniques, 1000, negativePreviewName);
            playAnimation(techniques, 1000, negativePreviewTime);
            playAnimation(techniques, 1000, negativePreviewRank);

            if (enter){
                negativePreviewImage.setImageResource(yourBubblePreviewItem.getResId());
                negativePreviewName.setText(yourBubblePreviewItem.getName());
                negativePreviewTime.setText(yourBubblePreviewItem.getTime());
                negativePreviewRank.setText("#"+rank);
                //negativePreviewHelper = new WaveHelper(negativePreviewBubble, yourBubblePreviewItem.getPercentage(), 3000, 5000);
                //negativePreviewHelper.start();
            }
            /*else {
                negativePreviewHelper = new WaveHelper(negativePreviewBubble, 0, 0, 0);
                negativePreviewHelper.start();
            }*/
        }
    }

    private void playAnimationOnGoalPreview(goalPreviewItem goalPreviewItem){
        Techniques techniques = Techniques.Landing;
        if (goalPreviewItem.isType()){
            goalPreviewLayout.setBackgroundResource(R.drawable.your_bubble_positiveicon_bg);
            goalsPreviewBar.setProgress(goalPreviewItem.getProgress());
            playAnimation(techniques, 1000, goalsPreviewImage);
            goalsPreviewImage.setImageResource(goalPreviewItem.getIcon());
            playAnimation(techniques, 1000, goalsPreviewName);
            goalsPreviewName.setText(goalPreviewItem.getName());
            playAnimation(techniques, 1000, goalsPreviewType);
            goalsPreviewType.setTextColor(Color.parseColor("#21e8c7"));
            goalsPreviewType.setText("Usage Goal");
            playAnimation(techniques, 1000, goalsPreviewTime);
            goalsPreviewTime.setTextColor(Color.parseColor("#21e8c7"));
            goalsPreviewTime.setText(goalPreviewItem.getTime());
            playAnimation(techniques, 1000, goalPercentLayout);
            goalsPreviewPercent.setTextColor(Color.parseColor("#21e8c7"));
            goalsPreviewPercent.setText(goalPreviewItem.getPercent()+"%");
            goalsPreviewPercentDescription.setText("done");

        }

        else {
            goalPreviewLayout.setBackgroundResource(R.drawable.your_bubble_negativeicon_bg);
            goalsPreviewBar.setProgress(goalPreviewItem.getProgress());
            playAnimation(techniques, 1000, goalsPreviewImage);
            goalsPreviewImage.setImageResource(goalPreviewItem.getIcon());
            playAnimation(techniques, 1000, goalsPreviewName);
            goalsPreviewName.setText(goalPreviewItem.getName());
            playAnimation(techniques, 1000, goalsPreviewType);
            goalsPreviewType.setTextColor(Color.parseColor("#f03145"));
            goalsPreviewType.setText("Usage Limit");
            playAnimation(techniques, 1000, goalsPreviewTime);
            goalsPreviewTime.setTextColor(Color.parseColor("#f03145"));
            goalsPreviewTime.setText(goalPreviewItem.getTime());
            playAnimation(techniques, 1000, goalPercentLayout);
            goalsPreviewPercent.setTextColor(Color.parseColor("#f03145"));
            goalsPreviewPercent.setText(goalPreviewItem.getPercent()+"%");
            goalsPreviewPercentDescription.setText("left");
        }
    }

    private int randomGenerator(int min, int max){
        return new Random().nextInt(max - min + 1) + min;
    }



}
