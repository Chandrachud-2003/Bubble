package com.chandrachud.bubble.Adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.chandrachud.bubble.Items.timelineItem;
import com.chandrachud.bubble.R;

import java.util.List;

public class timelineAdapter extends RecyclerView.Adapter<timelineAdapter.MyView> {

    // List with String type
    private List<timelineItem> list;

    private View mView;


    // View Holder class which
    // extends RecyclerView.ViewHolder
    public class MyView
            extends RecyclerView.ViewHolder {


        ImageView changeImage;
        TextView startTime;
        TextView appName;
        TextView hoursTime;
        TextView hoursText;
        TextView minutesTime;
        TextView minutesText;
        TextView appChangePercent;
        ImageView appIcon;
        ProgressBar timelineBar;





        public MyView(View view)
        {
            super(view);

            ViewGroup viewGroup;
            changeImage = view.findViewById(R.id.typeImageTimeline);
            startTime = view.findViewById(R.id.timelineTimeStart);
            appName = view.findViewById(R.id.timelineAppName);
            appChangePercent = view.findViewById(R.id.changePercentTimeLine);
            appIcon = view.findViewById(R.id.timelineAppIcon);
            timelineBar = view.findViewById(R.id.timelineProgressBar);
            hoursText = view.findViewById(R.id.timelineHoursText);
            hoursTime = view.findViewById(R.id.timelineHoursTime);
            minutesText = view.findViewById(R.id.timelineMinutesText);
            minutesTime = view.findViewById(R.id.timelineMinutes);




        }
    }

    // Constructor for adapter class
    // which takes a list of String type
    public timelineAdapter(List<timelineItem> list)
    {
        this.list = list;
    }

    // Override onCreateViewHolder which deals
    // with the inflation of the card layout
    // as an item for the RecyclerView.
    @Override
    public timelineAdapter.MyView onCreateViewHolder(ViewGroup parent,
                                                        int viewType)
    {

        // Inflate item.xml using LayoutInflator
        View itemView
                = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_timeline,
                        parent,
                        false);

        // return itemView
        return new timelineAdapter.MyView(itemView);
    }

    // Override onBindViewHolder which deals
    // with the setting of different data
    // and methods related to clicks on
    // particular items of the RecyclerView.
    @Override
    public void onBindViewHolder(final timelineAdapter.MyView holder,
                                 final int position)
    {

       holder.appName.setText(list.get(position).getAppName());
       holder.startTime.setText(list.get(position).getStartTime());
       holder.appIcon.setImageResource(list.get(position).getAppIcon());

       int change = list.get(position).getChangePercent();

       int minutes = list.get(position).getMinutes();
       int hours = list.get(position).getHours();

       if (hours>0)
       {
           holder.hoursTime.setText(String.valueOf(hours));


       }

       else {

           holder.hoursTime.setVisibility(View.GONE);
           holder.hoursText.setVisibility(View.GONE);

       }

        if (minutes>0)
        {
            holder.minutesTime.setText(String.valueOf(minutes));


        }

        else {

            holder.minutesTime.setVisibility(View.GONE);
            holder.minutesText.setVisibility(View.GONE);

        }






        if (list.get(position).getType().equals("positive"))
       {
           holder.changeImage.setImageResource(R.drawable.icons8_increase);

           holder.appChangePercent.setText("+ "+change+"%");
           holder.appChangePercent.setTextColor(Color.parseColor("#2ECC71"));




       }

       else if (list.get(position).getType().equals("negative"))
       {
           holder.changeImage.setImageResource(R.drawable.icons8_decrease);


           holder.appChangePercent.setText("- "+change+"%");
           holder.appChangePercent.setTextColor(Color.parseColor("#E74C3C"));





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
