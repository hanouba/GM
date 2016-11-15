package com.yoursecondworld.secondworld.modular.prepareModule.improvePersonInfo.popupWindow;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.view.View;
import android.widget.TextView;

import com.yoursecondworld.secondworld.R;
import com.yoursecondworld.secondworld.modular.prepareModule.improvePersonInfo.eventEntity.SelectImageEventEntity;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by cxj on 2016/7/25.
 * 底部弹出的小菜单
 */
public class BottomMenuWindow extends BottomSheetDialog implements View.OnClickListener {

    public BottomMenuWindow(@NonNull Context context) {
        super(context, 0);
        init(context);
    }

    /**
     * 初始化
     *
     * @param context
     */
    private void init(Context context) {
        View view = View.inflate(context, R.layout.act_edit_person_info_popup_window, null);
        initView(view);
        this.setContentView(view);
    }

    /**
     * 初始化试图
     *
     * @param view
     */
    private void initView(View view) {
        TextView tv_image = (TextView) view.findViewById(R.id.tv_act_edit_person_info_popup_window_image);
        TextView tv_cancel = (TextView) view.findViewById(R.id.tv_act_edit_person_info_popup_window_cancel);
        tv_image.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv_act_edit_person_info_popup_window_image:
                EventBus.getDefault().post(new SelectImageEventEntity());
                dismiss();
                break;
            case R.id.tv_act_edit_person_info_popup_window_cancel: //如果点击的是取消
                dismiss();
                break;
        }
    }
}
