package com.yoursecondworld.secondworld.modular.collection.view;


import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yoursecondworld.secondworld.R;
import com.yoursecondworld.secondworld.common.Constant;
import com.yoursecondworld.secondworld.common.event.SessionInvalidEvent;
import com.yoursecondworld.secondworld.modular.collection.itemDecoration.CollectionItemDecoration;
import com.yoursecondworld.secondworld.modular.collection.presenter.CollectionPresenter;
import com.yoursecondworld.secondworld.modular.dynamics.adapter.DynamicsContentRecyclerViewAdapter;
import com.yoursecondworld.secondworld.modular.dynamics.entity.NewDynamics;
import com.yoursecondworld.secondworld.modular.dynamics.view.BaseDynamicsActivity;
import com.yoursecondworld.secondworld.modular.main.eventEntity.ItemMenuEventEntity;
import com.yoursecondworld.secondworld.modular.main.popupWindow.itemMenu.ItemMenu;
import com.yoursecondworld.secondworld.modular.main.popupWindow.newShare.PopupShare;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import xiaojinzi.annotation.Injection;
import xiaojinzi.base.android.adapter.recyclerView.CommonRecyclerViewAdapter;
import xiaojinzi.base.android.os.T;


/**
 * 收藏的界面
 */
@Injection(R.layout.act_collection)
public class CollectionAct extends BaseDynamicsActivity implements ICollectionView, SwipeRefreshLayout.OnRefreshListener {

    /**
     * 标题栏的容器
     */
    @Injection(R.id.rl_act_titlebar_container)
    private RelativeLayout rl_titlebarContainer = null;

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

    @Injection(R.id.sr)
    private SwipeRefreshLayout sr = null;

    @Injection(R.id.rv_act_collection_content)
    private RecyclerView rv = null;

    private boolean isNoTAnyMore;

    /**
     * 收藏的主持人
     */
    private CollectionPresenter presenter = new CollectionPresenter(this);

    @Override
    public void initView() {
        super.initView();

        /**
         * 初始化基本信息
         */
        initBase();

        /**
         * 初始化显示的内容
         */
        initDynamicsContent();

    }

    @Override
    public void initData() {
        super.initData();
        presenter.getCollectDynamics();
    }


    /**
     * 初始化基本信息
     */
    private void initBase() {

        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) rl_titlebar.getLayoutParams();
        lp.topMargin = statusHeight;

        tv_titleName.setText("收藏");

        //针对白色的标题栏需要做的操作
//        boolean b = SystemUtil.FlymeSetStatusBarLightMode(getWindow(), true);
//        if (!b) {
//            b = SystemUtil.MIUISetStatusBarLightMode(getWindow(), true);
//            if (!b) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//                } else {
//                    //设置标题栏背景为黑色
//                    rl_titlebarContainer.setBackgroundColor(Color.BLACK);
//                }
//            }
//        }

    }

    @Override
    public void setOnlistener() {
        super.setOnlistener();

        sr.setOnRefreshListener(this);

        //加载更多的监听
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE) { //如果暂停了

                    boolean b = !ViewCompat.canScrollVertically(rv, 1);
                    //如果已经不可以向下滑动了,就去加载更多
                    if (b) {
                        if (!isNoTAnyMore) {
                            presenter.getMoreCollectDynamics();
                        } else {
//                            tip("没有啦");
                        }
                    }
                }
            }
        });

        //让adapter去管理视频的播放和销毁
        rv.addOnScrollListener(adapter.new MyScrollListener());

        //设置点击事件
        adapter.setOnRecyclerViewItemClickListener(new CommonRecyclerViewAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                goToDynamicsDetail(position);
            }
        });

    }

    /**
     * 初始化动态内容
     */
    private void initDynamicsContent() {

        //创建布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //设置布局管理器
        rv.setLayoutManager(layoutManager);

        rv.addItemDecoration(new CollectionItemDecoration());

        //创建适配器
        adapter = new DynamicsContentRecyclerViewAdapter(context, list_content, rv);

        rv.setAdapter(adapter);

    }

    public void popupShareWindow(String dynamicsId, String content) {
        PopupShare popopShare = new PopupShare(context);
        popopShare.show();
        popopShare.getSb().setLinkUrl(Constant.DYNAMICS_SHARE_URL_PREFIX + dynamicsId);
        popopShare.getSb().setContent(content);
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

    @Override
    public void tip(String content) {
        T.showShort(context, content);
    }

    @Override
    public void onLoadDataSuccess(List<NewDynamics> newDynamicses) {

        int size = list_content.size();
        list_content.clear();

        adapter.notifyItemRangeRemoved(0, size);

        list_content.addAll(newDynamicses);
        adapter.notifyItemRangeInserted(0, newDynamicses.size());

        isNoTAnyMore = false;

        //停止刷新
        sr.setRefreshing(false);

    }

    @Override
    public String getPass() {
        return pass;
    }

    @Override
    public void onLoadMoreDataSuccess(List<NewDynamics> newDynamicses) {

        if (newDynamicses.size() == 0) {
            tip("没有更多啦");
            isNoTAnyMore = true;
            return;
        }

        int size = list_content.size();

        list_content.addAll(newDynamicses);
        adapter.notifyItemRangeInserted(size, newDynamicses.size());

    }

    /**
     * 分页用
     */
    private String pass;

    @Override
    public void savePass(String pass) {
        this.pass = pass;
    }

    @Override
    public void onRefresh() {
        presenter.getCollectDynamics();
    }

    /**
     * 弹出点击item中的下拉小箭头之后的被调用
     *
     * @param item
     */
    @Subscribe
    public void onEventShowDynamicsItemMenu(ItemMenuEventEntity item) {
        if (item.from == ItemMenuEventEntity.FROM_COLLECT) {
            ItemMenu itemMenu = new ItemMenu(context, item);
            itemMenu.show();
        }
    }

    @Override
    public void onViewInItemClick(View v, int position) {
        int id = v.getId();

        if (id == R.id.iv_dynamics_content_item_arrow_bottom) {
            ItemMenuEventEntity eventEntity = new ItemMenuEventEntity();
            eventEntity.from = ItemMenuEventEntity.FROM_COLLECT;
            eventEntity.dynamicsId = list_content.get(position).get_id();
            eventEntity.userId = list_content.get(position).getUser().getUser_id();
            EventBus.getDefault().post(eventEntity);
            return;
        }

        if (id == R.id.iv_dynamics_content_item_foot_share) {
            popupShareWindow(list_content.get(position).get_id(), list_content.get(position).getContent());
            return;
        }
        super.onViewInItemClick(v, position);
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
