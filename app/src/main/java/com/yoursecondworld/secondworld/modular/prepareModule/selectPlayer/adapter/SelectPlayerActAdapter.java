package com.yoursecondworld.secondworld.modular.prepareModule.selectPlayer.adapter;

import android.content.Context;
import android.net.Uri;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yoursecondworld.secondworld.R;
import com.yoursecondworld.secondworld.common.Constant;
import com.yoursecondworld.secondworld.modular.systemInfo.entity.NewUser;

import java.util.List;

import xiaojinzi.base.android.adapter.recyclerView.CommonRecyclerViewAdapter;
import xiaojinzi.base.android.adapter.recyclerView.CommonRecyclerViewHolder;

/**
 * Created by cxj on 2016/7/7.
 */
public class SelectPlayerActAdapter extends CommonRecyclerViewAdapter<NewUser> {

    private Uri demoUrl = Uri.parse("http://e.hiphotos.baidu.com/image/h%3D360/sign=9c4d0179af4bd1131bcdb1346aaea488/7af" +
            "40ad162d9f2d303f4c1e5abec8a136227ccd7.jpg");

    private List<Boolean> playerIsSelected;

    //显示用户名称的文本
    private int nameSelectedColor;
    private int nameUnSelectedColor;

    //显示描述的文本
//    private int descSelectedColor;
//    private int descUnSelectedColor;

    /**
     * 构造函数
     *
     * @param context 上下文
     * @param data    显示的数据
     */
    public SelectPlayerActAdapter(Context context, List<NewUser> data, List<Boolean> playerIsSelected) {
        super(context, data);
        this.playerIsSelected = playerIsSelected;
        nameSelectedColor = context.getResources().getColor(R.color.gray_three);
        nameUnSelectedColor = context.getResources().getColor(R.color.common_app_color);
//        descSelectedColor = context.getResources().getColor(R.color.gray_two);
//        descUnSelectedColor = context.getResources().getColor(R.color.common_red);
    }

    @Override
    public void convert(CommonRecyclerViewHolder h, NewUser entity, int position) {


        //加载玩家的头像
        SimpleDraweeView simpleDraweeView = h.getView(R.id.iv_act_select_player_item_icon);

        simpleDraweeView.setImageURI(Uri.parse(entity.getUser_head_image() + Constant.HEADER_SMALL_IMAGE));

        //获取玩家的名字的文本控件
        TextView tv_name = h.getView(R.id.tv_act_select_player_item_name);
        tv_name.setText(entity.getUser_nickname());

        TextView tv_isfollow_tip = h.getView(R.id.tv_isfollow_tip);

        //获取玩家的描述的文本控件
        TextView tv_desc = h.getView(R.id.tv_act_select_player_item_desc);
        tv_desc.setText(entity.getUser_introduction());
        tv_desc.setText("皇室战争骨灰级玩家皇室战争骨灰级玩家皇室战争骨灰级玩家");
        if (playerIsSelected.get(position)) { //如果是已经关注
            tv_name.setTextColor(nameSelectedColor);
//            tv_desc.setTextColor(descSelectedColor);
            tv_isfollow_tip.setBackgroundResource(R.drawable.select_player_item_attentioned_bg);
            tv_isfollow_tip.setText("已关注");
        } else {
            tv_name.setTextColor(nameUnSelectedColor);
//            tv_desc.setTextColor(descUnSelectedColor);
            tv_isfollow_tip.setBackgroundResource(R.drawable.select_player_item_attention_bg);
            tv_isfollow_tip.setText("+关注");
        }

    }

    @Override
    public int getLayoutViewId(int viewType) {
        return R.layout.act_select_player_item;
    }
}
