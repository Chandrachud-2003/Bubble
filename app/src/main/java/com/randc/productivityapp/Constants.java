package com.randc.productivityapp;

import com.randc.productivityapp.Items.customTaskItem;

public class Constants {

    public static final String positiveAppKey = "Positive Apps";
    public static final String negativeAppKey = "Negative Apps";

    public static final String bubblePositiveKey = "Bubble Positive Usage";
    public static final String bubbleNegativeKey = "Bubble Negative Usage";
    public static final String bubbleTotalKey = "Bubble Total Usage";

    public static final String bubbleAppPrefKey = "Bubble Private Shared Preferences";

    public static final String packageIntentKey = "Package Name";
    public static final String appTypeIntentKey = "App Type";
    public static final String previousActivityIntentKey="Previous Activity";

    public static final String yourAppsActivityIntentName="YourAppsActivity";
    public static final String bubblePercentIntentKey="Bubble Percentage - App";

    public static final String showWarningIntent = "Show bubble warning";

    public static final String homePageActivity = "HomePage";
    public static final String timerActivity = "TimerActivity";
    public static final String timelineActivity = "TimelineActivity";
    public static final String aboutActivity = "AboutActivity";
    public static final String yourAppsActivity = "YourAppsActivity";
    public static final String goalsActivity = "GoalsActivity";
    public static final String settingsActivity = "SettingsActivity";

    public static final customTaskItem[] customTasks = {
            new customTaskItem(R.drawable.blogging_vector, "Blogging Session - ", 0),
            new customTaskItem(R.drawable.gardening_vector, "Gardening Session - ", 0),
            new customTaskItem(R.drawable.journaling_vector, "Journaling Session - ", 0),
            new customTaskItem(R.drawable.meditation_vector, "Meditation Session - ", 0),
            new customTaskItem(R.drawable.studying_vector, "Study Session - ", 0),
            new customTaskItem(R.drawable.workout_vector, "Workout Session - ", 0),

    };





}
