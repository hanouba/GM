package com.yoursecondworld.secondworld.modular.userDetail.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.yoursecondworld.secondworld.R;
import com.yoursecondworld.secondworld.modular.dynamics.adapter.DynamicsContentRecyclerViewAdapter;
import com.yoursecondworld.secondworld.modular.dynamics.entity.NewDynamics;

import java.util.List;

import xiaojinzi.base.android.adapter.recyclerView.CommonRecyclerViewHolder;

/**
 * Created by cxj on 2016/9/17.
 * 主要就是隐藏了下拉的箭头,所以就是在原有的基础上改进一下即可
 */
public class UserDetailAdapter extends DynamicsContentRecyclerViewAdapter {

    public UserDetailAdapter(Context context, List<NewDynamics> data) {
        super(context, data);
    }

    public UserDetailAdapter(Context context, List<NewDynamics> data, RecyclerView rv) {
        super(context, data, rv);
    }


    @Override
    public void convert(CommonRecyclerViewHolder h, NewDynamics item, int position) {
        ImageView iv_arrowbottom = h.getView(R.id.iv_dynamics_content_item_arrow_bottom);
        iv_arrowbottom.setVisibility(View.GONE);
        super.convert(h, item, position);
    }
}
