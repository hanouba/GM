package com.yoursecondworld.secondworld.modular.allRelease.popupWindow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

import android.support.design.widget.BottomSheetDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;


import com.yoursecondworld.secondworld.R;


/**
 * Created by cxj on 2016/9/2.
 */
public class PopupDeleteWindow extends BottomSheetDialog {

    /**
     * 上下文
     */
    private Context context;

    /**
     * 弹出的视图
     */
    private View contentView;

    private View.OnClickListener deleteClickListener;

    /**
     * 创建弹出的界面
     *
     * @param context
     */
    public PopupDeleteWindow(@NonNull Context context, View.OnClickListener deleteClickListener) {
        super(context);
        this.context = context;
        this.deleteClickListener = deleteClickListener;
        contentView = View.inflate(context, R.layout.act_all_release_popup_for_delete, null);
        setContentView(contentView);
        setOnListener();
    }

    /**
     * 初始化控件
     *
     * @param contentView
     */
    private void initView(View contentView) {
        setContentView(contentView);
    }

    /**
     * 设置条目的监听
     */
    private void setOnListener() {
        TextView tv_cancel = (TextView) contentView.findViewById(R.id.tv_cancel);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        TextView tv_delete = (TextView) contentView.findViewById(R.id.tv_delete);
        tv_delete.setOnClickListener(deleteClickListener);
    }

}
