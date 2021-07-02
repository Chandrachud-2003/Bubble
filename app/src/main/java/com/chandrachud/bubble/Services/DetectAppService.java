package com.chandrachud.bubble.Services;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.chandrachud.bubble.Constants;
import com.chandrachud.bubble.Items.AppSharedPreferencesItem;
import com.chandrachud.bubble.activities.MainActivity;
import com.chandrachud.bubble.R;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import static com.chandrachud.bubble.Services.App.CHANNEL_ID;

public class DetectAppService extends Service {

    private Timer mTimer;
    private TimerTask mTimerTask;

    private ArrayList<AppSharedPreferencesItem> positiveAppsList;
    private ArrayList<AppSharedPreferencesItem> negativeAppsList;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    private Gson positiveGson;
    private Gson negativeGson;
    private String positiveJson;
    private String negativeJson;
    private Type appType;

    private String previousApp;
    private boolean storedAppFound;
    private boolean type;

    private long startTime;
    private UsageStatsManager mUsageStatsManager;
    private int positiveTime;
    private int negativeTime;
    private int totalTime;

    private PendingIntent mPendingIntent;


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @SuppressLint("WrongConstant")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        mSharedPreferences = getSharedPreferences(Constants.bubbleAppPrefKey, MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        positiveAppsList= new ArrayList<>();
        negativeAppsList = new ArrayList<>();
        positiveGson = new Gson();
        negativeGson = new Gson();
        appType = new TypeToken<List<AppSharedPreferencesItem>>() {}.getType();
        previousApp= "";
        storedAppFound=false;
        type = false;
        positiveTime = mSharedPreferences.getInt(Constants.bubblePositiveKey,0);
        negativeTime = mSharedPreferences.getInt(Constants.bubbleNegativeKey,0);
        totalTime = mSharedPreferences.getInt(Constants.bubbleTotalKey,0);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            mUsageStatsManager =  (UsageStatsManager) getBaseContext().getSystemService(Context.USAGE_STATS_SERVICE);
        }

        else {
            mUsageStatsManager =  (UsageStatsManager) getBaseContext().getSystemService("usagestats");
        }

        positiveJson = mSharedPreferences.getString(Constants.positiveAppKey, "");
        negativeJson = mSharedPreferences.getString(Constants.negativeAppKey, "");


        if (!(positiveJson.equals("")))
        {
            positiveAppsList = positiveGson.fromJson(positiveJson, appType);

        }

