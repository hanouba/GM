package com.yoursecondworld.secondworld.modular.search.fragment.showUser;

import android.content.Intent;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yoursecondworld.secondworld.R;
import com.yoursecondworld.secondworld.common.AdapterNotify;
import com.yoursecondworld.secondworld.modular.dynamics.entity.NewDynamics;
import com.yoursecondworld.secondworld.modular.search.fragment.showUser.adapter.ShowUserFragmentAdapter;
import com.yoursecondworld.secondworld.modular.search.fragment.showUser.itemDecoration.ShowUserItemDecoration;
import com.yoursecondworld.secondworld.modular.search.presenter.SearchPresenter;
import com.yoursecondworld.secondworld.modular.search.ui.ISearchView;
import com.yoursecondworld.secondworld.modular.systemInfo.entity.NewUser;
import com.yoursecondworld.secondworld.modular.systemInfo.entity.User;
import com.yoursecondworld.secondworld.modular.userDetail.view.UserDetailAct;

import java.util.ArrayList;
import java.util.List;

import xiaojinzi.activity.fragment.BaseViewPagerFragment;
import xiaojinzi.annotation.Injection;
import xiaojinzi.base.android.adapter.recyclerView.CommonRecyclerViewAdapter;

/**
 * Created by cxj on 2016/8/1.
 * 显示用户的搜索结果的fragment
 */
public class ShowUserSearchResultFragment extends BaseViewPagerFragment implements ISearchView {

    @Override
    public int getLayoutId() {
        return R.layout.frag_show_user_search_result;
    }

    //===========================================================================================

    @Injection(R.id.rv)
    private RecyclerView rv = null;

    /**
     * 显示数据的适配器
     */
    private CommonRecyclerViewAdapter adapter = null;

    /**
     * 显示的数据
     */
    private List<NewUser> users = new ArrayList<NewUser>();

    private SearchPresenter searchPresenter = new SearchPresenter(this);

    @Override
    public void initView() {
        super.initView();

        //创建适配器
        adapter = new ShowUserFragmentAdapter(context, users);

        //创建布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(layoutManager);

        ShowUserItemDecoration showUserItemDecoration = new ShowUserItemDecoration();
        rv.addItemDecoration(showUserItemDecoration);

        //设置适配器
        rv.setAdapter(adapter);

    }

    /**
     * 初始化数据
     */
    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void setOnlistener() {
        super.setOnlistener();

        //设置点击事件
        adapter.setOnRecyclerViewItemClickListener(new CommonRecyclerViewAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent i = new Intent(context, UserDetailAct.class);
                i.putExtra(UserDetailAct.TARGET_USER_ID_FLAG, users.get(position).getUser_id());
                startActivity(i);
            }
        });

        //加载更多的监听
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE) { //如果暂停了

                    boolean b = !ViewCompat.canScrollVertically(rv, 1);
                    //如果已经不可以向下滑动了,就去加载更多
                    if (b) {
                        if (isNoTAnyMore) {
                            return;
                        }
                        searchPresenter.searchMoreUser();
                    }
                }
            }
        });

    }

    @Override
    public boolean isAutoSubscribeEvent() {
        return false;
    }

    public void displayUsers(List<NewUser> newUsers) {

        int size = users.size();
        users.clear();
        adapter.notifyItemRangeRemoved(0, size);

        users.addAll(newUsers);

        adapter.notifyItemRangeInserted(0, newUsers.size());

    }

    private String searchKey;

    public void startSearchUser(String key) {
        searchKey = key;
        searchPresenter.searchUser();
    }


    @Override
    public String getSearchContent() {
        return searchKey;
    }

    @Override
    public void onSearchDynamicsSuccess(List<NewDynamics> newDynamicses) {
    }

    @Override
    public void onSearchMoreDynamicsSuccess(List<NewDynamics> newDynamicses) {
    }

    private boolean isNoTAnyMore;

    @Override
    public void onSearchUsersSuccess(List<NewUser> newUsers) {
        isNoTAnyMore = false;
        AdapterNotify.notifyFreshData(users, newUsers, adapter);
    }

    @Override
    public void onSearchMoreUsersSuccess(List<NewUser> newUsers) {
        if (newUsers.size() == 0) {
            isNoTAnyMore = true;
            return;
        }
        AdapterNotify.notifyAppendData(users, newUsers, adapter);
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
    public void showDialog(String content) {

    }

    @Override
    public void tip(String content) {

    }

    @Override
    public void onSessionInvalid() {

    }
}
