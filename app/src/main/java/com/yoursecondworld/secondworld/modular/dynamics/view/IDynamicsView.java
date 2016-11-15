package com.yoursecondworld.secondworld.modular.dynamics.view;

import android.widget.ImageView;
import android.widget.TextView;

import com.yoursecondworld.secondworld.modular.dynamics.entity.DynamicsContentEntity;
import com.yoursecondworld.secondworld.modular.dynamics.entity.NewDynamics;
import com.yoursecondworld.secondworld.modular.mvp.view.IBaseView;

import java.util.List;

/**
 * Created by cxj on 2016/8/19.
 */
public interface IDynamicsView extends IBaseView {

    void onZanSuccess(TextView tv_zan, final ImageView iv_zan, NewDynamics dynamics, int liked_number);

    void onCancleZanSuccess(TextView tv_zan, final ImageView iv_zan, NewDynamics dynamics, int liked_number);

    void onCollectSuccess(ImageView iv_collect, NewDynamics dynamics);

    void onUnCollectSuccess(ImageView iv_collect, NewDynamics dynamics);

    /**
     * 举报动态成功
     */
    void onReportDynamicsSuccess();

}
