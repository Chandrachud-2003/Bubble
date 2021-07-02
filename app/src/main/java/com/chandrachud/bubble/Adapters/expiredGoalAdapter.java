package com.chandrachud.bubble.Adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.chandrachud.bubble.Items.expiredGoalItem;
import com.chandrachud.bubble.R;

import java.util.List;

public class expiredGoalAdapter extends RecyclerView.Adapter<expiredGoalAdapter.MyView> {

    // List with String type
    private List<expiredGoalItem> list;

    private View mView;


    // View Holder class which
    // extends RecyclerView.ViewHolder
    public class MyView
            extends RecyclerView.ViewHolder {


        ImageView expiredIcon;
        ImageView expiredCompletedImage;
        TextView expiredAppName;
        TextView expiredAppName2;
        TextView expiredCompletedText;
        TextView expiredGoalTime;
        TextView expiredGoalType;




        public MyView(View view)
        {
            super(view);

            expiredAppName = view.findViewById(R.id.expiredAppName);
            expiredAppName2 = view.findViewById(R.id.expiredAppName2);
            expiredIcon = view.findViewById(R.id.expiredAppIcon);
            expiredCompletedImage = view.findViewById(R.id.expiredStatusImage);
            expiredCompletedText = view.findViewById(R.id.completedStatusText);
            expiredGoalTime = view.findViewById(R.id.expiredGoalTimeText);
            expiredGoalType = view.findViewById(R.id.expiredCompletedText);


        }
    }

    // Constructor for adapter class
    // which takes a list of String type
    public expiredGoalAdapter(List<expiredGoalItem> list)
    {
        this.list = list;
    }

    // Override onCreateViewHolder which deals
    // with the inflation of the card layout
    // as an item for the RecyclerView.
    @Override
    public expiredGoalAdapter.MyView onCreateViewHolder(ViewGroup parent,
                                                        int viewType)
    {

        // Inflate item.xml using LayoutInflator
        View itemView
                = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.expired_goals_item,
                        parent,
                        false);

        // return itemView
        return new expiredGoalAdapter.MyView(itemView);
    }

    // Override onBindViewHolder which deals
    // with the setting of different data
    // and methods related to clicks on
    // particular items of the RecyclerView.
    @Override
    public void onBindViewHolder(final expiredGoalAdapter.MyView holder,
                                 final int position)
    {
        holder.expiredGoalTime.setText(list.get(position).getGoalTime());
        holder.expiredIcon.setImageResource(list.get(position).getAppIcon());

        String name = list.get(position).getAppName();
        name = name.trim();
        if (name.contains(" "))
        {
            holder.expiredAppName2.setVisibility(View.VISIBLE);
            holder.expiredAppName.setText(name.substring(0, name.indexOf(" ")));
            holder.expiredAppName2.setText(name.substring(name.indexOf(" ")+1));
        }

        else {

            holder.expiredAppName2.setVisibility(View.GONE);
            holder.expiredAppName.setText(name);
        }

        if (list.get(position).isGoalType())
        {
            holder.expiredGoalType.setText("Usage Goal");
            holder.expiredGoalType.setTextColor(Color.parseColor("#21e8c7"));


        }
        else {
            holder.expiredGoalType.setText("Usage Limit");
            holder.expiredGoalType.setTextColor(Color.parseColor("#f03145"));

        }

        String goalStatus = list.get(position).getCompleted();
        if (goalStatus.equalsIgnoreCase("yes"))
        {
            holder.expiredCompletedText.setText("Completed");
            holder.expiredCompletedText.setTextColor(Color.parseColor("#21e8c7"));
            holder.expiredCompletedImage.setImageResource(R.drawable.icons8_checkmark);

        }

        else if (goalStatus.equalsIgnoreCase("mid"))
        {
            holder.expiredCompletedText.setText("Expired");
            holder.expiredCompletedText.setTextColor(Color.parseColor("#F1C40F"));
            holder.expiredCompletedImage.setImageResource(R.drawable.icons8_expired);

        }

        else if (goalStatus.equalsIgnoreCase("no"))
        {
            holder.expiredCompletedText.setText("Failed");
            holder.expiredCompletedText.setTextColor(Color.parseColor("#f03145"));
            holder.expiredCompletedImage.setImageResource(R.drawable.icons8_cancel);

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
