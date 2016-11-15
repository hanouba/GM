package com.yoursecondworld.secondworld.modular.prepareModule.selectPlayer.ui;


import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yoursecondworld.secondworld.R;
import com.yoursecondworld.secondworld.common.AdapterNotify;
import com.yoursecondworld.secondworld.common.BaseAct;
import com.yoursecondworld.secondworld.common.event.SessionInvalidEvent;
import com.yoursecondworld.secondworld.modular.commonFunction.user.presenter.UserPresenter;
import com.yoursecondworld.secondworld.modular.commonFunction.user.view.IUserView;
import com.yoursecondworld.secondworld.modular.main.ui.MainAct;
import com.yoursecondworld.secondworld.modular.prepareModule.selectPlayer.adapter.SelectPlayerActAdapter;
import com.yoursecondworld.secondworld.modular.prepareModule.selectPlayer.entity.PlayerEntity;
import com.yoursecondworld.secondworld.modular.prepareModule.selectPlayer.entity.PlayerSpaceItemDecoration;
import com.yoursecondworld.secondworld.modular.prepareModule.selectPlayer.presenter.SelectPlayerPresenter;
import com.yoursecondworld.secondworld.modular.systemInfo.entity.NewUser;

import org.greenrobot.eventbus.Subscribe;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import xiaojinzi.annotation.Injection;
import xiaojinzi.base.android.activity.ActivityUtil;
import xiaojinzi.base.android.adapter.recyclerView.CommonRecyclerViewAdapter;


/**
 * 选择游戏玩家的界面
 */
@Injection(R.layout.act_select_player)
public class SelectPlayerAct extends BaseAct implements ISelectPlayerView, IUserView {

    @Injection(R.id.iv_back)
    private ImageView iv_back;

    @Injection(R.id.tv_title_name)
    private TextView tv_title;

    //====================================================

    @Injection(R.id.rv)
    private RecyclerView rv_players = null;

    @Injection(value = R.id.tv_ok, click = "clickView")
    private Button tv_ok;

    /**
     * 玩家信息的集合
     */
    private List<NewUser> playerEntityList = new ArrayList<NewUser>();

    /**
     * 记录玩家是否被选中的
     */
    private List<Boolean> playerIsSelected = new ArrayList<Boolean>();

    /**
     * 显示玩家信息的适配器
     */
    private SelectPlayerActAdapter adapter = null;

    private SelectPlayerPresenter selectPlayerPresenter = new SelectPlayerPresenter(this);

    private UserPresenter userPresenter = new UserPresenter(this);

    @Override
    public void initView() {
        super.initView();

        iv_back.setVisibility(View.INVISIBLE);
        tv_title.setText("请选择您感兴趣的用户");

        //创建适配器
        adapter = new SelectPlayerActAdapter(context, playerEntityList, playerIsSelected);
        //设置布局管理器
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        rv_players.setLayoutManager(layoutManager);

        rv_players.setAdapter(adapter);

    }

    @Override
    public void initData() {
        super.initData();

        selectPlayerPresenter.get_recommend_user_list();

    }

    @Override
    public void setOnlistener() {
        super.setOnlistener();

        //监听玩家列表的条目点击事件
        adapter.setOnRecyclerViewItemClickListener(new CommonRecyclerViewAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
//                playerIsSelected.set(position, !playerIsSelected.get(position));
//                adapter.notifyItemChanged(position);

                NewUser newUser = playerEntityList.get(position);
                if (newUser.isFollow()) { //如果关注过了
                    showDialog("正在取消关注");
                    userPresenter.unFollowUser(newUser.getUser_id(), position);
                } else {
                    showDialog("正在关注");
                    userPresenter.followUser(newUser.getUser_id(), position);
                }
            }
        });

    }

    /**
     * 点击事件集中处理
     *
     * @param v
     */
    public void clickView(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv_ok: //完成按钮
                //跳转到主界面
                ActivityUtil.startActivity(context, MainAct.class);
                finish();
                break;
        }
    }

    @Override
    public void showDialog(String text) {
    }

    @Override
    public void closeDialog() {
    }

    @Override
    public void tip(String content) {
    }

    @Override
    public void onLoadUsersSuccess(List<NewUser> list) {

        //刷新集合中的数据
        AdapterNotify.notifyFreshData(playerEntityList, list, adapter);

        int size = playerEntityList.size();
        playerIsSelected.clear();

        //默认不关注所有的人
        for (int i = 0; i < size; i++) {
            playerIsSelected.add(false);
        }

    }

    @Override
    public void onFollowSuccess(Object... obs) {
        int position = (int) obs[0];
        playerEntityList.get(position).setFollow(true);
        playerIsSelected.set(position, true);
        adapter.notifyItemChanged(position);
    }

    @Override
    public void onUnFollowSuccess(Object... obs) {
        int position = (int) obs[0];
        playerEntityList.get(position).setFollow(false);
        playerIsSelected.set(position, false);
        adapter.notifyItemChanged(position);
    }

    @Override
    public void onBlockSuccess() {
    }

    @Override
    public void onUnBlockSuccess() {
    }

    @Override
    public boolean isRegisterEvent() {
        return true;
    }

    /**
     * 当sessionId失效了
     */
    @Subscribe
    public void onSessionIdInvalid(SessionInvalidEvent event) {
        finish();
    }

}
