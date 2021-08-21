package com.chandrachud.bubble.HelperClasses;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chandrachud.bubble.Constants;
import com.chandrachud.bubble.activities.GoalCenter;
import com.chandrachud.bubble.activities.HomePage;
import com.chandrachud.bubble.R;
import com.chandrachud.bubble.activities.TimerActivity;
import com.chandrachud.bubble.activities.YourAppsActivity;
import com.thekhaeng.pushdownanim.PushDownAnim;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

public class SideNavClass {

    private Activity activity;

    private LinearLayout homeLayout;
    private ImageView homeImage;
    private TextView homeText;

    private LinearLayout goalsLayout;
    private ImageView goalsImage;
    private TextView goalsText;

    private LinearLayout timerLayout;
    private TextView timerText;
    private ImageView timerImage;

    /*private LinearLayout timelineLayout;
    private ImageView timelineImage;
    private TextView timelineText;*/

    private LinearLayout settingsLayout;
    private ImageView settingsImage;
    private TextView settingsText;

    private LinearLayout aboutLayout;
    private ImageView aboutImage;
    private TextView aboutText;

    private LinearLayout appsLayout;
    private ImageView appsImage;
    private TextView appsText;

    private SlidingRootNav slidingRootNav;
    private ImageButton menuButton;
    private String activityName;


