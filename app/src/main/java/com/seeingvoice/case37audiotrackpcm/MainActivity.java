package com.seeingvoice.case37audiotrackpcm;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


//DUAL_MONO_MODE_OFF
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "MainActivity";
    private StreamThread mStreamThread;
    private StaticThread mStaticThread;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnPlay =  findViewById(R.id.btn_play);
        Button btnPlayS=  findViewById(R.id.btn_play_static);
        Button btnLeft =  findViewById(R.id.btn_left);
        Button btnRight = findViewById(R.id.btn_right);
        Button btnStop =  findViewById(R.id.btn_stop);

        btnPlay.setOnClickListener(this);
        btnPlayS.setOnClickListener(this);
        btnLeft.setOnClickListener(this);
        btnRight.setOnClickListener(this);
        btnStop.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int vid= view.getId();
        if (vid == R.id.btn_play_static) {
            if (null != mStaticThread) {
                mStaticThread.stopStatic();
                mStaticThread = null;
            }
            mStaticThread = new StaticThread(this, "seeingvoice.com_250Hz45_37_3s.wav");
            mStaticThread.start();
        }else if (vid == R.id.btn_play){
            if (null != mStreamThread) {
                mStreamThread.stopStream();
                mStreamThread = null;
            }
            mStreamThread = new StreamThread(this, "440Hz_44100Hz_16bit_30sec.wav");
            mStreamThread.start();
        }else if (vid == R.id.btn_left){
            if (null != mStaticThread)                mStaticThread.setChannel(false, true);
            if (null != mStreamThread)                mStreamThread.setChannel(false, true);
        }else if (vid == R.id.btn_right){
            if (null != mStaticThread)                mStaticThread.setChannel(true, false);
            if (null != mStreamThread)                mStreamThread.setChannel(true, false);
        }else if (vid == R.id.btn_stop){
            if (null != mStreamThread) {
                mStreamThread.stopStream();
                mStreamThread = null;
            }
            if (null != mStaticThread) {
                mStaticThread.stopStatic();
                mStaticThread = null;
            }
        }
    }
}