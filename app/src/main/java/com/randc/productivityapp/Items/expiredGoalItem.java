package com.randc.productivityapp.Items;

public class expiredGoalItem {

    private String appName;
    private int appIcon;
    private String completed;
    private String goalTime;
    private boolean goalType;

    public expiredGoalItem(String appName, int appIcon, String completed, String goalTime, boolean goalType)
    {
        this.appName = appName;
        this.appIcon = appIcon;
        this.completed = completed;
        this.goalTime = goalTime;
        this.goalType = goalType;

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

    public String getCompleted() {
        return completed;
    }

    public void setCompleted(String completed) {
        this.completed = completed;
    }

    public String getGoalTime() {
        return goalTime;
    }

    public void setGoalTime(String goalTime) {
        this.goalTime = goalTime;
    }

    public boolean isGoalType() {
        return goalType;
    }

    public void setGoalType(boolean goalType) {
        this.goalType = goalType;
    }
}
