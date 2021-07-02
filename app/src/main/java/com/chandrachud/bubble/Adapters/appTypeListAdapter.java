package com.chandrachud.bubble.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.chandrachud.bubble.activities.AppInfoActivity;
import com.chandrachud.bubble.Constants;
import com.chandrachud.bubble.Items.appTypeListItem;
import com.chandrachud.bubble.R;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.List;

public class appTypeListAdapter extends RecyclerView.Adapter<appTypeListAdapter.MyView> {

    // List with String type
    private List<appTypeListItem> list;

    private View mView;
    private boolean type;
    private Context mContext;

    // View Holder class which
    // extends RecyclerView.ViewHolder
    public class MyView
            extends RecyclerView.ViewHolder {

        TextView appName;
        TextView appName2;
        ImageView appIcon;
        TextView bubbleChangePercent;
        TextView bubbleText;

        // parameterised constructor for View Holder class
        // which takes the view as a parameter
        public MyView(View view)
        {
            super(view);

            appName = view.findViewById(R.id.appNameType);
            appName2 = view.findViewById(R.id.appNameType2);
            appIcon = view.findViewById(R.id.appIconType);
            bubbleChangePercent = view.findViewById(R.id.todayBubblePercent);
            bubbleText = view.findViewById(R.id.todayBubbleText);


        }
    }

    // Constructor for adapter class
    // which takes a list of String type
    public appTypeListAdapter(List<appTypeListItem> horizontalList, boolean type, Context context)
    {
        this.list = horizontalList;
        this.type = type;
        this.mContext = context;
    }

    // Override onCreateViewHolder which deals
    // with the inflation of the card layout
    // as an item for the RecyclerView.
    @Override
    public appTypeListAdapter.MyView onCreateViewHolder(ViewGroup parent,
                                                     int viewType)
    {

        // Inflate item.xml using LayoutInflator
        View itemView
                = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.app_type_list_item,
                        parent,
                        false);

        // return itemView
        return new appTypeListAdapter.MyView(itemView);
    }

    // Override onBindViewHolder which deals
    // with the setting of different data
    // and methods related to clicks on
    // particular items of the RecyclerView.
    @Override
    public void onBindViewHolder(final appTypeListAdapter.MyView holder,
                                 final int position)
    {
        String name = list.get(position).getAppName();
        name = name.trim();
        if (name.contains(" "))
        {
            //Log.d("TAG", "onBindViewHolder: "+name.substring(0, name.indexOf(" ")));
            holder.appName.setText(name.substring(0, name.indexOf(" ")));
            holder.appName2.setText(name.substring(name.indexOf(" ")+1));

        }

        else {

            holder.appName.setText(name);
            holder.appName2.setText("");
        }

        Bitmap iconBitmap = drawableToBitmap(list.get(position).getAppIcon());
        holder.appIcon.setImageBitmap(Bitmap.createScaledBitmap(iconBitmap, 150, 150, false));

        holder.bubbleChangePercent.setText(list.get(position).getBubblePercent()+"%");



        if (type)
        {

            holder.bubbleChangePercent.setTextColor(Color.parseColor("#21e8c7"));
            holder.bubbleText.setTextColor(Color.parseColor("#21e8c7"));

        }

        else {
            holder.bubbleChangePercent.setTextColor(Color.parseColor("#f03145"));
            holder.bubbleText.setTextColor(Color.parseColor("#f03145"));


        }

        PushDownAnim.setPushDownAnimTo(holder.itemView)
                .setScale(PushDownAnim.MODE_SCALE, 0.7f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(mContext, AppInfoActivity.class);
                        intent.putExtra(Constants.packageIntentKey, list.get(position).getPackageName());
                        intent.putExtra(Constants.appTypeIntentKey, type);
                        intent.putExtra(Constants.previousActivityIntentKey, Constants.yourAppsActivityIntentName);
                        intent.putExtra(Constants.bubblePercentIntentKey, list.get(position).getBubblePercent());
                        mContext.startActivity(intent);

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

    public static Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }


}
