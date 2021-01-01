package com.randc.productivityapp.Items;

public class appUsageItem {

    private String appName;
    private String appUsage;
    private String changeInUsage;
    private String sinceName;
    private int appIcon;
    private boolean waveType;

    public appUsageItem(String appName, String appUsage, String changeInUsage, String sinceName, int appIcon, boolean waveType) {
        this.appName = appName;
        this.appUsage = appUsage;
        this.changeInUsage = changeInUsage;
        this.sinceName = sinceName;
        this.appIcon = appIcon;
        this.waveType = waveType;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppUsage() {
        return appUsage;
    }

    public void setAppUsage(String appUsage) {
        this.appUsage = appUsage;
    }

    public String getChangeInUsage() {
        return changeInUsage;
    }

    public void setChangeInUsage(String changeInUsage) {
        this.changeInUsage = changeInUsage;
    }

    public String getSinceName() {
        return sinceName;
    }

    public void setSinceName(String sinceName) {
        this.sinceName = sinceName;
    }

    public int getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(int appIcon) {
        this.appIcon = appIcon;
    }

    public boolean isWaveType() {
        return waveType;
    }

    public void setWaveType(boolean waveType) {
        this.waveType = waveType;
    }
}
