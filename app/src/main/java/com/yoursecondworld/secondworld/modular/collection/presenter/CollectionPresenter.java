package com.yoursecondworld.secondworld.modular.collection.presenter;

import com.yoursecondworld.secondworld.AppConfig;
import com.yoursecondworld.secondworld.common.CallbackAdapter;
import com.yoursecondworld.secondworld.common.Constant;
import com.yoursecondworld.secondworld.common.JsonRequestParameter;
import com.yoursecondworld.secondworld.common.StaticDataStore;
import com.yoursecondworld.secondworld.modular.collection.view.ICollectionView;
import com.yoursecondworld.secondworld.modular.dynamics.entity.DynamicsResult;

import retrofit2.Call;

/**
 * Created by cxj on 2016/8/12.
 */
public class CollectionPresenter {

    private ICollectionView collectionView;

    public CollectionPresenter(ICollectionView collectionView) {
        this.collectionView = collectionView;
    }

    /**
     * 获取收藏的动态
     */
    public void getCollectDynamics() {

        Call<DynamicsResult> call = AppConfig.netWorkService.getCollectedArticle(JsonRequestParameter.build(
                new String[]{Constant.RESULT_SESSION_ID, Constant.RESULT_OBJECT_ID, Constant.RESULT_PASS},
                new Object[]{StaticDataStore.session_id, StaticDataStore.newUser.getUser_id(), null}));

        call.enqueue(new CallbackAdapter<DynamicsResult>(collectionView) {

            @Override
            public void onResponse(DynamicsResult dynamicsResult) {
                collectionView.onLoadDataSuccess(dynamicsResult.getArticles());
                collectionView.savePass(dynamicsResult.getPass());
            }
        });

    }

    /**
     * 获取更多的动态
     */
    public void getMoreCollectDynamics() {

        Call<DynamicsResult> call = AppConfig.netWorkService.getCollectedArticle(JsonRequestParameter.build(
                new String[]{Constant.RESULT_SESSION_ID, Constant.RESULT_OBJECT_ID, Constant.RESULT_PASS},
                new Object[]{StaticDataStore.session_id, StaticDataStore.newUser.getUser_id(), collectionView.getPass()}));

        call.enqueue(new CallbackAdapter<DynamicsResult>(collectionView) {

            @Override
            public void onResponse(DynamicsResult dynamicsResult) {
                collectionView.onLoadMoreDataSuccess(dynamicsResult.getArticles());
                collectionView.savePass(dynamicsResult.getPass());
            }
        });

    }


}
