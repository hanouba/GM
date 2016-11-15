package com.yoursecondworld.secondworld.modular.commonFunction.searchUserDynamics.presenter;

import com.yoursecondworld.secondworld.AppConfig;
import com.yoursecondworld.secondworld.common.CallbackAdapter;
import com.yoursecondworld.secondworld.common.Constant;
import com.yoursecondworld.secondworld.common.JsonRequestParameter;
import com.yoursecondworld.secondworld.common.StaticDataStore;
import com.yoursecondworld.secondworld.modular.commonFunction.searchUserDynamics.view.ISearchUserDynamicsView;
import com.yoursecondworld.secondworld.modular.dynamics.entity.DynamicsResult;

import okhttp3.RequestBody;
import retrofit2.Call;

/**
 * Created by cxj on 2016/9/12.
 */
public class SearchUserDynamicsPresenter {

    private ISearchUserDynamicsView view;

    public SearchUserDynamicsPresenter(ISearchUserDynamicsView view) {
        this.view = view;
    }

    /**
     * 搜索用户的发布的文章
     */
    public void searchUserDynamics() {

        view.showDialog("正在搜索");

        RequestBody body = JsonRequestParameter.build(
                new String[]{Constant.RESULT_SESSION_ID, Constant.RESULT_OBJECT_ID, Constant.RESULT_PASS, "user_id", "to_search"},
                new Object[]{StaticDataStore.session_id, StaticDataStore.newUser.getUser_id(), null, StaticDataStore.newUser.getUser_id(), view.getSearchContent()}
        );

        Call<DynamicsResult> call = AppConfig.netWorkService.search_user_article(body);

        call.enqueue(new CallbackAdapter<DynamicsResult>(view) {
            @Override
            public void onResponse(DynamicsResult dynamicsResult) {
                view.disPlay(dynamicsResult.getArticles());
                view.savePass(dynamicsResult.getPass());
            }
        });

    }

    /**
     * 搜索更多的用户动态
     */
    public void searchMoreUserDynamics() {
        view.showDialog("正在搜索");

        RequestBody body = JsonRequestParameter.build(
                new String[]{Constant.RESULT_SESSION_ID, Constant.RESULT_OBJECT_ID, Constant.RESULT_PASS, "user_id", "to_search"},
                new Object[]{StaticDataStore.session_id, StaticDataStore.newUser.getUser_id(), view.getPass(), StaticDataStore.newUser.getUser_id(), view.getSearchContent()}
        );

        Call<DynamicsResult> call = AppConfig.netWorkService.search_user_article(body);

        call.enqueue(new CallbackAdapter<DynamicsResult>(view) {
            @Override
            public void onResponse(DynamicsResult dynamicsResult) {
                view.disPlayMore(dynamicsResult.getArticles());
                view.savePass(dynamicsResult.getPass());
            }
        });

    }

}
