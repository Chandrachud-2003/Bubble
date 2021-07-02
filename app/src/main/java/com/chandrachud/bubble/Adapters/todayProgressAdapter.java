package com.chandrachud.bubble.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.Image;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.chandrachud.bubble.Items.createGoalItem;
import com.chandrachud.bubble.Items.todayProgressItem;
import com.chandrachud.bubble.R;
import com.chandrachud.bubble.activities.HomePage;
import com.chandrachud.bubble.activities.MainActivity;
import com.deepan.pieprogress.PieProgress;
import com.robinhood.ticker.TickerView;
import com.thekhaeng.pushdownanim.PushDownAnim;

import org.w3c.dom.Text;

import java.util.List;

public class todayProgressAdapter extends RecyclerView.Adapter<todayProgressAdapter.MyView> {

    private List<todayProgressItem> list;
    private Context context;

    public static class MyView
            extends RecyclerView.ViewHolder {

        private PieProgress pieProgressComplete;
        private PieProgress pieProgressPositive;
        private PieProgress pieProgressNegative;
        private TextView name;
        private TextView deadline;
        private ImageView icon;
        private TickerView completed;
        private TextView total;
        private TextView type;

        public MyView(View view) {
            super(view);
            pieProgressComplete = view.findViewById(R.id.todayProgressPieComplete);
            pieProgressPositive = view.findViewById(R.id.todayProgressPiePositive);
            pieProgressNegative = view.findViewById(R.id.todayProgressPieNegative);
            name = view.findViewById(R.id.todayProgressAppName);
            deadline = view.findViewById(R.id.todayProgressDeadline);
            icon = view.findViewById(R.id.todayProgressAppIcon);
            completed = view.findViewById(R.id.todayProgressCompleted);
            total = view.findViewById(R.id.todayProgressTotal);
            type = view.findViewById(R.id.todayProgressType);
        }
    }

    public todayProgressAdapter(List<todayProgressItem> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public todayProgressAdapter.MyView onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        View itemView
                = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.today_progress_item_layout,
                        parent,
                        false);
        return new todayProgressAdapter.MyView(itemView);
    }

    @Override
    public void onBindViewHolder(final todayProgressAdapter.MyView holder,
                                 final int position) {

        Handler handler = new Handler();
        int progress = (int)((list.get(position).getCompleted()/list.get(position).getTotal())*100);
        if(progress == 100){
            holder.pieProgressComplete.setVisibility(View.VISIBLE);
            holder.completed.setTextColor(Color.parseColor("#3469FD"));
            holder.total.setTextColor(Color.parseColor("#803469FD"));
            holder.itemView.setBackgroundResource(R.drawable.today_progress_complete_bg);
            holder.type.setBackgroundResource(R.drawable.today_progress_complete_type_bg);
            holder.type.setTextColor(Color.parseColor("#3469FD"));
            if (list.get(position).isType()){
                holder.type.setText("Usage Goal");
            }
            else {
                holder.type.setText("Usage Limit");
            }

            for(int i=0;i<=progress;i++){
                final int temp = i;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        holder.pieProgressComplete.setProgress((float)temp);
                    }
                }, 20 * (long)i);

            }
        }
        else if (list.get(position).isType()){
            holder.pieProgressPositive.setVisibility(View.VISIBLE);
            holder.completed.setTextColor(Color.parseColor("#21e8c7"));
            holder.total.setTextColor(Color.parseColor("#8021e8c7"));
            holder.type.setBackgroundResource(R.drawable.today_progress_positive_bg);
            holder.type.setTextColor(Color.parseColor("#21e8c7"));
            holder.type.setText("Usage Goal");

            for(int i=0;i<=progress;i++){
                final int temp = i;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        holder.pieProgressPositive.setProgress((float)temp);
                    }
                }, 20 * (long)i);

            }
        }
        else {
            holder.pieProgressNegative.setVisibility(View.VISIBLE);
            holder.completed.setTextColor(Color.parseColor("#f03145"));
            holder.total.setTextColor(Color.parseColor("#80f03145"));
            holder.type.setBackgroundResource(R.drawable.today_progress_negative_bg);
            holder.type.setTextColor(Color.parseColor("#f03145"));
            holder.type.setText("Usage Limit");

            for(int i=0;i<=progress;i++){
                final int temp = i;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        holder.pieProgressNegative.setProgress((float)temp);
                    }
                }, 20 * (long)i);

            }
        }

        holder.name.setText(list.get(position).getName());
        holder.icon.setImageResource(list.get(position).getIcon());
        holder.deadline.setText(list.get(position).getDeadline());
        final Typeface mFont = ResourcesCompat.getFont(context, R.font.montserrat);
        holder.completed.setTypeface(mFont);
        float completed = list.get(position).getCompleted();
        float total = list.get(position).getTotal();
        if (completed % 1 == 0) {
            holder.completed.setText((int)completed + list.get(position).getUnit(), true);
        }
        else {
            holder.completed.setText(completed + list.get(position).getUnit(), true);
        }
        if (total % 1 == 0) {
            holder.total.setText("/ " + (int)total + list.get(position).getUnit());
        }
        else {
            holder.total.setText("/ " + total + list.get(position).getUnit());
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
