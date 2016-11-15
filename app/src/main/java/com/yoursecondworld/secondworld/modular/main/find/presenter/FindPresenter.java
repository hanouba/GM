package com.yoursecondworld.secondworld.modular.main.find.presenter;

import com.yoursecondworld.secondworld.AppConfig;
import com.yoursecondworld.secondworld.common.CallbackAdapter;
import com.yoursecondworld.secondworld.common.Constant;
import com.yoursecondworld.secondworld.common.JsonRequestParameter;
import com.yoursecondworld.secondworld.common.StaticDataStore;
import com.yoursecondworld.secondworld.modular.dynamics.entity.DynamicsResult;
import com.yoursecondworld.secondworld.modular.main.find.entity.AdvResult;
import com.yoursecondworld.secondworld.modular.main.find.fragment.IFindView;
import com.yoursecondworld.secondworld.modular.systemInfo.entity.UserResult;

import retrofit2.Call;

/**
 * Created by cxj on 2016/8/17.
 */
public class FindPresenter {

    private IFindView view;

    public FindPresenter(IFindView findView) {
        this.view = findView;

    }

    /**
     * 获取广告
     */
    public void getAllAdv() {

        Call<AdvResult> call = AppConfig.netWorkService.getAllAdv(JsonRequestParameter.build(
                new String[]{Constant.RESULT_SESSION_ID, Constant.RESULT_OBJECT_ID},
                new Object[]{StaticDataStore.session_id, StaticDataStore.newUser.getUser_id()}));

        call.enqueue(new CallbackAdapter<AdvResult>(view) {

            @Override
            public void onResponse(AdvResult advResult) {
                view.onLoadAdvSuccess(advResult);
            }
        });

    }

    public void getAllPopupLarStar() {

        Call<UserResult> call = AppConfig.netWorkService.getAllPopupLarStar(JsonRequestParameter.build(
                new String[]{Constant.RESULT_SESSION_ID, Constant.RESULT_OBJECT_ID},
                new Object[]{StaticDataStore.session_id, StaticDataStore.newUser.getUser_id()}));

        call.enqueue(new CallbackAdapter<UserResult>(view) {

            @Override
            public void onResponse(UserResult userResult) {
                view.onLoadPopupLarStarSuccess(userResult);
            }
        });

    }

    public void getHotDynamics() {

        Call<DynamicsResult> call = AppConfig.netWorkService.get_hot_article_list(JsonRequestParameter.build(
                new String[]{Constant.RESULT_SESSION_ID, Constant.RESULT_OBJECT_ID, Constant.RESULT_PASS},
                new Object[]{StaticDataStore.session_id, StaticDataStore.newUser.getUser_id(), null}));

        call.enqueue(new CallbackAdapter<DynamicsResult>(view) {

            @Override
            public void onResponse(DynamicsResult dynamicsResult) {
                view.onloadHotDynamicsSuccess(dynamicsResult);
                view.savePass(dynamicsResult.getPass());
            }
        });


    }

    public void getMoreHotDynamics() {

        Call<DynamicsResult> call = AppConfig.netWorkService.get_hot_article_list(JsonRequestParameter.build(
                new String[]{Constant.RESULT_SESSION_ID, Constant.RESULT_OBJECT_ID, Constant.RESULT_PASS},
                new Object[]{StaticDataStore.session_id, StaticDataStore.newUser.getUser_id(), view.getPass()}));

        call.enqueue(new CallbackAdapter<DynamicsResult>(view) {

            @Override
            public void onResponse(DynamicsResult dynamicsResult) {
                view.onloadMoreHotDynamicsSuccess(dynamicsResult);
                view.savePass(dynamicsResult.getPass());
            }
        });

    }

}
