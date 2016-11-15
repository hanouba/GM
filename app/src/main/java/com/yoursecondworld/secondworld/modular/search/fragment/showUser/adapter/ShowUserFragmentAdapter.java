package com.yoursecondworld.secondworld.modular.search.fragment.showUser.adapter;

import android.content.Context;
import android.net.Uri;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yoursecondworld.secondworld.R;
import com.yoursecondworld.secondworld.common.Constant;
import com.yoursecondworld.secondworld.modular.dynamics.entity.DynamicsContentEntity;
import com.yoursecondworld.secondworld.modular.systemInfo.entity.NewUser;
import com.yoursecondworld.secondworld.modular.systemInfo.entity.User;

import java.util.List;

import xiaojinzi.base.android.adapter.recyclerView.CommonRecyclerViewAdapter;
import xiaojinzi.base.android.adapter.recyclerView.CommonRecyclerViewHolder;

/**
 * Created by cxj on 2016/8/1.
 * 显示用户搜索结果的适配器
 */
public class ShowUserFragmentAdapter extends CommonRecyclerViewAdapter<NewUser> {
    /**
     * 构造函数
     *
     * @param context 上下文
     * @param data    显示的数据
     */
    public ShowUserFragmentAdapter(Context context, List<NewUser> data) {
        super(context, data);
    }

    @Override
    public void convert(CommonRecyclerViewHolder h, NewUser entity, int position) {

        SimpleDraweeView icon = h.getView(R.id.sdv);
        icon.setImageURI(Uri.parse(entity.getUser_head_image() + Constant.HEADER_SMALL_IMAGE));

        h.setText(R.id.tv_name, entity.getUser_nickname());
        h.setText(R.id.tv_desc, entity.getUser_introduction());

    }

    @Override
    public int getLayoutViewId(int viewType) {
        return R.layout.frag_show_user_search_result_item;
    }
}
