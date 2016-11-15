package com.yoursecondworld.secondworld.modular.personInfo.view;

import android.content.Context;

import com.yoursecondworld.secondworld.common.StaticDataStore;
import com.yoursecondworld.secondworld.modular.mvp.view.IBaseView;

import java.util.List;

/**
 * Created by cxj on 2016/9/6.
 */
public interface IEditPersonInfoView extends IBaseView {


    /**
     * 获取要上传的本地图片地址
     */
    String getImagepath();

    /**
     * 获取上下文
     *
     * @return
     */
    Context getContext();

    /**
     * 获取当前用户的id
     *
     * @return
     */
    String getUserId();

    String getNickName();

    String getDesc();

    void finishActivity();

    void onUpdateNickNameSuccess();

    void onUpdateDescSuccess();

    String getSex();

    void onUpdateSexSuccess();

    void onUpdateBirthSuccess();

    String getBirth();
}
