package com.chandrachud.bubble.Items;

public class timelineItem {

    private String appName;
    private String type;
    private int changePercent;
    private int minutes;
    private int hours;
    private String startTime;
    private int appIcon;

    public timelineItem(String appName, String type, int changePercent, int minutes, int hours, String startTime, int appIcon) {
        this.appName = appName;
        this.type = type;
        this.changePercent = changePercent;
        this.minutes = minutes;
        this.hours = hours;
        this.startTime = startTime;
        this.appIcon = appIcon;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getChangePercent() {
        return changePercent;
    }

    public void setChangePercent(int changePercent) {
        this.changePercent = changePercent;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public int getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(int appIcon) {
        this.appIcon = appIcon;
    }
}
