package com.chandrachud.bubble.Items;

public class customGoalListItem {

    private int icon;
    private boolean type;
    private String name;
    private float completed;
    private float total;
    private String unit;

    public customGoalListItem(int icon, boolean type, String name, float completed, float total, String unit) {
        this.icon = icon;
        this.type = type;
        this.name = name;
        this.completed = completed;
        this.total = total;
        this.unit = unit;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
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

    public float getCompleted() {
        return completed;
    }

    public void setCompleted(float completed) {
        this.completed = completed;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
