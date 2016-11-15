package com.yoursecondworld.secondworld.modular.search.fragment.showHot;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yoursecondworld.secondworld.R;
import com.yoursecondworld.secondworld.common.AdapterNotify;
import com.yoursecondworld.secondworld.common.Constant;
import com.yoursecondworld.secondworld.common.imagePreview.ImagePreviewAct;
import com.yoursecondworld.secondworld.common.videoFullScreenPlay.VideoFullScreenPlayAct;
import com.yoursecondworld.secondworld.modular.dynamics.adapter.DynamicsContentRecyclerViewAdapter;
import com.yoursecondworld.secondworld.modular.dynamics.entity.NewDynamics;
import com.yoursecondworld.secondworld.modular.dynamics.itemDecoration.DynamicsContentSpaceItemDecoration;
import com.yoursecondworld.secondworld.modular.dynamics.presenter.DynamicsPresenter;
import com.yoursecondworld.secondworld.modular.dynamics.view.IDynamicsView;
import com.yoursecondworld.secondworld.modular.dynamicsDetail.view.DynamicsDetailAct;
import com.yoursecondworld.secondworld.modular.main.eventEntity.ItemMenuEventEntity;
import com.yoursecondworld.secondworld.modular.main.popupWindow.itemMenu.ItemMenu;
import com.yoursecondworld.secondworld.modular.main.popupWindow.newShare.PopupShare;
import com.yoursecondworld.secondworld.modular.search.presenter.SearchPresenter;
import com.yoursecondworld.secondworld.modular.search.ui.ISearchView;
import com.yoursecondworld.secondworld.modular.systemInfo.entity.NewUser;
import com.yoursecondworld.secondworld.modular.userDetail.view.UserDetailAct;

import java.util.ArrayList;
import java.util.List;

import xiaojinzi.activity.fragment.BaseViewPagerFragment;
import xiaojinzi.annotation.Injection;
import xiaojinzi.autolayout.utils.AutoUtils;
import xiaojinzi.base.android.adapter.recyclerView.CommonRecyclerViewAdapter;
import xiaojinzi.view.CommonNineView;

/**
 * Created by cxj on 2016/8/1.
 * 显示用户的搜索结果的fragment
 */
public class ShowHotSearchResultFragment extends BaseViewPagerFragment implements ISearchView, CommonRecyclerViewAdapter.OnViewInItemClickListener, IDynamicsView {

    @Override
    public int getLayoutId() {
        return R.layout.frag_show_hot_search_result;
    }

    //===========================================================================================

    @Injection(R.id.rv)
    private RecyclerView rv = null;

    /**
     * 显示数据的适配器
     */
    private CommonRecyclerViewAdapter adapter = null;

    private SearchPresenter searchPresenter = new SearchPresenter(this);

    /**
     * 显示的数据
     */
    private List<NewDynamics> dynamicsContentEntities = new ArrayList<NewDynamics>();

    @Override
    public void initView() {
        super.initView();

        //创建适配器
        adapter = new DynamicsContentRecyclerViewAdapter(context, dynamicsContentEntities, rv);

        //创建布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(layoutManager);

        //适配不同屏幕的之间的条目间隔
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.frag_hot_dynamics_listview_item_space);
        dimensionPixelSize = AutoUtils.getPercentHeightSize(dimensionPixelSize);

        //条目之间的间隔
        DynamicsContentSpaceItemDecoration itemDecoration = new DynamicsContentSpaceItemDecoration(dimensionPixelSize);
        rv.addItemDecoration(itemDecoration);

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

    private boolean isNoTAnyMore;

