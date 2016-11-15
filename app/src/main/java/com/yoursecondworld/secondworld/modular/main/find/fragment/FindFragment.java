package com.yoursecondworld.secondworld.modular.main.find.fragment;


import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import xiaojinzi.autolayout.utils.AutoUtils;

import com.yoursecondworld.secondworld.R;
import com.yoursecondworld.secondworld.common.AdapterNotify;
import com.yoursecondworld.secondworld.common.imagePreview.ImagePreviewAct;
import com.yoursecondworld.secondworld.common.videoFullScreenPlay.VideoFullScreenPlayAct;
import com.yoursecondworld.secondworld.modular.dynamics.adapter.DynamicsContentRecyclerViewAdapter;

import com.yoursecondworld.secondworld.modular.dynamics.entity.DynamicsResult;
import com.yoursecondworld.secondworld.modular.dynamics.entity.NewDynamics;
import com.yoursecondworld.secondworld.modular.dynamics.presenter.DynamicsPresenter;
import com.yoursecondworld.secondworld.modular.dynamics.view.IDynamicsView;
import com.yoursecondworld.secondworld.modular.dynamicsDetail.view.DynamicsDetailAct;
import com.yoursecondworld.secondworld.modular.main.eventEntity.ItemMenuEventEntity;
import com.yoursecondworld.secondworld.modular.main.eventEntity.ShareEventEntity;

import com.yoursecondworld.secondworld.modular.main.find.entity.AdvResult;
import com.yoursecondworld.secondworld.modular.main.find.presenter.FindPresenter;
import com.yoursecondworld.secondworld.modular.main.util.FindContentSpaceItemDecoration;
import com.yoursecondworld.secondworld.modular.search.ui.SearchAct;
import com.yoursecondworld.secondworld.modular.systemInfo.entity.UserResult;
import com.yoursecondworld.secondworld.modular.userDetail.view.UserDetailAct;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import xiaojinzi.activity.fragment.BaseFragment;
import xiaojinzi.annotation.Injection;
import xiaojinzi.base.android.activity.ActivityUtil;
import xiaojinzi.base.android.adapter.recyclerView.CommonRecyclerViewAdapter;
import xiaojinzi.base.android.os.T;
import xiaojinzi.view.CommonNineView;


/**
 * Created by cxj on 2016/7/11.
 * 发现界面,切换fragment到MainAct上面去的哦
 */
public class FindFragment extends BaseFragment implements IFindView, CommonRecyclerViewAdapter.OnViewInItemClickListener, IDynamicsView, SwipeRefreshLayout.OnRefreshListener {

    @Injection(value = R.id.rl_frag_find_head, click = "clickView")
    private RelativeLayout rl_headSearch = null;

    @Injection(R.id.sr)
    private SwipeRefreshLayout sr = null;

    @Injection(R.id.lv_frag_find)
    private RecyclerView rv = null;

    //    private BaseAdapter lv_adapter = null;
    private DynamicsContentRecyclerViewAdapter lv_adapter = null;

    /**
     * listview的数据
     */
    private List<NewDynamics> list_content = new ArrayList<NewDynamics>();

    private FindPresenter presenter = new FindPresenter(this);

    /**
     * 点赞收藏的主持人
     */
    private DynamicsPresenter dynamicsPresenter = new DynamicsPresenter(this);

    private boolean isNoTAnyMore;


    @Override
    public int getLayoutId() {
        return R.layout.frag_find_for_main;
    }

    @Override
    protected void initView() {
        super.initView();

        //创建适配器
        lv_adapter = new DynamicsContentRecyclerViewAdapter(context, list_content, rv);

        //添加listviewd的头部
        addListViewHeader();

        //布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(linearLayoutManager);

        //适配不同屏幕的之间的条目间隔
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.frag_hot_dynamics_listview_item_space);
        dimensionPixelSize = AutoUtils.getPercentHeightSize(dimensionPixelSize);

        //条目之间的间隔
        FindContentSpaceItemDecoration itemDecoration = new FindContentSpaceItemDecoration(dimensionPixelSize);
        rv.addItemDecoration(itemDecoration);

