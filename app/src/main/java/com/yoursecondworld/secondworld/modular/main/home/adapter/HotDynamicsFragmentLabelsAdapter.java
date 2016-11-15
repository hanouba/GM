package com.yoursecondworld.secondworld.modular.main.home.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.yoursecondworld.secondworld.R;

import java.util.List;

import xiaojinzi.base.android.adapter.recyclerView.CommonRecyclerViewAdapter;
import xiaojinzi.base.android.adapter.recyclerView.CommonRecyclerViewHolder;

/**
 * Created by cxj on 2016/7/9.
 * 热门动态上面的标签的适配器
 */
public class HotDynamicsFragmentLabelsAdapter extends CommonRecyclerViewAdapter<String> {

    /**
     * 用于记录标签是否被选中的标识
     */
    private List<Boolean> labels_selectFlags;

    private int selectedGameNameColor;
    private int unSelectedGameNameColor;

    /**
     * 构造函数
     *
     * @param context 上下文
     * @param data    显示的数据
     */
    public HotDynamicsFragmentLabelsAdapter(Context context, List<String> data, List<Boolean> labels_selectFlags) {
        super(context, data);
        this.labels_selectFlags = labels_selectFlags;
        selectedGameNameColor = context.getResources().getColor(R.color.common_app_color);
        unSelectedGameNameColor = context.getResources().getColor(R.color.gray_two);
    }

    @Override
    public void convert(CommonRecyclerViewHolder h, String entity, int position) {

        TextView tv_gameName = h.getView(R.id.tv_frag_home_dynamics_labels_item_labelname);
        tv_gameName.setText(entity);

        View underLine = h.getView(R.id.view_frag_home_dynamics_labels_item_underline);
        if (labels_selectFlags.get(position)) {
            underLine.setVisibility(View.VISIBLE);
            tv_gameName.setTextColor(selectedGameNameColor);
        } else {
            underLine.setVisibility(View.INVISIBLE);
            tv_gameName.setTextColor(unSelectedGameNameColor);
        }
    }

    @Override
    public int getLayoutViewId(int viewType) {
        return R.layout.frag_home_dynamics_for_main_labels_item;
    }
}
