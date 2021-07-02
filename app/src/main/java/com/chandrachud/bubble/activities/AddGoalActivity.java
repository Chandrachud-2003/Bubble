package com.chandrachud.bubble.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.ramotion.fluidslider.FluidSlider;
import com.chandrachud.bubble.Adapters.chooseAppGoalAdapter;
import com.chandrachud.bubble.Items.createGoalItem;
import com.chandrachud.bubble.R;
import com.robinhood.ticker.TickerView;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.ArrayList;
import java.util.List;

import kotlin.Unit;

public class AddGoalActivity extends AppCompatActivity implements chooseAppGoalAdapter.selectedPositionCallback {

    //Heading
    private ImageView backButton;

    //Goal Title
    private EditText goalNameEditText;
    private String goalName;

    //Goal Type
    private TextView positiveType;
    private TextView negativeType;
    private int selectedType;

    //Goal Deadline
    private TextView endOfDayButton;
    private LinearLayout selectTimeLayout;
    private ImageView selectTimeImage;
    private TextView selectTimeText;
    private int deadlineType;

    //Choose App
    private RecyclerView chooseRecycler;
    private com.chandrachud.bubble.Adapters.chooseAppGoalAdapter chooseAppGoalAdapter;
    private FlexboxLayoutManager layoutManager;
    private List<createGoalItem> positiveApps;
    private List<createGoalItem> negativeApps;
    private List<createGoalItem> currentApps;
    private int chooseAppPosition;

    //Goal Amount
    private TextView goalAmountType;
    private TextView recommendedAmount;
    private TickerView hourTicker;
    private TickerView minuteTicker;
    private FluidSlider goalSlider;
    private int total;
    private int recommendedHour;
    private int recommendedMinute;