        //设置适配器
        rv.setAdapter(lv_adapter);

    }

    @Override
    protected void initData() {
        super.initData();
        presenter.getAllAdv();
        presenter.getAllPopupLarStar();
        presenter.getHotDynamics();
    }

    @Override
    protected void setOnListener() {
        super.setOnListener();

        //设置下拉刷新的监听
        sr.setOnRefreshListener(this);

        //让adapter去管理视频的播放和销毁
        rv.addOnScrollListener(lv_adapter.new MyScrollListener());

        //加载更多的监听
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE) { //如果暂停了

                    boolean b = !ViewCompat.canScrollVertically(rv, 1);
                    //如果已经不可以向下滑动了,就去加载更多
                    if (b && !isNoTAnyMore) {
                        presenter.getMoreHotDynamics();
                    }
                }
            }
        });

        lv_adapter.setOnRecyclerViewItemClickListener(new CommonRecyclerViewAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                //跳转到动态详情去
                goToDynamicsDetail(position);
            }
        });

        //监听item内部的控件点击事件
        lv_adapter.setOnViewInItemClickListener(this,
                R.id.iv_dynamics_content_item_user_icon,    //用户头像
                R.id.iv_dynamics_content_item_arrow_bottom, //往下的箭头
                R.id.cnv_dynamics_content_item_images,      //显示图片
                R.id.rl_share,   //分享
                R.id.rl_collect, //收藏
                R.id.rl_zan,      //点赞
                R.id.rl_video                                //视频
        );//收藏

    }

    private Header header;

    /**
     * 添加ListView的头部
     */
    private void addListViewHeader() {
        //添加头部.addHeaderView(headerView);
        header = new Header(context);
        lv_adapter.addHeaderView(header.getHeaderView());
    }

    public void clickView(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.rl_frag_find_head: //点击了发现界面的搜索框框

                ActivityUtil.startActivity(context, SearchAct.class);

                break;
        }
    }

    @Override
    public void onLoadAdvSuccess(AdvResult advResult) {
        header.disPlayAdv(advResult.getBanner());
        //停止刷新
        sr.setRefreshing(false);
    }

    @Override
    public void onLoadPopupLarStarSuccess(UserResult userResult) {
        header.disPlayPopupLarStar(userResult.getUsers());
        //停止刷新
        sr.setRefreshing(false);
    }

    @Override
    public void onloadHotDynamicsSuccess(DynamicsResult entity) {

        AdapterNotify.notifyFreshData(list_content, entity.getArticles(), lv_adapter);

        //停止刷新
        sr.setRefreshing(false);

        //标识一下还有数据
        isNoTAnyMore = false;

    }

    @Override
    public void onloadMoreHotDynamicsSuccess(DynamicsResult entity) {

        if (entity.getArticles().size() == 0) {
            tip("没有内容啦");
            isNoTAnyMore = true;
            return;
        }
        AdapterNotify.notifyAppendData(list_content, entity.getArticles(), lv_adapter);

    }

    private String pass;

    @Override
    public String getPass() {
        return pass;
    }

    @Override
    public void savePass(String pass) {
        this.pass = pass;
    }

    @Override
    public void tip(String content) {
        T.showShort(context, content);
    }

    @Override
    public void onSessionInvalid() {

    }

    /**
     * item内部控件的点击事件
     *
     * @param v
     * @param position
     */
    @Override
    public void onViewInItemClick(View v, int position) {

        int id = v.getId();

        NewDynamics dynamicsContentEntity = null;

        switch (id) {
            case R.id.iv_dynamics_content_item_user_icon: //用户头像
                //获取到点击的头像的id
                String targetId = list_content.get(position).getUser().getUser_id();
                context.startActivity(new Intent(context, UserDetailAct.class).putExtra(UserDetailAct.TARGET_USER_ID_FLAG, targetId));
                break;
            case R.id.rl_share: //分享

                ShareEventEntity shareEventEntity = new ShareEventEntity();
                shareEventEntity.dynamicsId = list_content.get(position).get_id();
                shareEventEntity.content = list_content.get(position).getContent();
                EventBus.getDefault().post(shareEventEntity);
                break;

            case R.id.rl_collect: //收藏

                dynamicsContentEntity = list_content.get(position);
                ImageView iv_collect = (ImageView) v.findViewById(R.id.iv_dynamics_content_item_foot_collect);

                //表示现在是没有收藏的状态
                if (dynamicsContentEntity.is_collected()) {
                    dynamicsPresenter.uncollect_article(iv_collect, dynamicsContentEntity, dynamicsContentEntity.get_id());
                } else {
                    dynamicsPresenter.collect_article(iv_collect, dynamicsContentEntity, dynamicsContentEntity.get_id());
                }

                break;

            case R.id.rl_zan: //点赞

                dynamicsContentEntity = list_content.get(position);
                ImageView iv_zan = (ImageView) v.findViewById(R.id.iv_dynamics_content_item_foot_zan);
                TextView tv_zan = (TextView) ((ViewGroup) v.getParent()).findViewById(R.id.tv_dynamics_content_item_foot_zan_count);

                //表示现在是没有收藏的状态
                if (dynamicsContentEntity.is_liked()) {
                    dynamicsPresenter.cancelZan(tv_zan, iv_zan, dynamicsContentEntity, dynamicsContentEntity.get_id());
                } else {
                    dynamicsPresenter.zan(tv_zan, iv_zan, dynamicsContentEntity, dynamicsContentEntity.get_id());
                }

                break;
            case R.id.iv_dynamics_content_item_arrow_bottom: //条目中的往下的箭头
                ItemMenuEventEntity eventEntity = new ItemMenuEventEntity();
                eventEntity.from = ItemMenuEventEntity.FROM_FIND;
                EventBus.getDefault().post(eventEntity);
                break;
            case R.id.cnv_dynamics_content_item_images: //点击的是九宫格控件

                CommonNineView cdv = (CommonNineView) v;

                int clickImageIndex = cdv.getClickImageIndex();

                if (clickImageIndex == -1) { //表示没有一个图片是被点击的
                    goToDynamicsDetail(position);
                    return;
                }

                //拿到对应的item实体对象
                dynamicsContentEntity = list_content.get(position);

                //如果动态的类型是图片的类型

                //拆分得到图片数组
                List<String> picture_list = dynamicsContentEntity.getPicture_list();
                int size = picture_list.size();
                if (size > 0) {
                    //创建意图跳转到图片预览界面
                    Intent intent = new Intent(context, ImagePreviewAct.class);

                    Bundle bundle = new Bundle();
                    bundle.putStringArrayList(ImagePreviewAct.IMAGES_FLAG, (ArrayList<String>) picture_list);
                    bundle.putInt(ImagePreviewAct.POSITION, clickImageIndex);
                    intent.putExtras(bundle);

                    startActivity(intent);
                }

                break;

            case R.id.rl_video: //点击的是视频

                Intent i = new Intent(context, VideoFullScreenPlayAct.class);
                i.putExtra(VideoFullScreenPlayAct.VIDEO_FALG, list_content.get(position).getVideo_url());
                context.startActivity(i);

                break;
        }
    }

    /**
     * 去动态详情的界面
     *
     * @param position
     */
    private void goToDynamicsDetail(int position) {
        //跳转到动态详情去
        Intent i = new Intent(context, DynamicsDetailAct.class);

        //携带被点击的item的动态的id过去
        i.putExtra(DynamicsDetailAct.DYNAMICS_ID_FLAG, list_content.get(position).get_id());

        context.startActivity(i);
    }

    @Override
    public void onZanSuccess(TextView tv_zan, final ImageView iv_zan, NewDynamics dynamics, int liked_number) {
        iv_zan.setImageResource(R.mipmap.zaned1);
        dynamics.setLiked_number(liked_number);
        tv_zan.setText(dynamics.getLiked_number() + "");

        ValueAnimator objectAnimator = ObjectAnimator//
                .ofFloat(1f, 1.4f, 1f)//
                .setDuration(1000);

        //设置更新数据的监听
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
                iv_zan.setScaleX(value);
                iv_zan.setScaleY(value);
            }

        });

        objectAnimator.start();

        dynamics.setIs_liked(true);

    }

    @Override
    public void onCancleZanSuccess(TextView tv_zan, final ImageView iv_zan, NewDynamics dynamics, int liked_number) {
        iv_zan.setImageResource(R.mipmap.zan1);
        dynamics.setLiked_number(liked_number);
        dynamics.setIs_liked(false);
        tv_zan.setText(liked_number + "");
    }

    @Override
    public void onCollectSuccess(final ImageView iv_collect, NewDynamics dynamics) {
        iv_collect.setImageResource(R.mipmap.collected1);

        ValueAnimator objectAnimator = ObjectAnimator//
                .ofFloat(1f, 1.4f, 1f)//
                .setDuration(1000);

        //设置更新数据的监听
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
                iv_collect.setScaleX(value);
                iv_collect.setScaleY(value);
            }

        });

        objectAnimator.start();

        dynamics.setIs_collected(true);
    }

    @Override
    public void onUnCollectSuccess(ImageView iv_collect, NewDynamics dynamics) {
        iv_collect.setImageResource(R.mipmap.uncollected1);

        dynamics.setIs_collected(false);
    }

    @Override
    public void onReportDynamicsSuccess() {
    }

    @Override
    public void onRefresh() {
        initData();
    }

}
