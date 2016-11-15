package com.yoursecondworld.secondworld.modular.prepareModule.selectPlayer.presenter;

import com.yoursecondworld.secondworld.AppConfig;
import com.yoursecondworld.secondworld.common.CallbackAdapter;
import com.yoursecondworld.secondworld.common.Constant;
import com.yoursecondworld.secondworld.common.JsonRequestParameter;
import com.yoursecondworld.secondworld.common.StaticDataStore;
import com.yoursecondworld.secondworld.modular.prepareModule.selectPlayer.ui.ISelectPlayerView;
import com.yoursecondworld.secondworld.modular.systemInfo.entity.UserResult;

import retrofit2.Call;

/**
 * Created by cxj on 2016/9/18.
 */
public class SelectPlayerPresenter {

    private ISelectPlayerView view;

    public SelectPlayerPresenter(ISelectPlayerView view) {
        this.view = view;
    }

    public void get_recommend_user_list() {

        Call<UserResult> call = AppConfig.netWorkService.get_recommend_user_list(JsonRequestParameter.build(
                new String[]{Constant.RESULT_SESSION_ID, Constant.RESULT_OBJECT_ID},
                new Object[]{StaticDataStore.session_id, StaticDataStore.newUser.getUser_id()}));

        call.enqueue(new CallbackAdapter<UserResult>(view) {
            @Override
            public void onResponse(UserResult userResult) {
                view.onLoadUsersSuccess(userResult.getUsers());
            }
        });

    }

}
