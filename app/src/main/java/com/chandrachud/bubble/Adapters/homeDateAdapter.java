package com.chandrachud.bubble.Adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.chandrachud.bubble.Items.homeDateItem;
import com.chandrachud.bubble.R;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.List;

public class homeDateAdapter extends RecyclerView.Adapter<homeDateAdapter.MyView>{

    private List<homeDateItem> list;
    private int selected;

    final float largeSize_Date = 42;
    final float smallSize_Date = 12;
    final int animationDuration = 600;

    public static class MyView
            extends RecyclerView.ViewHolder {
        TextView dayText;
        TextView dateText;
        View selectedIndicator;
        public MyView(View view)
        {
            super(view);
            dateText = view.findViewById(R.id.dateNumber);
            dayText = view.findViewById(R.id.dayText);
            selectedIndicator = view.findViewById(R.id.selectedIndicator);

        }
    }

    public homeDateAdapter(List<homeDateItem> list)
    {
        this.list = list;
        selected = 0;
    }

    @Override
    public homeDateAdapter.MyView onCreateViewHolder(ViewGroup parent,
                                                     int viewType)
    {
        View itemView
                = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.homepage_date_recycler_item,
                        parent,
                        false);
        return new MyView(itemView);
    }

    @Override
    public void onBindViewHolder(final homeDateAdapter.MyView holder,
                                 final int position)
    {
        holder.dateText.setText(list.get(position).getDate());
        holder.dayText.setText(list.get(position).getDay());
        if (list.get(position).isSelected())
        {

            holder.itemView.setBackgroundResource(R.drawable.home_date_clicked_bg);
            holder.dayText.setTextColor(Color.WHITE);
            holder.dateText.setTextColor(Color.WHITE);
            holder.dateText.setTextSize(21);
            holder.dayText.setTextSize(13);
            holder.selectedIndicator.setVisibility(View.VISIBLE);

        }
        else {
            holder.itemView.setBackgroundResource(R.drawable.home_date_bg);
            holder.dayText.setTextColor(Color.parseColor("#586389"));
            holder.dateText.setTextColor(Color.parseColor("#586389"));
            holder.dateText.setTextSize(20);
            holder.dayText.setTextSize(12);
            holder.selectedIndicator.setVisibility(View.GONE);
        }

        PushDownAnim.setPushDownAnimTo(holder.itemView)
                .setScale(PushDownAnim.MODE_SCALE, 0.8f)
                .setOnClickListener(new View.OnClickListener() {
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

    @Override
    public int getItemCount()
    {
        return list.size();
    }
}
