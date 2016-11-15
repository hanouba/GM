package com.yoursecondworld.secondworld.modular.main.home.fragment;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
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

import com.yoursecondworld.secondworld.modular.dynamics.itemDecoration.DynamicsContentSpaceItemDecoration;
import com.yoursecondworld.secondworld.modular.main.home.presenter.HomePresenter;
import com.yoursecondworld.secondworld.modular.userDetail.view.UserDetailAct;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import xiaojinzi.activity.fragment.BaseFragment;
import xiaojinzi.annotation.Injection;
import xiaojinzi.base.android.adapter.recyclerView.CommonRecyclerViewAdapter;
import xiaojinzi.base.android.os.T;
import xiaojinzi.view.CommonNineView;

/**
 * Created by cxj on 2016/7/9.
 * Home主页,挂载在MainAct中,点击底部的home选项卡切换到这个fragment
 */
public class HomeFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, IHomeView, CommonRecyclerViewAdapter.OnViewInItemClickListener, IDynamicsView {

    private Handler h = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            sr.setRefreshing(false);

        }

    };

    /**
     * 最上面的选择项卡热门动态
     */
    @Injection(value = R.id.tv_act_main_head_hot_dynamics, click = "clickView")
    private TextView tv_hotDynamics = null;

    /**
     * 最上面的选择项卡我的关注
     */
    @Injection(value = R.id.tv_act_main_head_my_attention, click = "clickView")
    private TextView tv_myAttention = null;

    /**
     * android原生的下拉刷新控件
     */
    @Injection(R.id.sr_frag_home_dynamics)
    private SwipeRefreshLayout sr = null;

    @Injection(R.id.lv_frag_home_dynamics_content)
    private RecyclerView rv_content = null;

    /**
     * 内容显示的数据
     */
