package com.yoursecondworld.secondworld.modular.main.popupWindow.share;

import android.content.Context;
import android.net.Uri;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yoursecondworld.secondworld.R;

import java.util.List;

import xiaojinzi.base.android.adapter.recyclerView.CommonRecyclerViewAdapter;
import xiaojinzi.base.android.adapter.recyclerView.CommonRecyclerViewHolder;

/**
 * Created by cxj on 2016/7/20.
 * 分享的popupWindow的item适配器
 */
public class ShareAdapter extends CommonRecyclerViewAdapter<ShareEntity> {

    /**
     * 构造函数
     *
     * @param context 上下文
     * @param data    显示的数据
     */
    public ShareAdapter(Context context, List<ShareEntity> data) {
        super(context, data);
    }

    @Override
    public void convert(CommonRecyclerViewHolder h, ShareEntity entity, int position) {
        SimpleDraweeView icon = h.getView(R.id.sdv_act_main_popup_for_share_item_qq);
        Uri uri = Uri.parse("res://com.yoursecondworld.secondworld/" + entity.getImageRsd());
        icon.setImageURI(uri);

        TextView tv_name = h.getView(R.id.tv_act_main_popup_for_share_item_qq);
        tv_name.setText(entity.getName());

    }

    @Override
    public int getLayoutViewId(int viewType) {
        return R.layout.act_main_popup_for_share_item;
    }
}
