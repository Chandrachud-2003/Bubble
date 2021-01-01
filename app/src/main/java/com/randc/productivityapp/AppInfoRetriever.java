package com.randc.productivityapp;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;

public class AppInfoRetriever {

    public static ArrayList<AppInfo> getListOfApps(Context context)
    {
        ArrayList<AppInfo> appList = new ArrayList<AppInfo>();
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> packageList = pm.getInstalledPackages(0);

        for (int i=0;i<packageList.size();i++)
        {
            PackageInfo packageInfo = packageList.get(i);

            if(pm.getLaunchIntentForPackage(packageInfo.packageName) != null) {


                String appName = packageInfo.applicationInfo.loadLabel(pm).toString();
                //Log.d(tag,"App Name: "+appName);
                String packageName = packageInfo.packageName;
                //Log.d(tag, "Package Name: "+packageName);
                Drawable icon = packageInfo.applicationInfo.loadIcon(context.getPackageManager());


                appList.add(new AppInfo(icon, appName, packageName));
            }



        }

        return  appList;


    }




}
