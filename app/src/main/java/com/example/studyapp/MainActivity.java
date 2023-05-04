package com.example.studyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.Format;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final long START_TIME_IN_MILLIS = 1500000 ;
    private TextView mTextViewCountDown;
    private Button mButtonStartPause;
    private Button mButtonReset;

    private CountDownTimer mCountDownTimer;
    private  boolean mTimerRunning;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextViewCountDown = findViewById(R.id.text_view_countdown);
        mButtonStartPause = findViewById(R.id.button_start_pause);
        mButtonReset = findViewById(R.id.button_reset);


        mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mTimerRunning)
                {
                    pauseTimer();
                }else {
                    startTimer();
                }

            }
        });
        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();

            }
        });
        updateCountDownText();
    }
    @SuppressLint("SetTextI18n")
    private void startTimer(){
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();


            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                mButtonStartPause.setText("Start");

            }
        }.start();
        mTimerRunning= true;
        mButtonStartPause.setText("Pause");

    }
    @SuppressLint("SetTextI18n")
    private void pauseTimer(){mCountDownTimer.cancel();
    mTimerRunning=false;
    mButtonStartPause.setText("Start");
    };
    private void resetTimer(){
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();

    };

    private void updateCountDownText()
    {
        int minutes = (int) (mTimeLeftInMillis/1000)/60;
        int seconds = (int) (mTimeLeftInMillis/1000)%60;

        @SuppressLint("DefaultLocale") String timeLeftFormatted = String.format("%02d:%02d",minutes,seconds);
        mTextViewCountDown.setText(timeLeftFormatted);


    }

}