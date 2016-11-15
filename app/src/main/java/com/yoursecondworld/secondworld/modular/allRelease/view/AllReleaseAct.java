package com.yoursecondworld.secondworld.modular.allRelease.view;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import xiaojinzi.autolayout.utils.AutoUtils;

import com.yoursecondworld.secondworld.MyApp;
import com.yoursecondworld.secondworld.R;

import com.yoursecondworld.secondworld.common.Constant;
import com.yoursecondworld.secondworld.common.StaticDataStore;
import com.yoursecondworld.secondworld.common.event.SessionInvalidEvent;
import com.yoursecondworld.secondworld.modular.allRelease.popupWindow.PopupDeleteWindow;
import com.yoursecondworld.secondworld.modular.commonFunction.loadMyDynamics.presenter.LoadMyDynamicsPresenter;
import com.yoursecondworld.secondworld.modular.commonFunction.loadMyDynamics.view.ILoadMyDynamicsView;
import com.yoursecondworld.secondworld.modular.commonFunction.searchUserDynamics.presenter.SearchUserDynamicsPresenter;
import com.yoursecondworld.secondworld.modular.commonFunction.searchUserDynamics.view.ISearchUserDynamicsView;
import com.yoursecondworld.secondworld.modular.dynamics.adapter.DynamicsContentRecyclerViewAdapter;

import com.yoursecondworld.secondworld.modular.dynamics.entity.NewDynamics;
import com.yoursecondworld.secondworld.modular.dynamics.itemDecoration.DynamicsContentSpaceItemDecoration;

import com.yoursecondworld.secondworld.modular.dynamics.view.BaseDynamicsActivity;
import com.yoursecondworld.secondworld.modular.main.eventEntity.SharePlatformEntity;
import com.yoursecondworld.secondworld.modular.main.popupWindow.newShare.PopupShare;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import xiaojinzi.annotation.Injection;
import xiaojinzi.base.android.adapter.recyclerView.CommonRecyclerViewAdapter;
import xiaojinzi.base.android.os.T;


/**
 * 所有的发布的界面
 */
@Injection(R.layout.act_all_release)
public class AllReleaseAct extends BaseDynamicsActivity implements ILoadMyDynamicsView, ISearchUserDynamicsView {

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

    @Injection(value = R.id.tv_menu, click = "clickView")
    private TextView tv_menu = null;

    //=============================================================================

    @Injection(R.id.et_act_all_release_search)
    private EditText et_searchContent;

    @Injection(R.id.rv_act_all_release_content)
    private RecyclerView rv = null;

    private PopupDeleteWindow popupDeleteWindow = null;


    /**
     * 加载我的动态的组件
     */
    private LoadMyDynamicsPresenter loadMyDynamicsPresenter = new LoadMyDynamicsPresenter(this);

    private SearchUserDynamicsPresenter searchUserDynamicsPresenter = new SearchUserDynamicsPresenter(this);

    /**
     * 是否是搜索的状态
     */
    private boolean isSearchStatus = false;

    /**
     * 是否还有更多的数据
     */
    private boolean isHaveMore = true;

