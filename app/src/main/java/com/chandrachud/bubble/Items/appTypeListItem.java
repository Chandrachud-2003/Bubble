package com.chandrachud.bubble.Items;

import android.graphics.drawable.Drawable;

public class appTypeListItem {

    private String appName;
    private int bubblePercent;
    private Drawable appIcon;
    private String packageName;

    public appTypeListItem(String appName, int bubblePercent, Drawable appIcon, String packageName) {
        this.appName = appName;
        this.bubblePercent = bubblePercent;
        this.appIcon = appIcon;
        this.packageName = packageName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public int getBubblePercent() {
        return bubblePercent;
    }

    public void setBubblePercent(int bubblePercent) {
        this.bubblePercent = bubblePercent;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}
