package com.randc.productivityapp.Items;

public class ongoingGoalItem {

    private String appName;
    private int appIcon;
    private String goalTime;
    private String goalLeftTime;
    private boolean goalType;

    public ongoingGoalItem(String appName, int appIcon, String goalTime, String goalLeftTime, boolean goalType)
    {
        this.appName = appName;
        this.appIcon = appIcon;
        this.goalTime = goalTime;
        this.goalLeftTime = goalLeftTime;
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

    public String getGoalTime() {
        return goalTime;
    }

    public void setGoalTime(String goalTime) {
        this.goalTime = goalTime;
    }

    public String getGoalLeftTime() {
        return goalLeftTime;
    }

    public void setGoalLeftTime(String goalLeftTime) {
        this.goalLeftTime = goalLeftTime;
    }

    public boolean isGoalType() {
        return goalType;
    }

    public void setGoalType(boolean goalType) {
        this.goalType = goalType;
    }
}
