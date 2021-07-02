package com.chandrachud.bubble.Items;

public class goalBubbleWalletItem {

    private int icon;
    private String name;
    private String time;
    private boolean type;
    private boolean custom;
    private int points;
    private boolean complete;

    public goalBubbleWalletItem(int icon, String name, String time, boolean type, boolean complete, boolean custom, int points) {
        this.icon = icon;
        this.name = name;
        this.time = time;
        this.type = type;
        this.complete = complete;
        this.custom = custom;
        this.points = points;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public boolean isCustom() {
        return custom;
    }

    public void setCustom(boolean custom) {
        this.custom = custom;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }
}
