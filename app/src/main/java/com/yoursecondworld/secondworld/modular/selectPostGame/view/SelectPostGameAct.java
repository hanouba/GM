package com.yoursecondworld.secondworld.modular.selectPostGame.view;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yoursecondworld.secondworld.AppConfig;
import com.yoursecondworld.secondworld.R;
import com.yoursecondworld.secondworld.common.BaseAct;
import com.yoursecondworld.secondworld.common.JsonRequestParameter;
import com.yoursecondworld.secondworld.common.StaticDataStore;
import com.yoursecondworld.secondworld.modular.dynamics.entity.DynamicsContentEntity;
import com.yoursecondworld.secondworld.modular.postDynamics.view.PostDynamicsAct;
import com.yoursecondworld.secondworld.modular.selectPostGame.adapter.SelectPostGameActHistoryGameAdapter;
import com.yoursecondworld.secondworld.modular.selectPostGame.adapter.SelectPostGameActHotGameAdapter;
import com.yoursecondworld.secondworld.modular.selectPostGame.entity.GamesResult;

import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xiaojinzi.annotation.Injection;
import xiaojinzi.base.android.activity.ActivityUtil;
import xiaojinzi.base.android.adapter.recyclerView.CommonRecyclerViewAdapter;
import xiaojinzi.base.android.os.T;
import xiaojinzi.base.android.store.SPUtil;

@Injection(R.layout.act_select_post_game)
public class SelectPostGameAct extends BaseAct {

    public static final int REQUEST_GAME_NAME_CODE = 433;

    /**
     * 存放历史游戏的key,存放在轻量级存储里面
     */
    public static final String SP_HISTORY_GAME_FLAG = "sp_history_game_flag";

    /**
     * 是否返回游戏结果,而不是跳转到发布界面
     */
    public static final String IS_RETURN_FLAG = "is_return_flag";

    private boolean isReturnGameName = false;

    @Injection(value = R.id.et_search, click = "clickView")
    private EditText et_search;

    @Injection(value = R.id.tv_cancel, click = "clickView")
    private TextView tv_cancel;

    @Injection(R.id.rv_hot)
    private RecyclerView rv_hot;

    @Injection(R.id.rv_history)
    private RecyclerView rv_history;

    private List<String> hotGames = new ArrayList<String>();

    private List<String> historyGames = new ArrayList<String>();

    private SelectPostGameActHotGameAdapter selectPostGameActHotGameAdapter = null;

    private SelectPostGameActHistoryGameAdapter selectPostGameActHistoryGameAdapter = null;


    @Override
    public void initView() {
        super.initView();

        //获取是否返回游戏标签
        isReturnGameName = getIntent().getBooleanExtra(IS_RETURN_FLAG, false);

        //魔兽世界   部落冲突  倩女幽魂   大话西游   守望先锋  英雄联盟  DOTA2   王者荣耀   梦幻西游  炉石传说

        //拿到历史游戏
        String games = SPUtil.get(context, SP_HISTORY_GAME_FLAG, "");
        if (!TextUtils.isEmpty(games)) {
            String[] gameArr = games.split(";");
            for (int i = 0; i < gameArr.length; i++) {
                historyGames.add(gameArr[i]);
            }
        }

        //创建适配器
        selectPostGameActHotGameAdapter = new SelectPostGameActHotGameAdapter(context, hotGames);
        selectPostGameActHistoryGameAdapter = new SelectPostGameActHistoryGameAdapter(context, historyGames);

        GridLayoutManager layoutManager = new GridLayoutManager(context, 4);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(context);
        layoutManager1.setOrientation(LinearLayoutManager.VERTICAL);

        //设置布局管理器
        rv_hot.setLayoutManager(layoutManager);
        rv_history.setLayoutManager(layoutManager1);

        rv_hot.setAdapter(selectPostGameActHotGameAdapter);
        rv_history.setAdapter(selectPostGameActHistoryGameAdapter);

    }

