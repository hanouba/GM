package com.yoursecondworld.secondworld.modular.statusNotification.presenter;

import com.yoursecondworld.secondworld.AppConfig;
import com.yoursecondworld.secondworld.common.CallbackAdapter;
import com.yoursecondworld.secondworld.common.Constant;
import com.yoursecondworld.secondworld.common.JsonRequestParameter;
import com.yoursecondworld.secondworld.common.StaticDataStore;
import com.yoursecondworld.secondworld.modular.statusNotification.enity.CommentsListResult;
import com.yoursecondworld.secondworld.modular.statusNotification.enity.ZanListResult;
import com.yoursecondworld.secondworld.modular.statusNotification.ui.IStatusNotificationView;

import retrofit2.Call;

/**
 * Created by cxj on 2016/9/19.
 */
public class StatusNotificationPresenter {

    private IStatusNotificationView view;

    public StatusNotificationPresenter(IStatusNotificationView view) {
        this.view = view;
    }

    public void getCommentsList() {

        Call<CommentsListResult> call = AppConfig.netWorkService.mes_get_comments_list(JsonRequestParameter.build(
                new String[]{Constant.RESULT_SESSION_ID, Constant.RESULT_OBJECT_ID, Constant.RESULT_PASS},
                new Object[]{StaticDataStore.session_id, StaticDataStore.newUser.getUser_id(), null}));

        call.enqueue(new CallbackAdapter<CommentsListResult>(view) {
            @Override
            public void onResponse(CommentsListResult commentsListResult) {
                view.onLoadCommentsListSuccess(commentsListResult.getEvent());
                view.savePass(commentsListResult.getPass());
            }
        });
    }

    public void getMoreCommentsList() {


        Call<CommentsListResult> call = AppConfig.netWorkService.mes_get_comments_list(JsonRequestParameter.build(
                new String[]{Constant.RESULT_SESSION_ID, Constant.RESULT_OBJECT_ID, Constant.RESULT_PASS},
                new Object[]{StaticDataStore.session_id, StaticDataStore.newUser.getUser_id(), view.getPass()}));

        call.enqueue(new CallbackAdapter<CommentsListResult>(view) {
            @Override
            public void onResponse(CommentsListResult commentsListResult) {
                view.onLoadMoreCommentsListSuccess(commentsListResult.getEvent());
                view.savePass(commentsListResult.getPass());
            }

            @Override
            public void onFailure(Call<CommentsListResult> call, Throwable t) {
                super.onFailure(call, t);
                view.tip("加载失败");
            }
        });

    }

    public void getZanList() {


        Call<ZanListResult> call = AppConfig.netWorkService.mes_get_likes_list(JsonRequestParameter.build(
                new String[]{Constant.RESULT_SESSION_ID, Constant.RESULT_OBJECT_ID, Constant.RESULT_PASS},
                new Object[]{StaticDataStore.session_id, StaticDataStore.newUser.getUser_id(), null}));

        call.enqueue(new CallbackAdapter<ZanListResult>(view) {
            @Override
            public void onResponse(ZanListResult zanListResult) {
                view.onLoadZanListSuccess(zanListResult.getEvent());
                view.savePass(zanListResult.getPass());
            }
        });

    }

    public void getMoreZanList() {

        Call<ZanListResult> call = AppConfig.netWorkService.mes_get_likes_list(JsonRequestParameter.build(
                new String[]{Constant.RESULT_SESSION_ID, Constant.RESULT_OBJECT_ID, Constant.RESULT_PASS},
                new Object[]{StaticDataStore.session_id, StaticDataStore.newUser.getUser_id(), view.getPass()}));

        call.enqueue(new CallbackAdapter<ZanListResult>(view) {
            @Override
            public void onResponse(ZanListResult zanListResult) {
                view.onLoadMoreZanListSuccess(zanListResult.getEvent());
                view.savePass(zanListResult.getPass());
            }
        });

    }

}
