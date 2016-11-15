package com.yoursecondworld.secondworld.modular.prepareModule.selectGame.adapter;

import android.content.Context;
import android.net.Uri;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yoursecondworld.secondworld.R;

import java.util.List;

import xiaojinzi.base.android.adapter.listView.CommonAdapter;
import xiaojinzi.base.android.adapter.listView.CommonViewHolder;



/**
 * Created by cxj on 2016/7/8.
 */
public class UnSelectedGameActAdapter extends CommonAdapter<String> {
    /**
     * 构造函数
     *
     * @param context   上下文
     * @param data      显示的数据
     * @param layout_id listview使用的条目的布局文件id
     */
    public UnSelectedGameActAdapter(Context context, List<String> data, int layout_id) {
        super(context, data, layout_id);
    }

    @Override
    public void convert(CommonViewHolder h, String item, int position) {
        h.setText(R.id.tv_flow_label_game_name, item);
    }
}
