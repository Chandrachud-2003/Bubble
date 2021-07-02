package com.chandrachud.bubble.Items;

public class createGoalItem {

    private int image;
    private String text;
    private boolean selected;

    public createGoalItem(int image, String text, boolean selected) {
        this.image = image;
        this.text = text;
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
