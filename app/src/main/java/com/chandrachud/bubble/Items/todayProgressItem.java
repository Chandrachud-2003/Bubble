package com.chandrachud.bubble.Items;

public class todayProgressItem {

    private boolean type;
    private String name;
    private int icon;
    private float completed;
    private float total;
    private String deadline;
    private String unit;

    public todayProgressItem(boolean type, String name, int icon, float completed, float total, String deadline, String unit) {
        this.type = type;
        this.name = name;
        this.icon = icon;
        this.completed = completed;
        this.total = total;
        this.deadline = deadline;
        this.unit = unit;
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

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
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

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
