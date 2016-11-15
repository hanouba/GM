package com.yoursecondworld.secondworld.modular.userDetail.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yoursecondworld.secondworld.R;
import com.yoursecondworld.secondworld.common.Constant;
import com.yoursecondworld.secondworld.common.imagePreview.ImagePreviewAct;
import com.yoursecondworld.secondworld.modular.systemInfo.entity.NewUser;

import java.util.ArrayList;

/**
 * Created by cxj on 2016/9/5.
 */
public class HeadView {

    //头部的试图
    private View header;

    /**
     * 头像
     */
    private SimpleDraweeView sdv_userIcon;

    /**
     * 用户名
     */
    private TextView tv_name;

    /**
     * 用户的描述
     */
    private TextView tv_desc = null;

    private TextView tv_attention = null;
    private TextView tv_fans = null;

    private LinearLayout ll_follow;
    private ImageView iv_follow;
    private TextView tv_follow;

    private LinearLayout ll_chat;
    private ImageView iv_chat;
    private TextView tv_chat;

    private Context mContext;

    public HeadView(final Context context) {

        mContext = context;

        //创建布局
        header = View.inflate(context, R.layout.act_user_detail_top_header, null);

        //设置用户名
        tv_name = (TextView) header.findViewById(R.id.tv_name);

        //显示关注和粉丝的数量
        tv_attention = (TextView) header.findViewById(R.id.tv_attention);
        tv_fans = (TextView) header.findViewById(R.id.tv_fans);

        //描述
        tv_desc = (TextView) header.findViewById(R.id.tv_desc);

        //获取里面的头像控件
        sdv_userIcon = (SimpleDraweeView) header.findViewById(R.id.sdv_icon);

        ll_follow = (LinearLayout) header.findViewById(R.id.ll_follow);
        iv_follow = (ImageView) header.findViewById(R.id.iv_follow);
        tv_follow = (TextView) header.findViewById(R.id.tv_follow);

        ll_chat = (LinearLayout) header.findViewById(R.id.ll_chat);
        iv_chat = (ImageView) header.findViewById(R.id.iv_chat);
        tv_chat = (TextView) header.findViewById(R.id.tv_chat);

    }

    public View getHeader() {
        return header;
    }

    public void disPlayUserInfo(final NewUser u) {

        sdv_userIcon.setImageURI(Uri.parse(u.getUser_head_image() + Constant.HEADER_BIG_IMAGE));

        sdv_userIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, ImagePreviewAct.class);
                Bundle bundle = new Bundle();
                ArrayList<String> images = new ArrayList<String>();
                images.add(u.getUser_head_image());
                bundle.putStringArrayList(ImagePreviewAct.IMAGES_FLAG, images);
                i.putExtras(bundle);
                mContext.startActivity(i);
            }
        });

        //设置用户名
        tv_name.setText(u.getUser_nickname());
        //设置描述信息
        String description = u.getUser_introduction();
        description = TextUtils.isEmpty(description) ? "主人很懒,啥都没有留下~~~" : description;
        tv_desc.setText(description);

        tv_attention.setText("关注 " + u.getUser_follow_number());
        tv_fans.setText(" | 粉丝 " + u.getUser_fans_number());

        boolean follow = u.isFollow();

        toggleFollowStatus(follow);

    }

    /**
     * 转换关注的状态
     *
     * @param isFollow
     */
    public void toggleFollowStatus(boolean isFollow) {
        //如果关注了
        if (isFollow) {
            iv_follow.setImageResource(R.mipmap.followed1);
            tv_follow.setText("已关注");
        } else {
            iv_follow.setImageResource(R.mipmap.follow_user1);
            tv_follow.setText("加关注");
        }
    }

    /**
     * 设置添加关注和聊天按钮的可见
     *
     * @param isVisiable
     */
    public void setMenuVisiable(boolean isVisiable) {
        ll_follow.setVisibility(isVisiable ? View.VISIBLE : View.GONE);
        ll_chat.setVisibility(isVisiable ? View.VISIBLE : View.GONE);
    }

    /**
     * 设置关注的点击事件
     *
     * @param clickListener
     */
    public void setOnFollowListener(View.OnClickListener clickListener) {
        ll_follow.setOnClickListener(clickListener);
    }

    public void setOnChatListener(View.OnClickListener clickListener) {
        ll_chat.setOnClickListener(clickListener);
    }

}
