package com.yoursecondworld.secondworld.modular.commonFunction.postComment.view;

import com.yoursecondworld.secondworld.modular.mvp.view.IBaseView;

/**
 * Created by cxj on 2016/9/20.
 */
public interface IpostCommentView extends IBaseView {

    /**
     * 清空评论内容
     */
    void clearCommentContent();

    /**
     * 评论成功
     */
    void onCommentSuccess();



    /**
     * 获取评论的目标用户
     *
     * @return
     */
    String getComentTargetUserId();

    /**
     * 获取动态的id
     *
     * @return
     */
    String getDynamicsId();

    /**
     * 获取评论内容
     *
     * @return
     */
    String getCommentContent();

}