    @Override
    public void setOnlistener() {
        super.setOnlistener();

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
                        searchPresenter.searchMoreDynamics();
                    }
                }
            }
        });

        //设置内容的item点击事件
        adapter.setOnRecyclerViewItemClickListener(new CommonRecyclerViewAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {

                //跳转到动态详情去
                goToDynamicsDetail(position);

            }
        });

        //监听item内部的控件点击事件
        adapter.setOnViewInItemClickListener(this,
                R.id.iv_dynamics_content_item_user_icon,    //用户头像
                R.id.iv_dynamics_content_item_arrow_bottom, //往下的箭头
                R.id.cnv_dynamics_content_item_images,      //显示图片
                R.id.iv_dynamics_content_item_foot_share,   //分享
                R.id.iv_dynamics_content_item_foot_collect, //收藏
                R.id.iv_dynamics_content_item_foot_zan,      //点赞
                R.id.rl_video                                //视频
        );//收藏

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
        i.putExtra(DynamicsDetailAct.DYNAMICS_ID_FLAG, dynamicsContentEntities.get(position).get_id());

        context.startActivity(i);
    }

    private String searchKey;

    public void startSearchDynamics(String key) {
        searchKey = key;
        searchPresenter.searchDynamics();
    }

    @Override
    public boolean isAutoSubscribeEvent() {
        return false;
    }


    @Override
    public String getSearchContent() {
        return searchKey;
    }

    @Override
    public void onSearchDynamicsSuccess(List<NewDynamics> dynamicses) {
        isNoTAnyMore = false;
        AdapterNotify.notifyFreshData(dynamicsContentEntities, dynamicses, adapter);
    }

    @Override
    public void onSearchMoreDynamicsSuccess(List<NewDynamics> newDynamicses) {
        if (newDynamicses.size() == 0) {
            isNoTAnyMore = true;
            return;
        }
        AdapterNotify.notifyAppendData(dynamicsContentEntities, newDynamicses, adapter);
    }

    @Override
    public void onSearchUsersSuccess(List<NewUser> newUsers) {
    }

    @Override
    public void onSearchMoreUsersSuccess(List<NewUser> newUsers) {
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

    /**
     * 点赞收藏的主持人
     */
    private DynamicsPresenter dynamicsPresenter = new DynamicsPresenter(this);

    public void popupShareWindow(String dynamicsId, String content) {
        PopupShare popopShare = new PopupShare(context);
        popopShare.show();
        popopShare.getSb().setLinkUrl(Constant.DYNAMICS_SHARE_URL_PREFIX + dynamicsId);
        popopShare.getSb().setContent(content);
    }

    @Override
    public void onViewInItemClick(View v, int position) {
        int id = v.getId();

        NewDynamics dynamicsContentEntity = null;

        switch (id) {
            case R.id.iv_dynamics_content_item_user_icon: //用户头像
                //获取到点击的头像的id
                String targetId = dynamicsContentEntities.get(position).getUser().getUser_id();
                context.startActivity(new Intent(context, UserDetailAct.class).putExtra(UserDetailAct.TARGET_USER_ID_FLAG, targetId));
                break;
            case R.id.iv_dynamics_content_item_foot_share: //分享

                popupShareWindow(dynamicsContentEntities.get(position).get_id(), dynamicsContentEntities.get(position).getContent());

                break;

            case R.id.iv_dynamics_content_item_foot_collect: //收藏

                dynamicsContentEntity = dynamicsContentEntities.get(position);
                ImageView iv_collect = (ImageView) v;

                //表示现在是没有收藏的状态
                if (dynamicsContentEntity.is_collected()) {
                    dynamicsPresenter.uncollect_article(iv_collect, dynamicsContentEntity, dynamicsContentEntity.get_id());
                } else {
                    dynamicsPresenter.collect_article(iv_collect, dynamicsContentEntity, dynamicsContentEntity.get_id());
                }

                break;

            case R.id.iv_dynamics_content_item_foot_zan: //点赞

                dynamicsContentEntity = dynamicsContentEntities.get(position);
                ImageView iv_zan = (ImageView) v;
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
                eventEntity.dynamicsId = dynamicsContentEntities.get(position).get_id();
                eventEntity.userId = dynamicsContentEntities.get(position).getUser().getUser_id();
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
                dynamicsContentEntity = dynamicsContentEntities.get(position);

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
                i.putExtra(VideoFullScreenPlayAct.VIDEO_FALG, dynamicsContentEntities.get(position).getVideo_url());
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


}
