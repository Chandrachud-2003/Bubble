package com.chandrachud.bubble;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.chandrachud.bubble.Items.AppSharedPreferencesItem;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class SPFunctions {

    private  SharedPreferences mSharedPreferences;
    private  SharedPreferences.Editor mEditor;
    private Gson mGson;
    private String json;

    SPFunctions(Context context)
    {
        mSharedPreferences = context.getSharedPreferences(Constants.bubbleAppPrefKey, MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        mGson = new Gson();

    }

    private  int getIntValue(String pref)
    {
        return mSharedPreferences.getInt(pref,0);

    }

    private void putIntValue(String pref, int value)
    {
        mEditor.putInt(pref, value).commit();
    }

    private ArrayList<AppSharedPreferencesItem> getAppSharedPrefsArrayList(String pref)
    {
        json = mSharedPreferences.getString(pref, "");

        ArrayList<AppSharedPreferencesItem> arrayList = new ArrayList<>();
        if (!(json.equals("")))
        {
            Type type = new TypeToken<List<AppSharedPreferencesItem>>() {}.getType();
            arrayList = mGson.fromJson(json,type);
        }

        return  arrayList;

    }

    private void storeAppSharedPrefsArrayList(String pref, ArrayList<AppSharedPreferencesItem> arrayList)
    {
        json = mGson.toJson(arrayList);
        mEditor.putString(pref, json).commit();

    }

}
