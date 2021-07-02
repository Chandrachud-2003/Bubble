package com.chandrachud.bubble.Items;

public class AppSharedPreferencesItem {

    private String packageName;
    private int openedTimes;
    private boolean type;
    private int totalMinutesToday;

    public AppSharedPreferencesItem(String packageName, int openedTimes, boolean type, int totalMinutesToday) {
        this.packageName = packageName;
        this.openedTimes = openedTimes;
        this.type = type;
        this.totalMinutesToday = totalMinutesToday;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public int getOpenedTimes() {
        return openedTimes;
    }

    public void setOpenedTimes(int openedTimes) {
        this.openedTimes = openedTimes;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public int getTotalMinutesToday() {
        return totalMinutesToday;
    }

    public void setTotalMinutesToday(int totalMinutesToday) {
        this.totalMinutesToday = totalMinutesToday;
    }
}
