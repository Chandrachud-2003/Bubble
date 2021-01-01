package com.randc.productivityapp.Adapters;

import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.gelitenight.waveview.library.WaveView;
import com.randc.productivityapp.HelperClasses.WaveHelper;
import com.randc.productivityapp.Items.appUsageItem;
import com.randc.productivityapp.Items.goalDateItem;
import com.randc.productivityapp.R;

import java.util.List;
import java.util.logging.Logger;

import de.hdodenhof.circleimageview.CircleImageView;

public class goalDateAdapter extends RecyclerView.Adapter<goalDateAdapter.MyView> {


    // List with String type
    private List<goalDateItem> list;

    private View mView;

    private int selected;

    // View Holder class which
    // extends RecyclerView.ViewHolder
    public class MyView
            extends RecyclerView.ViewHolder {

        // Text View
        TextView headingText;
        TextView dateText;
        ImageView premiumImage;
        LinearLayout itemLayout;


        // parameterised constructor for View Holder class
        // which takes the view as a parameter
        public MyView(View view)
        {
            super(view);

            headingText = view.findViewById(R.id.goalDateHeading);
            dateText = view.findViewById(R.id.goalDateRange);
            premiumImage = view.findViewById(R.id.goalDatePremium);
            itemLayout = view.findViewById(R.id.goalDateLayoutItem);



        }
    }

    // Constructor for adapter class
    // which takes a list of String type
    public goalDateAdapter(List<goalDateItem> list)
    {
        this.list = list;
        selected = 1;
    }

    // Override onCreateViewHolder which deals
    // with the inflation of the card layout
    // as an item for the RecyclerView.
    @Override
    public goalDateAdapter.MyView onCreateViewHolder(ViewGroup parent,
                                                     int viewType)
    {

        // Inflate item.xml using LayoutInflator
        View itemView
                = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.goal_time_list_item,
                        parent,
                        false);

        // return itemView
        return new goalDateAdapter.MyView(itemView);
    }

    // Override onBindViewHolder which deals
    // with the setting of different data
    // and methods related to clicks on
    // particular items of the RecyclerView.
    @Override
    public void onBindViewHolder(final goalDateAdapter.MyView holder,
                                 final int position)
    {

        holder.dateText.setText(list.get(position).getDate());
        holder.headingText.setText(list.get(position).getHeading());
        if (list.get(position).isPremium())
        {
            holder.premiumImage.setVisibility(View.VISIBLE);
            holder.premiumImage.setImageResource(R.drawable.icons8_crown2);

        }
        else {
            holder.premiumImage.setVisibility(View.GONE);

        }

        if (list.get(position).isSelected())
        {
            holder.itemView.setBackgroundResource(R.drawable.goal_timer_item2_bg);
            holder.headingText.setTextColor(Color.WHITE);
            holder.dateText.setTypeface(holder.dateText.getTypeface(), Typeface.BOLD);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams)holder.itemView.getLayoutParams();
            params.setMargins(10, 30, 10, 10);

            holder.itemView.setLayoutParams(params);




        }
        else {
            holder.itemView.setBackgroundResource(R.drawable.goals_date_bg);
            holder.headingText.setTextColor(Color.parseColor("#A9A9A9"));
            holder.dateText.setTypeface(holder.dateText.getTypeface(), Typeface.NORMAL);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams)holder.itemView.getLayoutParams();
            params.setMargins(10, 10, 10, 10);

            holder.itemView.setLayoutParams(params);


        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!(list.get(position).isSelected()))
                {

                    list.get(position).setSelected(true);
                    list.get(selected).setSelected(false);


                    notifyItemChanged(position);

                    notifyItemChanged(selected);
                    selected = position;

                }
            }
        });

    }

    // Override getItemCount which Returns
    // the length of the RecyclerView.
    @Override
    public int getItemCount()
    {
        return list.size();
    }


}
