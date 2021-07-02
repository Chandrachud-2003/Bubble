package com.chandrachud.bubble.Items;

public class goalDateItem {

    private String date;
    private String heading;
    private boolean premium;
    private boolean selected;

    public goalDateItem(String date, String heading, boolean premium, boolean selected)
    {
        this.date = date;
        this.heading = heading;
        this.premium = premium;
        this.selected = selected;

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public boolean isPremium() {
        return premium;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