//    private List<NewDynamics> list_content = new ArrayList<NewDynamics>();

    private DynamicsResult dynamicsResult = new DynamicsResult();

    /**
     * 水平的线性的布局管理器,这个布局管理器是显示水平的标签用的
     */
    private LinearLayoutManager labelLayoutManager;

    /**
     * 标签显示的集合
     */
    private List<String> labels = new ArrayList<String>();

    /**
     * 记录标签是否被选择
     */
    private List<Boolean> labels_selectFlags = new ArrayList<Boolean>();

    /**
     * 显示标签的适配器
     */
    private CommonRecyclerViewAdapter adapter_labels = null;

    /**
     * 显示内容的适配器
     */
    private DynamicsContentRecyclerViewAdapter adapter_content = null;

    /**
     * 选中的标签的下标
     */
    private int selectedLabelIndex = -1;

    /**
     * 全部标签字样的textView选中时候的颜色
     */
    private int selectedAllLabelColor;

    /**
     * 全部标签字样的textView未选中时候的颜色
     */
    private int unselectedAllLabelColor;

    /**
     * 主持人
     */
    private HomePresenter homePresenter = new HomePresenter(this);

    /**
     * 点赞收藏的主持人
     */
    private DynamicsPresenter dynamicsPresenter = new DynamicsPresenter(this);

    /**
     * 这个标识记录下来现在是处在
     * 热门动态or
     * 我的关注
     * 选项卡下面
     */
    private boolean isHotTab = true;

    /**
     * 表示是否没有数据了
     */
    private boolean isNoTAnyMore;

    @Override
    public int getLayoutId() {
        return R.layout.frag_home_dynamics_for_main;
    }

    /**
     * 初始化试图
     */
    @Override
    protected void initView() {

        selectedAllLabelColor = context.getResources().getColor(R.color.common_app_color);
        unselectedAllLabelColor = context.getResources().getColor(R.color.gray_two);

        //初始化内容
        initContent();

    }

    /**
     * 初始化内容
     */
    private void initContent() {

        //创建内容适配器
        adapter_content = new DynamicsContentRecyclerViewAdapter(context, dynamicsResult.getArticles(), rv_content);

        //布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_content.setLayoutManager(linearLayoutManager);

        //适配不同屏幕的之间的条目间隔
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.frag_hot_dynamics_listview_item_space);
        dimensionPixelSize = AutoUtils.getPercentHeightSize(dimensionPixelSize);

        //条目之间的间隔
        DynamicsContentSpaceItemDecoration itemDecoration = new DynamicsContentSpaceItemDecoration(dimensionPixelSize);
        rv_content.addItemDecoration(itemDecoration);

        //设置适配器
        rv_content.setAdapter(adapter_content);
    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {
        //相当于刷新数据
        homePresenter.getDynamics();
    }

    /**
     * 设置监听
     */
    @Override
    protected void setOnListener() {

        //设置下拉刷新的监听
        sr.setOnRefreshListener(this);
        //设置下拉刷新的一个颜色的资源
        sr.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light);

        rv_content.addOnScrollListener(adapter_content.new MyScrollListener());

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
                        if (isHotTab) {
                            if (gameTag == null) {
                                homePresenter.loadMoreDynamics();
                            } else {
                                homePresenter.loadMoreGameDynamics();
                            }
                        } else {
                            homePresenter.getMoreMyFollowDynamics();
                        }
                    }
                }
            }
        });

        //设置内容的item点击事件
        adapter_content.setOnRecyclerViewItemClickListener(new CommonRecyclerViewAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {

                //跳转到动态详情去
                goToDynamicsDetail(position);

            }
        });

        //监听item内部的控件点击事件
        adapter_content.setOnViewInItemClickListener(this,
                R.id.iv_dynamics_content_item_user_icon,    //用户头像
                R.id.iv_dynamics_content_item_arrow_bottom, //往下的箭头
                R.id.cnv_dynamics_content_item_images,      //显示图片
                R.id.rl_share,   //分享
                R.id.rl_collect, //收藏
                R.id.rl_zan,      //点赞
                R.id.rl_video                                //视频
        );//收藏

    }

    /**
     * 游戏标签的数据先造假
     */
    private void makeLabelsData() {
        labels.clear();

        labels_selectFlags.clear();

        int size = labels.size();
        for (int i = 0; i < size; i++) {
            labels_selectFlags.add(false);
        }
    }

    /**
     * 点击事件的集中处理
     *
     * @param view
     */
    public void clickView(View view) {
        int id = view.getId();

        switch (id) {

            case R.id.tv_act_main_head_hot_dynamics: //如果点击的是热门动态

                if (isHotTab) {
                    return;
                }

                tv_hotDynamics.setTextColor(Color.WHITE);
                tv_myAttention.setTextColor(Color.parseColor("#88FFFFFF"));
                tv_hotDynamics.setBackground(getResources().getDrawable(R.drawable.main_head_swith_hot_dynamics_selected_bg));
                tv_myAttention.setBackground(getResources().getDrawable(R.drawable.main_head_swith_my_attention_unselected_bg));

                if (gameTag == null) {
                    homePresenter.getDynamics();
                } else {
                    homePresenter.getGameDynamics();
                }

                isHotTab = true;

                break;
            case R.id.tv_act_main_head_my_attention: //如果点击的是我的关注

                if (!isHotTab) {
                    return;
                }

                tv_hotDynamics.setTextColor(Color.parseColor("#88FFFFFF"));
                tv_myAttention.setTextColor(Color.WHITE);
                tv_hotDynamics.setBackground(getResources().getDrawable(R.drawable.main_head_swith_hot_dynamics_unselected_bg));
                tv_myAttention.setBackground(getResources().getDrawable(R.drawable.main_head_swith_my_attention_selected_bg));

                homePresenter.getMyFollowDynamics();

                isHotTab = false;

                break;
        }

    }

    /**
     * 下拉刷新控件的回调
     */
    @Override
    public void onRefresh() {
        int size = labels_selectFlags.size();
        for (int i = 0; i < size; i++) {
            if (labels_selectFlags.get(i)) { //如果发现标签中有选中的
//                gameLabelId = labels.get(i).getId();
                break;
            }
        }
        if (isHotTab) {
            if (gameTag == null) {
                homePresenter.getDynamics();
            } else {
                homePresenter.getGameDynamics();
            }
        } else {
            homePresenter.getMyFollowDynamics();
        }
    }

    @Override
    protected void freshUI() {
        super.freshUI();
    }


    /**
     * 选择的游戏标签,如果点击了全部标签,
     * 那么就是置为null,如果点击了其他的游戏标签,
     * 就是对应游戏标签的id
     */
    private String gameTag;

    @Override
    public String getGameTag() {
        return gameTag;
    }

    /**
     * 成功加载数据
     *
     * @param entity
     */
    @Override
    public void onLoadDynamicsSuccess(DynamicsResult entity) {

        AdapterNotify.notifyFreshData(dynamicsResult.getArticles(), entity.getArticles(), adapter_content);

        h.sendEmptyMessage(0);

        isNoTAnyMore = false;

    }

    @Override
    public void onLoadFollowDynamicsSuccess(DynamicsResult entity) {

        AdapterNotify.notifyFreshData(dynamicsResult.getArticles(), entity.getArticles(), adapter_content);

        h.sendEmptyMessage(0);

        isNoTAnyMore = false;

    }

    @Override
    public String getPass() {
        return dynamicsResult.getPass();
    }

    @Override
    public void onLoadMoreDynamicsSuccess(DynamicsResult entity) {
        if (entity.getArticles().size() == 0) {
            tip("没有内容啦");
            isNoTAnyMore = true;
            return;
        }
        AdapterNotify.notifyAppendData(dynamicsResult.getArticles(), entity.getArticles(), adapter_content);
    }

    @Override
    public void onLoadMoreFollowDynamicsSuccess(DynamicsResult entity) {
        if (entity.getArticles().size() == 0) {
            tip("没有内容啦");
            isNoTAnyMore = true;
            return;
        }

        AdapterNotify.notifyAppendData(dynamicsResult.getArticles(), entity.getArticles(), adapter_content);

    }

    /**
     * 分页标识
     */
