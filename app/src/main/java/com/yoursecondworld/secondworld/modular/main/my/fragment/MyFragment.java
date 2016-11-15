package com.yoursecondworld.secondworld.modular.main.my.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yoursecondworld.secondworld.R;
import com.yoursecondworld.secondworld.common.Constant;
import com.yoursecondworld.secondworld.common.StaticDataStore;
import com.yoursecondworld.secondworld.common.imagePreview.ImagePreviewAct;
import com.yoursecondworld.secondworld.modular.allRelease.view.AllReleaseAct;
import com.yoursecondworld.secondworld.modular.collection.view.CollectionAct;
import com.yoursecondworld.secondworld.modular.drafts.view.DraftsAct;
import com.yoursecondworld.secondworld.modular.messageNotify.view.MessageAct;
import com.yoursecondworld.secondworld.modular.myAttention.view.MyAttentionAct;
import com.yoursecondworld.secondworld.modular.myFans.view.MyFansAct;
import com.yoursecondworld.secondworld.modular.personInfo.view.EditPersonInfoAct;
import com.yoursecondworld.secondworld.modular.setting.ui.SettingAct;
import com.yoursecondworld.secondworld.modular.systemInfo.entity.NewUser;
import com.yoursecondworld.secondworld.modular.systemInfo.entity.UserResult;
import com.yoursecondworld.secondworld.modular.userDetail.presenter.UserDetailPresenter;
import com.yoursecondworld.secondworld.modular.userDetail.view.IUserDetailView;

import java.util.ArrayList;

import xiaojinzi.activity.fragment.BaseFragment;
import xiaojinzi.annotation.Injection;
import xiaojinzi.base.android.activity.ActivityUtil;
import xiaojinzi.base.android.os.T;

/**
 * Created by cxj on 2016/7/13.
 * 主界面的我的界面
 */
public class MyFragment extends BaseFragment implements IUserDetailView {

    @Injection(value = R.id.rl_frag_my_part_one, click = "clickView")
    private RelativeLayout rl_partOne = null;

    @Injection(R.id.tv_title)
    private TextView tv_title;

    //用户头像
    @Injection(R.id.iv_frag_my_icon)
    private SimpleDraweeView icon = null;

    //用户描述
    @Injection(R.id.iv_frag_my_desc)
    private TextView tv_desc = null;

    @Injection(value = R.id.tv_follow_number, click = "clickView")
    private TextView tv_follow_number = null;

    @Injection(value = R.id.tv_follow_number_tip, click = "clickView")
    private TextView tv_follow_number_tip = null;

    @Injection(value = R.id.tv_fans_number, click = "clickView")
    private TextView tv_fans_number = null;

    @Injection(value = R.id.tv_fans_number_tip, click = "clickView")
    private TextView tv_fans_number_tip = null;

    //动态数量的容器
    @Injection(value = R.id.rl_dynamics_number_container, click = "clickView")
    private RelativeLayout rl_dynamicsNumContainer = null;

    //动态数量
    @Injection(R.id.tv_frag_my_part_one_dynamics_number)
    private TextView tv_dynamicsNumber;

    //粉丝数量的容器
    @Injection(value = R.id.rl_collect_number_container, click = "clickView")
    private RelativeLayout rl_collect_number_container = null;

    //草稿箱的数量
    @Injection(value = R.id.rl_draft_number_container, click = "clickView")
    private RelativeLayout rl_draft_number_container = null;

    //粉丝的数量
    @Injection(R.id.tv_fans_number)
    private TextView tv_fansNumber;

    //文章收藏的数量
    @Injection(R.id.tv_collect_number)
    private TextView tv_collect_number;

    @Injection(value = R.id.rl_frag_my_part_four_setting, click = "clickView")
    private RelativeLayout rl_setting = null;

    @Injection(value = R.id.rl_frag_my_part_five_message, click = "clickView")
    private RelativeLayout rl_message = null;

    private UserDetailPresenter userDetailPresenter = new UserDetailPresenter(this);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.frag_my_for_main;
    }

    @Override
    protected void initView() {
        super.initView();
    }

    private NewUser newUser;

    @Override
    protected void initData() {
        super.initData();

        if (newUser == null) {
            userDetailPresenter.getUserInfo();
        } else {
            //初始化用户的基本信息
            initBaseUserInfo();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //获取用户信息
        userDetailPresenter.getUserInfo();
    }

    /**
     * 初始化用户的基本信息
     */
    private void initBaseUserInfo() {
        //拿到登陆成功的User信息
        icon.setImageURI(Uri.parse(newUser.getUser_head_image() + Constant.HEADER_SMALL_IMAGE));
        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, ImagePreviewAct.class);
                Bundle bundle = new Bundle();
                ArrayList<String> images = new ArrayList<String>();
                images.add(newUser.getUser_head_image());
                bundle.putStringArrayList(ImagePreviewAct.IMAGES_FLAG, images);
                i.putExtras(bundle);
                context.startActivity(i);
            }
        });
        tv_title.setText(newUser.getUser_nickname());
        String description = newUser.getUser_introduction();
        if (TextUtils.isEmpty(description)) {
            tv_desc.setText("他很懒哦,没有留下任何足迹");
        } else {
            tv_desc.setText(description);
        }

        tv_dynamicsNumber.setText("" + newUser.getUser_article_number());
        tv_follow_number.setText("" + newUser.getUser_follow_number());
        tv_fansNumber.setText("" + newUser.getUser_fans_number());
        tv_collect_number.setText("" + newUser.getUser_collected_article_number());

    }

    /**
     * 点击事件的集中处理
     *
     * @param v
     */
    public void clickView(View v) {
        int id = v.getId();
        switch (id) {

            case R.id.rl_frag_my_part_one: //如果点击是头部
                //跳转到编辑的界面
                ActivityUtil.startActivity(context, EditPersonInfoAct.class);
                break;
            case R.id.rl_dynamics_number_container: //如果点击的是动态的数量

                //跳转到所有发布的界面
                ActivityUtil.startActivity(context, AllReleaseAct.class);
                break;
            case R.id.tv_follow_number: //如果点击的是关注的数量
            case R.id.tv_follow_number_tip: //如果点击的是关注的数量
                ActivityUtil.startActivity(context, MyAttentionAct.class);
                break;
            case R.id.tv_fans_number:
            case R.id.tv_fans_number_tip: //如果是粉丝数量
                ActivityUtil.startActivity(context, MyFansAct.class);
                break;
            case R.id.rl_collect_number_container: //如果点击的是收藏的数量
                ActivityUtil.startActivity(context, CollectionAct.class);
                break;
            case R.id.rl_frag_my_part_four_setting: //如果点击的是设置
                ActivityUtil.startActivity(context, SettingAct.class);
                break;
            case R.id.rl_frag_my_part_five_message: //如果点击的是消息

                ActivityUtil.startActivity(context, MessageAct.class);

                break;
            case R.id.rl_draft_number_container: //如果是点击草稿箱
                ActivityUtil.startActivity(context, DraftsAct.class);
                break;

        }
    }

    @Override
    public String getTargetUserId() {
        return StaticDataStore.newUser.getUser_id();
    }

    @Override
    public void onLoadUserInfoSuccess(UserResult entity) {
        newUser = entity.getUser();
        entity.getUser().setGames(entity.getGames());
        newUser.setFollow(entity.is_followed());
        initBaseUserInfo();
    }

    @Override
    public void showDialog(String content) {
    }

    @Override
    public void closeDialog() {
    }

    @Override
    public void onSessionInvalid() {
    }
}
