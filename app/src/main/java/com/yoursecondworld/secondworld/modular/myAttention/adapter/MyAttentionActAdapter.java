package com.yoursecondworld.secondworld.modular.myAttention.adapter;

import android.content.Context;
import android.net.Uri;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yoursecondworld.secondworld.R;
import com.yoursecondworld.secondworld.common.Constant;
import com.yoursecondworld.secondworld.modular.systemInfo.entity.NewUser;
import com.yoursecondworld.secondworld.modular.systemInfo.entity.User;

import java.util.List;

import xiaojinzi.base.android.adapter.recyclerView.CommonRecyclerViewAdapter;
import xiaojinzi.base.android.adapter.recyclerView.CommonRecyclerViewHolder;

/**
 * Created by cxj on 2016/7/15.
 * 我的关注的适配器
 */
public class MyAttentionActAdapter extends CommonRecyclerViewAdapter<NewUser> {


    /**
     * 构造函数
     *
     * @param context 上下文
     * @param data    显示的数据
     */
    public MyAttentionActAdapter(Context context, List<NewUser> data) {
        super(context, data);
    }

    @Override
    public void convert(CommonRecyclerViewHolder h, NewUser entity, int position) {
        //设置头像
        SimpleDraweeView icon = h.getView(R.id.iv_act_my_fans_and_attention_item_icon);
        icon.setImageURI(Uri.parse(entity.getUser_head_image() + Constant.HEADER_SMALL_IMAGE));

        //设置用户名
        h.setText(R.id.tv_act_my_fans_and_attention_item_name, entity.getUser_nickname());
        //设置描述
        h.setText(R.id.tv_act_my_fans_and_attention_item_desc, "");

        //如果是关注了的并且是我的粉丝
        if (entity.is_friend() && entity.isFollow()) {
            h.setImage(R.id.iv_act_my_fans_and_attention_item_follow_icon, R.mipmap.attentioned1);
        } else if (!entity.is_friend() && entity.isFollow()) {
            h.setImage(R.id.iv_act_my_fans_and_attention_item_follow_icon, R.mipmap.attentioned1);
        } else {
            h.setImage(R.id.iv_act_my_fans_and_attention_item_follow_icon, R.mipmap.attention1);
        }

    }

    @Override
    public int getLayoutViewId(int viewType) {
        return R.layout.act_my_fans_and_attention_item;
    }
}
