package com.yoursecondworld.secondworld.modular.myFans.view;


import android.content.Intent;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yoursecondworld.secondworld.R;
import com.yoursecondworld.secondworld.common.AdapterNotify;
import com.yoursecondworld.secondworld.common.BaseAct;
import com.yoursecondworld.secondworld.common.event.SessionInvalidEvent;
import com.yoursecondworld.secondworld.common.itemDecoration.VerticalItemDecoration;
import com.yoursecondworld.secondworld.modular.commonFunction.fansAndFollow.presenter.FansAndFollowPresenter;
import com.yoursecondworld.secondworld.modular.commonFunction.fansAndFollow.view.IFansAndFollowView;
import com.yoursecondworld.secondworld.modular.commonFunction.user.presenter.UserPresenter;
import com.yoursecondworld.secondworld.modular.commonFunction.user.view.IUserView;
import com.yoursecondworld.secondworld.modular.myFans.adapter.MyFansActAdapter;
import com.yoursecondworld.secondworld.modular.systemInfo.entity.NewUser;
import com.yoursecondworld.secondworld.modular.userDetail.view.UserDetailAct;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import xiaojinzi.annotation.Injection;
import xiaojinzi.base.android.adapter.recyclerView.CommonRecyclerViewAdapter;
import xiaojinzi.base.android.os.T;

/**
 * 我的粉丝的界面
 */
@Injection(R.layout.act_my_fans)
public class MyFansAct extends BaseAct implements CommonRecyclerViewAdapter.OnViewInItemClickListener, IUserView, IFansAndFollowView {

    /**
     * 标题栏
     */
    @Injection(R.id.rl_act_titlebar)
    private RelativeLayout rl_titlebar = null;

    /**
     * 返回按钮
     */
    @Injection(value = R.id.iv_back, click = "clickView")
    private ImageView iv_back = null;

    /**
     * 标题栏的文本
     */
    @Injection(R.id.tv_title_name)
    private TextView tv_titleName = null;

    //========================================================================

    /**
     * 列表控件
     */
    @Injection(R.id.rv_act_my_fans_content)
    private RecyclerView rv_content = null;

    /**
     * 适配器
     */
    private CommonRecyclerViewAdapter adapter = null;

    /**
     * 显示粉丝的集合
     */
    private List<NewUser> fansEntityList = new ArrayList<NewUser>();

    private UserPresenter userPresenter = new UserPresenter(this);

    @Override
    public void initView() {
        super.initView();
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) rl_titlebar.getLayoutParams();
        lp.topMargin = statusHeight;

        tv_titleName.setText("我的粉丝");

        //数据造假
        loadData();

        //创建适配器
        adapter = new MyFansActAdapter(context, fansEntityList);

        //创建布局管理器并且设置
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_content.setLayoutManager(layoutManager);

        rv_content.addItemDecoration(new VerticalItemDecoration());

        //设置适配器
        rv_content.setAdapter(adapter);

    }

    /**
     * 点击事件的集中处理
     *
     * @param v
     */
    public void clickView(View v) {

        //获取控件的id
        int id = v.getId();

        //对id的筛选然后做处理
        switch (id) {
            case R.id.iv_back:
                finish();
                break;
        }

    }

    private boolean isNoTAnyMore;

    @Override
    public void setOnlistener() {
        super.setOnlistener();

        adapter.setOnViewInItemClickListener(this,
                R.id.iv_act_my_fans_and_attention_item_follow_icon);

        adapter.setOnRecyclerViewItemClickListener(new CommonRecyclerViewAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent i = new Intent(context, UserDetailAct.class);
                i.putExtra(UserDetailAct.TARGET_USER_ID_FLAG, fansEntityList.get(position).getUser_id());
                startActivity(i);
            }
        });


        //加载更多的监听
        rv_content.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE) { //如果暂停了

                    boolean b = !ViewCompat.canScrollVertically(rv_content, 1);
                    //如果已经不可以向下滑动了,就去加载更多
                    if (b) {
                        if (isNoTAnyMore) {
//                            tip("没有啦");
                            return;
                        }
                        fansAndFollowPresenter.loadMoreUnFollowFans();
                    }
                }
            }
        });


    }

    private FansAndFollowPresenter fansAndFollowPresenter = new FansAndFollowPresenter(this);

    /**
     * 加载数据
     */
    private void loadData() {
        loadUnFollowFans();
    }


    /**
     * 加载没关注的
     */
    private void loadUnFollowFans() {
        fansAndFollowPresenter.loadUnFollowFans();
    }

    @Override
    public void onViewInItemClick(View v, int position) {
        NewUser newUser = fansEntityList.get(position);
        if (newUser.isFollow()) {
            userPresenter.unFollowUser(newUser.getUser_id(), v, position);
        } else {
            userPresenter.followUser(newUser.getUser_id(), v, position);
        }
    }

    @Override
    public void tip(String content) {
        T.showShort(context, content);
    }

    @Override
    public void onFollowSuccess(Object... obs) {
        ImageView iv = (ImageView) obs[0];
        iv.setImageResource(R.mipmap.attentioned1);
        int position = (int) obs[1];
        fansEntityList.get(position).setFollow(true);
    }

    @Override
    public void onUnFollowSuccess(Object... obs) {
        ImageView iv = (ImageView) obs[0];
        iv.setImageResource(R.mipmap.attention1);
        int position = (int) obs[1];
        fansEntityList.get(position).setFollow(false);
    }

    @Override
    public void onBlockSuccess() {
    }

    @Override
    public void onUnBlockSuccess() {

    }

    @Override
    public void onLoadFollowEachOtherSuccess(List<NewUser> newUsers) {
    }

    @Override
    public void onLoadUnFollowFansSuccess(List<NewUser> newUsers) {
        AdapterNotify.notifyFreshData(fansEntityList, newUsers, adapter);
    }

    @Override
    public void onLoadMoreUnFollowFansSuccess(List<NewUser> users) {
        if (users.size() == 0) {
            isNoTAnyMore = true;
        }
        AdapterNotify.notifyAppendData(fansEntityList, users, adapter);
    }

    private String pass;

    @Override
    public void savePass(String pass) {
        this.pass = pass;
    }

    @Override
    public String getPass() {
        return pass;
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