    @Override
    public void initData() {
        super.initData();

        RequestBody body = JsonRequestParameter.getInstance()
                .addParameter(StaticDataStore.session_id, StaticDataStore.newUser.getUser_id())
                .build();

        Call<GamesResult> call = AppConfig.netWorkService.get_hot_games(body);

        call.enqueue(new Callback<GamesResult>() {
            @Override
            public void onResponse(Call<GamesResult> call, Response<GamesResult> response) {
                GamesResult gamesResult = response.body();
                if (gamesResult != null) {
                    hotGames.addAll(gamesResult.getGames());
                } else {
                    hotGames.add("魔兽世界");
                    hotGames.add("部落冲突");
                    hotGames.add("倩女幽魂");
                    hotGames.add("大话西游");
                    hotGames.add("守望先锋");
                    hotGames.add("英雄联盟");
                    hotGames.add("DOTA2");
                    hotGames.add("王者荣耀");
                }
                selectPostGameActHotGameAdapter.notifyItemRangeInserted(0, hotGames.size());
            }

            @Override
            public void onFailure(Call<GamesResult> call, Throwable t) {
                hotGames.add("魔兽世界");
                hotGames.add("部落冲突");
                hotGames.add("倩女幽魂");
                hotGames.add("大话西游");
                hotGames.add("守望先锋");
                hotGames.add("英雄联盟");
                hotGames.add("DOTA2");
                hotGames.add("王者荣耀");
                selectPostGameActHotGameAdapter.notifyItemRangeInserted(0, hotGames.size());
            }
        });

    }

    @Override
    public void setOnlistener() {
        super.setOnlistener();

        selectPostGameActHotGameAdapter.setOnRecyclerViewItemClickListener(new CommonRecyclerViewAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                //拿到点击的游戏标签
                String game = hotGames.get(position);

                saveGameToHistory(game);

                if (isReturnGameName) { //如果需要返回游戏标签

                    Intent intent = new Intent();
                    intent.putExtra("data", game);
                    setResult(RESULT_OK, intent);

                } else {
                    Intent intent = new Intent(context, PostDynamicsAct.class);

                    intent.putExtra(PostDynamicsAct.GAME_TAG, game);
                    intent.putExtra(PostDynamicsAct.TYPE_TAG, DynamicsContentEntity.TOPICS[0]);

                    //启动发送动态的界面
                    context.startActivity(intent);
                }

                finish();

            }
        });

        selectPostGameActHistoryGameAdapter.setOnRecyclerViewItemClickListener(new CommonRecyclerViewAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                //拿到点击的游戏标签
                String game = historyGames.get(position);

                if (isReturnGameName) { //如果需要返回游戏标签

                    Intent intent = new Intent();
                    intent.putExtra("data", game);
                    setResult(RESULT_OK, intent);

                } else {

                    Intent intent = new Intent(context, PostDynamicsAct.class);

                    intent.putExtra(PostDynamicsAct.GAME_TAG, game);
                    intent.putExtra(PostDynamicsAct.TYPE_TAG, DynamicsContentEntity.TOPICS[0]);

                    //启动发送动态的界面
                    context.startActivity(intent);
                }

                finish();
            }
        });

    }

    /**
     * 把点击过的游戏存进历史里面
     *
     * @param game
     */
    private void saveGameToHistory(String game) {
        String games = SPUtil.get(context, SP_HISTORY_GAME_FLAG, "");
        if (TextUtils.isEmpty(games)) {
            games = game;
        } else {
            if (!games.contains(game)) {
                games = games + ";" + game;
            }
        }
        SPUtil.put(context, SP_HISTORY_GAME_FLAG, games);
    }

    /**
     * 点击事件的集中处理
     *
     * @param view
     */
    public void clickView(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.tv_cancel: //如果是返回

                finish();

                break;
            case R.id.et_search:

                Intent intent = new Intent(context, SearchPostGameAct.class);

                if (isReturnGameName) { //如果需要返回游戏标签

                    intent.putExtra(IS_RETURN_FLAG, true);
                    startActivityForResult(intent, REQUEST_GAME_NAME_CODE);

                } else {
                    startActivity(intent);
                    finish();
                }

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_GAME_NAME_CODE && resultCode == RESULT_OK && data != null) {
            setResult(RESULT_OK, data);
            finish();
        }
    }

    @Override
    public boolean isRegisterEvent() {
        return false;
    }

}
