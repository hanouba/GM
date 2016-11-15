package com.yoursecondworld.secondworld.modular.userDetail.view;


import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yoursecondworld.secondworld.R;
import com.yoursecondworld.secondworld.common.AdapterNotify;
import com.yoursecondworld.secondworld.common.BaseAct;
import com.yoursecondworld.secondworld.common.Constant;
import com.yoursecondworld.secondworld.common.StaticDataStore;
import com.yoursecondworld.secondworld.common.event.SessionInvalidEvent;
import com.yoursecondworld.secondworld.common.imagePreview.ImagePreviewAct;
import com.yoursecondworld.secondworld.common.videoFullScreenPlay.VideoFullScreenPlayAct;
import com.yoursecondworld.secondworld.modular.commonFunction.loadMyDynamics.presenter.LoadMyDynamicsPresenter;
import com.yoursecondworld.secondworld.modular.commonFunction.loadMyDynamics.view.ILoadMyDynamicsView;
import com.yoursecondworld.secondworld.modular.commonFunction.user.presenter.UserPresenter;
import com.yoursecondworld.secondworld.modular.commonFunction.user.view.IUserView;
import com.yoursecondworld.secondworld.modular.dynamics.adapter.DynamicsContentRecyclerViewAdapter;
import com.yoursecondworld.secondworld.modular.dynamics.entity.NewDynamics;
import com.yoursecondworld.secondworld.modular.dynamics.presenter.DynamicsPresenter;
import com.yoursecondworld.secondworld.modular.dynamics.view.IDynamicsView;
import com.yoursecondworld.secondworld.modular.dynamicsDetail.view.DynamicsDetailAct;
import com.yoursecondworld.secondworld.modular.im.ConversationAct;
import com.yoursecondworld.secondworld.modular.main.eventEntity.ItemMenuEventEntity;
import com.yoursecondworld.secondworld.modular.main.popupWindow.itemMenu.ItemMenu;
import com.yoursecondworld.secondworld.modular.main.popupWindow.newShare.PopupShare;
import com.yoursecondworld.secondworld.modular.systemInfo.entity.NewUser;
import com.yoursecondworld.secondworld.modular.systemInfo.entity.UserResult;
import com.yoursecondworld.secondworld.modular.userDetail.adapter.UserDetailAdapter;
import com.yoursecondworld.secondworld.modular.userDetail.itemDecoration.UserDetailContentSpaceItemDecoration;
import com.yoursecondworld.secondworld.modular.userDetail.presenter.UserDetailPresenter;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import xiaojinzi.annotation.Injection;
import xiaojinzi.autolayout.utils.AutoUtils;
import xiaojinzi.base.android.adapter.recyclerView.CommonRecyclerViewAdapter;
import xiaojinzi.base.android.os.ScreenUtils;
import xiaojinzi.base.android.os.T;
import xiaojinzi.view.CommonNineView;

/**
 * 用户详情的界面
 */
