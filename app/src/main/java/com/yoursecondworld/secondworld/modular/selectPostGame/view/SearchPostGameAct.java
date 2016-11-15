package com.yoursecondworld.secondworld.modular.selectPostGame.view;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.yoursecondworld.secondworld.R;
import com.yoursecondworld.secondworld.common.BaseAct;
import com.yoursecondworld.secondworld.modular.dynamics.entity.DynamicsContentEntity;
import com.yoursecondworld.secondworld.modular.postDynamics.view.PostDynamicsAct;
import com.yoursecondworld.secondworld.modular.selectPostGame.adapter.SearchPostGameActAdapter;
import com.yoursecondworld.secondworld.modular.selectPostGame.presenter.SearchPostGamePresenter;

import java.util.ArrayList;
import java.util.List;

import xiaojinzi.annotation.Injection;
import xiaojinzi.base.android.activity.ActivityUtil;
import xiaojinzi.base.android.adapter.recyclerView.CommonRecyclerViewAdapter;
import xiaojinzi.base.android.store.SPUtil;

/**
 * 搜索游戏标签的界面
 */
@Injection(R.layout.act_search_post_game)
public class SearchPostGameAct extends BaseAct implements ISearchPostGameView {

    @Injection(value = R.id.rl_search, click = "clickView")
    private RelativeLayout rl_search;

    @Injection(R.id.rv)
    private RecyclerView rv;

    private List<String> games = new ArrayList<String>();

    private SearchPostGameActAdapter adapter;

    @Injection(R.id.et_search)
    private EditText et_search;

    private SearchPostGamePresenter presenter = new SearchPostGamePresenter(this);

    private boolean isReturnGameName;

    @Override
    public void initView() {
        super.initView();

        //是否返回游戏标签
        isReturnGameName = getIntent().getBooleanExtra(SelectPostGameAct.IS_RETURN_FLAG, false);

        //创建适配器
        adapter = new SearchPostGameActAdapter(context, games);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //设置布局管理器
        rv.setLayoutManager(layoutManager);

        rv.setAdapter(adapter);

    }

    /**
     * 把点击过的游戏存进历史里面
     *
     * @param game
     */
    private void saveGameToHistory(String game) {
        String games = SPUtil.get(context, SelectPostGameAct.SP_HISTORY_GAME_FLAG, "");
        if (TextUtils.isEmpty(games)) {
            games = game;
        } else {
            if (!games.contains(game)) {
                games = games + ";" + game;
            }
        }
        SPUtil.put(context, SelectPostGameAct.SP_HISTORY_GAME_FLAG, games);
    }

    /**
     * 是否需要到发布界面,因为在这个界面可能直接返回了
     */
    private boolean isToPost;

    @Override
    public void setOnlistener() {
        super.setOnlistener();

        adapter.setOnRecyclerViewItemClickListener(new CommonRecyclerViewAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {

                //拿到点击的游戏标签
                String game = games.get(position);

                //保存到历史中
                saveGameToHistory(game);

                if (isReturnGameName) {

                    Intent intent = new Intent();
                    intent.putExtra("data", game);
                    setResult(RESULT_OK, intent);

                } else {

                    Intent intent = new Intent(context, PostDynamicsAct.class);

                    intent.putExtra(PostDynamicsAct.GAME_TAG, game);
                    intent.putExtra(PostDynamicsAct.TYPE_TAG, DynamicsContentEntity.TOPICS[0]);

                    //启动发送动态的界面
                    context.startActivity(intent);

                    isToPost = true;
                }

                finish();

            }
        });

    }

    @Override
    public boolean isRegisterEvent() {
        return false;
    }

    public void clickView(View view) {
        int id = view.getId();
        switch (id) {

            case R.id.rl_search: //点击了搜索

                //搜索游戏
                presenter.searchGames();

                break;
        }
    }

    @Override
    public void finish() {
        super.finish();
        if (!isToPost && !isReturnGameName) { //这种情况是直接点击了返回按钮
            ActivityUtil.startActivity(context, SelectPostGameAct.class);
        }
    }

    @Override
    public String getSearchKey() {
        return et_search.getText().toString().trim();
    }

    @Override
    public void onSearchSuccess(List<String> games) {

        int size = this.games.size();

        this.games.clear();
        adapter.notifyItemRangeRemoved(0, size);

        this.games.addAll(games);
        adapter.notifyItemRangeInserted(0, games.size());
    }

}
