package com.chandrachud.bubble.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.chandrachud.bubble.Items.customGoalListItem;
import com.chandrachud.bubble.Items.goalBubbleWalletItem;
import com.chandrachud.bubble.R;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.robinhood.ticker.TickerView;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.List;

public class goalBubbleWalletAdapter extends RecyclerView.Adapter<goalBubbleWalletAdapter.MyView> {

    private List<goalBubbleWalletItem> list;
    private Context context;

    public static class MyView
            extends RecyclerView.ViewHolder {

        private ImageView image;
        private TextView name;
        private TextView type;
        private TickerView points;
        private TextView description;
        private TextView pointsDescription;

        public MyView(View view) {
            super(view);
            image = view.findViewById(R.id.bubbleWalletImage);
            name = view.findViewById(R.id.bubbleWalletName);
            type = view.findViewById(R.id.bubbleWalletType);
            points = view.findViewById(R.id.bubbleWalletPoints);
            description = view.findViewById(R.id.bubbleWalletDescription);
            pointsDescription = view.findViewById(R.id.bubbleWalletPointsText);

        }
    }

    public goalBubbleWalletAdapter(List<goalBubbleWalletItem> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public goalBubbleWalletAdapter.MyView onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        View itemView
                = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.goal_bubble_wallet_item_layout,
                        parent,
                        false);
        return new goalBubbleWalletAdapter.MyView(itemView);
    }

    @Override
    public void onBindViewHolder(final goalBubbleWalletAdapter.MyView holder,
                                 final int position) {

        holder.image.setImageResource(list.get(position).getIcon());
        holder.name.setText(list.get(position).getName());
        String d = list.get(position).getTime()+" | ";
        if (list.get(position).isComplete()){
            d += "Success";
        }

        if (list.get(position).isType()){
            if (!list.get(position).isComplete()){
                d += "Expired";
            }

            if (list.get(position).isCustom()){
                holder.type.setBackgroundResource(R.drawable.today_progress_complete_type_bg);
                holder.type.setTextColor(Color.parseColor("#3469FD"));
                d += " | Custom";
            }
            else {
                holder.type.setBackgroundResource(R.drawable.today_progress_positive_bg);
                holder.type.setTextColor(Color.parseColor("#21e8c7"));
            }
            holder.type.setText("Usage Goal");
        }
        else {
            if (!list.get(position).isComplete()){
                d += "Failed";
            }

            if (list.get(position).isCustom()){
                holder.type.setBackgroundResource(R.drawable.today_progress_complete_type_bg);
                holder.type.setTextColor(Color.parseColor("#3469FD"));
                d += " | Custom";
            }
            else {
                holder.type.setBackgroundResource(R.drawable.today_progress_negative_bg);
                holder.type.setTextColor(Color.parseColor("#f03145"));
            }
            holder.type.setText("Usage Limit");
        }

        holder.description.setText(d);
        final Typeface mFont = Typeface.create(ResourcesCompat.getFont(context, R.font.montserrat), Typeface.BOLD);
        holder.points.setTypeface(mFont);
        if (list.get(position).isComplete()){
            holder.points.setTextColor(Color.parseColor("#21e8c7"));
            holder.pointsDescription.setTextColor(Color.parseColor("#8021e8c7"));
            holder.points.setText("+"+list.get(position).getPoints(), true);
        }
        else {
            holder.points.setTextColor(Color.parseColor("#f03145"));
            holder.pointsDescription.setTextColor(Color.parseColor("#80f03145"));
            holder.points.setText("-"+list.get(position).getPoints(), true);
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}