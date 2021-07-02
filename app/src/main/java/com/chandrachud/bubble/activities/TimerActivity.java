package com.chandrachud.bubble.activities;

import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;

import com.github.stefanodp91.android.circularseekbar.CircularSeekBar;
import com.github.stefanodp91.android.circularseekbar.OnCircularSeekBarChangeListener;
import com.chandrachud.bubble.Constants;
import com.chandrachud.bubble.HelperClasses.SideNavClass;
import com.chandrachud.bubble.R;
import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;
import com.scwang.wave.MultiWaveHeader;
import com.thekhaeng.pushdownanim.PushDownAnim;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.Toast;

public class TimerActivity extends AppCompatActivity {

    private TickerView timerView;
    private MultiWaveHeader timerWave;

    private CircularSeekBar timerSeekBar;

    private ImageButton playButton;

    private boolean changePlayImage = false;

    private CountDownTimer mCountDownTimer;

    private boolean timerStarted= false;

    private int timerTime=0;

    private SideNavClass mSideNavClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

            getWindow().setNavigationBarColor(getResources().getColor(R.color.white));
            getWindow().setStatusBarColor(getResources().getColor(R.color.white));


        }


        setContentView(R.layout.activity_timer);

        findViewsById(savedInstanceState);

        setUpClickListeners();

        setUpTimer();

        setUpTimerWave();

        setUpTimerSeekBar();


    }

    private void findViewsById(Bundle bundle)
    {
        timerView = findViewById(R.id.mainTimerView);
        timerWave = findViewById(R.id.timerWave);

        timerSeekBar = findViewById(R.id.timerSeekBar);
        playButton = findViewById(R.id.startTimerButton);

        changePlayImage = false;
        mCountDownTimer = null;

        mSideNavClass = new SideNavClass(TimerActivity.this, bundle, Constants.timerActivity);
        mSideNavClass.setUpSideNav();
    }

    private void setUpClickListeners()
    {
        Animation outAnimation = AnimationUtils.loadAnimation(TimerActivity.this, R.anim.fadeout);
        Animation inAnimation = AnimationUtils.loadAnimation(TimerActivity.this, R.anim.fadein);

        outAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                if (!changePlayImage) {

                    playButton.setImageResource(R.drawable.icons8_stop);
                    playButton.startAnimation(inAnimation);
                    changePlayImage = true;

                }

                else {
                    playButton.setImageResource(R.drawable.icons8_play_timer);
                    playButton.startAnimation(inAnimation);
                    changePlayImage = false;

                }

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });




        PushDownAnim.setPushDownAnimTo(playButton)
                .setScale(PushDownAnim.MODE_SCALE, 0.8f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (timerTime>0 && mCountDownTimer==null) {

                            playButton.startAnimation(outAnimation);
                            timerView.setPreferredScrollingDirection(TickerView.ScrollingDirection.UP);

                            startTimer(timerTime);
                        }

                        else {

                            stopTimer();
                            Toast.makeText(getBaseContext(), "Timer Cancelled", Toast.LENGTH_SHORT ).show();
                            playButton.startAnimation(outAnimation);




                        }


                    }
                });

    }

    private void setUpTimer()
    {

        timerView.setCharacterLists(TickerUtils.provideNumberList());
        timerView.setAnimationDuration(1000);

        final Typeface mFont = ResourcesCompat.getFont(this, R.font.montserrat );
        timerView.setTypeface(mFont);

        timerView.setText("0 : 00", true);

    }

    private void setUpTimerWave()
    {
        String[] waves = new String[]{
                "70,25,1.4,1.4,-50",//wave-1:offsetX(dp),offsetY(dp),scaleX,scaleY,velocity(dp/s)
                "100,25,1.4,1.2,50",
                "420,25,1.15,1,-10",//wave-3:水平偏移(dp),竖直偏移(dp),水平拉伸,竖直拉伸,速度(dp/s)

        };

        //timerWave.setStartColor(R.color.pPText);
        //timerWave.setCloseColor(R.color.timerWaveEndColor);

        timerWave.setWaveHeight(30);
        timerWave.setGradientAngle(90);
        timerWave.setProgress(0.1f);
        timerWave.setScaleY(-1f);
        timerWave.setVelocity(15f);
        timerWave.start();

    }

    private void setUpTimerSeekBar()
    {
        timerSeekBar.setProgress(0);
        timerSeekBar.setOnRoundedSeekChangeListener(new OnCircularSeekBarChangeListener() {
            @Override
            public void onProgressChange(CircularSeekBar CircularSeekBar, float progress) {

                timerTime =  round(((progress/100)*60), 5);
                timerView.setText(timerTime+" : 00", true);


            }

            @Override
            public void onStartTrackingTouch(CircularSeekBar CircularSeekBar) {

            }

            @Override
            public void onStopTrackingTouch(CircularSeekBar CircularSeekBar) {

            }
        });

    }

    int round(double i, int v){
        return (int) Math.round(i/v) * v;
    }

    private void startTimer(int min)
    {

        final float waveProgress = timerWave.getProgress();
        final float totalMillis = min*60000;


        mCountDownTimer = new CountDownTimer(min*60000, 100) {
            @Override
            public void onTick(long l) {


                    Log.d("TAG", "onTick: SetTickerView Entered");

                    int minutes = 0;
                    int seconds = 0;
                    if (l >= 60000) {
                        minutes = (int) l / 60000;
                        seconds = (int) (l - (minutes * 60000)) / 1000;

                    } else {
                        seconds = (int) l / 1000;

                    }

                    String minutesText = "00", secondsText = "00";
                    if (minutes != 0) {
                        minutesText = String.valueOf(minutes);

                    }

                    if (seconds != 0) {
                        secondsText = String.valueOf(seconds);

                    }

                    timerView.setText(minutesText + " : " + secondsText, true);
                    //Log.d("TAG", "onTick: timerText - "+minutesText+" : "+secondsText);



                int millisSpent = (int) (totalMillis-l);
                float percentage = ((float) millisSpent/ (float)(totalMillis));
                if (percentage>=0.1) {
                    timerWave.setProgress(percentage);
                }
                Log.d("TAG", "onTick: WavePercentage" + percentage);









            }

            @Override
            public void onFinish() {

                Toast.makeText(getBaseContext(), "Timer Completed", Toast.LENGTH_SHORT).show();


                timerView.setText("00 : 00",true);

                stopTimer();

            }
        };

        mCountDownTimer.start();

    }

    private void stopTimer()
    {

        if (mCountDownTimer!=null)
        {
            mCountDownTimer.cancel();
            timerSeekBar.setProgress(0.0f);
            timerWave.setProgress(0.1f);

            mCountDownTimer = null;
            timerStarted = false;

        }
    }


}