    @Override
    public void initView() {
        super.initView();

        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) rl_titlebar.getLayoutParams();
        lp.topMargin = statusHeight;
        tv_titleName.setText("全部发布");
        initContent();

    }

    @Override
    public void initData() {
        super.initData();

        //加载我的动态
        loadMyDynamicsPresenter.loadMyDynamics();

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

            case R.id.tv_header_search: //点击了搜索,如果是空的搜索内容,就表示是正常加载我的发布
                if (TextUtils.isEmpty(getSearchContent())) {
                    loadMyDynamicsPresenter.loadMyDynamics();
                } else {
                    searchUserDynamicsPresenter.searchUserDynamics();
                }
                break;
        }
    }

    @Override
    public void setOnlistener() {
        super.setOnlistener();

        //让adapter去管理视频的播放和销毁
        rv.addOnScrollListener(adapter.new MyScrollListener());

        //加载更多的监听
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE) { //如果暂停了

                    boolean b = !ViewCompat.canScrollVertically(rv, 1);
                    //如果已经不可以向下滑动了,就去加载更多
                    if (b) {
                        if (!isHaveMore) { //如果没有更多了
//                            tip("没有更多的啦");
                            return;
                        }
                        if (isSearchStatus) { //如果是搜索状态
                            searchUserDynamicsPresenter.searchMoreUserDynamics();
                        } else {
                            loadMyDynamicsPresenter.loadMoreMyDynamics();
                        }
                    }
                }
            }
        });

        adapter.setOnRecyclerViewItemClickListener(new CommonRecyclerViewAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                goToDynamicsDetail(position);
            }
        });

    }

    /**
     * 初始化显示内容
     */
    private void initContent() {
        //创建适配器
        adapter = new DynamicsContentRecyclerViewAdapter(context, list_content, rv);

        rv.addOnScrollListener(adapter.new MyScrollListener());

        //布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(linearLayoutManager);

        //适配不同屏幕的之间的条目间隔
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.frag_hot_dynamics_listview_item_space);
        dimensionPixelSize = AutoUtils.getPercentHeightSize(dimensionPixelSize);

        //条目之间的间隔
        DynamicsContentSpaceItemDecoration itemDecoration = new DynamicsContentSpaceItemDecoration(dimensionPixelSize);
        rv.addItemDecoration(itemDecoration);

        rv.setAdapter(adapter);
    }


    @Override
    public String getTargetUserId() {
        return StaticDataStore.newUser.getUser_id();
    }

    @Override
    public void onLoadMyDynamicsSuccess(List<NewDynamics> newDynamicses) {

        int size = list_content.size();

        list_content.clear();

        adapter.notifyItemRangeRemoved(0, size);

        list_content.addAll(newDynamicses);

        adapter.notifyItemRangeInserted(0, newDynamicses.size());

        isSearchStatus = false;
        isHaveMore = true;

    }

    @Override
    public void onViewInItemClick(View v, final int position) {

        int id = v.getId();

        if (id == R.id.iv_dynamics_content_item_arrow_bottom) {
            popupDeleteWindow = new PopupDeleteWindow(context, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    loadMyDynamicsPresenter.deleteDynamics(list_content.get(position).get_id(), position);
                    popupDeleteWindow.dismiss();
                }
            });
            popupDeleteWindow.show();
            return;
        }

        if (id == R.id.iv_dynamics_content_item_foot_share) {
            popupShareWindow(list_content.get(position).get_id(), list_content.get(position).getContent());
            return;
        }

        super.onViewInItemClick(v, position);

    }

    @Override
    public void onDeleteDynamicsSuccess(int postion) {
        list_content.remove(postion);
        adapter.notifyItemRemoved(postion);
    }

    private String pass;

    @Override
    public void savePass(String pass) {
        this.pass = pass;
    }

    @Override
    public String getPass() {
        return this.pass;
    }

    @Override
    public void onLoadMoreMyDynamicsSuccess(List<NewDynamics> newDynamicses) {

        if (newDynamicses.size() == 0) {
            isHaveMore = false;
            return;
        }

        int size = list_content.size();

        list_content.addAll(newDynamicses);

        adapter.notifyItemRangeInserted(size, newDynamicses.size());
    }

    @Override
    public String getSearchContent() {
        return et_searchContent.getText().toString();
    }

    @Override
    public void disPlay(List<NewDynamics> dynamicses) {

//        if (dynamicses.size() == 0) {
//            tip("没有搜索到啦");
//            return;
//        }

        int size = list_content.size();

        list_content.clear();

        adapter.notifyItemRangeRemoved(0, size);

        list_content.addAll(dynamicses);

        adapter.notifyItemRangeInserted(0, dynamicses.size());

        isSearchStatus = true;
        isHaveMore = true;
    }

    @Override
    public void disPlayMore(List<NewDynamics> dynamicses) {

        if (dynamicses.size() == 0) {
            isHaveMore = false;
            return;
        }

        int size = list_content.size();

        list_content.addAll(dynamicses);

        adapter.notifyItemRangeInserted(size, dynamicses.size());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void popupShareWindow(String dynamicsId, String content) {
        PopupShare popopShare = new PopupShare(context);
        popopShare.show();
        popopShare.getSb().setLinkUrl(Constant.DYNAMICS_SHARE_URL_PREFIX + dynamicsId);
        popopShare.getSb().setContent(content);
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
