package com.yoursecondworld.secondworld.modular.myAttention.presenter;

import com.yoursecondworld.secondworld.AppConfig;
import com.yoursecondworld.secondworld.common.CallbackAdapter;
import com.yoursecondworld.secondworld.common.Constant;
import com.yoursecondworld.secondworld.common.JsonRequestParameter;
import com.yoursecondworld.secondworld.common.StaticDataStore;
import com.yoursecondworld.secondworld.modular.myAttention.view.IMyAttentionView;
import com.yoursecondworld.secondworld.modular.systemInfo.entity.UserResult;

import retrofit2.Call;

/**
 * Created by cxj on 2016/8/18.
 * 我的关注的主持人
 */
public class MyAttentionPresenter {

    private IMyAttentionView myAttentionView;

    public MyAttentionPresenter(IMyAttentionView myAttentionView) {
        this.myAttentionView = myAttentionView;
    }

    /**
     * 获取我的关注
     */
    public void getMeFollow() {

        Call<UserResult> call = AppConfig.netWorkService.getUserFollows(JsonRequestParameter.build(
                new String[]{Constant.RESULT_SESSION_ID, Constant.RESULT_OBJECT_ID},
                new Object[]{StaticDataStore.session_id, StaticDataStore.newUser.getUser_id()}));

        call.enqueue(new CallbackAdapter<UserResult>(myAttentionView) {

            @Override
            public void onResponse(UserResult userResult) {
                myAttentionView.onLoadDataSuccess(userResult.getUsers());
                myAttentionView.savePass(userResult.getPass());
            }
        });

    }

    public void getMoreMeFollow() {

        Call<UserResult> call = AppConfig.netWorkService.getUserFollows(JsonRequestParameter.build(
                new String[]{Constant.RESULT_SESSION_ID, Constant.RESULT_OBJECT_ID, Constant.RESULT_PASS},
                new Object[]{StaticDataStore.session_id, StaticDataStore.newUser.getUser_id(), myAttentionView.getPass()}));

        call.enqueue(new CallbackAdapter<UserResult>(myAttentionView) {

            @Override
            public void onResponse(UserResult userResult) {
                myAttentionView.onLoadMoreDataSuccess(userResult.getUsers());
                myAttentionView.savePass(userResult.getPass());
            }
        });

    }

    public void search_followed_user() {

        Call<UserResult> call = AppConfig.netWorkService.search_followed_user(JsonRequestParameter.build(
                new String[]{Constant.RESULT_SESSION_ID, Constant.RESULT_OBJECT_ID, "to_search"},
                new Object[]{StaticDataStore.session_id, StaticDataStore.newUser.getUser_id(), myAttentionView.getSearchKey()}));

        call.enqueue(new CallbackAdapter<UserResult>(myAttentionView) {

            @Override
            public void onResponse(UserResult userResult) {
                myAttentionView.onLoadSearchDataSuccess(userResult.getUsers());
            }
        });

    }


}
