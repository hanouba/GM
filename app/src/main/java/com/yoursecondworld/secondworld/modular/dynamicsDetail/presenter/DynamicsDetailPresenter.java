package com.yoursecondworld.secondworld.modular.dynamicsDetail.presenter;

import android.widget.ImageView;
import android.widget.TextView;

import com.yoursecondworld.secondworld.AppConfig;
import com.yoursecondworld.secondworld.common.CallbackAdapter;
import com.yoursecondworld.secondworld.common.Constant;
import com.yoursecondworld.secondworld.common.JsonRequestParameter;
import com.yoursecondworld.secondworld.common.StaticDataStore;
import com.yoursecondworld.secondworld.modular.dynamics.entity.DynamicsResult;
import com.yoursecondworld.secondworld.modular.dynamicsDetail.entity.DynamicsComment;
import com.yoursecondworld.secondworld.modular.dynamicsDetail.entity.DynamicsCommentResult;
import com.yoursecondworld.secondworld.modular.dynamicsDetail.entity.DynamicsCommentZanResult;
import com.yoursecondworld.secondworld.modular.dynamicsDetail.view.IDynamicsDetailView;

import retrofit2.Call;

/**
 * Created by cxj on 2016/8/28.
 */
public class DynamicsDetailPresenter {

    private IDynamicsDetailView view = null;

    public DynamicsDetailPresenter(IDynamicsDetailView view) {
        this.view = view;
    }

    /**
     * 加载动态详情
     */
    public void getDynamicsById() {

        view.showDialog("正在加载");

        Call<DynamicsResult> call = AppConfig.netWorkService.getDynamicsById(JsonRequestParameter.build(
                new String[]{Constant.RESULT_SESSION_ID, Constant.RESULT_OBJECT_ID, "article_id"},
                new Object[]{StaticDataStore.session_id, StaticDataStore.newUser.getUser_id(), view.getDynamicsId()}));

        call.enqueue(new CallbackAdapter<DynamicsResult>(view) {

            @Override
            public void onResponse(DynamicsResult dynamicsResult) {
                view.onLoadDataSuccess(dynamicsResult.getArticle());
            }
        });

    }

    /**
     * 获取动态的评论
     */
    public void getDynamicsComment() {

        view.showDialog("正在加载评论列表");

        Call<DynamicsCommentResult> call = AppConfig.netWorkService.getDynamicsComment(JsonRequestParameter.build(
                new String[]{Constant.RESULT_SESSION_ID, Constant.RESULT_OBJECT_ID, "article_id"},
                new Object[]{StaticDataStore.session_id, StaticDataStore.newUser.getUser_id(), view.getDynamicsId()}));

        call.enqueue(new CallbackAdapter<DynamicsCommentResult>(view) {

            @Override
            public void onResponse(DynamicsCommentResult dynamicsCommentResult) {
                view.onLoadCommentDataSuccess(dynamicsCommentResult.getComments());
                view.savePass(dynamicsCommentResult.getPass());
            }
        });

    }

    /**
     * 加载更多评论
     */
    public void getMoreDynamicsComment() {
        view.showDialog("正在加载评论列表");

        Call<DynamicsCommentResult> call = AppConfig.netWorkService.getDynamicsComment(JsonRequestParameter.build(
                new String[]{Constant.RESULT_SESSION_ID, Constant.RESULT_OBJECT_ID, Constant.RESULT_PASS, "article_id"},
                new Object[]{StaticDataStore.session_id, StaticDataStore.newUser.getUser_id(), view.getPass(), view.getDynamicsId()}));

        call.enqueue(new CallbackAdapter<DynamicsCommentResult>(view) {

            @Override
            public void onResponse(DynamicsCommentResult dynamicsCommentResult) {
                view.onLoadMoreCommentDataSuccess(dynamicsCommentResult.getComments());
                view.savePass(dynamicsCommentResult.getPass());
            }
        });
    }

    public void like_comments(final TextView tv_zan, final ImageView iv_zan, final DynamicsComment dynamicsComment, String comment_id) {

        Call<DynamicsCommentZanResult> call = AppConfig.netWorkService.like_comments(JsonRequestParameter.build(
                new String[]{Constant.RESULT_SESSION_ID, Constant.RESULT_OBJECT_ID, "comment_id"},
                new Object[]{StaticDataStore.session_id, StaticDataStore.newUser.getUser_id(), comment_id}));

        call.enqueue(new CallbackAdapter<DynamicsCommentZanResult>(view) {

            @Override
            public void onResponse(DynamicsCommentZanResult dynamicsCommentZanResult) {
                view.onZanCommentSuccess(tv_zan, iv_zan, dynamicsComment, dynamicsCommentZanResult.getLiked_number());
            }
        });

    }

    public void unlike_comments(final TextView tv_zan, final ImageView iv_zan, final DynamicsComment dynamicsComment, String comment_id) {

        Call<DynamicsCommentZanResult> call = AppConfig.netWorkService.unlike_comments(JsonRequestParameter.build(
                new String[]{Constant.RESULT_SESSION_ID, Constant.RESULT_OBJECT_ID, "comment_id"},
                new Object[]{StaticDataStore.session_id, StaticDataStore.newUser.getUser_id(), comment_id}));

        call.enqueue(new CallbackAdapter<DynamicsCommentZanResult>(view) {

            @Override
            public void onResponse(DynamicsCommentZanResult dynamicsCommentZanResult) {
                view.onCancelZanCommentSuccess(tv_zan, iv_zan, dynamicsComment, dynamicsCommentZanResult.getLiked_number());
            }
        });

    }
}
