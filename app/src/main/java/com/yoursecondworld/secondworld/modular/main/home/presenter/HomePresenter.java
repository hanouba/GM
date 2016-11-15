package com.yoursecondworld.secondworld.modular.main.home.presenter;


import com.yoursecondworld.secondworld.AppConfig;
import com.yoursecondworld.secondworld.common.CallbackAdapter;
import com.yoursecondworld.secondworld.common.Constant;
import com.yoursecondworld.secondworld.common.JsonRequestParameter;
import com.yoursecondworld.secondworld.common.StaticDataStore;
import com.yoursecondworld.secondworld.modular.dynamics.entity.DynamicsResult;
import com.yoursecondworld.secondworld.modular.main.home.fragment.IHomeView;

import retrofit2.Call;

/**
 * Created by cxj on 2016/8/29.
 * 去除了之前的Model层面代码
 */
public class HomePresenter {

    private IHomeView view;

    public HomePresenter(IHomeView view) {
        this.view = view;
    }

    /**
     * 获取动态
     */
    public void getDynamics() {

        Call<DynamicsResult> call = AppConfig.netWorkService.loadDynamics(JsonRequestParameter.build(
                new String[]{Constant.RESULT_SESSION_ID, Constant.RESULT_OBJECT_ID, Constant.RESULT_PASS},
                new Object[]{StaticDataStore.session_id, StaticDataStore.newUser.getUser_id(), null}));

        call.enqueue(new CallbackAdapter<DynamicsResult>(view) {

            @Override
            public void onResponse(DynamicsResult dynamicsResult) {
                view.onLoadDynamicsSuccess(dynamicsResult);
                view.savePass(dynamicsResult.getPass());
            }
        });

    }

    public void loadMoreDynamics() {

        Call<DynamicsResult> call = AppConfig.netWorkService.loadDynamics(JsonRequestParameter.build(
                new String[]{Constant.RESULT_SESSION_ID, Constant.RESULT_OBJECT_ID, Constant.RESULT_PASS},
                new Object[]{StaticDataStore.session_id, StaticDataStore.newUser.getUser_id(), view.getPass()}));

        call.enqueue(new CallbackAdapter<DynamicsResult>(view) {

            @Override
            public void onResponse(DynamicsResult dynamicsResult) {
                view.onLoadMoreDynamicsSuccess(dynamicsResult);
                view.savePass(dynamicsResult.getPass());
            }
        });

    }

    /**
     * 获取游戏的动态
     */
    public void getGameDynamics() {

        Call<DynamicsResult> call = AppConfig.netWorkService.get_game_hot_article_list(JsonRequestParameter.build(
                new String[]{Constant.RESULT_SESSION_ID, Constant.RESULT_OBJECT_ID, Constant.RESULT_PASS, "game"},
                new Object[]{StaticDataStore.session_id, StaticDataStore.newUser.getUser_id(), null, view.getGameTag()}));

        call.enqueue(new CallbackAdapter<DynamicsResult>(view) {

            @Override
            public void onResponse(DynamicsResult dynamicsResult) {
                view.onLoadDynamicsSuccess(dynamicsResult);
                view.savePass(dynamicsResult.getPass());
            }
        });

    }

    /**
     * 加载更多的游戏动态
     */
    public void loadMoreGameDynamics() {
        Call<DynamicsResult> call = AppConfig.netWorkService.get_game_hot_article_list(JsonRequestParameter.build(
                new String[]{Constant.RESULT_SESSION_ID, Constant.RESULT_OBJECT_ID, Constant.RESULT_PASS, "game"},
                new Object[]{StaticDataStore.session_id, StaticDataStore.newUser.getUser_id(), view.getPass(), view.getGameTag()}));

        call.enqueue(new CallbackAdapter<DynamicsResult>(view) {

            @Override
            public void onResponse(DynamicsResult dynamicsResult) {
                view.onLoadMoreDynamicsSuccess(dynamicsResult);
                view.savePass(dynamicsResult.getPass());
            }
        });
    }

    /**
     * 获取我关注的列表
     */
    public void getMyFollowDynamics() {

        Call<DynamicsResult> call = AppConfig.netWorkService.get_mylist(JsonRequestParameter.build(
                new String[]{Constant.RESULT_SESSION_ID, Constant.RESULT_OBJECT_ID, Constant.RESULT_PASS},
                new Object[]{StaticDataStore.session_id, StaticDataStore.newUser.getUser_id(), null}));

        call.enqueue(new CallbackAdapter<DynamicsResult>(view) {

            @Override
            public void onResponse(DynamicsResult dynamicsResult) {
                view.onLoadFollowDynamicsSuccess(dynamicsResult);
                view.savePass(dynamicsResult.getPass());
            }
        });

    }

    public void getMoreMyFollowDynamics() {

        Call<DynamicsResult> call = AppConfig.netWorkService.get_mylist(JsonRequestParameter.build(
                new String[]{Constant.RESULT_SESSION_ID, Constant.RESULT_OBJECT_ID, Constant.RESULT_PASS},
                new Object[]{StaticDataStore.session_id, StaticDataStore.newUser.getUser_id(), view.getPass()}));

        call.enqueue(new CallbackAdapter<DynamicsResult>(view) {

            @Override
            public void onResponse(DynamicsResult dynamicsResult) {
                view.onLoadMoreFollowDynamicsSuccess(dynamicsResult);
                view.savePass(dynamicsResult.getPass());
            }
        });

    }

}
