package com.chandrachud.bubble.Items;

public class goalPreviewItem {

    private boolean type;
    private String name;
    private int progress;
    private String time;
    private int percent;
    private int icon;

    public goalPreviewItem(boolean type, String name, int progress, String time, int percent, int icon) {
        this.type = type;
        this.name = name;
        this.progress = progress;
        this.time = time;
        this.percent = percent;
        this.icon = icon;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
