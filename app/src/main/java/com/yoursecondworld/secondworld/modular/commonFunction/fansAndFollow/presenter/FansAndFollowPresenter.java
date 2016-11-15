package com.yoursecondworld.secondworld.modular.commonFunction.fansAndFollow.presenter;

import com.yoursecondworld.secondworld.AppConfig;
import com.yoursecondworld.secondworld.common.CallbackAdapter;
import com.yoursecondworld.secondworld.common.Constant;
import com.yoursecondworld.secondworld.common.JsonRequestParameter;
import com.yoursecondworld.secondworld.common.StaticDataStore;
import com.yoursecondworld.secondworld.modular.commonFunction.fansAndFollow.view.IFansAndFollowView;
import com.yoursecondworld.secondworld.modular.systemInfo.entity.UserResult;

import retrofit2.Call;

/**
 * Created by cxj on 2016/9/9.
 */
public class FansAndFollowPresenter {

    private IFansAndFollowView view;

    public FansAndFollowPresenter(IFansAndFollowView view) {
        this.view = view;
    }

    public void loadUnFollowFans() {

        Call<UserResult> call = AppConfig.netWorkService.loadUnFollowFans(JsonRequestParameter.build(
                new String[]{Constant.RESULT_SESSION_ID, Constant.RESULT_OBJECT_ID},
                new Object[]{StaticDataStore.session_id, StaticDataStore.newUser.getUser_id()}));

        call.enqueue(new CallbackAdapter<UserResult>(view) {
            @Override
            public void onResponse(UserResult entity) {
                view.onLoadUnFollowFansSuccess(entity.getUsers());
                view.savePass(entity.getPass());
            }

            @Override
            public void onFailure(Call<UserResult> call, Throwable t) {
                super.onFailure(call, t);
                view.tip("加载失败");
            }
        });
    }

    public void loadMoreUnFollowFans() {

        Call<UserResult> call = AppConfig.netWorkService.loadUnFollowFans(JsonRequestParameter.build(
                new String[]{Constant.RESULT_SESSION_ID, Constant.RESULT_OBJECT_ID,Constant.RESULT_PASS},
                new Object[]{StaticDataStore.session_id, StaticDataStore.newUser.getUser_id(),view.getPass()}));

        call.enqueue(new CallbackAdapter<UserResult>(view) {
            @Override
            public void onResponse(UserResult entity) {
                view.onLoadMoreUnFollowFansSuccess(entity.getUsers());
                view.savePass(entity.getPass());
            }

            @Override
            public void onFailure(Call<UserResult> call, Throwable t) {
                super.onFailure(call, t);
                view.tip("加载失败");
            }
        });

    }

    public void getFollowEachOther() {

        Call<UserResult> call = AppConfig.netWorkService.getFollowEachOther(JsonRequestParameter.build(
                new String[]{Constant.RESULT_SESSION_ID, Constant.RESULT_OBJECT_ID},
                new Object[]{StaticDataStore.session_id, StaticDataStore.newUser.getUser_id()}));

        call.enqueue(new CallbackAdapter<UserResult>(view) {
            @Override
            public void onResponse(UserResult userResult) {
                for (int i = 0; i < userResult.getUsers().size(); i++) {
                    userResult.getUsers().get(i).setFollow(true);
                }
                view.onLoadFollowEachOtherSuccess(userResult.getUsers());
            }

            @Override
            public void onFailure(Call<UserResult> call, Throwable t) {
                super.onFailure(call, t);
                view.tip("加载失败");
            }
        });
    }

}
