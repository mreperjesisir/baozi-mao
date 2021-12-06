package com.example.catsbirthdayboost;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public ImageView mCat;
    public ImageView mCake;
    //TODO: find a less fugly birthday text
    public ImageView mBday;
    public ImageButton mBirthdayButton;
    public TextView mSubtext;
    private MediaPlayer mPlayer;
    private AudioManager mManager;

    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                mPlayer.pause();
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN){
                mPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS){
                releaseMediaPlayer();
            }
        }
    };

    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
            finish();
        }
    };

    private void releaseMediaPlayer() {
        if (mPlayer!=null){
            mPlayer.release();
            mPlayer = null;
            mManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);

        mCat = findViewById(R.id.mao);
        mCake = findViewById(R.id.cake);
        mBday = findViewById(R.id.bday);
        mSubtext = findViewById(R.id.subText);
        mBirthdayButton = findViewById(R.id.button_birthday);
        mBirthdayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBirthdayButton.setVisibility(Button.INVISIBLE);
                mSubtext.setVisibility(TextView.INVISIBLE);
                playMusic();
                animationIn();
            }
        });
    }

    private void animationIn() {
        ObjectAnimator catAnimator = ObjectAnimator.ofFloat(mCat, "translationX", 150f);
        ObjectAnimator catAnimatorUp = ObjectAnimator.ofFloat(mCat, "translationY", -300f);
        ObjectAnimator cakeAnimator = ObjectAnimator.ofFloat(mCake, "translationX", 420f);
        ObjectAnimator cakeAnimatorUp = ObjectAnimator.ofFloat(mCake, "translationY", -300f);
        ObjectAnimator bdayAnimator = ObjectAnimator.ofFloat(mBday, "translationY", -950f);

        catAnimatorUp.setDuration(4000);
        catAnimatorUp.start();
        cakeAnimatorUp.setDuration(4000);
        cakeAnimatorUp.start();
        catAnimator.setDuration(4000);
        catAnimator.start();
        cakeAnimator.setDuration(4000);
        cakeAnimator.start();
        bdayAnimator.setDuration(4000);
        bdayAnimator.start();

        //TODO: here I rotate stuff with some butchered code.
        // Find a more elegant way to do it, something that loops animations

        ObjectAnimator catRotatorFirst = ObjectAnimator.ofFloat(mCat, "rotation", -15f);
        catRotatorFirst.setStartDelay(4000);
        catRotatorFirst.setDuration(500);
        catRotatorFirst.start();

        ObjectAnimator cakeRotatorFirst = ObjectAnimator.ofFloat(mCake, "rotation", 15f);
        cakeRotatorFirst.setStartDelay(4000);
        cakeRotatorFirst.setDuration(500);
        cakeRotatorFirst.start();

        ObjectAnimator catRotator = ObjectAnimator.ofFloat(mCat, "rotation", 30f);
        catRotator.setStartDelay(4500);
        catRotator.setDuration(500);
        catRotator.setRepeatMode(ObjectAnimator.REVERSE);
        catRotator.setRepeatCount(50);
        catRotator.start();

        ObjectAnimator cakeRotator = ObjectAnimator.ofFloat(mCake, "rotation", -30f);
        cakeRotator.setStartDelay(4500);
        cakeRotator.setDuration(500);
        cakeRotator.setRepeatMode(ObjectAnimator.REVERSE);
        cakeRotator.setRepeatCount(50);
        cakeRotator.start();





    }
    //TODO: Kill the music when we switch between apps
    private void playMusic(){
        int result = mManager.requestAudioFocus(mOnAudioFocusChangeListener,AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
            mPlayer = MediaPlayer.create(this, R.raw.hb);
            mPlayer.start();
            mPlayer.setOnCompletionListener(mCompletionListener);
        }
    }
}