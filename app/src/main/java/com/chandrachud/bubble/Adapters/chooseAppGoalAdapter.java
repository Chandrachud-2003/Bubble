package com.chandrachud.bubble.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.chandrachud.bubble.Items.createGoalItem;
import com.chandrachud.bubble.R;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.List;

public class chooseAppGoalAdapter extends RecyclerView.Adapter<chooseAppGoalAdapter.MyView>{

private List<createGoalItem> list;
public int selected;
private boolean type;
private selectedPositionCallback positionCallback;

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public static class MyView
        extends RecyclerView.ViewHolder {

    TextView text;
    ImageView image;

    public MyView(View view)
    {
        super(view);
        text = view.findViewById(R.id.chooseAppText);
        image = view.findViewById(R.id.chooseAppImage);

    }
}

    public chooseAppGoalAdapter(List<createGoalItem> list, boolean type, Context context)
    {
        this.list = list;
        selected = -1;
        this.type = type;
        try {
            this.positionCallback = ((chooseAppGoalAdapter.selectedPositionCallback) context);
        }
        catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement AdapterCallback.");
        }

    }

    @Override
    public chooseAppGoalAdapter.MyView onCreateViewHolder(ViewGroup parent,
                                                     int viewType)
    {
        View itemView
                = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.choose_app_item_create_goal,
                        parent,
                        false);
        return new chooseAppGoalAdapter.MyView(itemView);
    }

    @Override
    public void onBindViewHolder(final chooseAppGoalAdapter.MyView holder,
                                 final int position)
    {
        holder.text.setText(list.get(position).getText());
        holder.image.setImageResource(list.get(position).getImage());
        if (list.get(position).isSelected())
        {
            if (type) {
                holder.itemView.setBackgroundResource(R.drawable.choose_app_positive_bg);
                holder.text.setTextColor(Color.parseColor("#21e8c7"));
            }
            else {
                holder.itemView.setBackgroundResource(R.drawable.choose_app_negative_bg);
                holder.text.setTextColor(Color.parseColor("#f03145"));
            }
            holder.text.setTypeface(holder.text.getTypeface(), Typeface.BOLD);

        }
        else {
            holder.itemView.setBackgroundResource(R.drawable.choose_app_bg);
            holder.text.setTextColor(Color.WHITE);
            holder.text.setTypeface(holder.text.getTypeface(), Typeface.NORMAL);
        }

        PushDownAnim.setPushDownAnimTo(holder.itemView)
                .setScale(PushDownAnim.MODE_SCALE, 0.8f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        positionCallback.selectedPosition(position);
                        if (!(list.get(position).isSelected()))
                        {
                            if (selected==-1){
                                list.get(position).setSelected(true);
                                notifyItemChanged(position);
                                selected = position;
                            }
                            else {
                                list.get(position).setSelected(true);
                                list.get(selected).setSelected(false);
                                notifyItemChanged(position);
                                notifyItemChanged(selected);
                                selected = position;
                            }
                        }


                    }
                });
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    public interface selectedPositionCallback {
        void selectedPosition(int position);
    }
}

