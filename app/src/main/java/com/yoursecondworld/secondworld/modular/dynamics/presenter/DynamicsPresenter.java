package com.yoursecondworld.secondworld.modular.dynamics.presenter;

import android.widget.ImageView;
import android.widget.TextView;

import com.yoursecondworld.secondworld.AppConfig;
import com.yoursecondworld.secondworld.common.CallbackAdapter;
import com.yoursecondworld.secondworld.common.Constant;
import com.yoursecondworld.secondworld.common.JsonRequestParameter;
import com.yoursecondworld.secondworld.common.StaticDataStore;
import com.yoursecondworld.secondworld.common.baseResult.BaseEntity;
import com.yoursecondworld.secondworld.modular.dynamics.entity.NewDynamics;
import com.yoursecondworld.secondworld.modular.dynamics.entity.ZanResult;
import com.yoursecondworld.secondworld.modular.dynamics.view.IDynamicsView;

import retrofit2.Call;

/**
 * Created by cxj on 2016/8/19.
 * 动态的主持人,抽取了点赞、收藏、取消点赞等功能
 */
public class DynamicsPresenter {

    private IDynamicsView view;

    public DynamicsPresenter(IDynamicsView dynamicsView) {
        this.view = dynamicsView;
    }

    /**
     * 点赞
     *
     * @param tv_zan
     * @param iv_zan
     * @param dynamics
     * @param dynamicsId
     */
    public void zan(final TextView tv_zan, final ImageView iv_zan, final NewDynamics dynamics, String dynamicsId) {

        Call<ZanResult> call = AppConfig.netWorkService.zan(JsonRequestParameter.build(
                new String[]{Constant.RESULT_SESSION_ID, Constant.RESULT_OBJECT_ID, "article_id"},
                new Object[]{StaticDataStore.session_id, StaticDataStore.newUser.getUser_id(), dynamicsId}));

        call.enqueue(new CallbackAdapter<ZanResult>(view) {

            @Override
            public void onResponse(ZanResult zanResult) {
                view.onZanSuccess(tv_zan, iv_zan, dynamics, zanResult.getLiked_number());
            }
        });

    }

    /**
     * 取消点赞
     *
     * @param tv_zan
     * @param iv_zan
     * @param dynamics
     * @param dynamicsId
     */
    public void cancelZan(final TextView tv_zan, final ImageView iv_zan, final NewDynamics dynamics, String dynamicsId) {

        Call<ZanResult> call = AppConfig.netWorkService.cancelZan(JsonRequestParameter.build(
                new String[]{Constant.RESULT_SESSION_ID, Constant.RESULT_OBJECT_ID, "article_id"},
                new Object[]{StaticDataStore.session_id, StaticDataStore.newUser.getUser_id(), dynamicsId}));

        call.enqueue(new CallbackAdapter<ZanResult>(view) {

            @Override
            public void onResponse(ZanResult zanResult) {
                view.onCancleZanSuccess(tv_zan, iv_zan, dynamics, zanResult.getLiked_number());
            }
        });

    }

    /**
     * 收藏
     *
     * @param iv_collect
     * @param dynamics
     * @param dynamicsId
     */
    public void collect_article(final ImageView iv_collect, final NewDynamics dynamics, String dynamicsId) {


        Call<BaseEntity> call = AppConfig.netWorkService.collect_article(JsonRequestParameter.build(
                new String[]{Constant.RESULT_SESSION_ID, Constant.RESULT_OBJECT_ID, "article_id"},
                new Object[]{StaticDataStore.session_id, StaticDataStore.newUser.getUser_id(), dynamicsId}));

        call.enqueue(new CallbackAdapter<BaseEntity>(view) {

            @Override
            public void onResponse(BaseEntity entity) {
                view.onCollectSuccess(iv_collect, dynamics);
            }
        });

    }

    /**
     * 取消收藏
     *
     * @param iv_collect
     * @param dynamics
     * @param dynamicsId
     */
    public void uncollect_article(final ImageView iv_collect, final NewDynamics dynamics, String dynamicsId) {

        Call<BaseEntity> call = AppConfig.netWorkService.uncollect_article(JsonRequestParameter.build(
                new String[]{Constant.RESULT_SESSION_ID, Constant.RESULT_OBJECT_ID, "article_id"},
                new Object[]{StaticDataStore.session_id, StaticDataStore.newUser.getUser_id(), dynamicsId}));

        call.enqueue(new CallbackAdapter<BaseEntity>(view) {

            @Override
            public void onResponse(BaseEntity entity) {
                view.onUnCollectSuccess(iv_collect, dynamics);
            }
        });

    }

    public void report_article(String dynamicsId) {

        Call<BaseEntity> call = AppConfig.netWorkService.report_article(JsonRequestParameter.build(
                new String[]{Constant.RESULT_SESSION_ID, Constant.RESULT_OBJECT_ID, "article_id", "reason"},
                new Object[]{StaticDataStore.session_id, StaticDataStore.newUser.getUser_id(), dynamicsId, ""}));

        call.enqueue(new CallbackAdapter<BaseEntity>(view) {

            @Override
            public void onResponse(BaseEntity entity) {
                view.onReportDynamicsSuccess();
                view.tip("举报此动态成功");
            }
        });

    }

}
