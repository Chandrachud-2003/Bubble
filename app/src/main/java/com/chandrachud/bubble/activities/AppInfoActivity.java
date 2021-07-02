package com.chandrachud.bubble.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AppOpsManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.gelitenight.waveview.library.WaveView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.chandrachud.bubble.Constants;
import com.chandrachud.bubble.HelperClasses.WaveHelper;
import com.chandrachud.bubble.Items.AppSharedPreferencesItem;
import com.chandrachud.bubble.R;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class AppInfoActivity extends AppCompatActivity {

    private AppOpsManager appOps;
    private int statsRequestCode = 23123;

    private UsageStatsManager mUsageStatsManager;

    private ImageButton backButton;
    private WaveView appUsageWave;
    private TextView appGoals;
    private TextView weeklyAppUsage;
    private TextView timesAppOpened;
    private TextView percentAppUsageChanged;
    private TextView todayAppUsage;
    private TextView appName;
    private TextView appRanking;
    private ImageView appIcon;

    private String packageName;
    private boolean appType;
    private String previousActivity;

    private PackageManager mPackageManager;

    private final Context mContext = AppInfoActivity.this;

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    private long todayMinutes=0;
    private long yesterdayMinutes=0;

    private ArrayList<AppSharedPreferencesItem> storedList;
    private int indexItem;

    private int percentage;





    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_info);

        findViewsById();
        getInfoFromIntent();
        getIndexAndArrayList();
        setOnClickListeners();
        setUpUsageStatsManager();
        setAppIcon();
        setAppName();
        setUsageNumTimes();
        compareUsageTimeYesterday();
        setUpWave();



    }

    private void findViewsById()
    {
        backButton = findViewById(R.id.backButton);
        appUsageWave = findViewById(R.id.appUsageWave);
        appGoals = findViewById(R.id.appGoals);
        weeklyAppUsage = findViewById(R.id.weeklyAppUsage);
        timesAppOpened = findViewById(R.id.timesAppOpenedTextView);
        percentAppUsageChanged = findViewById(R.id.percentAppUsageChange);
        todayAppUsage = findViewById(R.id.todayAppUsage);
        backButton = findViewById(R.id.backButton);
        appName = findViewById(R.id.appUsageName);
        appRanking = findViewById(R.id.appRankingUsage);
        appIcon = findViewById(R.id.appIcon);

        mPackageManager = mContext.getPackageManager();

        mSharedPreferences = getSharedPreferences(Constants.bubbleAppPrefKey, MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();


        appUsageWave.setShapeType(WaveView.ShapeType.SQUARE);
        appUsageWave.setBorder(0, Color.parseColor("#00FFFFFF"));
        appUsageWave.setWaveColor(Color.parseColor("#26978C"), Color.parseColor("#21e8c7"));

        WaveHelper usageWaveHelper = new WaveHelper(appUsageWave, 0.35f, 3000, 5000);

        usageWaveHelper.start();

        percentAppUsageChanged.setTextColor(Color.parseColor("#07453b"));









    }

    private void setOnClickListeners()
    {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.d("TAG", "onClick: back  button ");
               // Log.d("TAG", "onClick: back  button text : "+previousActivity);

                if (previousActivity.equals(Constants.yourAppsActivityIntentName))
                {
                    //Log.d("TAG", "onClick: back  button entered");
                    Intent intent = new Intent(AppInfoActivity.this, YourAppsActivity.class);
                    startActivity(intent);
                }

            }
        });


    }

    private void getInfoFromIntent()
    {
        Intent intent = getIntent();
        packageName = intent.getStringExtra(Constants.packageIntentKey);
        appType = intent.getBooleanExtra(Constants.appTypeIntentKey, false);
        previousActivity = intent.getStringExtra(Constants.previousActivityIntentKey);
        percentage = intent.getIntExtra(Constants.bubblePercentIntentKey, 0);

    }

    private void getIndexAndArrayList()
    {
        Gson gson = new Gson();
        String json="";

        if (appType)
        {
            json = mSharedPreferences.getString(Constants.positiveAppKey, "");
        }
        else {
            json = mSharedPreferences.getString(Constants.negativeAppKey, "");

        }

        Type type = new TypeToken<List<AppSharedPreferencesItem>>() {}.getType();

        storedList = gson.fromJson(json, type);

        for(int i=0;i<storedList.size();i++)
        {
            if (storedList.get(i).getPackageName().equals(packageName))
            {
                indexItem = i;
                break;

            }
        }



    }

    private void setAppName()
    {
        String name="";
        try {
             name = (String) mPackageManager.getApplicationLabel(mPackageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA));
        } catch (PackageManager.NameNotFoundException e) {
            Log.d("TAG", "setAppName: entered NameNotFoundException - AppName");
            e.printStackTrace();
        }

        appName.setText(name);



    }

    private void setAppIcon()
    {
        Drawable icon=null;

        try {
            icon = mPackageManager.getApplicationIcon(packageName);
        }
        catch (PackageManager.NameNotFoundException e) {
            Log.d("TAG", "setAppName: entered NameNotFoundException - AppIcon");
            e.printStackTrace();
        }

        Bitmap iconBitmap = drawableToBitmap(icon);
        appIcon.setImageBitmap(Bitmap.createScaledBitmap(iconBitmap, 250, 250, false));



    }

    private void setUsageNumTimes()
    {
       int num = storedList.get(indexItem).getOpenedTimes();

        timesAppOpened.setText(String.valueOf(num));





    }

    @SuppressLint("WrongConstant")
    private void setUpUsageStatsManager()
    {
        appOps = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);

        int mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                Process.myUid(), getPackageName());

        if (mode!=AppOpsManager.MODE_ALLOWED)
        {
            startActivityForResult(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS), statsRequestCode);

        }

        else
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                mUsageStatsManager =  (UsageStatsManager) getBaseContext().getSystemService(Context.USAGE_STATS_SERVICE);
            }

            else {
                mUsageStatsManager =  (UsageStatsManager) getBaseContext().getSystemService("usagestats");
            }

        }

        setUsageTimeToday();




    }

    private void setUsageTimeToday()
    {
        /*Calendar currentDatCal = Calendar.getInstance();
        currentDatCal.add(Calendar.DATE, 0);
        currentDatCal.set(Calendar.HOUR_OF_DAY, 0);
        currentDatCal.set(Calendar.MINUTE, 0);
        currentDatCal.set(Calendar.SECOND, 0);
        currentDatCal.set(Calendar.MILLISECOND, 0);
        long currentDate = currentDatCal.getTimeInMillis();
        Map<String, UsageStats> lUsageStatsMap = mUsageStatsManager.
                queryAndAggregateUsageStats(currentDate,
                        System.currentTimeMillis());


        long totalTimeUsageInMillis = lUsageStatsMap.get(packageName).
                getTotalTimeInForeground();

        //long minutes = TimeUnit.MILLISECONDS.toMinutes(totalTimeUsageInMillis);
        //todayMinutes = minutes;

         */

        int minutes = storedList.get(indexItem).getTotalMinutesToday();
        todayMinutes = minutes;


        double hours = minutes/60.0d;

        //Log.d("TAG", "setUpUsageStatsManager: minutes: "+minutes);
        //Log.d("TAG", "setUpUsageStatsManager: hours"+hours);
        String hoursText="";
        String minutesText="";


        if (hours<1.0d)
        {
            minutesText = String.valueOf(minutes);
            todayAppUsage.setText(minutesText+" min");

        }

        else {
            DecimalFormat df1 = new DecimalFormat("0.##");
            hoursText = df1.format(hours);
            todayAppUsage.setText(hoursText+" hrs");
        }

    }

    private void compareUsageTimeYesterday()
    {
        Calendar yesterdayCal = Calendar.getInstance();
        yesterdayCal.add(Calendar.DATE, -1);
        yesterdayCal.set(Calendar.HOUR_OF_DAY,0);
        yesterdayCal.set(Calendar.MINUTE, 0);
        yesterdayCal.set(Calendar.SECOND, 0);
        yesterdayCal.set(Calendar.MILLISECOND, 0);
        long yesterdayDate = yesterdayCal.getTimeInMillis();

        Calendar currentDatCal = Calendar.getInstance();
        currentDatCal.add(Calendar.DATE, 0);
        currentDatCal.set(Calendar.HOUR_OF_DAY, 0);
        currentDatCal.set(Calendar.MINUTE, 0);
        currentDatCal.set(Calendar.SECOND, 0);
        currentDatCal.set(Calendar.MILLISECOND, 0);
        long currentDate = currentDatCal.getTimeInMillis();

        Map<String, UsageStats> lUsageStatsMap = mUsageStatsManager.
                queryAndAggregateUsageStats(yesterdayDate,
                        currentDate);


        long totalTimeUsageInMillis = lUsageStatsMap.get(packageName).
                getTotalTimeInForeground();

        long minutes = TimeUnit.MILLISECONDS.toMinutes(totalTimeUsageInMillis);
        yesterdayMinutes = minutes;

       // Log.d("TAG", "compareUsageTimeYesterday: yesterday: "+yesterdayMinutes+"minutes");
       // Log.d("TAG", "compareUsageTimeYesterday: today: "+todayMinutes+"minutes");


        double changePercent = ((double)(todayMinutes-yesterdayMinutes)/ (double)yesterdayMinutes)*100.0;
        int change = Math.abs((int) changePercent);



        boolean greaterThanZero = (changePercent>=0);
        if (change==0)
        {
            greaterThanZero=true;
        }


        if (appType)
        {
            if (greaterThanZero)
            {
                percentAppUsageChanged.setText("+ "+ change +"%");
                percentAppUsageChanged.setTextColor(Color.parseColor("#21e8c7"));

            }

            else {

                percentAppUsageChanged.setText("- "+ change +"%");
                percentAppUsageChanged.setTextColor(Color.parseColor("#f03145"));
            }

        }

        else {

            if (greaterThanZero)
            {
                percentAppUsageChanged.setText("+ "+ change +"%");
                percentAppUsageChanged.setTextColor(Color.parseColor("#f03145"));

            }

            else {

                percentAppUsageChanged.setText("- "+ change +"%");
                percentAppUsageChanged.setTextColor(Color.parseColor("#21e8c7"));
            }

        }




    }

    private void setUpWave()
    {





        appUsageWave.setShapeType(WaveView.ShapeType.SQUARE);
        appUsageWave.setBorder(0, Color.parseColor("#00FFFFFF"));

        if (appType)
        {
            appUsageWave.setWaveColor(Color.parseColor("#26978C"), Color.parseColor("#21e8c7"));

        }

        else {

            appUsageWave.setWaveColor(Color.parseColor("#953F53"), Color.parseColor("#f03145"));


        }

        if (percentage>=10)
        {
            if (appType)
            {
                percentAppUsageChanged.setTextColor(Color.parseColor("#07453b"));

            }

            else {
                percentAppUsageChanged.setTextColor(Color.parseColor("#180204"));


            }

        }

        else {

            if (appType)
            {
                percentAppUsageChanged.setTextColor(Color.parseColor("#21e8c7"));

            }

            else {
                percentAppUsageChanged.setTextColor(Color.parseColor("#f03145"));


            }
        }

        float waveValue = 0.0f;

        if (percentage==0)
        {
            waveValue=0.0f;

        }

        else if (percentage>=100)
        {
            waveValue = 1.0f;

        }

        else {

            waveValue = ((float)percentage)/100.0f;
        }

        WaveHelper usageWaveHelper = new WaveHelper(appUsageWave, waveValue, 3000, 5000);

        usageWaveHelper.start();

    }

    public static Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    }

