package com.randc.productivityapp.Items;

public class yourBubblePreviewItem {

    private String name;
    private String time;
    private float percentage;
    private int resId;

    public yourBubblePreviewItem(String name, String time, float percentage, int resId) {
        this.name = name;
        this.time = time;
        this.percentage = percentage;
        this.resId = resId;
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

    public float getPercentage() {
        return percentage;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }
}
