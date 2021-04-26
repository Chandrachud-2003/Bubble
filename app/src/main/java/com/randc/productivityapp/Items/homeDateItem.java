package com.randc.productivityapp.Items;

public class homeDateItem {

    private String date;
    private String day;
    private boolean selected;

    public homeDateItem(String date, String day,
                        boolean selected){
        this.date = date;
        this.day = day;
        this.selected = selected;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
