package com.chandrachud.bubble.Adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.gelitenight.waveview.library.WaveView;
import com.chandrachud.bubble.Items.appUsageItem;
import com.chandrachud.bubble.R;
import com.chandrachud.bubble.HelperClasses.WaveHelper;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class appUsageAdapter
        extends RecyclerView.Adapter<appUsageAdapter.MyView> {

    // List with String type
    private List<appUsageItem> list;

    private View mView;

    // View Holder class which
    // extends RecyclerView.ViewHolder
    public class MyView
            extends RecyclerView.ViewHolder {

        // Text View
        TextView appName;
        TextView appUsage;
        TextView changeText;
        CircleImageView appIcon;
        TextView sinceText;
        WaveView distributionWave;
        LinearLayout distributionItemLayout;

        // parameterised constructor for View Holder class
        // which takes the view as a parameter
        public MyView(View view)
        {
            super(view);

            appName = view.findViewById(R.id.appNameText);
            appUsage = view.findViewById(R.id.appUsageText);
            changeText = view.findViewById(R.id.changeUsageText);
            appIcon = view.findViewById(R.id.app_image);
            sinceText = view.findViewById(R.id.sinceUsageText);
            distributionWave = view.findViewById(R.id.distributionWave);
            distributionItemLayout = view.findViewById(R.id.relativeDistributionItem);



        }
    }

    // Constructor for adapter class
    // which takes a list of String type
    public appUsageAdapter(List<appUsageItem> horizontalList)
    {
        this.list = horizontalList;
    }

    // Override onCreateViewHolder which deals
    // with the inflation of the card layout
    // as an item for the RecyclerView.
    @Override
    public MyView onCreateViewHolder(ViewGroup parent,
                                     int viewType)
    {

        // Inflate item.xml using LayoutInflator
        View itemView
                = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.distribution_item,
                        parent,
                        false);

        // return itemView
        return new MyView(itemView);
    }

    // Override onBindViewHolder which deals
    // with the setting of different data
    // and methods related to clicks on
    // particular items of the RecyclerView.
    @Override
    public void onBindViewHolder(final MyView holder,
                                 final int position)
    {

        holder.appName.setText(list.get(position).getAppName());
        holder.appUsage.setText(list.get(position).getAppUsage());
        holder.sinceText.setText(list.get(position).getSinceName());
        holder.changeText.setText(list.get(position).getChangeInUsage());
        holder.appIcon.setImageResource(list.get(position).getAppIcon());

        final float change = (Float.parseFloat(list.get(position).getChangeInUsage().substring(2, list.get(position).getChangeInUsage().indexOf("%"))))/100;


        holder.distributionItemLayout.post(new Runnable() {
            @Override
            public void run() {

                int height = holder.distributionItemLayout.getHeight();
                int width = holder.distributionItemLayout.getWidth();

                holder.distributionWave.setLayoutParams(new RelativeLayout.LayoutParams(width, height));
                holder.distributionWave.setShapeType(WaveView.ShapeType.SQUARE);
                holder.distributionWave.setBorder(0, Color.parseColor("#00FFFFFF"));

                if (list.get(position).isWaveType()) {
                    holder.distributionWave.setWaveColor(Color.parseColor("#2621e8c7"), Color.parseColor("#4D21e8c7"));
                    if (change >= 0.25f) {
                        holder.changeText.setTextColor(Color.parseColor("#07453b"));
                    } else {
                        holder.changeText.setTextColor(Color.parseColor("#21e8c7"));
                    }

                    WaveHelper waveHelper = new WaveHelper(holder.distributionWave, change, 3000, 5000);
                    waveHelper.start();

                }
                else {
                    holder.distributionWave.setWaveColor(Color.parseColor("#26f03145"), Color.parseColor("#4Df03145"));
                    if (change >= 0.25f) {
                        holder.changeText.setTextColor(Color.parseColor("#180204"));
                    } else {
                        holder.changeText.setTextColor(Color.parseColor("#f03145"));

                    }

                    WaveHelper waveHelper = new WaveHelper(holder.distributionWave, change, 3000, 5000);
                    waveHelper.start();

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

