package com.randc.productivityapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AppOpsManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Process;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.randc.productivityapp.Adapters.addAppAdapter;
import com.randc.productivityapp.Adapters.appTypeListAdapter;
import com.randc.productivityapp.AppInfo;
import com.randc.productivityapp.AppInfoRetriever;
import com.randc.productivityapp.Constants;
import com.randc.productivityapp.HelperClasses.SideNavClass;
import com.randc.productivityapp.Items.AppSharedPreferencesItem;
import com.randc.productivityapp.Items.addAppItem;
import com.randc.productivityapp.Items.appTypeListItem;
import com.randc.productivityapp.R;
import com.randc.productivityapp.Services.DetectAppService;


import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class YourAppsActivity extends AppCompatActivity implements com.randc.productivityapp.Adapters.addAppAdapter.positiveAppCallBack, com.randc.productivityapp.Adapters.addAppAdapter.negativeAppCallBack {

    private RecyclerView positiveAppsRecycler;
    private RecyclerView negativeAppsRecycler;
    private RecyclerView addAppsRecycler;

    private ImageButton menuButton;

    private appTypeListAdapter positiveListAdapter;
    private appTypeListAdapter negativeListAdapter;
    private addAppAdapter addAppAdapter;
    private ArrayList<appTypeListItem> positiveAppsList;
    private ArrayList<appTypeListItem> negativeAppsList;
    private ArrayList<addAppItem> addAppsList;

    private LinearLayoutManager positiveAppsLayout;
    private LinearLayoutManager negativeAppsLayout;
    private GridLayoutManager addAppsLayout;

    private ImageButton premiumButton;

    private ArrayList<AppInfo> appList;


    private LinearLayout emptyPositiveLayout;
    private LinearLayout emptyNegativeLayout;
    private LottieAnimationView emptyPositiveAnimation;
    private LottieAnimationView emptyNegativeAnimation;

    private ArrayList<String> addPositiveAppsList;
    private ArrayList<String> addNegativeAppsList;

    private Button addButton;
    private LinearLayout addButtonLayout;

    private EditText searchEditText;
    private ImageView searchView;
    private ImageButton cancelSearchButton;

    private ArrayList<addAppItem> filteredList;
    private boolean searching=false;

    private TextView positiveAppCountText;
    private TextView negativeAppCountText;

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    private ArrayList<AppSharedPreferencesItem> storedPositiveList;
    private ArrayList<AppSharedPreferencesItem> storedNegativeList;

    private AppOpsManager appOps;
    private int statsRequestCode = 23123;

    private UsageStatsManager mUsageStatsManager;

    private boolean permissionGranted=false;

    private SideNavClass mSideNavClass;

    private LottieAnimationView yourAppsLoadingAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_your_apps);

        checkForPermissions();

        findViewsById(savedInstanceState);

        setPositiveEmptyAnimation(true);
        setNegativeEmptyAnimation(true);


        setUpSharedPreferences();

        setUpAddAppsRecycler();

        setUpOnClickListeners();





    }



    private void findViewsById(Bundle bundle)
    {
        positiveAppsRecycler = findViewById(R.id.positiveAppsRecycler);
        negativeAppsRecycler = findViewById(R.id.negativeAppsRecycler);
        menuButton = findViewById(R.id.menuButton);
        addAppsRecycler = findViewById(R.id.addAppsRecycler);
        premiumButton = findViewById(R.id.premiumButton);


        emptyPositiveLayout = findViewById(R.id.positiveEmptyLayout);
        emptyNegativeLayout = findViewById(R.id.negativeEmptyLayout);
        emptyNegativeAnimation = findViewById(R.id.emptyNegativeApps);
        emptyPositiveAnimation = findViewById(R.id.emptyPositiveApps);

        addPositiveAppsList = new ArrayList<>();
        addNegativeAppsList = new ArrayList<>();
        negativeAppsList = new ArrayList<>();
        positiveAppsList = new ArrayList<>();


        addButton = findViewById(R.id.addAppsButton);
        addButtonLayout = findViewById(R.id.addButtonBottomLayout);

        setBottomButton(false);

        searchEditText = findViewById(R.id.searchText);
        searchView = findViewById(R.id.searchImage);
        cancelSearchButton = findViewById(R.id.searchCloseButton);

        filteredList = new ArrayList<>();

        positiveAppCountText = findViewById(R.id.positiveAppsCount);
        negativeAppCountText = findViewById(R.id.negativeAppsCount);

        yourAppsLoadingAnim = findViewById(R.id.yourAppsLoading);

        mSideNavClass = new SideNavClass(YourAppsActivity.this, bundle, Constants.yourAppsActivity);
        mSideNavClass.setUpSideNav();
    }

    public void startService()
    {
        Intent serviceIntent = new Intent(this, DetectAppService.class);
        startService(serviceIntent);

    }

    @SuppressLint("WrongConstant")
    private void checkForPermissions()
    {
        appOps = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);

        int mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                Process.myUid(), getPackageName());

        if (mode!=AppOpsManager.MODE_ALLOWED)
        {
            startActivityForResult(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS), statsRequestCode);
            permissionGranted = false;

        }

        else
        {
            permissionGranted = true;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                mUsageStatsManager =  (UsageStatsManager) getBaseContext().getSystemService(Context.USAGE_STATS_SERVICE);
            }

            //2:12 start

            else {
                mUsageStatsManager =  (UsageStatsManager) getBaseContext().getSystemService("usagestats");
            }

            startService();

        }

    }

    private void setUpOnClickListeners()
    {
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setBottomButton(false);


                if (addPositiveAppsList.size()>0 && addNegativeAppsList.size()>0)
                {
                    ArrayList<Integer> positiveMinutes = addItemsToPositiveSharedPrefs();
                    ArrayList<Integer> negativeMinutes = addItemsToNegativeSharedPrefs();
                    int totalTime = mSharedPreferences.getInt(Constants.bubbleTotalKey,0);

                    addPositiveApps(false,totalTime, positiveMinutes);
                    addNegativeApps(false,totalTime, negativeMinutes);

                }

                else if(addPositiveAppsList.size()>0)
                {

                    ArrayList<Integer> positiveMinutes = addItemsToPositiveSharedPrefs();
                    //displayArrayList(addPositiveAppsList);
                    addPositiveApps(false, mSharedPreferences.getInt(Constants.bubbleTotalKey,0), positiveMinutes);



                }

                else if(addNegativeAppsList.size()>0)
                {
                    //displayArrayList(addNegativeAppsList);
                    ArrayList<Integer> negativeMinutes= addItemsToNegativeSharedPrefs();
                    addNegativeApps(false, mSharedPreferences.getInt(Constants.bubbleTotalKey,0), negativeMinutes);

                }

                if (searching)
                {
                    addAppAdapter = new addAppAdapter(addAppsList, YourAppsActivity.this);
                    addAppsRecycler.setAdapter(addAppAdapter);
                    searching = false;
                    filteredList.clear();
                    searchEditText.setText("");

                }



            }
        });

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                String text = charSequence.toString();
                if (text.length()>0 )
                {
                    if (cancelSearchButton.getVisibility()==View.INVISIBLE)
                    {


                        YoYo.with(Techniques.SlideInRight)
                                .duration(500)
                                .playOn(cancelSearchButton);
                        cancelSearchButton.setVisibility(View.VISIBLE);
                        cancelSearchButton.setClickable(true);


                    }

                    addAppAdapter = new addAppAdapter(searchForApp(text), YourAppsActivity.this);
                    addAppsRecycler.setAdapter(addAppAdapter);
                    searching = true;



                }

                else {

                    YoYo.with(Techniques.SlideOutRight)
                            .duration(500)
                            .playOn(cancelSearchButton);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            cancelSearchButton.setVisibility(View.INVISIBLE);
                            cancelSearchButton.setClickable(false);

                        }
                    }, 500);

                    addAppAdapter = new addAppAdapter(addAppsList, YourAppsActivity.this);
                    addAppsRecycler.setAdapter(addAppAdapter);
                    searching = false;
                    filteredList.clear();





                }







            }

            @Override
            public void afterTextChanged(Editable editable) {



            }
        });

        cancelSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                searchEditText.setText("");
                YoYo.with(Techniques.SlideOutRight)
                        .duration(500)
                        .playOn(cancelSearchButton);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        cancelSearchButton.setVisibility(View.INVISIBLE);
                        cancelSearchButton.setClickable(false);


                    }
                }, 500);
                searching = false;


            }
        });


        searchEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if(searchEditText.hasFocus()){

                    searchEditText.setCursorVisible(true);


                }

                else {
                    searchEditText.setCursorVisible(false);
                }
            }
        });

        KeyboardVisibilityEvent.setEventListener(
                YourAppsActivity.this,
                new KeyboardVisibilityEventListener() {
                    @Override
                    public void onVisibilityChanged(boolean isOpen) {
                       if (isOpen)
                       {
                           searchEditText.setCursorVisible(true);

                       }

                       else {
                           searchEditText.setCursorVisible(false );
                       }
                    }
                });

    }




    private void setUpSharedPreferences()
    {
        mSharedPreferences = getSharedPreferences(Constants.bubbleAppPrefKey, MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();

         storedPositiveList = new ArrayList<>();
         storedNegativeList = new ArrayList<>();

        Gson positiveGson = new Gson();
        Gson negativeGson = new Gson();

        String positiveJson = mSharedPreferences.getString(Constants.positiveAppKey, "");
        String negativeJson = mSharedPreferences.getString(Constants.negativeAppKey, "");

        if (!(positiveJson.equals("")))
        {
            Type type = new TypeToken<List<AppSharedPreferencesItem>>() {}.getType();
            storedPositiveList = positiveGson.fromJson(positiveJson, type);

            ArrayList<Integer> positiveMinutesList = new ArrayList<>();

            for(int i=0;i<storedPositiveList.size();i++)
            {
                addPositiveAppsList.add(storedPositiveList.get(i).getPackageName());
                positiveMinutesList.add(storedPositiveList.get(i).getTotalMinutesToday());

                Log.d("TAG", "setUpSharedPreferences: package: "+storedPositiveList.get(i).getPackageName()+" total min:"+storedPositiveList.get(i).getTotalMinutesToday());

            }
            addPositiveApps(true, mSharedPreferences.getInt(Constants.bubbleTotalKey,0), positiveMinutesList);
            setPositiveEmptyAnimation(false);




        }

        if (!(negativeJson.equals("")))
        {
            Type type = new TypeToken<List<AppSharedPreferencesItem>>() {}.getType();
            storedNegativeList = negativeGson.fromJson(negativeJson, type);

            ArrayList<Integer> negativeMinutesList = new ArrayList<>();

            for(int i=0;i<storedNegativeList.size();i++)
            {
                addNegativeAppsList.add(storedNegativeList.get(i).getPackageName());
                negativeMinutesList.add(storedNegativeList.get(i).getTotalMinutesToday());
                Log.d("TAG", "setUpSharedPreferences: package: "+storedNegativeList.get(i).getPackageName()+" total min:"+storedNegativeList.get(i).getTotalMinutesToday());


            }



            addNegativeApps(true, mSharedPreferences.getInt(Constants.bubbleTotalKey,0), negativeMinutesList);
            setNegativeEmptyAnimation(false);


        }





    }

    private ArrayList<Integer> addItemsToPositiveSharedPrefs()
    {
        Gson tempGson = new Gson();
        String json = mSharedPreferences.getString(Constants.positiveAppKey, "");


        int totalTime = mSharedPreferences.getInt(Constants.bubbleTotalKey, 0);
        int positiveTime = mSharedPreferences.getInt(Constants.bubblePositiveKey,0);


        ArrayList<AppSharedPreferencesItem> tempList = new ArrayList<>();
        ArrayList<Integer> minutesList = new ArrayList<>();
        if (!(json.equals("")))
        {
            Type type = new TypeToken<List<AppSharedPreferencesItem>>() {}.getType();
            tempList = tempGson.fromJson(json,type);

        }


        for(int i=0;i<addPositiveAppsList.size();i++)
        {
            int minutes = getAppUsageTime(addPositiveAppsList.get(i));
            minutesList.add(minutes);
            positiveTime+=minutes;
            //Log.d("TAG", "Package Name: "+addPositiveAppsList.get(i)+" Usage: "+minutes+"min");

            tempList.add(new AppSharedPreferencesItem(addPositiveAppsList.get(i), 0, true, minutes));

        }

        totalTime+=positiveTime;

        mEditor.putInt(Constants.bubblePositiveKey, positiveTime);
        mEditor.putInt(Constants.bubbleTotalKey,totalTime);

        Gson storeGson = new Gson();
        String storeJson = storeGson.toJson(tempList);

        mEditor.putString(Constants.positiveAppKey, storeJson);
        mEditor.commit();

        checkForPermissions();

        Log.d("TAG", "addItemsToNegativeSharedPrefs: Positive Time :  "+mSharedPreferences.getInt(Constants.bubblePositiveKey,0));
        Log.d("TAG", "addItemsToNegativeSharedPrefs: Total Time :  "+mSharedPreferences.getInt(Constants.bubbleTotalKey,0));

        return  minutesList;

    }

    private ArrayList<Integer> addItemsToNegativeSharedPrefs()
    {
        Gson tempGson = new Gson();
        String json = mSharedPreferences.getString(Constants.negativeAppKey, "");


        int negativeTime=mSharedPreferences.getInt(Constants.bubbleNegativeKey,0);
        int totalTime = mSharedPreferences.getInt(Constants.bubbleTotalKey,0);


        ArrayList<AppSharedPreferencesItem> tempList = new ArrayList<>();
        ArrayList<Integer> minutesList = new ArrayList<>();

        if (!(json.equals("")))
        {
            Type type = new TypeToken<List<AppSharedPreferencesItem>>() {}.getType();
            tempList = tempGson.fromJson(json,type);

        }

        for(int i=0;i<addNegativeAppsList.size();i++)
        {
            int minutes = getAppUsageTime(addNegativeAppsList.get(i));
            minutesList.add(minutes);
            negativeTime+=minutes;
            //Log.d("TAG", "Package Name: "+addNegativeAppsList.get(i)+" Usage: "+minutes+"min");

            tempList.add(new AppSharedPreferencesItem(addNegativeAppsList.get(i), 0, false, minutes));

        }

        totalTime+=negativeTime;

        mEditor.putInt(Constants.bubbleTotalKey,totalTime);
        mEditor.putInt(Constants.bubbleNegativeKey,negativeTime);

        Gson storeGson = new Gson();
        String storeJson = storeGson.toJson(tempList);

        mEditor.putString(Constants.negativeAppKey, storeJson);
        mEditor.commit();
        checkForPermissions();

        Log.d("TAG", "addItemsToNegativeSharedPrefs: Negative Time :  "+mSharedPreferences.getInt(Constants.bubbleNegativeKey,0));
        Log.d("TAG", "addItemsToNegativeSharedPrefs: Total Time :  "+mSharedPreferences.getInt(Constants.bubbleTotalKey,0));

        return minutesList;


    }

    private int getAppUsageTime(String packageName)
    {
        Calendar currentDatCal = Calendar.getInstance();
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

        long minutes = TimeUnit.MILLISECONDS.toMinutes(totalTimeUsageInMillis);

        return (int) minutes;


    }

    private void setUpPositiveAppsRecycler(boolean firstTime)
    {

        if (firstTime) {


            positiveAppsLayout
                    = new LinearLayoutManager(
                    YourAppsActivity.this,
                    LinearLayoutManager.HORIZONTAL,
                    false);

            positiveAppsRecycler.setLayoutManager(positiveAppsLayout);
            positiveListAdapter = new appTypeListAdapter(positiveAppsList, true, YourAppsActivity.this);

            positiveAppsRecycler.setAdapter(positiveListAdapter);

        }

        else {

            positiveListAdapter.notifyDataSetChanged();


        }

        setPositiveAppCount(positiveAppsList.size());



    }

    private void setUpNegativeAppsRecycler(boolean firstTime)
    {


        if (firstTime) {


            negativeAppsLayout
                    = new LinearLayoutManager(
                    YourAppsActivity.this,
                    LinearLayoutManager.HORIZONTAL,
                    false);

            negativeAppsRecycler.setLayoutManager(negativeAppsLayout);
            negativeListAdapter = new appTypeListAdapter(negativeAppsList, false, YourAppsActivity.this);

            negativeAppsRecycler.setAdapter(negativeListAdapter);

        }

        else {
            negativeListAdapter.notifyDataSetChanged();

        }

        setNegativeAppCount(negativeAppsList.size());





    }

    private void setUpAddAppsRecycler()
    {

        /*getAppsOnPhone();




        addAppsLayout = new GridLayoutManager(getApplicationContext(), 3, LinearLayoutManager.VERTICAL, false);
        addAppsRecycler.setLayoutManager(addAppsLayout);

        addAppAdapter = new addAppAdapter(addAppsList, YourAppsActivity.this);
        addAppsRecycler.setAdapter(addAppAdapter);

         */

        AddAppsRecycler addTask = new AddAppsRecycler(YourAppsActivity.this);
        addTask.execute();







    }



    private void setPositiveEmptyAnimation(boolean start)
    {
        if (start)
        {
            emptyPositiveLayout.setVisibility(View.VISIBLE);
            emptyPositiveAnimation.setRepeatCount(0);
            emptyPositiveAnimation.setSpeed(1f);
            emptyPositiveAnimation.setRepeatMode(LottieDrawable.REVERSE);
            emptyPositiveAnimation.setRepeatCount(LottieDrawable.INFINITE);


            emptyPositiveAnimation.playAnimation();



        }

        else {
            emptyPositiveLayout.setVisibility(View.GONE);

            emptyPositiveAnimation.cancelAnimation();


        }

    }

    private void setNegativeEmptyAnimation(boolean start)
    {
        if (start)
        {
            emptyNegativeLayout.setVisibility(View.VISIBLE);
            emptyNegativeAnimation.setSpeed(1f);
            emptyNegativeAnimation.setRepeatCount(0);
            emptyNegativeAnimation.setRepeatMode(LottieDrawable.REVERSE);
            emptyNegativeAnimation.setRepeatCount(LottieDrawable.INFINITE);
            emptyNegativeAnimation.playAnimation();



        }

        else {
            emptyNegativeLayout.setVisibility(View.GONE);
            emptyNegativeAnimation.cancelAnimation();


        }


    }



    //Interfaces

    @Override
    public void onPositiveCallback(String packageName, boolean add) {

        if (add)
        {
            if (addPositiveAppsList.size()==0 && addNegativeAppsList.size()==0)
            {
                setBottomButton(true);

            }

            addPositiveAppsList.add(packageName);

            //Log.d("TAG", "onPositiveCallback: "+packageName+ "added");

        }
        else {
            for (int i=0;i <addPositiveAppsList.size(); i++)
            {
                if (addPositiveAppsList.get(i).equals(packageName))
                {
                    //Log.d("TAG", "onPositiveCallback: "+addPositiveAppsList.get(i)+" removed");

                    addPositiveAppsList.remove(i);
                    break;

                }

            }

            if (addPositiveAppsList.size()==0)
            {
                setBottomButton(false);

            }

        }

        //displayArrayList(addPositiveAppsList);


    }

    @Override
    public void onNegativeCallback(String packageName, boolean add){

        if (add)
        {
            if (addPositiveAppsList.size()==0 && addNegativeAppsList.size()==0)
            {
                setBottomButton(true);

            }

            addNegativeAppsList.add(packageName);
            //Log.d("TAG", "onNegativeCallback: "+packageName+" added");

        }
        else {
            for (int i=0;i <addNegativeAppsList.size(); i++)
            {
                if (addNegativeAppsList.get(i).equals(packageName))
                {
                    //Log.d("TAG", "onNegativeCallback: "+addNegativeAppsList.get(i)+" removed");
                    addNegativeAppsList.remove(i);
                    break;

                }

            }

            if (addNegativeAppsList.size()==0 && addPositiveAppsList.size()==0)
            {
                setBottomButton(false);

            }

        }

       // displayArrayList(addNegativeAppsList);




    }

    private void displayArrayList(ArrayList<String> list)
    {
        Log.d("TAG", "displayArrayList: items =  "+list.size());
        for (int i=0;i<list.size();i++)
        {
            Log.d("TAG", i+". "+list.get(i));

        }

    }

    private void setBottomButton(boolean show)
    {
        if (show)
        {
            addButtonLayout.setVisibility(View.VISIBLE);
            addButton.setClickable(true);

        }
        else {
            addButton.setClickable(false);

            addButtonLayout.setVisibility(View.GONE);

        }

    }

    private void removeFromAddAppsList(String packageName)
    {
        for (int i=0;i<addAppsList.size();i++)
        {
            if (addAppsList.get(i).getAppPackage().equals(packageName))
            {
                int index = i;

                addAppsList.remove(index);
                if (searching)
                {
                    for (int j=0;i<filteredList.size();i++)
                    {
                        if (filteredList.get(i).getAppPackage().equals(packageName))
                        {
                            index = j;
                            filteredList.remove(j);
                            break;

                        }

                    }

                }

                addAppAdapter.notifyItemRemoved(index);

                //addAppAdapter.notifyDataSetChanged();

                //Log.d("TAG", "removeFromAddAppsList: "+packageName+" removed");
                break;
            }


        }



    }

    private void addPositiveApps(boolean fromPref, int totalTime, ArrayList<Integer> minutesList)
    {
         final Context context=YourAppsActivity.this;

        boolean isFirstTime = positiveAppsList.size()==0;


        for (int i=0;i <addPositiveAppsList.size(); i++)
        {
            PackageManager packageManager= context.getPackageManager();
            Drawable icon;
            String appName;
            try {
                icon = packageManager.getApplicationIcon(addPositiveAppsList.get(i));
                appName = (String) packageManager.getApplicationLabel(packageManager.getApplicationInfo(addPositiveAppsList.get(i), PackageManager.GET_META_DATA));

                int minutes = minutesList.get(i);
                int percentage = (int) ((minutes*100)/totalTime);

                positiveAppsList.add(new appTypeListItem(appName, percentage, icon, addPositiveAppsList.get(i)));

                if (!fromPref) {
                    removeFromAddAppsList(addPositiveAppsList.get(i));
                }









            } catch (PackageManager.NameNotFoundException e) {
                Log.d("TAG", "doInBackground: entered Exception ");

                e.printStackTrace();
            }



        }

        if (!fromPref) {
            addAppAdapter.notifyDataSetChanged();
        }

        addPositiveAppsList.clear();

        if (isFirstTime || fromPref)
        {
            setPositiveEmptyAnimation(false);
            setUpPositiveAppsRecycler(true);


        }

        else {

            setUpPositiveAppsRecycler(false);

        }




    }

    private void addNegativeApps(boolean fromPref, int totalTime, ArrayList<Integer> minutesList)
    {
        final Context context = YourAppsActivity.this;

        boolean isFirstTime = negativeAppsList.size()==0;

        for (int i=0;i <addNegativeAppsList.size(); i++)
        {
            PackageManager packageManager= context.getPackageManager();
            Drawable icon;
            String appName;
            try {
                icon = packageManager.getApplicationIcon(addNegativeAppsList.get(i));
                appName = (String) packageManager.getApplicationLabel(packageManager.getApplicationInfo(addNegativeAppsList.get(i), PackageManager.GET_META_DATA));

                int minutes = minutesList.get(i);
                int percentage = (int) ((minutes*100)/totalTime);


                negativeAppsList.add(new appTypeListItem(appName, percentage, icon, addNegativeAppsList.get(i)));

                if (!fromPref) {
                    removeFromAddAppsList(addNegativeAppsList.get(i));
                }






            } catch (PackageManager.NameNotFoundException e) {
                Log.d("TAG", "doInBackground: entered Exception ");
                e.printStackTrace();
            }



        }


        if (!fromPref) {
            addAppAdapter.notifyDataSetChanged();
        }

        addNegativeAppsList.clear();



        if (isFirstTime || fromPref)
        {
            setNegativeEmptyAnimation(false);
            setUpNegativeAppsRecycler(true);


        }

        else {

            setUpNegativeAppsRecycler(false);

        }

    }

    private void arrangeAddAppsInAlphabeticalOrder()
    {

        Collections.sort(addAppsList, new Comparator<addAppItem>() {
            @Override
            public int compare(addAppItem i1, addAppItem i2) {
                return i1.getAppName().compareToIgnoreCase(i2.getAppName());
            }
        });





    }
    private boolean containsElement(ArrayList<AppSharedPreferencesItem> searchList, String packageName)
    {
        for(int i=0;i<searchList.size();i++)
        {
            if (searchList.get(i).getPackageName().equals(packageName))
            {
                return true;

            }

        }

        return false;

    }

    private List<addAppItem> searchForApp(String search)
    {
        ArrayList<addAppItem> filteredList = new ArrayList<>();

        int midIndex = addAppsList.size()/2;
        String midItem = addAppsList.get(midIndex).getAppName();

        if (midItem.equalsIgnoreCase(search))
        {
            filteredList.add(addAppsList.get(midIndex));
            return filteredList;


        }

        else {
            if (search.compareToIgnoreCase(midItem) > 0)
            {
                for (int i=midIndex;i<addAppsList.size();i++)
                {
                    if (addAppsList.get(i).getAppName().toLowerCase().startsWith(search.toLowerCase()))
                    {
                        filteredList.add(addAppsList.get(i));

                    }

                }


            }

            else if (search.compareToIgnoreCase(midItem)<0)
            {
                for (int i=0;i<=midIndex;i++)
                {
                    if (addAppsList.get(i).getAppName().toLowerCase().startsWith(search.toLowerCase()))
                    {
                        filteredList.add(addAppsList.get(i));

                    }

                }

            }

        }

        return filteredList;

    }

    private void setPositiveAppCount(int count)
    {
        positiveAppCountText.setText("("+count+")");

    }

    private void setNegativeAppCount(int count)
    {
        negativeAppCountText.setText("("+count+")");

    }






    private class AddAppsRecycler extends AsyncTask<Void, Integer, Void> {

        private final WeakReference<Activity> weakActivity;

        AddAppsRecycler(Activity myActivity) {
            this.weakActivity = new WeakReference<>(myActivity);
        }


        protected Void doInBackground(Void... voids) {
        // code that will run in the background

            Activity activity = weakActivity.get();
            if (!(activity == null || activity.isFinishing() || activity.isDestroyed()))
            {
                appList = AppInfoRetriever.getListOfApps(activity);
                addAppsList = new ArrayList<>(appList.size());
                addAppAdapter = new addAppAdapter(addAppsList, activity);

                runOnUiThread(new Runnable() {
                    public void run() {

                        addAppsLayout = new GridLayoutManager(getApplicationContext(), 3, LinearLayoutManager.VERTICAL, false);
                        addAppsRecycler.setLayoutManager(addAppsLayout);

                        addAppAdapter = new addAppAdapter(addAppsList, activity);
                        addAppsRecycler.setAdapter(addAppAdapter);

                    }
                });




                boolean found=false;


                for (int i=0;i<appList.size();i++)
                {
                    if (storedNegativeList.size()>0 || storedPositiveList.size()>0)
                    {
                        if (containsElement(storedNegativeList, appList.get(i).getAppPackage()) || containsElement(storedPositiveList, appList.get(i).getAppPackage()))
                        {
                            found = true;

                        }

                    }

                    if (!found) {
                        addAppsList.add(new addAppItem(appList.get(i).getAppName(), appList.get(i).getAppIcon(), false, "none", appList.get(i).getAppPackage()));

                    }
                    found = false;
                    //publishProgress(i);

                }

                arrangeAddAppsInAlphabeticalOrder();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        yourAppsLoadingAnim.setVisibility(View.GONE);
                        yourAppsLoadingAnim.cancelAnimation();

                    }
                });



            }



            return null;
        }

        protected void onProgressUpdate(Integer... progress) {
        // receive progress updates from doInBackground

            //addAppAdapter.notifyItemChanged(progress[0]);


        }

        protected void onPostExecute() {
        // update the UI after background processes completes
            addAppAdapter.notifyDataSetChanged();


        }
    }










}

