package com.yoursecondworld.secondworld.modular.dynamicsDetail.view;

import android.widget.ImageView;
import android.widget.TextView;

import com.yoursecondworld.secondworld.modular.dynamics.entity.NewDynamics;
import com.yoursecondworld.secondworld.modular.dynamicsDetail.entity.DynamicsComment;
import com.yoursecondworld.secondworld.modular.mvp.view.IBaseView;

import java.util.List;

/**
 * Created by cxj on 2016/8/28.
 * 动态详情的View接口
 */
public interface IDynamicsDetailView extends IBaseView {

    /**
     * 获取动态的id
     *
     * @return
     */
    String getDynamicsId();

    /**
     * 获取楼主的用户id
     *
     * @return
     */
    String getUserId();

    /**
     * 数据加载成功回调用
     *
     * @param dynamics
     */
    void onLoadDataSuccess(NewDynamics dynamics);

    /**
     * 评论列表加载成功
     *
     * @param dynamicsComments
     */
    void onLoadCommentDataSuccess(List<DynamicsComment> dynamicsComments);

    void onLoadMoreCommentDataSuccess(List<DynamicsComment> dynamicsComments);

    void savePass(String pass);

    String getPass();

    void onZanCommentSuccess(TextView tv_zan, ImageView iv_zan, DynamicsComment dynamicsComment, int liked_number);

    void onCancelZanCommentSuccess(TextView tv_zan, ImageView iv_zan, DynamicsComment dynamicsComment, int liked_number);
}
