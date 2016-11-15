package com.yoursecondworld.secondworld.modular.main.popupWindow.inputTime;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yoursecondworld.secondworld.AppConfig;
import com.yoursecondworld.secondworld.R;
import com.yoursecondworld.secondworld.common.CallbackAdapter;
import com.yoursecondworld.secondworld.common.Constant;
import com.yoursecondworld.secondworld.common.JsonRequestParameter;
import com.yoursecondworld.secondworld.common.StaticDataStore;
import com.yoursecondworld.secondworld.common.baseResult.BaseEntity;
import com.yoursecondworld.secondworld.common.view.WheelView;
import com.yoursecondworld.secondworld.modular.mvp.view.IBaseView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import xiaojinzi.base.android.os.KeyBoardUtils;
import xiaojinzi.base.android.os.T;

/**
 * Created by cxj on 2016/7/13.
 * 弹出选择游戏和分享的方法
 */
public class InputTimePopupWindow extends PopupWindow implements View.OnClickListener, IBaseView {

    /**
     * 弹出的试图
     */
    private View contentView = null;

    /**
     * 上下文
     */
    private Context context = null;

    private RelativeLayout rl_game_name;

    /**
     * 年份
     */
    private int year;

    /**
     * 月份
     */
    private int month;

    /**
     * 当月第几天
     */
    private int day;

    /**
     * 构造函数
     *
     * @param context 上下文
     */
    public InputTimePopupWindow(final Context context) {
        super(context);

        //记录上下文
        this.context = context;

        //创建视图
        contentView = View.inflate(context, R.layout.popup_input_master_time, null);

        //初始化控件
        initView(contentView);

        //初始化popupWindow本身
        initPopupWindow();

    }

    private RelativeLayout rl_date;
    private RelativeLayout rl_pick_date;
    private Button bt_confirm;
    private TextView tv_date;
    private TextView tv_game_name_content;
    private DatePicker dp_date;
    private TextView tv_time;
    private TextView tv_complete;
    private TextView tv_cancel;

    /**
     * 初始化控件
     *
     * @param contentView
     */
    private void initView(View contentView) {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);

        rl_game_name = (RelativeLayout) contentView.findViewById(R.id.rl_game_name);
        rl_date = (RelativeLayout) contentView.findViewById(R.id.rl_date);
        rl_pick_date = (RelativeLayout) contentView.findViewById(R.id.rl_pick_date);
        bt_confirm = (Button) contentView.findViewById(R.id.bt_confirm);
        tv_date = (TextView) contentView.findViewById(R.id.tv_date);
        tv_game_name_content = (TextView) contentView.findViewById(R.id.tv_game_name_content);
        dp_date = (DatePicker) contentView.findViewById(R.id.dp_date);
        tv_time = (TextView) contentView.findViewById(R.id.tv_time);
        tv_complete = (TextView) contentView.findViewById(R.id.tv_complete);
        tv_cancel = (TextView) contentView.findViewById(R.id.tv_cancel);
        rl_game_name.setOnClickListener(this);
        rl_date.setOnClickListener(this);
        bt_confirm.setOnClickListener(this);
        tv_complete.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
        tv_time.setOnClickListener(this);

        tv_date.setText(month + "-" + day);
        dp_date.setMaxDate(calendar.getTimeInMillis());

    }

    /**
     * 初始化popupWindow本身
     */
    private void initPopupWindow() {
        setContentView(contentView);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setFocusable(true);
        setAnimationStyle(R.style.anim_popup_dir);
        Drawable drawable = new ColorDrawable(Color.parseColor("#000000"));
        drawable.setAlpha(180);
        setBackgroundDrawable(drawable);
    }

    private View.OnClickListener listenerGameNameClick;

    public void setListenerGameNameClick(View.OnClickListener listenerGameNameClick) {
        this.listenerGameNameClick = listenerGameNameClick;
        rl_game_name.setOnClickListener(listenerGameNameClick);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {

            case R.id.rl_date://点击了日期
                //显示时间选择器
                rl_pick_date.setVisibility(View.VISIBLE);
                break;
            case R.id.bt_confirm://点击了日期确定的按钮

                month = dp_date.getMonth() + 1;
                day = dp_date.getDayOfMonth();
                //设置时间
                tv_date.setText(month + "-" + day);
                //隐藏时间选择器
                rl_pick_date.setVisibility(View.INVISIBLE);
                break;

            case R.id.tv_complete:

                addMoneyRecoder();

                break;

            case R.id.tv_cancel:

                dismiss();

                break;

            case R.id.tv_time:

                SelectTime selectTime = new SelectTime(context);
                selectTime.setOnChangeListener(new SelectTime.OnChangeListener() {
                    @Override
                    public void onChange(int hour, int min) {
                        mMin = hour * 60 + min;
                        tv_time.setText(hour + "小时" + min + "分");
                    }
                });
                selectTime.show();

                break;
        }
    }

    //分钟
    private int mMin;

    /**
     * 添加金钱的记录
     */
    private void addMoneyRecoder() {

        float value = mMin;

        if (value <= 0) {
            tip("时间不能为空或者为0");
            return;
        }

        if (value >= 24 * 60) {
            tip("时间不能够超过二十四小时哦");
            return;
        }

        String gameName = tv_game_name_content.getText().toString().trim();

        if (TextUtils.isEmpty(gameName)) {
            tip("请先选择一个游戏标签");
            return;
        }

        RequestBody body = JsonRequestParameter.build(
                new String[]{Constant.RESULT_SESSION_ID, Constant.RESULT_OBJECT_ID, "game_name", "year", "month", "day", "cost_time"},
                new Object[]{StaticDataStore.session_id, StaticDataStore.newUser.getUser_id(),
                        gameName, year, month, day, value}
        );

        Call<BaseEntity> call = AppConfig.netWorkService.master_cost_time(body);

        call.enqueue(new CallbackAdapter<BaseEntity>(this) {
            @Override
            public void onResponse(BaseEntity entity) {
                tip("添加成功");
                dismiss();
            }
        });

    }

    public void setGameName(String gameName) {
        tv_game_name_content.setText(gameName);
    }

    @Override
    public void showDialog(String content) {

    }

    @Override
    public void closeDialog() {

    }

    @Override
    public void tip(String content) {
        T.showShort(context, content);
    }

    @Override
    public void onSessionInvalid() {
    }
}