    public SideNavClass(Activity activity, Bundle savedInstanceState, String activityName)
    {
        this.activity = activity;
        slidingRootNav = new SlidingRootNavBuilder(activity)
                .withMenuOpened(false)
                .withContentClickableWhenMenuOpened(false)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.menu_left_drawer)
                .inject();
        this.activityName = activityName;
    }

    public void setUpSideNav()
    {
        findViewsById();
        setUpButtonAppearences();
        setUpOnClickListeners();
    }

    private void findViewsById()
    {
        homeLayout = activity.findViewById(R.id.homeMenuLayout);
        homeImage = activity.findViewById(R.id.homeMenuImage);
        homeText = activity.findViewById(R.id.homeMenuText);

        timerLayout = activity.findViewById(R.id.timerMenuLayout);
        timerImage = activity.findViewById(R.id.timerMenuImage);
        timerText = activity.findViewById(R.id.timerMenuText);

        goalsLayout = activity.findViewById(R.id.goalCenterMenuLayout);
        goalsImage = activity.findViewById(R.id.goalcenterMenuImage);
        goalsText = activity.findViewById(R.id.goalcenterMenuText);

       /* timelineLayout = activity.findViewById(R.id.timelineMenuLayout);
        timelineImage = activity.findViewById(R.id.timelineMenuImage);
        timelineText = activity.findViewById(R.id.timelineMenuText);*/

        aboutLayout = activity.findViewById(R.id.aboutMenuLayout);
        aboutImage = activity.findViewById(R.id.aboutMenuImage);
        aboutText = activity.findViewById(R.id.aboutMenuText);

        settingsLayout = activity.findViewById(R.id.settingsMenuLayout);
        settingsImage = activity.findViewById(R.id.settingsMenuImage);
        settingsText = activity.findViewById(R.id.settingsMenuText);

        appsLayout = activity.findViewById(R.id.appsMenuLayout);
        appsImage = activity.findViewById(R.id.appsMenuImage);
        appsText = activity.findViewById(R.id.appsMenuText);

        menuButton = activity.findViewById(R.id.menuButton);
    }

    private void setUpButtonAppearences()
    {
        if(activityName.equals(Constants.homePageActivity))
        {
            homeText.setTextColor(Color.WHITE);
            homeImage.setImageResource(R.drawable.home_menu_white);

            /*timelineText.setTextColor(Color.parseColor("#707cc2"));
            timelineImage.setImageResource(R.drawable.timeline_menu);*/

            timerText.setTextColor(Color.parseColor("#707cc2"));
            timerImage.setImageResource(R.drawable.timer_menu);

            goalsText.setTextColor(Color.parseColor("#707cc2"));
            goalsImage.setImageResource(R.drawable.goals_menu);

            aboutText.setTextColor(Color.parseColor("#707cc2"));
            aboutImage.setImageResource(R.drawable.info_menu);

            settingsText.setTextColor(Color.parseColor("#707cc2"));
            settingsImage.setImageResource(R.drawable.settings_menu);

            appsText.setTextColor(Color.parseColor("#707cc2"));
            appsImage.setImageResource(R.drawable.your_apps);
        }

        else if(activityName.equals(Constants.timerActivity))
        {
            timerText.setTextColor(Color.WHITE);
            timerImage.setImageResource(R.drawable.timer_menu_white);

            /*timelineText.setTextColor(Color.parseColor("#707cc2"));
            timelineImage.setImageResource(R.drawable.timeline_menu);*/

            homeText.setTextColor(Color.parseColor("#707cc2"));
            homeImage.setImageResource(R.drawable.home_menu);

            goalsText.setTextColor(Color.parseColor("#707cc2"));
            goalsImage.setImageResource(R.drawable.goals_menu);

            aboutText.setTextColor(Color.parseColor("#707cc2"));
            aboutImage.setImageResource(R.drawable.info_menu);

            settingsText.setTextColor(Color.parseColor("#707cc2"));
            settingsImage.setImageResource(R.drawable.settings_menu);

            appsText.setTextColor(Color.parseColor("#707cc2"));
            appsImage.setImageResource(R.drawable.your_apps);
        }

       /* else if(activityName.equals(Constants.timelineActivity))
        {
            timelineText.setTextColor(Color.WHITE);
            timelineImage.setImageResource(R.drawable.timeline_menu_white);

            homeText.setTextColor(Color.parseColor("#707cc2"));
            homeImage.setImageResource(R.drawable.home_menu);

            timerText.setTextColor(Color.parseColor("#707cc2"));
            timerImage.setImageResource(R.drawable.timer_menu);

            goalsText.setTextColor(Color.parseColor("#707cc2"));
            goalsImage.setImageResource(R.drawable.goals_menu);

            aboutText.setTextColor(Color.parseColor("#707cc2"));
            aboutImage.setImageResource(R.drawable.info_menu);

            settingsText.setTextColor(Color.parseColor("#707cc2"));
            settingsImage.setImageResource(R.drawable.settings_menu);

            appsText.setTextColor(Color.parseColor("#707cc2"));
            appsImage.setImageResource(R.drawable.your_apps);
        }
*/
        else if(activityName.equals(Constants.goalsActivity))
        {
            goalsText.setTextColor(Color.WHITE);
            goalsImage.setImageResource(R.drawable.goals_menu_white);

           /* timelineText.setTextColor(Color.parseColor("#707cc2"));
            timelineImage.setImageResource(R.drawable.timeline_menu);*/

            timerText.setTextColor(Color.parseColor("#707cc2"));
            timerImage.setImageResource(R.drawable.timer_menu);

            homeText.setTextColor(Color.parseColor("#707cc2"));
            homeImage.setImageResource(R.drawable.home_menu);

            aboutText.setTextColor(Color.parseColor("#707cc2"));
            aboutImage.setImageResource(R.drawable.info_menu);

            settingsText.setTextColor(Color.parseColor("#707cc2"));
            settingsImage.setImageResource(R.drawable.settings_menu);

            appsText.setTextColor(Color.parseColor("#707cc2"));
            appsImage.setImageResource(R.drawable.your_apps);
        }

        else if(activityName.equals(Constants.aboutActivity))
        {
            aboutText.setTextColor(Color.WHITE);
            aboutImage.setImageResource(R.drawable.info_menu_white);

           /* timelineText.setTextColor(Color.parseColor("#707cc2"));
            timelineImage.setImageResource(R.drawable.timeline_menu);*/

            timerText.setTextColor(Color.parseColor("#707cc2"));
            timerImage.setImageResource(R.drawable.timer_menu);

            goalsText.setTextColor(Color.parseColor("#707cc2"));
            goalsImage.setImageResource(R.drawable.goals_menu);

            homeText.setTextColor(Color.parseColor("#707cc2"));
            homeImage.setImageResource(R.drawable.home_menu);

            settingsText.setTextColor(Color.parseColor("#707cc2"));
            settingsImage.setImageResource(R.drawable.settings_menu);

            appsText.setTextColor(Color.parseColor("#707cc2"));
            appsImage.setImageResource(R.drawable.your_apps);
        }

        else if(activityName.equals(Constants.settingsActivity))
        {
            settingsText.setTextColor(Color.WHITE);
            settingsImage.setImageResource(R.drawable.settings_menu_white);

            /*timelineText.setTextColor(Color.parseColor("#707cc2"));
            timelineImage.setImageResource(R.drawable.timeline_menu);*/

            timerText.setTextColor(Color.parseColor("#707cc2"));
            timerImage.setImageResource(R.drawable.timer_menu);

            goalsText.setTextColor(Color.parseColor("#707cc2"));
            goalsImage.setImageResource(R.drawable.goals_menu);

            aboutText.setTextColor(Color.parseColor("#707cc2"));
            aboutImage.setImageResource(R.drawable.info_menu);

            homeText.setTextColor(Color.parseColor("#707cc2"));
            homeImage.setImageResource(R.drawable.home_menu);

            appsText.setTextColor(Color.parseColor("#707cc2"));
            appsImage.setImageResource(R.drawable.your_apps);
        }

        else if(activityName.equals(Constants.yourAppsActivity))
        {
            appsText.setTextColor(Color.WHITE);
            appsImage.setImageResource(R.drawable.your_apps_white);

            /*timelineText.setTextColor(Color.parseColor("#707cc2"));
            timelineImage.setImageResource(R.drawable.timeline_menu);*/

            timerText.setTextColor(Color.parseColor("#707cc2"));
            timerImage.setImageResource(R.drawable.timer_menu);

            goalsText.setTextColor(Color.parseColor("#707cc2"));
            goalsImage.setImageResource(R.drawable.goals_menu);

            aboutText.setTextColor(Color.parseColor("#707cc2"));
            aboutImage.setImageResource(R.drawable.info_menu);

            homeText.setTextColor(Color.parseColor("#707cc2"));
            homeImage.setImageResource(R.drawable.home_menu);

            settingsText.setTextColor(Color.parseColor("#707cc2"));
            settingsImage.setImageResource(R.drawable.settings_menu);
        }

    }

    private void setUpOnClickListeners()
    {


        homeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (activityName.equals(Constants.homePageActivity))
                {
                    slidingRootNav.closeMenu();
                }

                else {
                    slidingRootNav.closeMenu();
                    Intent intent = new Intent(activity, HomePage.class);
                    activity.startActivity(intent);
                    activity.finish();
                }

            }
        });

        timerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (activityName.equals(Constants.timerActivity))
                {
                    slidingRootNav.closeMenu();
                }

                else {
                    slidingRootNav.closeMenu();
                    Intent intent = new Intent(activity, TimerActivity.class);
                    activity.startActivity(intent);
                    activity.finish();
                }

            }
        });

        /*timelineLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (activityName.equals(Constants.timelineActivity))
                {
                    slidingRootNav.closeMenu();
                }

                else {
                    slidingRootNav.closeMenu();
                    Intent intent = new Intent(activity, timelineActivity.class);
                    activity.startActivity(intent);
                    activity.finish();
                }

            }
        });*/

        goalsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (activityName.equals(Constants.goalsActivity))
                {
                    slidingRootNav.closeMenu();
                }

                else {
                    slidingRootNav.closeMenu();
                    Intent intent = new Intent(activity, GoalCenter.class);
                    activity.startActivity(intent);
                    activity.finish();
                }

            }
        });

        appsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (activityName.equals(Constants.yourAppsActivity))
                {
                    slidingRootNav.closeMenu();
                }

                else {
                    slidingRootNav.closeMenu();
                    Intent intent = new Intent(activity, YourAppsActivity.class);
                    activity.startActivity(intent);
                    activity.finish();
                }

            }
        });
    }





}
