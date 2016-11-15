package com.yoursecondworld.secondworld.modular.dynamics.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yoursecondworld.secondworld.R;
import com.yoursecondworld.secondworld.common.BaseAct;
import com.yoursecondworld.secondworld.common.imagePreview.ImagePreviewAct;
import com.yoursecondworld.secondworld.common.videoFullScreenPlay.VideoFullScreenPlayAct;
import com.yoursecondworld.secondworld.modular.dynamics.adapter.DynamicsContentRecyclerViewAdapter;
import com.yoursecondworld.secondworld.modular.dynamics.entity.NewDynamics;
import com.yoursecondworld.secondworld.modular.dynamics.presenter.DynamicsPresenter;
import com.yoursecondworld.secondworld.modular.dynamicsDetail.view.DynamicsDetailAct;
import com.yoursecondworld.secondworld.modular.main.eventEntity.ItemMenuEventEntity;
import com.yoursecondworld.secondworld.modular.main.eventEntity.ShareEventEntity;
import com.yoursecondworld.secondworld.modular.userDetail.view.UserDetailAct;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import xiaojinzi.base.android.adapter.recyclerView.CommonRecyclerViewAdapter;
import xiaojinzi.view.CommonNineView;

/**
 * Created by cxj on 2016/9/2.
 * 主要抽取内容:点赞，收藏，内部控件的click点击监听,实现跳转
 */
public class BaseDynamicsActivity extends BaseAct implements IDynamicsView, CommonRecyclerViewAdapter.OnViewInItemClickListener {

    /**
     * 显示内容的适配器
     */
    protected DynamicsContentRecyclerViewAdapter adapter = null;

    /**
     * 显示的集合
     */
    protected List<NewDynamics> list_content = new ArrayList<NewDynamics>();

    /**
     * 用于点赞，收藏等操作
     */
    protected DynamicsPresenter dynamicsPresenter = new DynamicsPresenter(this);

    @Override
    public void setOnlistener() {
        super.setOnlistener();

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

                EventBus.getDefault().post(new ItemMenuEventEntity());

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
    protected void goToDynamicsDetail(int position) {
        //跳转到动态详情去
        Intent i = new Intent(context, DynamicsDetailAct.class);

        //携带被点击的item的动态的id过去
        i.putExtra(DynamicsDetailAct.DYNAMICS_ID_FLAG, list_content.get(position).get_id());

        context.startActivity(i);
    }

}
