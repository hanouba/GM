package com.yoursecondworld.secondworld.modular.dynamicsDetail.adapter;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yoursecondworld.secondworld.R;
import com.yoursecondworld.secondworld.common.Constant;
import com.yoursecondworld.secondworld.common.DateFormat;
import com.yoursecondworld.secondworld.common.EmojiReplaceUtil;
import com.yoursecondworld.secondworld.modular.dynamics.adapter.DynamicsContentRecyclerViewAdapter;
import com.yoursecondworld.secondworld.modular.dynamicsDetail.entity.DynamicsComment;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import xiaojinzi.base.android.adapter.recyclerView.CommonRecyclerViewAdapter;
import xiaojinzi.base.android.adapter.recyclerView.CommonRecyclerViewHolder;

/**
 * Created by cxj on 2016/7/13.
 * 用作显示评论用,头部是一个动态的条目详情
 */
public class DynamicsDetailActAdapter extends CommonRecyclerViewAdapter<DynamicsComment> {

    /**
     * 构造函数
     *
     * @param context 上下文
     * @param data    显示的数据
     */
    public DynamicsDetailActAdapter(Context context, List<DynamicsComment> data) {
        super(context, data);
    }

    @Override
    public void convert(CommonRecyclerViewHolder h, DynamicsComment entity, int position) {

        //评论者的头像
        SimpleDraweeView icon = h.getView(R.id.sdv_act_dynamics_detail_comment_item_user_icon);
        icon.setImageURI(Uri.parse(entity.getUser().getUser_head_image() + Constant.HEADER_SMALL_IMAGE));

        //评论者的名字
        h.setText(R.id.tv_act_dynamics_detail_comment_item_user_name, entity.getUser().getUser_nickname());

        //设置是否点赞了
        if (entity.is_liked()) {
            h.setImage(R.id.iv_zan,R.mipmap.zaned1);
        }else{
            h.setImage(R.id.iv_zan,R.mipmap.zan1);
        }

        //设置点赞数量
        h.setText(R.id.tv_zan_number, "" + entity.getLiked_number());

        String content = entity.getContent();

        //设置评论内容
        TextView tv_content = h.getView(R.id.tv_act_dynamics_detail_comment_item_content);
        tv_content.setText(EmojiReplaceUtil.converse(context, content));

        TextView tv_time = h.getView(R.id.tv_act_dynamics_detail_comment_item_time);
        //拿到返回的时间
        String time = entity.getTime();
        try {
            //解析出日期时间
            Date date = DynamicsContentRecyclerViewAdapter.dateUtil.parse(time);
            //设置上去,按照一定的格式
            tv_time.setText(DateFormat.format(date.getTime()));
        } catch (ParseException e) {
            tv_time.setText("--:--");
        }

//        if (entity.getTargetUser() != null) {
//            content = "回复@" + entity.getTargetUser().getName() + ":" + content;
//        }


        //设置评论时间
//        String time = DateFormat.format(entity.getCommentTime());
//        h.setText(R.id.tv_act_dynamics_detail_comment_item_time, time);

        ImageView iv_sex = h.getView(R.id.iv_sex);
        String user_sex = entity.getUser().getUser_sex();
        if ("male".equals(user_sex)) {
            iv_sex.setImageResource(R.mipmap.sex_man_one);
        }else{
            iv_sex.setImageResource(R.mipmap.sex_women_one);
        }
    }

    @Override
    public int getLayoutViewId(int viewType) {
        return R.layout.act_dynamics_detail_comment_item;
    }
}
