package com.seeingvoice.case37audiotrackpcm;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "MainActivity";
    private PlayThread tPlayThread;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnPlay =  findViewById(R.id.btn_play);
        Button btnLeft =  findViewById(R.id.btn_left);
        Button btnRight = findViewById(R.id.btn_right);
        Button btnStop =  findViewById(R.id.btn_stop);

        btnPlay.setOnClickListener(this);
        btnLeft.setOnClickListener(this);
        btnRight.setOnClickListener(this);
        btnStop.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int vid= view.getId();
        if (vid == R.id.btn_play) {
            if (null != tPlayThread) {
                tPlayThread.stopp();
                tPlayThread = null;
            }
            tPlayThread = new PlayThread(this, "seeingvoice.com_sin_1500Hz_0dBFS_3s.wav");
            tPlayThread.start();
        }else if (vid == R.id.btn_left){
            if (null != tPlayThread)
                tPlayThread.setChannel(false, true);
        }else if (vid == R.id.btn_right){
            if (null != tPlayThread)
                tPlayThread.setChannel(true, false);
        }else if (vid == R.id.btn_stop){
            if (null != tPlayThread) {
                tPlayThread.stopp();
                tPlayThread = null;
            }
        }
    }
}