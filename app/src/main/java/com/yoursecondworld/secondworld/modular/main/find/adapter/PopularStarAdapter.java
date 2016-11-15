package com.yoursecondworld.secondworld.modular.main.find.adapter;

import android.content.Context;
import android.net.Uri;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yoursecondworld.secondworld.R;
import com.yoursecondworld.secondworld.common.Constant;

import com.yoursecondworld.secondworld.modular.systemInfo.entity.NewUser;

import java.util.List;

import xiaojinzi.base.android.adapter.recyclerView.CommonRecyclerViewAdapter;
import xiaojinzi.base.android.adapter.recyclerView.CommonRecyclerViewHolder;

/**
 * Created by cxj on 2016/7/11.
 * 人气明星的适配器
 */
public class PopularStarAdapter extends CommonRecyclerViewAdapter<NewUser> {

    /**
     * 构造函数
     *
     * @param context 上下文
     * @param data    显示的数据
     */
    public PopularStarAdapter(Context context, List<NewUser> data) {
        super(context, data);
    }

    @Override
    public void convert(CommonRecyclerViewHolder h, NewUser entity, int position) {

        SimpleDraweeView sdv_icon = h.getView(R.id.sdv_frag_find_popular_star_item_icon);
//        System.out.println("PopularStarAdapter:entity.getUser() = " + entity.getUser());
        sdv_icon.setImageURI(Uri.parse(entity.getUser_head_image() + Constant.HEADER_SMALL_IMAGE));

        String name = entity.getUser_nickname().trim();
//        if (name.length() > 4) {
//            h.setText(R.id.tv_frag_find_popular_star_item_name, name.substring(0, 4) + "...");
//        } else {
            h.setText(R.id.tv_frag_find_popular_star_item_name, name);
//        }
    }

    @Override
    public int getLayoutViewId(int viewType) {
        return R.layout.frag_find_for_main_popular_star_item;
    }

}
