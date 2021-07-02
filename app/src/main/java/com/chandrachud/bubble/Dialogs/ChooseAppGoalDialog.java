package com.chandrachud.bubble.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;

import com.ramotion.circlemenu.CircleMenuView;
import com.chandrachud.bubble.R;

import org.jetbrains.annotations.NotNull;

public class ChooseAppGoalDialog {

    private Dialog chooseAppDialog;

    private boolean type;
    private Activity activity;

    private CircleMenuView chooseAppMenu;

    public ChooseAppGoalDialog(Activity activity, boolean type){
        this.activity = activity;
        this.type = type;
    }

    public void startDialog() {
        chooseAppDialog = new Dialog(activity);
        chooseAppDialog.setContentView(R.layout.choose_app_goal_dialog);
        Window window = chooseAppDialog.getWindow();

        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        chooseAppDialog.setCanceledOnTouchOutside(false);
        chooseAppDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        findViewsById();
        chooseAppDialog.show();
    }

    private void findViewsById(){
        chooseAppMenu = chooseAppDialog.findViewById(R.id.chooseAppGoalMenu);
    }

    private void  setOnClickListeners(){
        chooseAppMenu.setEventListener(new CircleMenuView.EventListener(){
            @Override
            public void onMenuOpenAnimationStart(@NonNull @NotNull CircleMenuView view) {
                super.onMenuOpenAnimationStart(view);
                chooseAppMenu.setVisibility(View.VISIBLE);
            }

            @Override
            public void onMenuOpenAnimationEnd(@NonNull @NotNull CircleMenuView view) {
                super.onMenuOpenAnimationEnd(view);
            }

            @Override
            public void onMenuCloseAnimationStart(@NonNull @NotNull CircleMenuView view) {
                super.onMenuCloseAnimationStart(view);
            }

            @Override
            public void onMenuCloseAnimationEnd(@NonNull @NotNull CircleMenuView view) {
                super.onMenuCloseAnimationEnd(view);
                chooseAppDialog.dismiss();
            }

            @Override
            public void onButtonClickAnimationStart(@NonNull @NotNull CircleMenuView view, int buttonIndex) {
                super.onButtonClickAnimationStart(view, buttonIndex);
            }

            @Override
            public void onButtonClickAnimationEnd(@NonNull @NotNull CircleMenuView view, int buttonIndex) {
                super.onButtonClickAnimationEnd(view, buttonIndex);
                chooseAppDialog.dismiss();
            }

            @Override
            public boolean onButtonLongClick(@NonNull @NotNull CircleMenuView view, int buttonIndex) {
                return super.onButtonLongClick(view, buttonIndex);
            }

            @Override
            public void onButtonLongClickAnimationStart(@NonNull @NotNull CircleMenuView view, int buttonIndex) {
                super.onButtonLongClickAnimationStart(view, buttonIndex);
            }

            @Override
            public void onButtonLongClickAnimationEnd(@NonNull @NotNull CircleMenuView view, int buttonIndex) {
                super.onButtonLongClickAnimationEnd(view, buttonIndex);
                chooseAppDialog.dismiss();
            }
        });
    }

}
