package com.yoursecondworld.secondworld.modular.userDetail.presenter;


import com.yoursecondworld.secondworld.AppConfig;
import com.yoursecondworld.secondworld.common.CallbackAdapter;
import com.yoursecondworld.secondworld.common.Constant;
import com.yoursecondworld.secondworld.common.JsonRequestParameter;
import com.yoursecondworld.secondworld.common.StaticDataStore;
import com.yoursecondworld.secondworld.modular.systemInfo.entity.UserResult;
import com.yoursecondworld.secondworld.modular.userDetail.view.IUserDetailView;

import retrofit2.Call;


/**
 * Created by cxj on 2016/8/26.
 * 用户详情界面的主持人
 */
public class UserDetailPresenter {

    private IUserDetailView view;

    public UserDetailPresenter(IUserDetailView view) {
        this.view = view;
    }

    /**
     * 获取用户信息
     */
    public void getUserInfo() {

        Call<UserResult> call = AppConfig.netWorkService.getUserInfo(JsonRequestParameter.build(
                new String[]{Constant.RESULT_SESSION_ID, Constant.RESULT_OBJECT_ID, "user_id"},
                new Object[]{StaticDataStore.session_id, StaticDataStore.newUser.getUser_id(), view.getTargetUserId()}));

        call.enqueue(new CallbackAdapter<UserResult>(view) {

            @Override
            public void onResponse(UserResult userResult) {
                userResult.getUser().setGames(userResult.getGames());
                view.onLoadUserInfoSuccess(userResult);
            }
        });

    }
}
