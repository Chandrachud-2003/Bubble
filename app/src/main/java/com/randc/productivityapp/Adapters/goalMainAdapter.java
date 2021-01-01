package com.randc.productivityapp.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import com.ramijemli.percentagechartview.PercentageChartView;
import com.ramijemli.percentagechartview.renderer.BaseModeRenderer;
import com.randc.productivityapp.Items.addAppItem;
import com.randc.productivityapp.Items.goalMainItem;
import com.randc.productivityapp.R;

import org.w3c.dom.Text;

import java.util.List;

import spencerstudios.com.ezdialoglib.Animation;
import spencerstudios.com.ezdialoglib.EZDialog;
import spencerstudios.com.ezdialoglib.EZDialogListener;
import spencerstudios.com.ezdialoglib.Font;

public class goalMainAdapter extends RecyclerView.Adapter<goalMainAdapter.MyView>{

    // List with String type
    private List<goalMainItem> list;

    private View mView;

    private Context mContext;


    // View Holder class which
    // extends RecyclerView.ViewHolder
    public class MyView
            extends RecyclerView.ViewHolder {


        TextView appName;
        TextView typeName;
        TextView usageCompleted;
        TextView usageGoal;
        TextView goalLimit;
        ImageView appIcon;
        CircularProgressBar percentChart;




        public MyView(View view)
        {
            super(view);

            appName = view.findViewById(R.id.appNameGoalMain);
            typeName = view.findViewById(R.id.goalTypeMain);
            appIcon = view.findViewById(R.id.goalAppIconMain);
            usageCompleted = view.findViewById(R.id.goalCompletedTimeMain);
            usageGoal = view.findViewById(R.id.goalTotalTimeMain);
            goalLimit = view.findViewById(R.id.limitGoalMain);
            percentChart = view.findViewById(R.id.percentageGoalMain);

        }
    }

    // Constructor for adapter class
    // which takes a list of String type
    public goalMainAdapter(List<goalMainItem> list, Context context)
    {
        this.list = list;
        this.mContext = context;
    }

    // Override onCreateViewHolder which deals
    // with the inflation of the card layout
    // as an item for the RecyclerView.
    @Override
    public goalMainAdapter.MyView onCreateViewHolder(ViewGroup parent,
                                                   int viewType)
    {

        // Inflate item.xml using LayoutInflator
        View itemView
                = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.goal_list_main_item,
                        parent,
                        false);

        // return itemView
        return new goalMainAdapter.MyView(itemView);
    }

    // Override onBindViewHolder which deals
    // with the setting of different data
    // and methods related to clicks on
    // particular items of the RecyclerView.
    @Override
    public void onBindViewHolder(final goalMainAdapter.MyView holder,
                                 final int position)
    {

        holder.appIcon.setImageResource(list.get(position).getAppIcon());
        holder.appName.setText(list.get(position).getAppName());
        holder.goalLimit.setText(list.get(position).getGoalLimit());
        holder.usageGoal.setText(list.get(position).getTotalTime());
        holder.usageCompleted.setText(list.get(position).getCompletedTime());


        if (list.get(position).isType())
        {
            holder.typeName.setText("Usage Goal");
            holder.typeName.setTextColor(Color.parseColor("#21e8c7"));
            holder.typeName.setBackgroundResource(R.drawable.positive_main_goal_bg);

            holder.usageCompleted.setTextColor(Color.parseColor("#21e8c7"));


            holder.percentChart.setProgressBarColor(Color.parseColor("#21e8c7"));
            holder.percentChart.setProgressDirection(CircularProgressBar.ProgressDirection.TO_RIGHT);




        }

        else {

            holder.typeName.setText("Usage Limit");
            holder.typeName.setTextColor(Color.parseColor("#f03145"));
            holder.typeName.setBackgroundResource(R.drawable.negative_main_goal_bg);

            holder.usageCompleted.setTextColor(Color.parseColor("#f03145"));

            holder.percentChart.setProgressBarColor(Color.parseColor("#f03145"));
            holder.percentChart.setProgressDirection(CircularProgressBar.ProgressDirection.TO_LEFT);

        }

        holder.percentChart.setProgress(list.get(position).getPercentage());

    }

    // Override getItemCount which Returns
    // the length of the RecyclerView.
    @Override
    public int getItemCount()
    {
        return list.size();
    }


}
