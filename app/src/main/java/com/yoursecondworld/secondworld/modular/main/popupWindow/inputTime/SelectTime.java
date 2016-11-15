package com.yoursecondworld.secondworld.modular.main.popupWindow.inputTime;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.view.View;
import android.widget.TextView;

import com.yoursecondworld.secondworld.R;
import com.yoursecondworld.secondworld.common.view.WheelView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cxj on 2016/11/10.
 */
public class SelectTime extends BottomSheetDialog {

    /**
     * 上下文
     */
    private Context context;

    /**
     * 弹出的视图
     */
    private View contentView;

    public SelectTime(@NonNull Context context) {
        super(context);

        this.context = context;
        contentView = View.inflate(context, R.layout.popup_input_master_time_select_time, null);

        //设置不能取消
        setCancelable(false);

        initView(contentView);

        setContentView(contentView);

        setOnListener();

    }


    List<String> hours = new ArrayList<String>();

    List<String> mins = new ArrayList<String>();

    private int hour;
    private int min;

    private void setOnListener() {
        wv1.setOnWheelViewListener(new WheelView.OnWheelViewListener(){
            @Override
            public void onSelected(int selectedIndex, String item) {
                super.onSelected(selectedIndex, item);
                hour = selectedIndex - 1;
            }
        });
        wv2.setOnWheelViewListener(new WheelView.OnWheelViewListener(){
            @Override
            public void onSelected(int selectedIndex, String item) {
                super.onSelected(selectedIndex, item);
                min = (selectedIndex - 1) * 15;

            }
        });

        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onChangeListener != null) {
                    onChangeListener.onChange(hour,min);
                }
                dismiss();
            }
        });

    }

    public interface OnChangeListener{
        void onChange(int hour, int min);
    }

    private OnChangeListener onChangeListener;

    public void setOnChangeListener(OnChangeListener onChangeListener) {
        this.onChangeListener = onChangeListener;
    }

    private WheelView wv1;
    private WheelView wv2;

    private TextView tv_confirm;

    private void initView(View contentView) {

        wv1 = (WheelView) contentView.findViewById(R.id.wv1);
        wv2 = (WheelView) contentView.findViewById(R.id.wv2);
        tv_confirm = (TextView) contentView.findViewById(R.id.tv_confirm);

        for (int i = 0; i < 24; i++) {
            hours.add(i + "小时");
        }
        wv1.setItems(hours);

        mins.add("0分钟");
        mins.add("15分钟");
        mins.add("30分钟");
        mins.add("45分钟");

        wv2.setItems(mins);
    }


}
