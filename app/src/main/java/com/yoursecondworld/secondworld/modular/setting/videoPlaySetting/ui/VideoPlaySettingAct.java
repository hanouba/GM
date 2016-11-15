package com.yoursecondworld.secondworld.modular.setting.videoPlaySetting.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yoursecondworld.secondworld.R;
import com.yoursecondworld.secondworld.common.BaseAct;
import com.yoursecondworld.secondworld.common.Constant;

import xiaojinzi.annotation.Injection;
import xiaojinzi.base.android.store.SPUtil;

/**
 * 视频播放的设置界面
 */
@Injection(R.layout.act_setting_video_play)
public class VideoPlaySettingAct extends BaseAct {

    @Injection(R.id.rl_act_titlebar)
    private RelativeLayout rl_titlebar = null;

    @Injection(value = R.id.iv_back, click = "clickView")
    private ImageView iv_back = null;

    @Injection(R.id.tv_title_name)
    private TextView tv_titleName = null;

    //===============================================

    @Injection(value = R.id.rl_act_setting_video_play_default_container, click = "clickView")
    private RelativeLayout rl_act_setting_video_play_default_container = null;

    @Injection(R.id.iv_act_setting_video_play_default)
    private ImageView iv_default = null;

    @Injection(value = R.id.rl_act_setting_video_play_three_four_wifi_container, click = "clickView")
    private RelativeLayout rl_act_setting_video_play_three_four_wifi_container = null;

    @Injection(R.id.iv_act_setting_video_play_three_four_wifi)
    private ImageView iv_threeG_fourG_Wifi = null;

    @Injection(value = R.id.rl_act_setting_video_play_not_auto_play_container, click = "clickView")
    private RelativeLayout rl_act_setting_video_play_not_auto_play_container = null;

    @Injection(R.id.iv_act_setting_video_play_not_auto_play)
    private ImageView iv_notAutoPlay = null;

    @Override
    public void initView() {
        super.initView();
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) rl_titlebar.getLayoutParams();
        lp.topMargin = statusHeight;

        //设置标题栏的标题
        tv_titleName.setText("视频播放");

        /**
         * 获取视频播放的模式i
         */
        int videoPlaMode = SPUtil.get(context, Constant.SP.mySetting.VIDEOPLAYMODE_FLAG, Constant.SP.mySetting.DEFAULTMODE);

        changeSelectState(videoPlaMode);

    }

    /**
     * 点击事件的集中处理
     *
     * @param v
     */
    public void clickView(View v) {

        //获取控件的id
        int id = v.getId();

        //对id的筛选然后做处理
        switch (id) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.rl_act_setting_video_play_default_container: //选择了默认的
                SPUtil.put(context, Constant.SP.mySetting.VIDEOPLAYMODE_FLAG, Constant.SP.mySetting.DEFAULTMODE);
                changeSelectState(Constant.SP.mySetting.DEFAULTMODE);
                break;
            case R.id.rl_act_setting_video_play_three_four_wifi_container: //选择了3G/4G/WiFi下自动播放的
                SPUtil.put(context, Constant.SP.mySetting.VIDEOPLAYMODE_FLAG, Constant.SP.mySetting.THREEG_FOURG_WIFI_MODE);
                changeSelectState(Constant.SP.mySetting.THREEG_FOURG_WIFI_MODE);
                break;
            case R.id.rl_act_setting_video_play_not_auto_play_container: //选择了不自动播放
                SPUtil.put(context, Constant.SP.mySetting.VIDEOPLAYMODE_FLAG, Constant.SP.mySetting.NOTAUTOPLAY_MODE);
                changeSelectState(Constant.SP.mySetting.NOTAUTOPLAY_MODE);
                break;
        }

    }

    @Override
    public boolean isRegisterEvent() {
        return false;
    }

    /**
     * 根据参数,来控制哪一个条目被选中了
     *
     * @param videoPlaMode
     */
    private void changeSelectState(int videoPlaMode) {
        if (videoPlaMode == Constant.SP.mySetting.THREEG_FOURG_WIFI_MODE) {
            iv_default.setVisibility(View.INVISIBLE);
            iv_threeG_fourG_Wifi.setVisibility(View.VISIBLE);
            iv_notAutoPlay.setVisibility(View.INVISIBLE);
        } else if (videoPlaMode == Constant.SP.mySetting.NOTAUTOPLAY_MODE) {
            iv_default.setVisibility(View.INVISIBLE);
            iv_threeG_fourG_Wifi.setVisibility(View.INVISIBLE);
            iv_notAutoPlay.setVisibility(View.VISIBLE);
        } else {
            iv_default.setVisibility(View.VISIBLE);
            iv_threeG_fourG_Wifi.setVisibility(View.INVISIBLE);
            iv_notAutoPlay.setVisibility(View.INVISIBLE);
        }
    }


}
