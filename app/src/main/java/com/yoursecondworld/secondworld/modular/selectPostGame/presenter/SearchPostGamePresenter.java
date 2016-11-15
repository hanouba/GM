package com.yoursecondworld.secondworld.modular.selectPostGame.presenter;

import com.yoursecondworld.secondworld.AppConfig;
import com.yoursecondworld.secondworld.common.CallbackAdapter;
import com.yoursecondworld.secondworld.common.JsonRequestParameter;
import com.yoursecondworld.secondworld.modular.selectPostGame.entity.GamesResult;
import com.yoursecondworld.secondworld.modular.selectPostGame.view.ISearchPostGameView;

import okhttp3.RequestBody;
import retrofit2.Call;

/**
 * Created by cxj on 2016/10/24.
 */
public class SearchPostGamePresenter {

    private ISearchPostGameView view;

    public SearchPostGamePresenter(ISearchPostGameView view) {
        this.view = view;
    }

    public void searchGames() {

        RequestBody body = JsonRequestParameter.build(
                new String[]{"to_search"},
                new Object[]{view.getSearchKey()}
        );

        Call<GamesResult> call = AppConfig.netWorkService.search_game(body);

        //发布评论
        call.enqueue(new CallbackAdapter<GamesResult>(view) {
            @Override
            public void onResponse(GamesResult entity) {
                view.onSearchSuccess(entity.getGames());
            }

            @Override
            public void onFailure(Call<GamesResult> call, Throwable t) {
                super.onFailure(call, t);
                view.tip("加载评论列表失败");
            }
        });

    }


}
