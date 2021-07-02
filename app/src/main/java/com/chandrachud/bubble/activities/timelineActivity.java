package com.chandrachud.bubble.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;


import com.av.smoothviewpager.Smoolider.SmoothViewpager;
import com.chandrachud.bubble.Adapters.smooliderAdapter;
import com.chandrachud.bubble.Constants;
import com.chandrachud.bubble.HelperClasses.SideNavClass;
import com.chandrachud.bubble.Items.timelineItem;
import com.chandrachud.bubble.R;

import java.util.ArrayList;

public class timelineActivity extends AppCompatActivity {

    private ImageButton menuButton;

    private ArrayList<timelineItem> todayList;
    private ArrayList<timelineItem> yesterdayList;
    private ArrayList<ArrayList<timelineItem>> smooliderList;

    private ArrayList<String> weekDaysList;
    private ArrayList<String> datesList;
    private ArrayList<Integer> minutesList;
    private ArrayList<Integer> hoursList;

    private TextView dayText;
    private TextView dateText;
    private TextView totalHours;
    private TextView totalHoursText;
    private TextView totalMinutes;
    private TextView totalMinutesText;

    private SmoothViewpager smooliderViewPager;

    private SideNavClass mSideNavClass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        findViewsById(savedInstanceState);
        setUpLists();

        changeHeaderInfo(0);

        smooliderViewPager.setAdapter(new smooliderAdapter(smooliderList, getApplicationContext()));


        smooliderViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                changeHeaderInfo(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });




    }

    private void findViewsById(Bundle bundle)
    {
        menuButton = findViewById(R.id.menuButton);
        dayText = findViewById(R.id.timelineAppDay);
        dateText = findViewById(R.id.timelineAppDate);
        totalHours = findViewById(R.id.timelineTotalHours);
        totalHoursText = findViewById(R.id.timelineTotalHoursText);
        totalMinutes = findViewById(R.id.timelineTotalMin);
        totalMinutesText = findViewById(R.id.timelineTotalMinText);
        smooliderViewPager = findViewById(R.id.smooliderLayout);

        mSideNavClass = new SideNavClass(timelineActivity.this, bundle, Constants.timelineActivity);
        mSideNavClass.setUpSideNav();


    }

    private void setUpLists()
    {
        todayList = new ArrayList<>();
        yesterdayList = new ArrayList<>();
        smooliderList = new ArrayList<>();
        weekDaysList = new ArrayList<>();
        datesList = new ArrayList<>();
        minutesList = new ArrayList<>();
        hoursList = new ArrayList<>();

        todayList.add(new timelineItem("Spotify","positive", 30, 15, 2, "Just Now", R.drawable.spotify_timeline));
        todayList.add(new timelineItem("WhatsApp","negative", 50, 45, 0, "9:00 PM", R.drawable.icons8_whatsapp_timeline));
        todayList.add(new timelineItem("Classroom","positive", 20, 20, 0, "8:00 PM", R.drawable.classroom_timeline));
        todayList.add(new timelineItem("Chrome","positive", 70, 0, 3, "5:00 PM", R.drawable.chrome_timeline));
        todayList.add(new timelineItem("Super Mario","negative", 30, 30, 1, "4:00 PM", R.drawable.mario_timeline));
        todayList.add(new timelineItem("Word","positive", 15, 45, 0, "3:00 PM", R.drawable.word_timeline));


        yesterdayList.add(new timelineItem("Excel","positive", 30, 30, 2, "Just Now", R.drawable.excel_timeline));
        yesterdayList.add(new timelineItem("WhatsApp","negative", 50, 35, 1, "8:45 PM", R.drawable.icons8_whatsapp_timeline));
        yesterdayList.add(new timelineItem("Instagram","negative", 15, 20, 0, "8:00 PM", R.drawable.insta_timeline));
        yesterdayList.add(new timelineItem("Zoom","positive", 70, 0, 3, "5:00 PM", R.drawable.zoom_timeline));

        smooliderList.add(todayList);
        smooliderList.add(yesterdayList);

        weekDaysList.add("Monday");
        weekDaysList.add("Sunday");

        datesList.add("June 23, 2020");
        datesList.add("June 22, 2020");

        minutesList.add(20);
        hoursList.add(5);

        minutesList.add(45);
        hoursList.add(7);





    }

    private void changeHeaderInfo(int pos)
    {
        setAnimation(datesList.get(pos), dateText);
        setAnimation(weekDaysList.get(pos), dayText);

        setAnimation(String.valueOf(hoursList.get(pos)), totalHours);
        setAnimation(String.valueOf(minutesList.get(pos)), totalMinutes);

    }

    private void setAnimation(String text, TextView textView)
    {
        textView.setVisibility(View.INVISIBLE);
        textView.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in_text));
        textView.setVisibility(View.VISIBLE);
        textView.setText(text);

    }




}
