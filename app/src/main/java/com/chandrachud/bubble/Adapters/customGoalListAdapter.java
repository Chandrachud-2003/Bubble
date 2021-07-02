package com.chandrachud.bubble.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.chandrachud.bubble.Items.customGoalListItem;
import com.chandrachud.bubble.Items.todayProgressItem;
import com.chandrachud.bubble.R;
import com.deepan.pieprogress.PieProgress;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.robinhood.ticker.TickerView;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.List;

public class customGoalListAdapter extends RecyclerView.Adapter<customGoalListAdapter.MyView> {

    private List<customGoalListItem> list;
    private Context context;

    public static class MyView
            extends RecyclerView.ViewHolder {

        private ImageView image;
        private TextView name;
        private TextView type;
        private TickerView completed;
        private TextView total;
        private LinearProgressIndicator bar;

        public MyView(View view) {
            super(view);
            image = view.findViewById(R.id.customGoalItemImage);
            name = view.findViewById(R.id.customGoalItemName);
            type = view.findViewById(R.id.customGoalItemType);
            completed = view.findViewById(R.id.customGoalItemCompleted);
            total = view.findViewById(R.id.customGoalItemTotal);
            bar = view.findViewById(R.id.customGoalItemBar);

        }
    }

    public customGoalListAdapter(List<customGoalListItem> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public customGoalListAdapter.MyView onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        View itemView
                = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.custom_goal_item_layout,
                        parent,
                        false);
        return new customGoalListAdapter.MyView(itemView);
    }

    @Override
    public void onBindViewHolder(final MyView holder,
                                 final int position) {

        int progress = (int)((list.get(position).getCompleted()/list.get(position).getTotal())*100);
        if(progress == 100){
            holder.completed.setTextColor(Color.parseColor("#3469FD"));
            holder.total.setTextColor(Color.parseColor("#803469FD"));
            holder.itemView.setBackgroundResource(R.drawable.custom_goal_item_complete_bg);
            holder.type.setBackgroundResource(R.drawable.today_progress_complete_type_bg);
            holder.type.setTextColor(Color.parseColor("#3469FD"));
            if (list.get(position).isType()){
                holder.type.setText("Usage Goal");
            }
            else {
                holder.type.setText("Usage Limit");
            }
            holder.bar.setIndicatorColor(Color.parseColor("#3469FD"));
            holder.bar.setTrackColor(Color.parseColor("#803469FD"));

        }
        else if (list.get(position).isType()){
            holder.completed.setTextColor(Color.parseColor("#21e8c7"));
            holder.total.setTextColor(Color.parseColor("#8021e8c7"));
            holder.type.setBackgroundResource(R.drawable.today_progress_positive_bg);
            holder.type.setTextColor(Color.parseColor("#21e8c7"));
            holder.type.setText("Usage Goal");
            holder.bar.setIndicatorColor(Color.parseColor("#21e8c7"));
            holder.bar.setTrackColor(Color.parseColor("#8021e8c7"));
        }
        else {
            holder.completed.setTextColor(Color.parseColor("#f03145"));
            holder.total.setTextColor(Color.parseColor("#80f03145"));
            holder.type.setBackgroundResource(R.drawable.today_progress_negative_bg);
            holder.type.setTextColor(Color.parseColor("#f03145"));
            holder.type.setText("Usage Limit");
            holder.bar.setIndicatorColor(Color.parseColor("#f03145"));
            holder.bar.setTrackColor(Color.parseColor("#80f03145"));
        }

        holder.name.setText(list.get(position).getName());
        holder.image.setImageResource(list.get(position).getIcon());
        final Typeface mFont = Typeface.create(ResourcesCompat.getFont(context, R.font.montserrat), Typeface.BOLD);
        holder.completed.setTypeface(mFont);
        float completed = list.get(position).getCompleted();
        float total = list.get(position).getTotal();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.bar.setProgress(progress, true);
        }
        else {
            holder.bar.setProgress(progress);
        }
        if (completed % 1 == 0) {
            holder.completed.setText((int)completed + list.get(position).getUnit(), true);
        }
        else {
            holder.completed.setText(completed + list.get(position).getUnit(), true);
        }
        if (total % 1 == 0) {
            holder.total.setText(" / " + (int)total + list.get(position).getUnit());
        }
        else {
            holder.total.setText(" / " + total + list.get(position).getUnit());
        }


        PushDownAnim.setPushDownAnimTo(holder.itemView)
                .setScale(PushDownAnim.MODE_SCALE, 0.9f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
