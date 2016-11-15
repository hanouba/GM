package com.yoursecondworld.secondworld.modular.gameTimeDetail.view;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.yoursecondworld.secondworld.R;
import com.yoursecondworld.secondworld.commonBean.MasterInfo;

import xiaojinzi.annotation.Injection;
import xiaojinzi.annotation.ViewInjectionUtil;

/**
 * Created by cxj on 2016/10/31.
 * 游戏时间详情的Header
 */
public class GameTimeDetailHeader {

    private TextView tv_surplus;
    private TextView tv_time;

    /**
     * 初始化视图
     *
     * @param mContext 上下文
     * @return
     */
    public View init(Context mContext, int month) {

        View contentView = View.inflate(mContext, R.layout.act_game_time_detail_header, null);

        ((TextView) contentView.findViewById(R.id.tv1)).setText("你" + month + "月游戏时间");

        tv_surplus = (TextView) contentView.findViewById(R.id.tv_surplus);
        tv_time = (TextView) contentView.findViewById(R.id.tv_time);

        return contentView;

    }

    public void disPlay(MasterInfo masterInfo) {

        tv_surplus.setText("剩余/h：" + (masterInfo.getTime_left() * 100 / 60 / 100f));
        tv_time.setText(masterInfo.getCost_time() * 100 / 60 / 100f + "");

    }

}
