package com.yoursecondworld.secondworld.modular.postDynamics.view;

import android.app.ProgressDialog;
import android.content.Context;

import com.yoursecondworld.secondworld.modular.db.dynamicsDraft.DynamicsDraftDao;
import com.yoursecondworld.secondworld.modular.mvp.view.IBaseView;
import com.yoursecondworld.secondworld.modular.systemInfo.entity.GameLabel;

import java.util.List;

/**
 * Created by cxj on 2016/8/24.
 */
public interface IPostDynamicsView extends IBaseView {

    /**
     * 获取对话框
     *
     * @return
     */
    ProgressDialog getDialog();


    /**
     * 获取被选中的图片的集合,元素是图片路径
     * 是本地的图片的地址
     * 上传成功后的地址在presenter里面会保存
     *
     * @return
     */
    List<String> getSelectImages();

    /**
     * 获取视频的地址,本地的,不是上传之后的
     * 上传成功后的地址在presenter里面会保存
     *
     * @return
     */
    String getVideoPath();

    /**
     * 获取动态内容
     *
     * @return
     */
    String getDynamicsContent();


    String getTopic();

    /**
     * 获取剁手时刻的金额
     *
     * @return
     */
    Integer getMoney();

    /**
     * 获取游戏标签
     *
     * @return
     */
    String getGameLabel();

    /**
     * 获取额外的信息
     *
     * @return
     */
    String getExtraInfo();

    /**
     * 获取上下文
     *
     * @return
     */
    Context getContext();

    /**
     * 结束activity
     */
    void finishActivity();


}
