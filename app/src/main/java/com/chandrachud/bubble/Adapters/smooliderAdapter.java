package com.chandrachud.bubble.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.chandrachud.bubble.Items.timelineItem;
import com.chandrachud.bubble.R;

import java.util.ArrayList;

public class smooliderAdapter extends PagerAdapter {


    private Context mContext;
    private ArrayList<ArrayList<timelineItem>> itemList;

    public smooliderAdapter(ArrayList<ArrayList<timelineItem>> itemList, Context mContext) {
            this.mContext = mContext;
            this.itemList = itemList;
        }

        @Override
        public int getCount() {
            return itemList.size();
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            ArrayList<timelineItem> item = itemList.get(position);

            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.smoolider_layout, container, false);

            RecyclerView timelineRecycler = view.findViewById(R.id.timelineRecycler);

            timelineAdapter timelineAdapter = new timelineAdapter(item);

            GridLayoutManager timelineGrid = new GridLayoutManager(mContext, 2, LinearLayoutManager.VERTICAL, false);

            timelineRecycler.setLayoutManager(timelineGrid);
            timelineRecycler.setAdapter(timelineAdapter);










            container.addView(view);
            return view;
        }


        @Override
        public void destroyItem (ViewGroup container,int position, Object object){
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject (View view, Object object){
            return view.equals(object);
        }
    }


