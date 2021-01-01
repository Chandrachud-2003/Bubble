package com.randc.productivityapp.Items;

public class goalMainItem {

    private boolean type;
    private String appName;
    private int appIcon;
    private String goalLimit;
    private String completedTime;
    private String totalTime;
    private int percentage;

    public goalMainItem(boolean type, String appName, int appIcon, String goalLimit, String completedTime, String totalTime, int percentage) {
        this.type = type;
        this.appName = appName;
        this.appIcon = appIcon;
        this.goalLimit = goalLimit;
        this.completedTime = completedTime;
        this.totalTime = totalTime;
        this.percentage = percentage;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public int getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(int appIcon) {
        this.appIcon = appIcon;
    }

    public String getGoalLimit() {
        return goalLimit;
    }

    public void setGoalLimit(String goalLimit) {
        this.goalLimit = goalLimit;
    }

    public String getCompletedTime() {
        return completedTime;
    }

    public void setCompletedTime(String completedTime) {
        this.completedTime = completedTime;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }
}
