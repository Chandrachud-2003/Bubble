package com.randc.productivityapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.randc.productivityapp.Items.customTaskItem;
import com.randc.productivityapp.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class customTaskViewpagerAdapter extends RecyclerView.Adapter<customTaskViewpagerAdapter.ViewHolder> {

    List<customTaskItem> list;

    public customTaskViewpagerAdapter(List<customTaskItem> list){
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_task_viewpager_item, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String end="AM";
        holder.image.setImageResource(list.get(position).getImage());
        holder.name.setText(list.get(position).getName());
        int duration = list.get(position).getDuration();
        long time= System.currentTimeMillis();
        long minutes = (time / 1000) / 60;
        int rem = (int) minutes%5;
        if (rem!=0){
            time-=rem * 60 * 1000;
            time+=5 * 60 * 1000;
        }
        DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
        String currentTime = dateFormat.format(new Date(time));
        String newTime = dateFormat.format(new Date(time+30 * 60 * 1000));
        String resultTime = currentTime+" - "+newTime;
        holder.time.setText(resultTime);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView name;
        TextView time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.customTaskViewpagerImage);
            name = itemView.findViewById(R.id.customTaskViewpagerName);
            time = itemView.findViewById(R.id.customTaskViewpagerTime);

        }
    }




}
