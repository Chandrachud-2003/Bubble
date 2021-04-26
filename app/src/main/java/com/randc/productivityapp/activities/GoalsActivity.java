package com.randc.productivityapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.randc.productivityapp.Adapters.expiredGoalAdapter;
import com.randc.productivityapp.Adapters.goalDateAdapter;
import com.randc.productivityapp.Adapters.ongoingGoalAdapter;
import com.randc.productivityapp.Constants;
import com.randc.productivityapp.HelperClasses.SideNavClass;
import com.randc.productivityapp.Items.expiredGoalItem;
import com.randc.productivityapp.Items.goalDateItem;
import com.randc.productivityapp.Items.ongoingGoalItem;
import com.randc.productivityapp.R;

import java.util.ArrayList;
import java.util.List;

public class GoalsActivity extends AppCompatActivity {

    private ImageButton menuButton;
    private ImageButton premiumButton;

    private RecyclerView dateRecycler;
    private List<goalDateItem> mGoalDateItemList;
    private LinearLayoutManager HorizontalLayout;
    private goalDateAdapter mGoalDateAdapter;

    private RecyclerView goalOngoingRecycler;
    private LinearLayoutManager HorizontalLayout2;
    private ongoingGoalAdapter mOngoingGoalAdapter;
    private List<ongoingGoalItem> mOngoingGoalItemList;

    private RecyclerView goalExpiredRecycler;
    private LinearLayoutManager verticalLayout;
    private expiredGoalAdapter mExpiredGoalAdapter;
    private List<expiredGoalItem> mExpiredGoalItemsList;

    private TextView mainStatsPercent;
    private TextView mainStatsGoalsCompleted;
    private TextView mainStatsPointsClaimed;
    private ImageButton expandButton;
    private LinearLayout extraStatsLayout;
    private boolean isExpanded;

    private SideNavClass mSideNavClass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals);

        findViewsById(savedInstanceState);

        setUpOnClickListeners();

        setUpDateRecycler();

        setupOngoingRecycler();

        setUpExpiredRecycler();


    }

    private void findViewsById(Bundle bundle){

        menuButton = findViewById(R.id.menuButton);
        premiumButton = findViewById(R.id.premiumButton);
        dateRecycler = findViewById(R.id.goalDateRecycler);
        goalOngoingRecycler = findViewById(R.id.ongoingRecyclerView);
        goalExpiredRecycler = findViewById(R.id.expiredGoalsRecyclerView);

        mainStatsGoalsCompleted = findViewById(R.id.goalsCompletedStatsText);
        mainStatsPercent = findViewById(R.id.percentCompleteStatText);
        mainStatsPointsClaimed = findViewById(R.id.goalsPointsCompletedStatText);
        expandButton = findViewById(R.id.expand_collapseButton);
        extraStatsLayout = findViewById(R.id.extraStatsMainLinearLayout);
        isExpanded=true;

        mSideNavClass = new SideNavClass(GoalsActivity.this, bundle, Constants.goalsActivity);
        mSideNavClass.setUpSideNav();



    }

    private void setUpOnClickListeners()
    {
        expandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isExpanded)
                {
                    isExpanded=false;
                    extraStatsLayout.setVisibility(View.GONE);
                    expandButton.setRotation(0);
                }
                else {
                    isExpanded=true;
                    extraStatsLayout.setVisibility(View.VISIBLE);
                    expandButton.setRotation(180);
                }

            }
        });

    }

    private void setUpDateRecycler()
    {
        mGoalDateItemList = new ArrayList<>();
        mGoalDateItemList.add(new goalDateItem("12 - 19", "This week", false, false));
        mGoalDateItemList.add(new goalDateItem("17", "Today", false, true));
        mGoalDateItemList.add(new goalDateItem("18", "Mon", true, false));
        mGoalDateItemList.add(new goalDateItem("20 - 27", "Next week", true, false));

        HorizontalLayout
                = new LinearLayoutManager(
                GoalsActivity.this,
                LinearLayoutManager.HORIZONTAL,
                false);

        mGoalDateAdapter = new goalDateAdapter(mGoalDateItemList);
        dateRecycler.setLayoutManager(HorizontalLayout);
        dateRecycler.setAdapter(mGoalDateAdapter);




    }

    private void setupOngoingRecycler()
    {

        mOngoingGoalItemList = new ArrayList<>();
        mOngoingGoalItemList.add(new ongoingGoalItem("WhatsApp", R.drawable.icons8_whatsapp_small, " 2 hrs", "80%", false));
        mOngoingGoalItemList.add(new ongoingGoalItem("Google Classroom", R.drawable.icons8_classroom, " 1 hr", "75%", true));

        mOngoingGoalItemList.add(new ongoingGoalItem("Facebook", R.drawable.icons8_facebook_small, " 1 hr 30 min", "50%", false));
        mOngoingGoalItemList.add(new ongoingGoalItem("Word", R.drawable.icons8_word_small, " 3 hrs", "10%", true));
        mOngoingGoalItemList.add(new ongoingGoalItem("Kindle", R.drawable.icons8_kindle, " 1 hrs", "30%", true));

        HorizontalLayout2
                = new LinearLayoutManager(
                GoalsActivity.this,
                LinearLayoutManager.HORIZONTAL,
                false);

        mOngoingGoalAdapter = new ongoingGoalAdapter(mOngoingGoalItemList);
        goalOngoingRecycler.setLayoutManager(HorizontalLayout2);
        goalOngoingRecycler.setAdapter(mOngoingGoalAdapter);





    }

    private void setUpExpiredRecycler(){

        mExpiredGoalItemsList = new ArrayList<>();
        mExpiredGoalItemsList.add(new expiredGoalItem("Facebook", R.drawable.icons8_facebook_medium, "yes", "2hrs", false));
        mExpiredGoalItemsList.add(new expiredGoalItem("Powerpoint", R.drawable.icons8_powerpoint_medium, "mid", "1hr 30min", true));
        mExpiredGoalItemsList.add(new expiredGoalItem("Studying", R.drawable.icons8_study_medium, "yes", "2hrs 30min", true));
        mExpiredGoalItemsList.add(new expiredGoalItem("WhatsApp", R.drawable.icons8_whatsapp_medium, "yes", "1hr", false));
        mExpiredGoalItemsList.add(new expiredGoalItem("Spotify", R.drawable.icons8_spotify_medium, "no", "1hr 15min", false));

        verticalLayout
                = new LinearLayoutManager(
                GoalsActivity.this,
                LinearLayoutManager.VERTICAL,
                false);

        goalExpiredRecycler.setLayoutManager(verticalLayout);
        mExpiredGoalAdapter = new expiredGoalAdapter(mExpiredGoalItemsList);
        goalExpiredRecycler.setAdapter(mExpiredGoalAdapter);


    }
}
