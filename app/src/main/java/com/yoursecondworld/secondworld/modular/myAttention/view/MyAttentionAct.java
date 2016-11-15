package com.yoursecondworld.secondworld.modular.myAttention.view;


import android.content.Intent;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yoursecondworld.secondworld.R;
import com.yoursecondworld.secondworld.common.AdapterNotify;
import com.yoursecondworld.secondworld.common.BaseAct;
import com.yoursecondworld.secondworld.common.event.SessionInvalidEvent;
import com.yoursecondworld.secondworld.common.itemDecoration.VerticalItemDecoration;
import com.yoursecondworld.secondworld.modular.commonFunction.user.presenter.UserPresenter;
import com.yoursecondworld.secondworld.modular.commonFunction.user.view.IUserView;
import com.yoursecondworld.secondworld.modular.myAttention.adapter.MyAttentionActAdapter;
import com.yoursecondworld.secondworld.modular.myAttention.presenter.MyAttentionPresenter;
import com.yoursecondworld.secondworld.modular.systemInfo.entity.NewUser;
import com.yoursecondworld.secondworld.modular.userDetail.view.UserDetailAct;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import xiaojinzi.annotation.Injection;
import xiaojinzi.base.android.adapter.recyclerView.CommonRecyclerViewAdapter;
import xiaojinzi.base.android.adapter.view.TextWatcherAdapter;
import xiaojinzi.base.android.os.T;

/**
 * 我的关注的界面
 */
@Injection(R.layout.act_my_attention)
public class MyAttentionAct extends BaseAct implements IMyAttentionView, IUserView {

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

    @Injection(R.id.et_search)
    private EditText et_search;

    @Injection(value = R.id.tv_header_search, click = "clickView")
    private TextView tv_header_search;

    @Injection(R.id.rv_act_my_attention_content)
    private RecyclerView rv_content = null;

    private CommonRecyclerViewAdapter adapter = null;

//    private List<NewUser> backUsers = new ArrayList<NewUser>();

    /**
     * 显示粉丝的集合
     */
    private List<NewUser> users = new ArrayList<NewUser>();

    /**
     * 我的关注的主持人
     */
    private MyAttentionPresenter presenter = new MyAttentionPresenter(this);

    /**
     * 关注用户和取消关注的主持人
     */
    private UserPresenter userPresenter = new UserPresenter(this);

    @Override
    public void initView() {
        super.initView();
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) rl_titlebar.getLayoutParams();
        lp.topMargin = statusHeight;

        tv_titleName.setText("我的关注");

        //创建适配器
        adapter = new MyAttentionActAdapter(context, users);

        //创建布局管理器并且设置
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_content.setLayoutManager(layoutManager);

        rv_content.addItemDecoration(new VerticalItemDecoration());

        //设置适配器
        rv_content.setAdapter(adapter);

    }

    @Override
    public void initData() {
        super.initData();

        presenter.getMeFollow();

    }

    private boolean isNoTAnyMore;

    private boolean isSearchStatus;

    @Override
    public void setOnlistener() {
        super.setOnlistener();

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
                            return;
                        }
                        presenter.getMoreMeFollow();
                    }
                }
            }
        });

        adapter.setOnViewInItemClickListener(new CommonRecyclerViewAdapter.OnViewInItemClickListener() {
            @Override
            public void onViewInItemClick(View v, int position) {
                int id = v.getId();
                switch (id) {
                    case R.id.iv_act_my_fans_and_attention_item_follow_icon: //取消关注
                        NewUser newUser = users.get(position);
                        if (newUser.isFollow()) {
                            userPresenter.unFollowUser(newUser.getUser_id(), v, position);
                        } else {
                            userPresenter.followUser(newUser.getUser_id(), v, position);
                        }
                        break;
                }
            }
        }, R.id.iv_act_my_fans_and_attention_item_follow_icon);

        adapter.setOnRecyclerViewItemClickListener(new CommonRecyclerViewAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent i = new Intent(context, UserDetailAct.class);
                i.putExtra(UserDetailAct.TARGET_USER_ID_FLAG, users.get(position).getUser_id());
                startActivity(i);
            }
        });

        //实现全部用户的搜索功能
//        et_search.addTextChangedListener(new TextWatcherAdapter() {
//            @Override
//            public void afterTextChanged(Editable s) {
//                String content = s.toString().trim().toLowerCase();
//                if ("".equals(content)) {
//                    AdapterNotify.notifyFreshData(users, backUsers, adapter);
//                } else {
//                    users.clear();
//                    for (NewUser u : backUsers) {
//                        if (u.getUser_nickname().toLowerCase().contains(content)) {
//                            users.add(u);
//                        }
//                    }
//                    adapter.notifyDataSetChanged();
//                }
//            }
//        });

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
            case R.id.tv_header_search: //搜索按钮

                String searchKey = getSearchKey();
                if (TextUtils.isEmpty(searchKey)) {
                    presenter.getMeFollow();
                }else{
                    presenter.search_followed_user();
                }

                break;
        }
    }

    @Override
    public void onLoadDataSuccess(List<NewUser> users) {
        isNoTAnyMore = false;
        isSearchStatus = false;
        for (int i = 0; i < users.size(); i++) {
            users.get(i).setFollow(true);
        }
        AdapterNotify.notifyFreshData(this.users, users, adapter);
        //备份集合,用于搜索的实现
//        backUsers.clear();
//        backUsers.addAll(users);
    }

    @Override
    public void onLoadMoreDataSuccess(List<NewUser> users) {
        if (users.size() == 0) {
            isNoTAnyMore = true;
            return;
        }
        for (int i = 0; i < users.size(); i++) {
            users.get(i).setFollow(true);
        }
        AdapterNotify.notifyAppendData(this.users, users, adapter);
        //备份集合,用于搜索的实现
//        backUsers.addAll(users);
    }

    @Override
    public void onLoadSearchDataSuccess(List<NewUser> users) {
        isSearchStatus = true;
        for (int i = 0; i < users.size(); i++) {
            users.get(i).setFollow(true);
        }
        AdapterNotify.notifyFreshData(this.users, users, adapter);
    }

    private String mPass;

    @Override
    public String getPass() {
        return mPass;
    }

    @Override
    public void savePass(String pass) {
        mPass = pass;
    }

    @Override
    public String getSearchKey() {
        return et_search.getText().toString().trim();
    }

    @Override
    public void tip(String content) {
        T.showShort(context, content);
    }

    @Override
    public void onFollowSuccess(Object... obs) {

        int position = (int) obs[1];
        NewUser newUser = users.get(position);

        ImageView iv = (ImageView) obs[0];
        if (newUser.is_friend()) {
            iv.setImageResource(R.mipmap.attentioned1);
        } else {
            iv.setImageResource(R.mipmap.attentioned1);
        }

        newUser.setFollow(true);

    }

    @Override
    public void onUnFollowSuccess(Object... obs) {

        int position = (int) obs[1];
        NewUser newUser = users.get(position);

        ImageView iv = (ImageView) obs[0];
        iv.setImageResource(R.mipmap.attention1);

        newUser.setFollow(false);

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
