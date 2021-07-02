package com.chandrachud.bubble.Adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.chandrachud.bubble.Items.ongoingGoalItem;
import com.chandrachud.bubble.R;

import java.util.List;

public class ongoingGoalAdapter extends RecyclerView.Adapter<ongoingGoalAdapter.MyView> {

    // List with String type
    private List<ongoingGoalItem> list;

    private View mView;


    // View Holder class which
    // extends RecyclerView.ViewHolder
    public class MyView
            extends RecyclerView.ViewHolder {


        ImageView ongoingIcon;
        TextView ongoingName;
        TextView ongoingTextType;
        TextView ongoingTime;
        TextView ongoingTimeLeft;
        TextView ongoingName2;




        public MyView(View view)
        {
            super(view);

            ongoingIcon = view.findViewById(R.id.ongoingAppIcon);
            ongoingName = view.findViewById(R.id.ongoingAppName);
            ongoingTextType = view.findViewById(R.id.ongoingAppUsageType);
            ongoingTime = view.findViewById(R.id.ongoingTimeText);
            ongoingTimeLeft = view.findViewById(R.id.ongoingLeftText);
            ongoingName2 = view.findViewById(R.id.ongoingAppName2);





        }
    }

    // Constructor for adapter class
    // which takes a list of String type
    public ongoingGoalAdapter(List<ongoingGoalItem> list)
    {
        this.list = list;
    }

    // Override onCreateViewHolder which deals
    // with the inflation of the card layout
    // as an item for the RecyclerView.
    @Override
    public ongoingGoalAdapter.MyView onCreateViewHolder(ViewGroup parent,
                                                     int viewType)
    {

        // Inflate item.xml using LayoutInflator
        View itemView
                = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.ongoing_goal_list_item,
                        parent,
                        false);

        // return itemView
        return new ongoingGoalAdapter.MyView(itemView);
    }

    // Override onBindViewHolder which deals
    // with the setting of different data
    // and methods related to clicks on
    // particular items of the RecyclerView.
    @Override
    public void onBindViewHolder(final ongoingGoalAdapter.MyView holder,
                                 final int position)
    {

        holder.ongoingIcon.setImageResource(list.get(position).getAppIcon());

        holder.ongoingTime.setText(list.get(position).getGoalTime());

        String name = list.get(position).getAppName();
        name=name.trim();
        if (name.contains(" "))
        {
            holder.ongoingName.setText(name.substring(0, name.indexOf(" ")));
            holder.ongoingName2.setVisibility(View.VISIBLE);
            holder.ongoingName2.setText(name.substring(name.indexOf(" ")+1));
            holder.ongoingTimeLeft.setText(list.get(position).getGoalLeftTime()+" done");


        }

        else {
            holder.ongoingName2.setVisibility(View.INVISIBLE);
            holder.ongoingName.setText(name);
            holder.ongoingTimeLeft.setText(list.get(position).getGoalLeftTime()+" left");



        }

        if (list.get(position).isGoalType())
        {
            holder.ongoingTextType.setText("Usage Goal");
            holder.ongoingTextType.setTextColor(Color.parseColor("#21e8c7"));
            holder.ongoingTimeLeft.setTextColor(Color.parseColor("#21e8c7"));


        }
        else {

            holder.ongoingTextType.setText("Limit Usage");
            holder.ongoingTextType.setTextColor(Color.parseColor("#f03145"));
            holder.ongoingTimeLeft.setTextColor(Color.parseColor("#f03145"));


        }

    }

    // Override getItemCount which Returns
    // the length of the RecyclerView.
    @Override
    public int getItemCount()
    {
        return list.size();
    }



}
