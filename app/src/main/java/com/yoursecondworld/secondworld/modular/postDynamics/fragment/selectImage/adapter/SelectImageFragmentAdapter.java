package com.yoursecondworld.secondworld.modular.postDynamics.fragment.selectImage.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yoursecondworld.secondworld.R;
import com.yoursecondworld.secondworld.common.FrescoImageResizeUtil;
import com.yoursecondworld.secondworld.modular.postDynamics.fragment.selectImage.entity.ImageInfoEntity;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import xiaojinzi.base.android.adapter.recyclerView.CommonRecyclerViewAdapter;
import xiaojinzi.base.android.adapter.recyclerView.CommonRecyclerViewHolder;
import xiaojinzi.base.android.image.ImageUtil;
import xiaojinzi.base.android.log.L;

/**
 * Created by cxj on 2016/7/27.
 * 显示横向的图片列表的适配器
 */
public class SelectImageFragmentAdapter extends CommonRecyclerViewAdapter<ImageInfoEntity> {

    /**
     * 构造函数
     *
     * @param context     上下文
     * @param data        显示的数据
     */
    public SelectImageFragmentAdapter(Context context, final List<ImageInfoEntity> data) {
        super(context, data);
    }

    @Override
    public void convert(CommonRecyclerViewHolder h, ImageInfoEntity entity, int position) {
        SimpleDraweeView iv = h.getView(R.id.iv);
        BitmapFactory.Options options = ImageUtil.getBitMapOptions(entity.getLocalPath());
//        float percent = (options.outHeight + 0.0f) / (options.outWidth + 0.0f);
        float percent = (options.outWidth + 0.0f) / (options.outHeight + 0.0f);
        iv.setAspectRatio(percent);

//        FrescoImageResizeUtil.setResizeControl(iv, Uri.fromFile(new File(entity.getLocalPath())));

        Glide.with(context).load(entity.getLocalPath()).into(iv);

        TextView tv_position = h.getView(R.id.tv_position);

        int selectedPotion = entity.getSelectedPotion();
        if (selectedPotion != -1) {
            tv_position.setText(selectedPotion + "");
            tv_position.setBackground(context.getResources().getDrawable(R.drawable.act_post_dynamics_selected_image_flag));
        } else {
            tv_position.setText("");
            tv_position.setBackground(context.getResources().getDrawable(R.drawable.act_post_dynamics_unselected_image_flag));
        }

    }

    @Override
    public int getLayoutViewId(int viewType) {
        return R.layout.frag_select_image_for_post_dynamics_item;
    }

}
