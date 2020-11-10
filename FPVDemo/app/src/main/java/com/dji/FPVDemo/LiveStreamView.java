package com.dji.FPVDemo;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.dji.FPVDemo.R;

import com.dji.FPVDemo.FPVDemoApplication;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.Nullable;
import dji.sdk.base.BaseProduct;
import dji.sdk.camera.VideoFeeder;
import dji.sdk.sdkmanager.DJISDKManager;
import dji.sdk.sdkmanager.LiveStreamManager;

/**
 * Class for live stream demo.
 *
 * @author Hoker
 * @date 2019/1/28
 * <p>
 * Copyright (c) 2019, DJI All Rights Reserved.
 */
public class LiveStreamView extends Activity implements PresentableView, View.OnClickListener {

    private String liveShowUrl = "rtmp://10.61.24.252:1935/live/test";

    private VideoFeedView primaryVideoFeedView;
    private VideoFeedView fpvVideoFeedView;
    private EditText showUrlInputEdit;

    private Button startLiveShowBtn;
    private Button enableVideoEncodingBtn;
    private Button disableVideoEncodingBtn;
    private Button stopLiveShowBtn;
    private Button soundOnBtn;
    private Button soundOffBtn;
    private Button isLiveShowOnBtn;
    private Button showInfoBtn;
    private Button showLiveStartTimeBtn;
    private Button showCurrentVideoSourceBtn;
    private Button changeVideoSourceBtn;

