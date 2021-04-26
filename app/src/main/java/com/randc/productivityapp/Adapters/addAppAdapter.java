package com.randc.productivityapp.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.randc.productivityapp.Items.addAppItem;
import com.randc.productivityapp.R;
import com.thekhaeng.pushdownanim.PushDownAnim;


import java.util.List;

import spencerstudios.com.ezdialoglib.Animation;
import spencerstudios.com.ezdialoglib.EZDialog;
import spencerstudios.com.ezdialoglib.EZDialogListener;
import spencerstudios.com.ezdialoglib.Font;

public class addAppAdapter extends RecyclerView.Adapter<addAppAdapter.MyView> {

    // List with String type
    private List<addAppItem> list;

    private View mView;

    private Context mContext;

    private positiveAppCallBack mPositiveAppCallBack;
    private negativeAppCallBack mNegativeAppCallBack;


    // View Holder class which
    // extends RecyclerView.ViewHolder
    public class MyView
            extends RecyclerView.ViewHolder {


        TextView appName;
        ImageView appIcon;
        CardView cardView;




        public MyView(View view)
        {
            super(view);

            appName = view.findViewById(R.id.appNameAdd);
            appIcon = view.findViewById(R.id.appIconAdd);
            cardView = view.findViewById(R.id.cardAddApp);





        }
    }

    // Constructor for adapter class
    // which takes a list of String type
    public addAppAdapter(List<addAppItem> list, Context context)
    {
        this.list = list;
        this.mContext = context;
        try {
            this.mPositiveAppCallBack = ((positiveAppCallBack) context);
            this.mNegativeAppCallBack = ((negativeAppCallBack) context);
        }
        catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement AdapterCallback.");
        }

    }

    // Override onCreateViewHolder which deals
    // with the inflation of the card layout
    // as an item for the RecyclerView.
    @Override
    public addAppAdapter.MyView onCreateViewHolder(ViewGroup parent,
                                                        int viewType)
    {

        // Inflate item.xml using LayoutInflator
        View itemView
                = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.add_app_list_item,
                        parent,
                        false);

        // return itemView
        return new addAppAdapter.MyView(itemView);
    }

    // Override onBindViewHolder which deals
    // with the setting of different data
    // and methods related to clicks on
    // particular items of the RecyclerView.
    @Override
    public void onBindViewHolder(final addAppAdapter.MyView holder,
                                 final int position)
    {
        if (list.get(position) != null) {


            holder.appName.setText(list.get(position).getAppName());


            Bitmap iconBitmap = drawableToBitmap(list.get(position).getAppIcon());
            holder.appIcon.setImageBitmap(Bitmap.createScaledBitmap(iconBitmap, 120, 120, false));

            ColorMatrix colorMatrix = new ColorMatrix();
            colorMatrix.setSaturation(0);
            holder.cardView.setElevation(20f);

            holder.appIcon.setColorFilter(new ColorMatrixColorFilter(colorMatrix));

            PushDownAnim.setPushDownAnimTo(holder.itemView)
                    .setScale(PushDownAnim.MODE_SCALE, 0.8f)
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            // Score - 27
                            // 7%

                            if (!(list.get(position).isSelected())) {

                                new EZDialog.Builder(mContext)
                                        .setTitle(list.get(position).getAppName())
                                        .setTitleTextColor(Color.WHITE)
                                        .setMessage("Add App To")
                                        .setPositiveBtnText("Positive Apps")
                                        .setNegativeBtnText("Negative Apps")
                                        .showTitleDivider(true)
                                        .setTitleDividerLineColor(Color.WHITE)
                                        .setBackgroundColor(Color.WHITE)
                                        .setHeaderColor(Color.parseColor("#0e153f"))
                                        .setMessageTextColor(Color.BLACK)
                                        .setButtonTextColor(Color.BLACK)
                                        .setCancelableOnTouchOutside(true)
                                        .setAnimation(Animation.UP)
                                        .setFont(Font.QUICK_SAND)
                                        //.setCustomFont(R.font.montserrat)
                                        .OnPositiveClicked(new EZDialogListener() {
                                            @Override
                                            public void OnClick() {

                                                holder.cardView.setCardBackgroundColor(Color.parseColor("#3CB371"));
                                                holder.appName.setTextColor(Color.WHITE);
                                                holder.cardView.setElevation(50f);
                                                list.get(position).setSelected(true);

                                                holder.appIcon.clearColorFilter();

                                                mPositiveAppCallBack.onPositiveCallback(list.get(position).getAppPackage(), true);
                                                Log.d("TAG", "OnClick:  "+list.get(position).getAppPackage()+"sent - positive");

                                                list.get(position).setType("positive");


                                            }
                                        })
                                        .OnNegativeClicked(new EZDialogListener() {
                                            @Override
                                            public void OnClick() {
                                                holder.cardView.setCardBackgroundColor(Color.parseColor("#f03145"));
                                                holder.appName.setTextColor(Color.WHITE);
                                                holder.appIcon.clearColorFilter();
                                                holder.cardView.setElevation(50f);

                                                list.get(position).setSelected(true);

                                                mNegativeAppCallBack.onNegativeCallback(list.get(position).getAppPackage(), true);
                                                Log.d("TAG", "OnClick:  "+list.get(position).getAppPackage()+" sent - negative");
                                                list.get(position).setType("negative");


                                            }
                                        })
                                        .build();

                            } else {
                                ColorMatrix colorMatrix = new ColorMatrix();
                                colorMatrix.setSaturation(0);
                                holder.appIcon.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
                                holder.appName.setTextColor(Color.parseColor("#A9A9A9"));
                                holder.cardView.setCardBackgroundColor(Color.parseColor("#0A0F2D"));
                                list.get(position).setSelected(false);
                                holder.cardView.setElevation(20f);

                                if (list.get(position).getType().equals("positive"))
                                {
                                    mPositiveAppCallBack.onPositiveCallback(list.get(position).getAppPackage(), false);
                                    list.get(position).setType("none");

                                }
                                else if (list.get(position).getType().equals("negative"))
                                {
                                    mNegativeAppCallBack.onNegativeCallback(list.get(position).getAppPackage(), false);
                                    list.get(position).setType("none");

                                }


                            }




                        }
                    });
        }

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

    public static interface positiveAppCallBack {
        void onPositiveCallback(String packageName, boolean add);
    }

    public static interface negativeAppCallBack {
        void onNegativeCallback(String packageName, boolean add);
    }



}