//    private String pass;
    @Override
    public void savePass(String pass) {
        dynamicsResult.setPass(pass);
    }

    @Override
    public void showDialog(String content) {
        sr.setRefreshing(true);
    }

    @Override
    public void closeDialog() {
        h.sendEmptyMessageDelayed(0, 1000);
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
                String targetId = dynamicsResult.getArticles().get(position).getUser().getUser_id();
                context.startActivity(new Intent(context, UserDetailAct.class).putExtra(UserDetailAct.TARGET_USER_ID_FLAG, targetId));
                break;
            case R.id.rl_share: //分享

                ShareEventEntity shareEventEntity = new ShareEventEntity();
                shareEventEntity.dynamicsId = dynamicsResult.getArticles().get(position).get_id();
                shareEventEntity.content = dynamicsResult.getArticles().get(position).getContent();
                EventBus.getDefault().post(shareEventEntity);
                break;

            case R.id.rl_collect: //收藏

                dynamicsContentEntity = dynamicsResult.getArticles().get(position);
                ImageView iv_collect = (ImageView) v.findViewById(R.id.iv_dynamics_content_item_foot_collect);

                //表示现在是没有收藏的状态
                if (dynamicsContentEntity.is_collected()) {
                    dynamicsPresenter.uncollect_article(iv_collect, dynamicsContentEntity, dynamicsContentEntity.get_id());
                } else {
                    dynamicsPresenter.collect_article(iv_collect, dynamicsContentEntity, dynamicsContentEntity.get_id());
                }

                break;

            case R.id.rl_zan: //点赞

                dynamicsContentEntity = dynamicsResult.getArticles().get(position);
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
                eventEntity.from = ItemMenuEventEntity.FROM_HOME;
                eventEntity.dynamicsId = dynamicsResult.getArticles().get(position).get_id();
                eventEntity.userId = dynamicsResult.getArticles().get(position).getUser().getUser_id();
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
                dynamicsContentEntity = dynamicsResult.getArticles().get(position);

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
                i.putExtra(VideoFullScreenPlayAct.VIDEO_FALG, dynamicsResult.getArticles().get(position).getVideo_url());
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

        if (position > -1 && position < dynamicsResult.getArticles().size()) {
            //跳转到动态详情去
            Intent i = new Intent(context, DynamicsDetailAct.class);

            //携带被点击的item的动态的id过去
            i.putExtra(DynamicsDetailAct.DYNAMICS_ID_FLAG, dynamicsResult.getArticles().get(position).get_id());

            context.startActivity(i);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        //调整标签的右边的箭头的是否显示
        h.sendEmptyMessageDelayed(10, 1000);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

}