    private LiveStreamManager.OnLiveChangeListener listener;
    private LiveStreamManager.LiveStreamVideoSource currentVideoSource = LiveStreamManager.LiveStreamVideoSource.Primary;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_live_stream);
        initUI();
        initListener();
    }


    private void initUI() {
        primaryVideoFeedView = (VideoFeedView) findViewById(R.id.video_view_primary_video_feed);
        primaryVideoFeedView.registerLiveVideo(VideoFeeder.getInstance().getPrimaryVideoFeed(), true);

        fpvVideoFeedView = (VideoFeedView) findViewById(R.id.video_view_fpv_video_feed);
        fpvVideoFeedView.registerLiveVideo(VideoFeeder.getInstance().getSecondaryVideoFeed(), false);
        if (Helper.isMultiStreamPlatform()) {
            fpvVideoFeedView.setVisibility(View.VISIBLE);
        }

        showUrlInputEdit = (EditText) findViewById(R.id.edit_live_show_url_input);
        showUrlInputEdit.setText(liveShowUrl);
        showUrlInputEdit.setOnClickListener(this);

        startLiveShowBtn = (Button) findViewById(R.id.btn_start_live_show);
        enableVideoEncodingBtn = (Button) findViewById(R.id.btn_enable_video_encode);
        disableVideoEncodingBtn = (Button) findViewById(R.id.btn_disable_video_encode);
        stopLiveShowBtn = (Button) findViewById(R.id.btn_stop_live_show);
        soundOnBtn = (Button) findViewById(R.id.btn_sound_on);
        soundOffBtn = (Button) findViewById(R.id.btn_sound_off);
        isLiveShowOnBtn = (Button) findViewById(R.id.btn_is_live_show_on);
        showInfoBtn = (Button) findViewById(R.id.btn_show_info);
        showLiveStartTimeBtn = (Button) findViewById(R.id.btn_show_live_start_time);
        showCurrentVideoSourceBtn = (Button) findViewById(R.id.btn_show_current_video_source);
        changeVideoSourceBtn = (Button) findViewById(R.id.btn_change_video_source);

        startLiveShowBtn.setOnClickListener(this);
        enableVideoEncodingBtn.setOnClickListener(this);
        disableVideoEncodingBtn.setOnClickListener(this);
        stopLiveShowBtn.setOnClickListener(this);
        soundOnBtn.setOnClickListener(this);
        soundOffBtn.setOnClickListener(this);
        isLiveShowOnBtn.setOnClickListener(this);
        showInfoBtn.setOnClickListener(this);
        showLiveStartTimeBtn.setOnClickListener(this);
        showCurrentVideoSourceBtn.setOnClickListener(this);
        changeVideoSourceBtn.setOnClickListener(this);

    }

    private void initListener() {
        showUrlInputEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                liveShowUrl = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        listener = new LiveStreamManager.OnLiveChangeListener() {
            @Override
            public void onStatusChanged(int i) {
                //wang
//                ToastUtils.setResultToToast("status changed : " + i);
            }
        };
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        BaseProduct product = FPVDemoApplication.getProductInstance();
        if (product == null || !product.isConnected()) {
            Toast.makeText(getApplicationContext(),"Disconnect",Toast.LENGTH_SHORT).show();
            //wang
//            ToastUtils.setResultToToast("Disconnect");
            return;
        }
        if (isLiveStreamManagerOn()) {
            DJISDKManager.getInstance().getLiveStreamManager().registerListener(listener);
        }
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (isLiveStreamManagerOn()) {
            DJISDKManager.getInstance().getLiveStreamManager().unregisterListener(listener);
        }
    }

    @Override
    public int getDescription() {
        return R.string.component_listview_live_stream;
    }

    @NonNull
    @Override
    public String getHint() {
        return this.getClass().getSimpleName() + ".java";
    }

    void startLiveShow() {
        //wang
        Toast.makeText(getApplicationContext(),"Start Live Show",Toast.LENGTH_SHORT).show();
//        ToastUtils.setResultToToast("Start Live Show");
        if (!isLiveStreamManagerOn()) {
            return;
        }
        if (DJISDKManager.getInstance().getLiveStreamManager().isStreaming()) {
            //wang
            Toast.makeText(getApplicationContext(),"already started!",Toast.LENGTH_SHORT).show();
//            ToastUtils.setResultToToast("already started!");
            return;
        }
        new Thread() {
            @Override
            public void run() {
                DJISDKManager.getInstance().getLiveStreamManager().setLiveUrl(liveShowUrl);
                int result = DJISDKManager.getInstance().getLiveStreamManager().startStream();
                DJISDKManager.getInstance().getLiveStreamManager().setStartTime();

                //wang
                Toast.makeText(getApplicationContext(),"startLive:" + result +
                        "\n isVideoStreamSpeedConfigurable:" + DJISDKManager.getInstance().getLiveStreamManager().isVideoStreamSpeedConfigurable() +
                        "\n isLiveAudioEnabled:" + DJISDKManager.getInstance().getLiveStreamManager().isLiveAudioEnabled(),Toast.LENGTH_SHORT).show();
              /*  ToastUtils.setResultToToast("startLive:" + result +
                        "\n isVideoStreamSpeedConfigurable:" + DJISDKManager.getInstance().getLiveStreamManager().isVideoStreamSpeedConfigurable() +
                        "\n isLiveAudioEnabled:" + DJISDKManager.getInstance().getLiveStreamManager().isLiveAudioEnabled());*/
            }
        }.start();
    }

    private void enableReEncoder() {
        if (!isLiveStreamManagerOn()) {
            return;
        }
        DJISDKManager.getInstance().getLiveStreamManager().setVideoEncodingEnabled(true);
        //wang
        Toast.makeText(getApplicationContext(),"Force Re-Encoder Enabled!",Toast.LENGTH_SHORT).show();
        /// ToastUtils.setResultToToast("Force Re-Encoder Enabled!");
    }

    private void disableReEncoder() {
        if (!isLiveStreamManagerOn()) {
            return;
        }
        DJISDKManager.getInstance().getLiveStreamManager().setVideoEncodingEnabled(false);
        //wang
        Toast.makeText(getApplicationContext(),"Disable Force Re-Encoder!",Toast.LENGTH_SHORT).show();
        //    ToastUtils.setResultToToast("Disable Force Re-Encoder!");
    }

    private void stopLiveShow() {
        if (!isLiveStreamManagerOn()) {
            return;
        }
        DJISDKManager.getInstance().getLiveStreamManager().stopStream();
        //wang
        Toast.makeText(getApplicationContext(),"Stop Live Show",Toast.LENGTH_SHORT).show();
        //  ToastUtils.setResultToToast("Stop Live Show");
    }

    private void soundOn() {
        if (!isLiveStreamManagerOn()) {
            return;
        }
        DJISDKManager.getInstance().getLiveStreamManager().setAudioMuted(false);
        //wang
        Toast.makeText(getApplicationContext(),"Sound On",Toast.LENGTH_SHORT).show();
        //    ToastUtils.setResultToToast("Sound On");
    }

    private void soundOff() {
        if (!isLiveStreamManagerOn()) {
            return;
        }
        DJISDKManager.getInstance().getLiveStreamManager().setAudioMuted(true);
        //wang
        Toast.makeText(getApplicationContext(),"Sound Off",Toast.LENGTH_SHORT).show();
        // ToastUtils.setResultToToast("Sound Off");
    }

    private void isLiveShowOn() {
        if (!isLiveStreamManagerOn()) {
            return;
        }
        //wang
        Toast.makeText(getApplicationContext(),"Is Live Show On:" + DJISDKManager.getInstance().getLiveStreamManager().isStreaming(),Toast.LENGTH_SHORT).show();
        //ToastUtils.setResultToToast("Is Live Show On:" + DJISDKManager.getInstance().getLiveStreamManager().isStreaming());
    }

    private void showInfo() {
        if (!isLiveStreamManagerOn()) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Video BitRate:").append(DJISDKManager.getInstance().getLiveStreamManager().getLiveVideoBitRate()).append(" kpbs\n");
        sb.append("Audio BitRate:").append(DJISDKManager.getInstance().getLiveStreamManager().getLiveAudioBitRate()).append(" kpbs\n");
        sb.append("Video FPS:").append(DJISDKManager.getInstance().getLiveStreamManager().getLiveVideoFps()).append("\n");
        sb.append("Video Cache size:").append(DJISDKManager.getInstance().getLiveStreamManager().getLiveVideoCacheSize()).append(" frame");
        //wang
        Toast.makeText(getApplicationContext(),sb.toString(),Toast.LENGTH_SHORT).show();
        //    ToastUtils.setResultToToast(sb.toString());
    }

    private void showLiveStartTime() {
        if (!isLiveStreamManagerOn()) {
            return;
        }
        if (!DJISDKManager.getInstance().getLiveStreamManager().isStreaming()) {
            //wang
            Toast.makeText(getApplicationContext(),"Please Start Live First",Toast.LENGTH_SHORT).show();
            //     ToastUtils.setResultToToast("Please Start Live First");
            return;
        }
        long startTime = DJISDKManager.getInstance().getLiveStreamManager().getStartTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String sd = sdf.format(new Date(Long.parseLong(String.valueOf(startTime))));
        //wang
        Toast.makeText(getApplicationContext(),"Live Start Time: " + sd,Toast.LENGTH_SHORT).show();
        //      ToastUtils.setResultToToast("Live Start Time: " + sd);
    }

    private void changeVideoSource() {
        if (!isLiveStreamManagerOn()) {
            return;
        }
        if (!isSupportSecondaryVideo()) {
            return;
        }
        if (DJISDKManager.getInstance().getLiveStreamManager().isStreaming()) {
            //wang
            Toast.makeText(getApplicationContext(),"Before change live source, you should stop live stream!",Toast.LENGTH_SHORT).show();
            //    ToastUtils.setResultToToast("Before change live source, you should stop live stream!");
            return;
        }
        currentVideoSource = (currentVideoSource == LiveStreamManager.LiveStreamVideoSource.Primary) ?
                LiveStreamManager.LiveStreamVideoSource.Secoundary :
                LiveStreamManager.LiveStreamVideoSource.Primary;
        DJISDKManager.getInstance().getLiveStreamManager().setVideoSource(currentVideoSource);
        //wang
        Toast.makeText(getApplicationContext(),"Change Success ! Video Source : " + currentVideoSource.name(),Toast.LENGTH_SHORT).show();
        //   ToastUtils.setResultToToast("Change Success ! Video Source : " + currentVideoSource.name());
    }

    private void showCurrentVideoSource() {
        //    ToastUtils.setResultToToast("Video Source : " + currentVideoSource.name());
        Toast.makeText(getApplicationContext(),"Video Source : " + currentVideoSource.name(),Toast.LENGTH_SHORT).show();
        //wang
    }

    private boolean isLiveStreamManagerOn() {
        if (DJISDKManager.getInstance().getLiveStreamManager() == null) {
            //    ToastUtils.setResultToToast("No live stream manager!");
            //wang
            Toast.makeText(getApplicationContext(),"No live stream manager!",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean isSupportSecondaryVideo() {
        if (!Helper.isMultiStreamPlatform()) {
            // ToastUtils.setResultToToast("No secondary video!");
            //wang
            Toast.makeText(getApplicationContext(),"No secondary video!",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_start_live_show:
                startLiveShow();
                break;
            case R.id.btn_enable_video_encode:
                enableReEncoder();
                break;
            case R.id.btn_disable_video_encode:
                disableReEncoder();
                break;
            case R.id.btn_stop_live_show:
                stopLiveShow();
                break;
            case R.id.btn_sound_on:
                soundOn();
                break;
            case R.id.btn_sound_off:
                soundOff();
                break;
            case R.id.btn_is_live_show_on:
                isLiveShowOn();
                break;
            case R.id.btn_show_info:
                showInfo();
                break;
            case R.id.btn_show_live_start_time:
                showLiveStartTime();
                break;
            case R.id.btn_show_current_video_source:
                showCurrentVideoSource();
                break;
            case R.id.btn_change_video_source:
                changeVideoSource();
                break;
            case R.id.edit_live_show_url_input:
                showUrlInputEdit.setText("");
                break;
            default:
                break;
        }
    }
}
