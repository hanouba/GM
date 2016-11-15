package com.yoursecondworld.secondworld.modular.commonFunction.loadMyDynamics.presenter;

import com.yoursecondworld.secondworld.AppConfig;
import com.yoursecondworld.secondworld.common.CallbackAdapter;
import com.yoursecondworld.secondworld.common.Constant;
import com.yoursecondworld.secondworld.common.JsonRequestParameter;
import com.yoursecondworld.secondworld.common.StaticDataStore;
import com.yoursecondworld.secondworld.common.baseResult.BaseEntity;
import com.yoursecondworld.secondworld.modular.commonFunction.loadMyDynamics.view.ILoadMyDynamicsView;
import com.yoursecondworld.secondworld.modular.dynamics.entity.DynamicsResult;

import retrofit2.Call;

/**
 * Created by cxj on 2016/9/2.
 */
public class LoadMyDynamicsPresenter {

    private ILoadMyDynamicsView view;

    public LoadMyDynamicsPresenter(ILoadMyDynamicsView view) {
        this.view = view;
    }

    /**
     * 加载动态,这个方法相当于刷新,加载更多用另外的方法
     */
    public void loadMyDynamics() {

        view.showDialog("正在加载");

        Call<DynamicsResult> call = AppConfig.netWorkService.getUserDynamics(JsonRequestParameter.build(
                new String[]{Constant.RESULT_SESSION_ID, Constant.RESULT_OBJECT_ID, Constant.RESULT_PASS, "user_id"},
                new Object[]{StaticDataStore.session_id, StaticDataStore.newUser.getUser_id(), null, view.getTargetUserId()}));

        call.enqueue(new CallbackAdapter<DynamicsResult>(view) {

            @Override
            public void onResponse(DynamicsResult dynamicsResult) {
                view.onLoadMyDynamicsSuccess(dynamicsResult.getArticles());
                view.savePass(dynamicsResult.getPass());
            }
        });

    }

    public void loadMoreMyDynamics() {

        view.showDialog("正在加载我的动态");

        Call<DynamicsResult> call = AppConfig.netWorkService.getUserDynamics(JsonRequestParameter.build(
                new String[]{Constant.RESULT_SESSION_ID, Constant.RESULT_OBJECT_ID, Constant.RESULT_PASS, "user_id"},
                new Object[]{StaticDataStore.session_id, StaticDataStore.newUser.getUser_id(), view.getPass(), view.getTargetUserId()}));

        call.enqueue(new CallbackAdapter<DynamicsResult>(view) {

            @Override
            public void onResponse(DynamicsResult dynamicsResult) {
                view.onLoadMoreMyDynamicsSuccess(dynamicsResult.getArticles());
                view.savePass(dynamicsResult.getPass());
            }
        });

    }

    public void deleteDynamics(String dynamicsId, final int postion) {

        view.showDialog("正在删除动态");

        Call<BaseEntity> call = AppConfig.netWorkService.deleteDynamics(JsonRequestParameter.build(
                new String[]{Constant.RESULT_SESSION_ID, Constant.RESULT_OBJECT_ID, "article_id"},
                new Object[]{StaticDataStore.session_id, StaticDataStore.newUser.getUser_id(), dynamicsId}));

        call.enqueue(new CallbackAdapter<BaseEntity>(view) {

            @Override
            public void onResponse(BaseEntity entity) {
                view.onDeleteDynamicsSuccess(postion);
            }
        });

    }

}