@Injection(R.layout.act_user_detail)
public class UserDetailAct extends BaseAct implements IUserDetailView, ILoadMyDynamicsView, IUserView,
        CommonRecyclerViewAdapter.OnViewInItemClickListener, IDynamicsView {

    /**
     * 跳转到这个界面要传递的数据的key
     */
    public static final String TARGET_USER_ID_FLAG = "targetId";

    //========================================================================================


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
     * 标题栏的文本
     */
    @Injection(R.id.tv_title_name)
    private TextView tv_titleName = null;

    /**
     * 返回按钮
     */
    @Injection(value = R.id.iv_back, click = "finishMe")
    private ImageView iv_back = null;

    /**
     * 返回按钮
     */
    @Injection(value = R.id.iv_user_detail_back, click = "finishMe")
    private ImageView iv_back2 = null;

    //==============================================================================================


    @Injection(value = R.id.rl_person_info, click = "clickView")
    private RelativeLayout rl_personInfo = null;

    /**
     * 展示数据的列表
     */
    @Injection(R.id.rv_act_user_detail_content)
    private RecyclerView rv = null;

    /**
     * 适配器
     */
    private CommonRecyclerViewAdapter adapter = null;

    /**
     * 要显示的数据.但是这个集合中的数据在选项卡被切换的时候会被删除或者添加下面这个集合中的数据
     */
    private List<NewDynamics> data = new ArrayList<NewDynamics>();

    /**
     * 这个用来存放真的动态数据
     */
    private List<NewDynamics> dynamics = new ArrayList<NewDynamics>();

    /**
     * 是否加载过了动态
     */
    private boolean isLoadDynamics = false;

    /**
     * 用户详情的主持人
     */
    private UserDetailPresenter presenter = new UserDetailPresenter(this);

    /**
     * 用于点赞，收藏等操作
     */
    protected DynamicsPresenter dynamicsPresenter = new DynamicsPresenter(this);

    /**
     * 加载我的动态的主持人
     */
    private LoadMyDynamicsPresenter loadMyDynamicsPresenter = new LoadMyDynamicsPresenter(this);

    private UserPresenter userPresenter = new UserPresenter(this);

    /**
     * 目标用户的id
     */
    private String targetUserId = null;

    /**
     * 是否关注了用户
     */
    private boolean isFollowUser = false;

    /**
     * 是否进入了自己的用户详情
     */
    private boolean isMyInfo;

    private UserDetailContentSpaceItemDecoration itemDecoration;

    /**
     * 表示是否没有数据了
     */
    private boolean isNoTAnyMore;

    @Override
    public void initView() {
        super.initView();

        //状态栏高度
        statusHeight = ScreenUtils.getStatusHeight(context);

        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) rl_titlebar.getLayoutParams();
        lp.topMargin = statusHeight;

        lp = (RelativeLayout.LayoutParams) iv_back2.getLayoutParams();
        lp.topMargin = statusHeight + AutoUtils.getPercentHeightSize(20);

        tv_titleName.setVisibility(View.GONE);
        rl_titlebarContainer.setAlpha(0f);
        iv_back2.setAlpha(1f);

        //获取跳转过来的数据
        targetUserId = getIntent().getStringExtra(TARGET_USER_ID_FLAG);

        if (TextUtils.isEmpty(targetUserId)) { //表示跳转过来的时候数据没有传对
            T.showShort(context, "参数错误!");
            finish();
        }

        //判断是否是自己
        isMyInfo = targetUserId.equals(StaticDataStore.newUser.getUser_id());

        //如果是自己
        if (isMyInfo) {
            //创建适配器,这个适配器是继承自DynamicsContentRecyclerViewAdapter,目的就是隐藏了下拉箭头
            adapter = new UserDetailAdapter(context, data, rv);
        } else {
            //创建适配器
            adapter = new DynamicsContentRecyclerViewAdapter(context, data, rv);
        }

        //适配不同屏幕的之间的条目间隔
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.frag_hot_dynamics_listview_item_space);
        dimensionPixelSize = AutoUtils.getPercentHeightSize(dimensionPixelSize);

        //条目之间的间隔
        itemDecoration = new UserDetailContentSpaceItemDecoration(dimensionPixelSize);
        rv.addItemDecoration(itemDecoration);

        //创建布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(layoutManager);

        //初始化头部
        addTopHeader();

        if (isMyInfo) { //如果是自己,隐藏菜单栏
            headView.setMenuVisiable(false);
        }

        //设置适配器
        rv.setAdapter(adapter);

    }

    /**
     * 加载用户数据解析出来的user对象
     */
    private NewUser u;

    @Override
    public void initData() {
        super.initData();

        //获取用户信息
        presenter.getUserInfo();

        //获取动态
        loadMyDynamicsPresenter.loadMyDynamics();

    }

    @Override
    public void setOnlistener() {
        super.setOnlistener();

        //状态栏有一个颜色渐变的效果
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {

            /**
             * 不能超过这个值,因为会出现因为滑动距离计算不对的情况
             */
            private float tmp = AutoUtils.getPercentHeightSize(256);

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


                int scollYDistance = getScollYDistance();

                float a = 0f + scollYDistance / tmp;

                if (a > 1) {
                    a = 1;
                }

                iv_back2.setAlpha(1 - a);
                rl_titlebarContainer.setAlpha(a);

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
                        if (adapter.getHeaderCounts() == 2) { //说明不是动态的选项卡下面
                            return;
                        }
                        if (isNoTAnyMore) {
//                            tip("没有啦");
                            return;
                        }

                        loadMyDynamicsPresenter.loadMoreMyDynamics();

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

        //监听item内部的控件点击事件
        adapter.setOnViewInItemClickListener(this,
                R.id.iv_dynamics_content_item_user_icon,    //用户头像
                R.id.iv_dynamics_content_item_arrow_bottom, //往下的箭头
                R.id.cnv_dynamics_content_item_images,      //显示图片
                R.id.rl_share,   //分享
                R.id.rl_collect, //收藏
                R.id.rl_zan,      //点赞
                R.id.rl_video                                //视频
        );//收藏

        headView.setOnFollowListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFollowUser) { //是否关注用户
                    userPresenter.unFollowUser(getTargetUserId());
                } else {
                    //去关注用户
                    userPresenter.followUser(getTargetUserId());
                }
            }
        });

        headView.setOnChatListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RongIM rongIM = RongIM.getInstance();
                RongIMClient.ConnectionStatusListener.ConnectionStatus currentConnectionStatus = rongIM.getCurrentConnectionStatus();
                if (currentConnectionStatus == RongIMClient.ConnectionStatusListener.ConnectionStatus.CONNECTED) { //如果是已经连接的
                    if (u != null) {
                        ConversationAct.targetUserId = u.getUser_id();
                        rongIM.startPrivateChat(context, getTargetUserId() + "", u.getUser_nickname());
                    }
                } else {
                    T.showShort(context, "未连接");
                }
            }
        });

    }

    public int getScollYDistance() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) rv.getLayoutManager();
        int position = layoutManager.findFirstVisibleItemPosition();
        View firstVisiableChildView = layoutManager.findViewByPosition(position);
        int itemHeight = firstVisiableChildView.getHeight();
        return (position) * itemHeight - firstVisiableChildView.getTop();
    }

    /**
     * 头视图
     */
    private HeadView headView;

    /**
     * 添加顶部的布局
     */
    private void addTopHeader() {

        //创建头部试图
        headView = new HeadView(context);

        //添加到列表的头部里面
        adapter.addHeaderView(headView.getHeader());

    }

    /**
     * StaticDataStore.tmpData = u;
     */


    @Override
    public String getTargetUserId() {
        return targetUserId;
    }

    @Override
    public void onLoadMyDynamicsSuccess(List<NewDynamics> newDynamicses) {
        isLoadDynamics = true;
        dynamics.addAll(newDynamicses);
        data.addAll(dynamics);
        adapter.notifyItemRangeInserted(0 + adapter.getHeaderCounts(), data.size());
    }

    /**
     * 这里面没有删除操作所以这个不必理会
     *
     * @param postion
     */
    @Override
    public void onDeleteDynamicsSuccess(int postion) {
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
    public void onLoadMoreMyDynamicsSuccess(List<NewDynamics> newDynamicses) {
        if (newDynamicses.size() == 0) {
            isNoTAnyMore = true;
            return;
        }
        dynamics.addAll(newDynamicses);
        AdapterNotify.notifyAppendData(data, newDynamicses, adapter);
    }

    @Override
    public void onLoadUserInfoSuccess(UserResult userResult) {
        this.u = userResult.getUser();
        userResult.getUser().setFollow(userResult.is_followed());
        StaticDataStore.newUser.setUser_nickname(u.getUser_nickname());
        StaticDataStore.newUser.setUser_introduction(u.getUser_introduction());
        StaticDataStore.newUser.setGames(u.getGames());
        StaticDataStore.newUser.setUser_head_image(u.getUser_head_image());
        disPlayUserInfo(u);
    }

    /**
     * xianshi用户信息
     *
     * @param u
     */
    private void disPlayUserInfo(NewUser u) {

        //显示用户信息
        headView.disPlayUserInfo(u);

        //记录下是否已经关注过了
        isFollowUser = u.isFollow();

    }

    @Override
    public void tip(String content) {
        T.showShort(context, content);
    }

    @Override
    public void onFollowSuccess(Object... obs) {
        isFollowUser = true;
        headView.toggleFollowStatus(true);
    }

    @Override
    public void onUnFollowSuccess(Object... obs) {
        isFollowUser = false;
        headView.toggleFollowStatus(false);
    }

    @Override
    public void onBlockSuccess() {
    }

    @Override
    public void onUnBlockSuccess() {

    }

    /**
     * 两个返回按钮,调用结束自己
     *
     * @param view
     */
    public void finishMe(View view) {
        finish();
    }

    @Override
    public void onViewInItemClick(View v, int position) {
        int id = v.getId();

        NewDynamics dynamicsContentEntity = null;

        switch (id) {
            case R.id.iv_dynamics_content_item_user_icon: //用户头像
                //获取到点击的头像的id
                String targetId = data.get(position).getUser().getUser_id();
                context.startActivity(new Intent(context, UserDetailAct.class).putExtra(UserDetailAct.TARGET_USER_ID_FLAG, targetId));
                break;
            case R.id.rl_share: //分享

                PopupShare popopShare = new PopupShare(context);
                popopShare.getSb().setLinkUrl(Constant.DYNAMICS_SHARE_URL_PREFIX + data.get(position).get_id());
                popopShare.getSb().setContent(data.get(position).getContent());
                popopShare.show();

                break;

            case R.id.rl_collect: //收藏

                dynamicsContentEntity = data.get(position);
                ImageView iv_collect = (ImageView) v.findViewById(R.id.iv_dynamics_content_item_foot_collect);

                //表示现在是没有收藏的状态
                if (dynamicsContentEntity.is_collected()) {
                    dynamicsPresenter.uncollect_article(iv_collect, dynamicsContentEntity, dynamicsContentEntity.get_id());
                } else {
                    dynamicsPresenter.collect_article(iv_collect, dynamicsContentEntity, dynamicsContentEntity.get_id());
                }

                break;

            case R.id.rl_zan: //点赞

                dynamicsContentEntity = data.get(position);
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
                eventEntity.dynamicsId = data.get(position).get_id();
                eventEntity.userId = data.get(position).getUser().getUser_id();
                ItemMenu itemMenu = new ItemMenu(context, eventEntity);
                itemMenu.show();

                break;

            case R.id.cnv_dynamics_content_item_images: //点击的是九宫格控件

                CommonNineView cdv = (CommonNineView) v;

                int clickImageIndex = cdv.getClickImageIndex();

                if (clickImageIndex == -1) { //表示没有一个图片是被点击的
                    goToDynamicsDetail(position);
                    return;
                }

                //拿到对应的item实体对象
                dynamicsContentEntity = data.get(position);

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
                i.putExtra(VideoFullScreenPlayAct.VIDEO_FALG, data.get(position).getVideo_url());
                context.startActivity(i);

                break;
        }
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

    /**
     * 去动态详情的界面
     *
     * @param position
     */
    protected void goToDynamicsDetail(int position) {

        //跳转到动态详情去
        Intent i = new Intent(context, DynamicsDetailAct.class);

        //携带被点击的item的动态的id过去
        i.putExtra(DynamicsDetailAct.DYNAMICS_ID_FLAG, data.get(position).get_id());

        //跳转到详情界面
        context.startActivity(i);
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
