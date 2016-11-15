package com.yoursecondworld.secondworld.modular.statusNotification.fragment.adapter;

import android.content.Context;
import android.net.Uri;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yoursecondworld.secondworld.R;
import com.yoursecondworld.secondworld.common.Constant;
import com.yoursecondworld.secondworld.common.DateFormat;
import com.yoursecondworld.secondworld.modular.dynamics.adapter.DynamicsContentRecyclerViewAdapter;
import com.yoursecondworld.secondworld.modular.statusNotification.enity.CommentsListEntity;

import java.util.Date;
import java.util.List;

import xiaojinzi.base.android.adapter.recyclerView.CommonRecyclerViewAdapter;
import xiaojinzi.base.android.adapter.recyclerView.CommonRecyclerViewHolder;

/**
 * Created by cxj on 2016/7/23.
 */
public class CommentFragmentAdapter extends CommonRecyclerViewAdapter<CommentsListEntity> {

    /**
     * 构造函数
     *
     * @param context 上下文
     * @param data    显示的数据
     */
    public CommentFragmentAdapter(Context context, List<CommentsListEntity> data) {
        super(context, data);
    }

    @Override
    public void convert(CommonRecyclerViewHolder h, CommentsListEntity entity, int position) {

        SimpleDraweeView icon = h.getView(R.id.sdv_frag_zan_for_status_notifi_item_icon);
        icon.setImageURI(Uri.parse(entity.getUser_head_image() + Constant.HEADER_SMALL_IMAGE));

        h.setText(R.id.tv_name, entity.getUser_nickname());

        h.setText(R.id.tv_frag_zan_for_status_notifi_item_tip, entity.getContent());

        h.setText(R.id.tv_frag_zan_for_status_notifi_item_text_content, "原文:" + entity.getSource_article_content());

        try {
            //解析成了日期
            Date date = DynamicsContentRecyclerViewAdapter.dateUtil.parse(entity.getTime());
            h.setText(R.id.tv_date, DateFormat.format(date.getTime()));

        } catch (Exception e) {
            h.setText(R.id.tv_date, "--:--");
        }


    }

    @Override
    public int getLayoutViewId(int viewType) {
        return R.layout.frag_zan_for_status_notifi_item;
    }
}
