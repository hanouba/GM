package com.yoursecondworld.secondworld.modular.search.presenter;

import android.text.TextUtils;

import com.yoursecondworld.secondworld.AppConfig;
import com.yoursecondworld.secondworld.common.CallbackAdapter;
import com.yoursecondworld.secondworld.common.Constant;
import com.yoursecondworld.secondworld.common.JsonRequestParameter;
import com.yoursecondworld.secondworld.common.StaticDataStore;
import com.yoursecondworld.secondworld.modular.dynamics.entity.DynamicsResult;
import com.yoursecondworld.secondworld.modular.search.ui.ISearchView;
import com.yoursecondworld.secondworld.modular.systemInfo.entity.UserResult;

import okhttp3.RequestBody;
import retrofit2.Call;

/**
 * Created by cxj on 2016/9/3.
 */
public class SearchPresenter {

    private ISearchView view;

    public SearchPresenter(ISearchView view) {
        this.view = view;
    }

    /**
     * 搜索动态
     */
    public void searchDynamics() {

        if (TextUtils.isEmpty(view.getSearchContent())) {
            view.tip("搜索内容不能为空哦");
            return;
        }

        view.showDialog("正在搜索动态");

        RequestBody body = JsonRequestParameter.build(
                new String[]{Constant.RESULT_SESSION_ID, Constant.RESULT_OBJECT_ID, Constant.RESULT_PASS, "to_search"},
                new Object[]{StaticDataStore.session_id, StaticDataStore.newUser.getUser_id(), null, view.getSearchContent()}
        );

        Call<DynamicsResult> call = AppConfig.netWorkService.searchDynamics(body);

        call.enqueue(new CallbackAdapter<DynamicsResult>(view) {
            @Override
            public void onResponse(DynamicsResult dynamicsResult) {
                view.onSearchDynamicsSuccess(dynamicsResult.getArticles());
                view.savePass(dynamicsResult.getPass());
            }
        });

    }

    public void searchMoreDynamics() {

        if (TextUtils.isEmpty(view.getSearchContent())) {
            view.tip("搜索内容不能为空哦");
            return;
        }

        view.showDialog("正在搜索动态");

        RequestBody body = JsonRequestParameter.build(
                new String[]{Constant.RESULT_SESSION_ID, Constant.RESULT_OBJECT_ID, Constant.RESULT_PASS, "to_search"},
                new Object[]{StaticDataStore.session_id, StaticDataStore.newUser.getUser_id(), view.getPass(), view.getSearchContent()}
        );

        Call<DynamicsResult> call = AppConfig.netWorkService.searchDynamics(body);

        call.enqueue(new CallbackAdapter<DynamicsResult>(view) {
            @Override
            public void onResponse(DynamicsResult dynamicsResult) {
                view.onSearchMoreDynamicsSuccess(dynamicsResult.getArticles());
                view.savePass(dynamicsResult.getPass());
            }
        });

    }

    /**
     * 搜索用户
     */
    public void searchUser() {

        if (TextUtils.isEmpty(view.getSearchContent())) {
            view.tip("搜索内容不能为空哦");
            return;
        }

        view.showDialog("正在搜索用户");

        RequestBody body = JsonRequestParameter.build(
                new String[]{Constant.RESULT_SESSION_ID, Constant.RESULT_OBJECT_ID, Constant.RESULT_PASS, "to_search"},
                new Object[]{StaticDataStore.session_id, StaticDataStore.newUser.getUser_id(), null, view.getSearchContent()}
        );

        Call<UserResult> call = AppConfig.netWorkService.searchUser(body);

        call.enqueue(new CallbackAdapter<UserResult>(view) {
            @Override
            public void onResponse(UserResult userResult) {
                view.onSearchUsersSuccess(userResult.getUsers());
                view.savePass(userResult.getPass());
            }
        });

    }

    public void searchMoreUser() {

        if (TextUtils.isEmpty(view.getSearchContent())) {
            view.tip("搜索内容不能为空哦");
            return;
        }

        view.showDialog("正在搜索用户");

        RequestBody body = JsonRequestParameter.build(
                new String[]{Constant.RESULT_SESSION_ID, Constant.RESULT_OBJECT_ID, Constant.RESULT_PASS, "to_search"},
                new Object[]{StaticDataStore.session_id, StaticDataStore.newUser.getUser_id(), view.getPass(), view.getSearchContent()}
        );

        Call<UserResult> call = AppConfig.netWorkService.searchUser(body);

        call.enqueue(new CallbackAdapter<UserResult>(view) {
            @Override
            public void onResponse(UserResult userResult) {
                view.onSearchMoreUsersSuccess(userResult.getUsers());
                view.savePass(userResult.getPass());
            }
        });


    }

}
