package com.chandrachud.bubble.HelperClasses;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.chandrachud.bubble.R;

import com.chandrachud.bubble.activities.GoalCenter;
import com.chandrachud.bubble.activities.HomePage;
import com.chandrachud.bubble.activities.TimerActivity;
import com.chandrachud.bubble.activities.YourAppsActivity;


public class BottomNav {

    private Activity activity;

    public BottomNav(Activity activity) {
        this.activity = activity;
    }

    public void setupBottomNav() {
        findViewsById();
        setupOnClickListeners();
    }

    private void findViewsById() {

    }

    private void setupOnClickListeners() {

    }
}