        if (!(negativeJson.equals("")))
        {
            negativeAppsList = negativeGson.fromJson(negativeJson, appType);

        }



        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Bubble Service")
                .setContentText("Bubble is currently Running")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)

                .build();



        startForeground(1, notification);
        startTimer();

        return  START_STICKY;
    }

    private Notification getMyActivityNotification(String text){
        // The PendingIntent to launch our activity if the user selects
        // this notification
        Intent notificationIntent = new Intent(this, MainActivity.class);
        mPendingIntent  = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Bubble Service")
                .setContentText("Bubble Service is Running")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(mPendingIntent)

                .build();
    }

    /**
     * This is the method that can be called to update the Notification
     */
    private void updateNotification(String text) {

        Notification notification = getMyActivityNotification(text);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, notification);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    private void startTimer()
    {
        mTimer = new Timer();

        initializeTimerTask();

        mTimer.schedule(mTimerTask,3000, 3000);



    }

    private void initializeTimerTask()
    {
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                String currentApp="";
                 currentApp = retriveNewApp();

                Log.d("Service", "Current App: "+currentApp);
                Log.d("Service", "Previous App: "+previousApp);
                //Log.d("TAG", "run: startTime"+startTime);
                Log.d("TAG", "run: Warning intent"+mSharedPreferences.getBoolean(Constants.showWarningIntent, false));

                if (!(currentApp.equals("")) && !previousApp.equals(currentApp))
                {



                    if(!storedAppFound)
                    {
                        if (arrayContains(positiveAppsList, currentApp))
                        {
                            storedAppFound = true;
                            type = true;
                            Log.d("TAG", "stored app found: ");
                            startTime = System.currentTimeMillis();

                        }

                        else if(arrayContains(negativeAppsList, currentApp))
                        {
                            storedAppFound = true;
                            type = false;
                            Log.d("TAG", "stored app found: ");
                            startTime = System.currentTimeMillis();

                        }
                        //previousApp = currentApp;




                    }

                    else {

                        Log.d("TAG", "run: just before accessing "+startTime);
                        Log.d("TAG", "run: currentTime before accessing "+System.currentTimeMillis());

                        long currentTime = System.currentTimeMillis();



                        int minutes = (int) (TimeUnit.MILLISECONDS.toMinutes(currentTime-startTime));

                        Log.d("TAG", "run: minutes passed to be added "+minutes);

                        if (type)
                        {
                            incrementTimesOpened(positiveAppsList, type, previousApp);
                            incrementUsedMinutes(positiveAppsList, type, previousApp, minutes);
                            startTime=0;


                        }
                        else {

                            incrementTimesOpened(negativeAppsList, type, previousApp);
                            incrementUsedMinutes(negativeAppsList, type, previousApp, minutes);
                            startTime=0;

                        }
                        storedAppFound=false;
                        Log.d("TAG", "stored app incremented: "+previousApp);

                       /* if (arrayContains(positiveAppsList, currentApp))
                        {
                            storedAppFound = true;
                            type = true;
                            Log.d("TAG", "stored app found: ");
                            startTime = System.currentTimeMillis();
                            //previousApp = currentApp;


                        }

                        else if(arrayContains(negativeAppsList, currentApp))
                        {
                            storedAppFound = true;
                            type = false;
                            Log.d("TAG", "stored app found: ");
                            startTime = System.currentTimeMillis();
                            //previousApp = currentApp;


                        }
                        */







                    }

                    previousApp = currentApp;
                    //updateNotification(currentApp);


                }



            }
        };

    }

    public void stopTimerTask() {
        //stop the timer, if it's not already null
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    private String retriveNewApp() {
        if (Build.VERSION.SDK_INT >= 21) {
            String currentApp = null;
            UsageStatsManager usm = (UsageStatsManager) this.getSystemService(Context.USAGE_STATS_SERVICE);
            long time = System.currentTimeMillis();
            List<UsageStats> applist = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - 1000 * 1000, time);
            if (applist != null && applist.size() > 0) {
                SortedMap<Long, UsageStats> mySortedMap = new TreeMap<>();
                for (UsageStats usageStats : applist) {
                    mySortedMap.put(usageStats.getLastTimeUsed(), usageStats);
                }
                if (mySortedMap != null && !mySortedMap.isEmpty()) {
                    currentApp = mySortedMap.get(mySortedMap.lastKey()).getPackageName();
                }
            }
            Log.e("Service", "Current App in foreground is: " + currentApp);

            return currentApp;

        }
        else {

            ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
            String mm=(manager.getRunningTasks(1).get(0)).topActivity.getPackageName();
            Log.e("Service", "Current App in foreground is: " + mm);
            return mm;
        }
    }

    private boolean arrayContains(ArrayList<AppSharedPreferencesItem> searchList, String packageName )
    {
        for (int i=0;i<searchList.size();i++)
        {
            if (searchList.get(i).getPackageName().equals(packageName))
            {
                return true;
            }

        }

        return  false;

    }

    private void incrementTimesOpened(ArrayList<AppSharedPreferencesItem> storeList, boolean appStoreType, String packageName)
    {
        for(int i=0;i<storeList.size();i++)
        {
            if (storeList.get(i).getPackageName().equals(packageName))
            {
                int timesOpened = storeList.get(i).getOpenedTimes()+1;
                storeList.get(i).setOpenedTimes(timesOpened);
                break;

            }

        }

        if (type)
        {
            positiveJson = positiveGson.toJson(storeList);
            mEditor.putString(Constants.positiveAppKey, positiveJson);
            mEditor.commit();

        }

        else {
            negativeJson = negativeGson.toJson(storeList);
            mEditor.putString(Constants.negativeAppKey, negativeJson);
            mEditor.commit();

        }

    }

    private void incrementUsedMinutes(ArrayList<AppSharedPreferencesItem> storedList, boolean appStoreType, String packageName, int minutes)
    {
        for(int i=0;i<storedList.size();i++)
        {

            if (storedList.get(i).getPackageName().equals(packageName))
            {
                int oldMin = storedList.get(i).getTotalMinutesToday();
                int newMin = oldMin+minutes;
                storedList.get(i).setTotalMinutesToday(newMin);
                break;

            }
        }

        if (type)
        {
            positiveJson = positiveGson.toJson(storedList);
            mEditor.putString(Constants.positiveAppKey, positiveJson);
            mEditor.commit();

        }

        else {
            negativeJson = negativeGson.toJson(storedList);
            mEditor.putString(Constants.negativeAppKey, negativeJson);
            mEditor.commit();

        }

        Log.d("TAG", "incrementUsedMinutes: "+packageName+" Minutes added:"+minutes);

        // Score - 36
        //10%

    }

}