    //Bottom
    private TextView createButton;
    private boolean allowCreate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goal);

        findViewsById();
        setOnClickListeners();
        setupGoalAmount();
    }

    private void findViewsById(){
        backButton = findViewById(R.id.backButton);
        goalNameEditText = findViewById(R.id.goalNameInput);
        positiveType = findViewById(R.id.goalTypePositive);
        negativeType = findViewById(R.id.goalTypeNegative);
        endOfDayButton = findViewById(R.id.endOfToday);
        selectTimeImage = findViewById(R.id.selectTimeImage);
        selectTimeLayout = findViewById(R.id.chooseTimeLayout);
        selectTimeText = findViewById(R.id.selectTimeText);
        goalAmountType = findViewById(R.id.goalAmountTypeText);
        recommendedAmount = findViewById(R.id.goalAmountRecommendText);
        hourTicker = findViewById(R.id.hourTickerView);
        minuteTicker = findViewById(R.id.minuteTickerView);
        goalSlider = findViewById(R.id.goalAmountSeekBar);
        createButton = findViewById(R.id.createGoalButton);
        chooseRecycler = findViewById(R.id.createGoalChooseRecycler);

        positiveApps = new ArrayList<>();
        negativeApps = new ArrayList<>();
        currentApps = new ArrayList<>();

        positiveApps.add(new createGoalItem(R.drawable.icons8_classroom, "Google Classroom", false));
        positiveApps.add(new createGoalItem(R.drawable.icons8_clock, "Clock", false));
        positiveApps.add(new createGoalItem(R.drawable.icons8_forest_timeline, "Forest", false));
        positiveApps.add(new createGoalItem(R.drawable.icons8_gmail_type, "Gmail", false));
        positiveApps.add(new createGoalItem(R.drawable.icons8_excel_type, "Excel", false));
        positiveApps.add(new createGoalItem(R.drawable.icons8_khan_academy_timeline, "Khan Academy", false));
        positiveApps.add(new createGoalItem(R.drawable.icons8_kindle, "Kindle", false));

        negativeApps.add(new createGoalItem(R.drawable.icons8_facebook, "Facebook", false));
        negativeApps.add(new createGoalItem(R.drawable.icons8_instagram_goal, "Instagram", false));
        negativeApps.add(new createGoalItem(R.drawable.icons8_mario_goal, "Super Mario", false));
        negativeApps.add(new createGoalItem(R.drawable.icons8_twitter_goal, "Twitter", false));
        negativeApps.add(new createGoalItem(R.drawable.icons8_spotify, "Spotify", false));
        negativeApps.add(new createGoalItem(R.drawable.icons8_youtube, "YouTube", false));
        negativeApps.add(new createGoalItem(R.drawable.icons8_whatsapp, "WhatsApp", false));

        selectedType = -1;
        deadlineType = -1;
        total = 7;
        allowCreate = false;
        chooseAppPosition = -1;
        goalName = "";

        float h = (float)total/2f;
        recommendedMinute = (int)(h*60);
        recommendedHour = (int) recommendedMinute/60;
        recommendedMinute = (int) (recommendedMinute - (recommendedHour*60));
        Log.d("TAG", "findViewsById: h: "+h);
        Log.d("TAG", "findViewsById: Hour: "+recommendedHour+", Minute: "+recommendedMinute);

    }

    private void setOnClickListeners(){

        goalNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                goalName = s.toString();
                if (!allowCreate && selectedType != -1 && deadlineType != -1 && !goalName.isEmpty() && goalSlider.getPosition()!=0f && chooseAppPosition!=-1){
                    allowCreate = true;
                    setCreateButton(true);
                }
                else if (goalName.isEmpty() && allowCreate){
                    allowCreate = false;
                    setCreateButton(allowCreate);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        PushDownAnim.setPushDownAnimTo(backButton)
                .setScale(PushDownAnim.MODE_SCALE, 0.8f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(AddGoalActivity.this, GoalCenter.class);
                        startActivity(intent);
                        finish();
                    }
                });



        PushDownAnim.setPushDownAnimTo(positiveType)
                .setScale(PushDownAnim.MODE_SCALE, 0.8f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (selectedType!=0) {
                            setupChooseRecycler(true, selectedType==-1);
                            setTypeUI(positiveType, negativeType, true);
                        }
                    }
                });

        PushDownAnim.setPushDownAnimTo(negativeType)
                .setScale(PushDownAnim.MODE_SCALE, 0.8f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (selectedType!=1) {
                            setupChooseRecycler(false, selectedType==-1);
                            setTypeUI(negativeType, positiveType, false);
                        }
                    }
                });

        PushDownAnim.setPushDownAnimTo(endOfDayButton)
                .setScale(PushDownAnim.MODE_SCALE, 0.8f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (deadlineType!=0) {
                            deadlineType = 0;
                            setDeadlineUI(false);
                        }
                    }
                });

        PushDownAnim.setPushDownAnimTo(selectTimeLayout)
                .setScale(PushDownAnim.MODE_SCALE, 0.8f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (deadlineType!=1) {
                            deadlineType = 1;
                            setDeadlineUI(true);
                        }
                    }
                });


        PushDownAnim.setPushDownAnimTo(createButton)
                .setScale(PushDownAnim.MODE_SCALE, 0.8f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d("TAG", "onClick: selectedType:"+selectedType+"\ndeadlineType:"+deadlineType+"\nchooseAppPosition:"+chooseAppPosition+"\ngoalSlider Position:"+goalSlider.getPosition()+"\ngoalName:"+goalName+"\nallowCreate:"+allowCreate);
                        if (allowCreate){
                            Intent intent = new Intent(AddGoalActivity.this, GoalCenter.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });


    }

    private void setTypeUI(TextView selected, TextView unselected, boolean isPositive){
        int bg;
        if (isPositive){
            bg = R.drawable.goal_positive_selected_bg;
        }
        else {
            bg = R.drawable.goal_negative_selected_bg;
        }

        selected.setTextColor(Color.WHITE);
        selected.setTypeface(null, Typeface.BOLD);
        selected.setBackgroundResource(bg);

        unselected.setTextColor(Color.parseColor("#A9A9A9"));
        unselected.setTypeface(null, Typeface.NORMAL);
        unselected.setBackgroundResource(R.drawable.create_goal_unselected_bg);

        if (goalAmountType.getVisibility() == View.GONE){
            goalAmountType.setVisibility(View.VISIBLE);
        }

        if (recommendedAmount.getVisibility() == View.GONE){
            recommendedAmount.setVisibility(View.VISIBLE);
        }

        if (isPositive){
            selectedType = 0;
            playAnimation(Techniques.Landing, 700, goalAmountType);
            goalAmountType.setTextColor(Color.parseColor("#21e8c7"));
            goalAmountType.setText("USAGE GOAL");
            goalSlider.setColorBubbleText(Color.parseColor("#21e8c7"));
            goalSlider.setColorBarText(Color.parseColor("#21e8c7"));

        }
        else {
            selectedType = 1;
            playAnimation(Techniques.Landing, 700, goalAmountType);
            goalAmountType.setTextColor(Color.parseColor("#f03145"));
            goalAmountType.setText("USAGE LIMIT");
            goalSlider.setColorBubbleText(Color.parseColor("#f03145"));
            goalSlider.setColorBarText(Color.parseColor("#f03145"));

        }
        if (allowCreate){
            allowCreate = false;
            setCreateButton(allowCreate);
        }

    }

    private void setDeadlineUI(boolean custom) {
        if (custom){
            endOfDayButton.setBackgroundResource(R.drawable.create_goal_unselected_bg);
            endOfDayButton.setTextColor(Color.parseColor("#A9A9A9"));
            endOfDayButton.setTypeface(null, Typeface.NORMAL);

            selectTimeLayout.setBackgroundResource(R.drawable.select_time_selected_bg);
            selectTimeImage.setVisibility(View.GONE);
            selectTimeText.setTextColor(Color.WHITE);
            selectTimeText.setTypeface(null, Typeface.BOLD);
            selectTimeText.setText("7:00 PM");
        }

        else {
            selectTimeLayout.setBackgroundResource(R.drawable.create_goal_unselected_bg);
            selectTimeText.setTextColor(Color.parseColor("#A9A9A9"));
            selectTimeText.setTypeface(null, Typeface.NORMAL);
            selectTimeText.setText("Select Time");
            selectTimeImage.setVisibility(View.VISIBLE);

            endOfDayButton.setBackgroundResource(R.drawable.select_time_selected_bg);
            endOfDayButton.setTextColor(Color.WHITE);
            endOfDayButton.setTypeface(null, Typeface.BOLD);
        }

        if (!allowCreate && selectedType != -1 && deadlineType != -1 && !goalName.isEmpty() && goalSlider.getPosition()!=0f && chooseAppPosition!=-1){
            allowCreate = true;
            setCreateButton(allowCreate);
        }
    }

    private void setupChooseRecycler(boolean type, boolean firstTime){
        layoutManager = new FlexboxLayoutManager(this){};
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);

        if (!firstTime && chooseAppGoalAdapter.selected != -1){
            currentApps.get(chooseAppGoalAdapter.selected).setSelected(false);
        }

        if (type)
        {
            currentApps = positiveApps;
        }
        else {
            currentApps = negativeApps;
        }
        chooseAppPosition = -1;
        if (firstTime){
            chooseAppGoalAdapter = new chooseAppGoalAdapter(currentApps, type, AddGoalActivity.this);
            chooseRecycler.setLayoutManager(layoutManager);
            chooseRecycler.setAdapter(chooseAppGoalAdapter);
        }
        else {
            chooseAppGoalAdapter = new chooseAppGoalAdapter(currentApps, type, AddGoalActivity.this);
            chooseRecycler.setAdapter(chooseAppGoalAdapter);
        }
    }

    private void setupGoalAmount(){
        final Typeface mFont = ResourcesCompat.getFont(this, R.font.montserrat);
        hourTicker.setTypeface(mFont);
        minuteTicker.setTypeface(mFont);
        hourTicker.setText("00", false);
        minuteTicker.setText("00", false);
        goalSlider.setPosition(0);
        goalSlider.setBubbleText("0");
        goalSlider.setStartText("0hr");
        goalSlider.setEndText(total +"hr");

        goalSlider.setPositionListener(pos -> {
            if (goalSlider.getPosition()==0.0f){
                if (allowCreate){
                    allowCreate = false;
                    setCreateButton(allowCreate);
                }
            }
            else {
                if (selectedType != -1 && deadlineType != -1 && !goalName.isEmpty() && goalSlider.getPosition()!=0f && chooseAppPosition!=-1){
                    if (!allowCreate) {
                        allowCreate = true;
                        setCreateButton(allowCreate);
                    }
                }
            }
            float value = (float) (Math.round((total*2) * goalSlider.getPosition()) / 2.0);
            setTickerAmount(value);
            if (value % 1 == 0) {
                goalSlider.setBubbleText(String.valueOf((int)value));
            }
            else {
                goalSlider.setBubbleText(String.valueOf(value));
            }
            return Unit.INSTANCE;
        });
    }

    private void setTickerAmount(float value){
        int minutes = (int)(value*60);
        int hours = (int)(minutes/60);
        minutes = minutes-(60*hours);
        if (hours<10){
            hourTicker.setText("0"+hours, true);
        }
        else {
            hourTicker.setText(String.valueOf(hours), true);
        }
        if (minutes<10){
            minuteTicker.setText("0"+minutes, true);
        }
        else {
            minuteTicker.setText(String.valueOf(minutes), true);
        }
        if (hours==((int)recommendedHour) && minutes==recommendedMinute){
            if (recommendedAmount.getVisibility() == View.GONE){
                recommendedAmount.setVisibility(View.VISIBLE);
                playAnimation(Techniques.Landing, 700, recommendedAmount);
            }
        }
        else {
            recommendedAmount.setVisibility(View.GONE);
        }
    }

    private void setCreateButton(boolean activated){
        if (activated){
            createButton.setBackgroundResource(R.drawable.create_goal_button_done_bg);
            createButton.setTextColor(Color.parseColor("#3469FD"));
            createButton.setTypeface(createButton.getTypeface(), Typeface.BOLD);
        }
        else {
            createButton.setBackgroundResource(R.drawable.create_goal_unselected_bg);
            createButton.setTextColor(Color.parseColor("#A9A9A9"));
            createButton.setTypeface(createButton.getTypeface(), Typeface.NORMAL);
        }
    }

    private void playAnimation(Techniques techniques, int duration, View v){
        YoYo.with(techniques)
                .duration(duration)
                .playOn(v);
    }

    public static Drawable resizeImage(Context ctx, int resId, int iconWidth,
                                       int iconHeight) {

        // load the origial Bitmap
        Bitmap BitmapOrg = BitmapFactory.decodeResource(ctx.getResources(),
                resId);

        int width = BitmapOrg.getWidth();
        int height = BitmapOrg.getHeight();
        int newWidth = iconWidth;
        int newHeight = iconHeight;

        // calculate the scale
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        // create a matrix for the manipulation
        Matrix matrix = new Matrix();
        // resize the Bitmap
        matrix.postScale(scaleWidth, scaleHeight);

        // if you want to rotate the Bitmap
        // matrix.postRotate(45);

        // recreate the new Bitmap
        Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width,
                height, matrix, true);

        // make a Drawable from Bitmap to allow to set the Bitmap
        // to the ImageView, ImageButton or what ever
        return new BitmapDrawable(resizedBitmap);

    }

    @Override
    public void selectedPosition(int position) {
        chooseAppPosition = position;
        if (position != -1){
            goalSlider.setPosition(0.5f);
            setTickerAmount((float)total/2);
        }
        if (selectedType != -1 && deadlineType != -1 && !goalName.isEmpty() && goalSlider.getPosition()!=0f && position!=-1){
            allowCreate = true;
            setCreateButton(allowCreate);
        }
    }
}