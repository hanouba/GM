package com.yoursecondworld.secondworld.modular.prepareModule.improvePersonInfo.view;

import android.content.Context;

import com.yoursecondworld.secondworld.modular.mvp.view.IBaseView;

/**
 * Created by cxj on 2016/8/11.
 */
public interface IImprovePersonInfoView extends IBaseView {

    /**
     * 获取昵称
     *
     * @return
     */
    String getNickName();

    /**
     * 获取性别
     *
     * @return
     */
    String getSex();

    String getDefultImageUrl();

    /**
     * 获取本地的图片的路径,用于上传头像
     *
     * @return
     */
    String getLocalImagePath();

    /**
     * 完善信息成功的回掉
     */
    void onProveSuccess();

    Context getContext();

    String getUserId();

    String getBirth();
}
