package com.randc.productivityapp.Items;

import android.graphics.drawable.Drawable;

public class addAppItem {

    private String appName;
    private Drawable appIcon;
    private boolean selected;
    private String type;
    private String appPackage;

    public addAppItem(String appName, Drawable appIcon, boolean selected, String type, String appPackage) {
        this.appName = appName;
        this.appIcon = appIcon;
        this.selected = selected;
        this.type = type;
        this.appPackage = appPackage;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAppPackage() {
        return appPackage;
    }

    public void setAppPackage(String appPackage) {
        this.appPackage = appPackage;
    }
}
