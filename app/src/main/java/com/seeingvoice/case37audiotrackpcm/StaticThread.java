package com.seeingvoice.case37audiotrackpcm;

import android.app.Activity;
import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static com.seeingvoice.case37audiotrackpcm.GlobalConfig.SAMPLE_RATE;

/**
 * Created by LeoReny@hypech.com on 2021/2/10.
 */

public class StaticThread extends Thread {
    private static final String TAG = "Static";

    private Activity    mActivity;
    private AudioTrack  mAudioTrack;
    private byte[] audioData;
    private String mFileName;

    public StaticThread(Activity activity, String fileName) {
        mActivity = activity;
        mFileName = fileName;

        try {
            // InputStream in = mActivity.getResources().openRawResource(R.raw.ss);
            InputStream in = mActivity.getAssets().open("seeingvoice.com_250Hz85_2_3s.wav");
            try {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                for (int b; (b = in.read()) != -1; ) {
                    out.write(b);
                }
                Log.d(TAG, "Got the data");
                audioData = out.toByteArray();
            } finally {
                in.close();
            }
        } catch (IOException e) {
            Log.wtf(TAG, "Failed to read", e);
        }
        mAudioTrack = new AudioTrack(
                new AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build(),
                new AudioFormat.Builder().setSampleRate(SAMPLE_RATE)
                        .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                        .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                        .build(),
                audioData.length,
                AudioTrack.MODE_STATIC,
                AudioManager.AUDIO_SESSION_ID_GENERATE);
        Log.e(TAG, "Writing audio data..."+audioData.length);
    }

    @Override
    public void run() {
        super.run();
        Log.e(TAG, "Writing audio data..."+audioData.length);
        mAudioTrack.write(audioData, 44, audioData.length-44);
        mAudioTrack.play();
    }

    /**
     * 设置左右声道是否可用
     *
     * @param left  左声道
     * @param right 右声道
     */
    public void setChannel(boolean left, boolean right) {
        if (null != mAudioTrack) {
            mAudioTrack.setStereoVolume(left ? 1 : 0, right ? 1 : 0);
            mAudioTrack.play();
        }
    }

    public void play() {
        if (null != mAudioTrack)
            mAudioTrack.play();
    }

    public void stopStatic() {
        releaseAudioTrack();
    }

    private void releaseAudioTrack() {
        if (null != mAudioTrack) {
            mAudioTrack.stop();
            mAudioTrack.release();
            mAudioTrack = null;
        }
    }
}
