package com.chandrachud.bubble.Items;

public class customTaskItem {

    private int image;
    private String name;
    private int duration;

    public customTaskItem(int image, String name, int duration) {
        this.image = image;
        this.name = name;
        this.duration = duration;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
