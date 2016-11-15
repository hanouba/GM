package com.yoursecondworld.secondworld.modular.drafts.adapter;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yoursecondworld.secondworld.R;
import com.yoursecondworld.secondworld.common.Constant;
import com.yoursecondworld.secondworld.common.FrescoImageResizeUtil;
import com.yoursecondworld.secondworld.modular.dynamics.entity.DynamicsContentEntity;
import com.yoursecondworld.secondworld.modular.dynamics.entity.NewDynamics;
import com.yoursecondworld.secondworld.modular.postDynamics.widget.ShowImagesViewForPostDynamics;

import java.io.File;
import java.util.List;

import xiaojinzi.autolayout.utils.AutoUtils;
import xiaojinzi.base.android.adapter.recyclerView.CommonRecyclerViewAdapter;
import xiaojinzi.base.android.adapter.recyclerView.CommonRecyclerViewHolder;

/**
 * Created by cxj on 2016/8/24.
 * 草稿箱的适配器
 */
public class DraftsActAdapter extends CommonRecyclerViewAdapter<NewDynamics> {


    private int hSpace;
    private int vSpace;

    /**
     * 构造函数
     *
     * @param context 上下文
     * @param data    显示的数据
     */
    public DraftsActAdapter(Context context, List<NewDynamics> data) {
        super(context, data);
        hSpace = AutoUtils.
                getPercentWidthSize(context.getResources().getDimensionPixelSize(R.dimen.dynamics_recyclerview_item_image_horizontal_space_space));
        vSpace = AutoUtils.
                getPercentHeightSize(context.getResources().getDimensionPixelSize(R.dimen.dynamics_recyclerview_item_image_vertical_space_space));
    }


    @Override
    public void convert(CommonRecyclerViewHolder h, NewDynamics entity, int position) {
        //设置内容
        h.setText(R.id.tv_content, entity.getContent());
        h.setText(R.id.tv_time, entity.getTime());

        //拿到图片展示的控件
        ShowImagesViewForPostDynamics images = h.getView(R.id.cdv_images);
        images.removeAllViewsInLayout();
        images.setHorizontalIntervalDistance(hSpace);
        images.setVerticalIntervalDistance(vSpace);

        List<String> pictureList = entity.getPicture_list();

        for (int i = 0; i < pictureList.size(); i++) {
            //拿到一个图片的文件对象
            File f = new File(pictureList.get(i));
            if (f.exists() && f.isFile()) { //文件如果存在并且是一个文件
                View itemView = View.inflate(context, R.layout.simple_drawee_view, null);
                SimpleDraweeView sdv = (SimpleDraweeView) itemView.findViewById(R.id.sdv);
                Uri uri = Uri.fromFile(f);
                FrescoImageResizeUtil.setResizeControl(sdv, uri);
                images.addClildView(itemView);
            }
        }
    }

    @Override
    public int getLayoutViewId(int viewType) {
        return R.layout.act_drafts_item;
    }


}
