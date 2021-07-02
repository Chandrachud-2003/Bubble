package com.chandrachud.bubble.Items;

import java.util.ArrayList;

public class SmooliderItem {


    private ArrayList<ArrayList<timelineItem>> smooliderList;


    public SmooliderItem(ArrayList<ArrayList<timelineItem>> smooliderList) {
        this.smooliderList = smooliderList;
    }

    public ArrayList<ArrayList<timelineItem>> getSmooliderList() {
        return smooliderList;
    }

    public void setSmooliderList(ArrayList<ArrayList<timelineItem>> smooliderList) {
        this.smooliderList = smooliderList;
    }
}
