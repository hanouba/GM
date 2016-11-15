package com.yoursecondworld.secondworld.common.videoFullScreenPlay;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.net.Uri;
import android.widget.MediaController;
import android.widget.VideoView;

import com.yoursecondworld.secondworld.R;
import com.yoursecondworld.secondworld.common.BaseAct;
import com.yoursecondworld.secondworld.common.Constant;
import com.yoursecondworld.secondworld.common.event.SessionInvalidEvent;

import org.greenrobot.eventbus.Subscribe;

import xiaojinzi.annotation.Injection;
import xiaojinzi.base.android.log.L;
import xiaojinzi.base.android.os.NetUtils;
import xiaojinzi.base.android.store.SPUtil;

/**
 * 视频全屏播放
 * 添加了播放视频时候的省流量提醒
 */
@Injection(R.layout.act_video_full_screen_play)
public class VideoFullScreenPlayAct extends BaseAct {

    /**
     * 上一个界面传过来的视频的地址
     */
    public static final String VIDEO_FALG = "video_falg";

    @Injection(R.id.video)
    private VideoView video;

    /**
     * 视频播放的地址
     */
    private String videoUrl;

    //    @Override
    public void initView() {
        super.initView();
        //横屏
        L.s(TAG, "初始化");
    }

    //    @Override
    public void initData() {

        //是否需要省流量
        boolean isShengLiuLiang = SPUtil.get(context, Constant.SP.mySetting.MOBILE_NETWORK_TRAFFIC_FLAG, false);
        boolean isWifi = NetUtils.isWifi(context);

        if (isShengLiuLiang && !isWifi) {
            AlertDialog dialog = new AlertDialog.Builder(context).setMessage("目前处于移动网络下,是否继续播放").setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    playVedio();
                }
            }).create();
            dialog.show();
        } else {
            playVedio();
        }

    }

    private void playVedio() {
        //获取到视频播放的地址
        videoUrl = getIntent().getStringExtra(VIDEO_FALG);

        L.s(TAG, videoUrl);

        video.setVideoURI(Uri.parse(videoUrl));

        MediaController m = new MediaController(context);

        video.setMediaController(m);

        video.start();

        video.setKeepScreenOn(true);
    }

    @Override
    public void setOnlistener() {
        super.setOnlistener();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {

        }
    }


    @Override
    public boolean isRegisterEvent() {
        return true;
    }

    private int seek = -1;

    //比如说来电了,或者home键
    @Override
    protected void onPause() {
        super.onPause();
        if (video.isPlaying()) {
            video.pause();
            seek = video.getCurrentPosition();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (seek != -1) {
            video.seekTo(seek);
            video.start();
            seek = -1;
        }
    }

    /**
     * 当sessionId失效了
     */
    @Subscribe
    public void onSessionIdInvalid(SessionInvalidEvent event) {
        finish();
    }

}
