package com.yoursecondworld.secondworld.modular.setting.notDisturbSetting.ui;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.yoursecondworld.secondworld.R;
import com.yoursecondworld.secondworld.common.BaseAct;
import com.yoursecondworld.secondworld.common.Constant;
import com.yoursecondworld.secondworld.common.timePicker.TimePickerAct;

import xiaojinzi.annotation.Injection;
import xiaojinzi.base.android.store.SPUtil;

/**
 * 请勿打扰的设置界面
 */
@Injection(R.layout.act_not_disturb_setting)
public class NotDisturbSettingAct extends BaseAct {

    @Injection(R.id.rl_act_titlebar)
    private RelativeLayout rl_titlebar = null;

    @Injection(value = R.id.iv_back, click = "clickView")
    private ImageView iv_back = null;

    @Injection(R.id.tv_title_name)
    private TextView tv_titleName = null;

    //====================================================================================

    @Injection(value = R.id.switch_act_not_disturb_setting_is_open, click = "clickView")
    private Switch aSwitch = null;

    @Injection(value = R.id.tv_act_not_disturb_setting_start_time, click = "clickView")
    private TextView tv_startTime = null;

    @Injection(value = R.id.tv_act_not_disturb_setting_end_time, click = "clickView")
    private TextView tv_endTime = null;

    @Injection(value = R.id.bt_act_not_disturb_confirm, click = "clickView")
    private Button bt_confirm;

    @Override
    public void initView() {
        super.initView();
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) rl_titlebar.getLayoutParams();
        lp.topMargin = statusHeight;

        //设置标题栏的标题
        tv_titleName.setText("勿扰时段");

        //获取保存的勿扰时间
        String startTime = SPUtil.get(context, Constant.SP.mySetting.NOTDISTURBSTARTTIME_FLAG, "22:00");
        String endTime = SPUtil.get(context, Constant.SP.mySetting.NOTDISTURBENDTIME_FLAG, "08:00");

        //回显勿扰时间
        tv_startTime.setText(startTime);
        tv_endTime.setText(endTime);

        //获取用户是否开启了勿扰
        boolean isOpenNotDisturb = SPUtil.get(context, Constant.SP.mySetting.NOTDISTURBISOPEN_FLAG, false);
        aSwitch.setChecked(isOpenNotDisturb);

    }

    @Override
    public void setOnlistener() {
        super.setOnlistener();

        //监听开关按钮
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //保存用户的选择
                SPUtil.put(context, Constant.SP.mySetting.NOTDISTURBISOPEN_FLAG, isChecked);
            }
        });

    }

    /**
     * 记录当前是采集起始时间和结束时间
     */
    private int mask;

    /**
     * 点击事件的集中处理
     *
     * @param v
     */
    public void clickView(View v) {

        //获取控件的id
        int id = v.getId();

        //对id的筛选然后做处理
        switch (id) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.switch_act_not_disturb_setting_is_open:


                break;
            case R.id.tv_act_not_disturb_setting_start_time:
                mask = 0;
                startActivityForResult(new Intent(context, TimePickerAct.class), 0);
                break;
            case R.id.tv_act_not_disturb_setting_end_time:
                mask = 1;
                startActivityForResult(new Intent(context, TimePickerAct.class), 0);
                break;
            case R.id.bt_act_not_disturb_confirm:
                //结束当前activity
                finish();
                break;
        }

    }

    @Override
    public boolean isRegisterEvent() {
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == TimePickerAct.TIMEPICKERRESULT) {
            //获取返回的小时和分钟
            int hour = data.getIntExtra(TimePickerAct.HOUR_FLAG, mask == 0 ? 22 : 8);
            int minute = data.getIntExtra(TimePickerAct.MINUTE_FLAG, 0);

            //转化返回的时间为字符串
            String time = (hour < 10 ? ("0" + hour) : hour) + ":" + (minute < 10 ? ("0" + minute) : minute);

            if (mask == 0) { //mask标识了这次是采集起始时间还是结束时间
                tv_startTime.setText(time);
                SPUtil.put(context, Constant.SP.mySetting.NOTDISTURBSTARTTIME_FLAG, time);
            } else {
                tv_endTime.setText(time);
                SPUtil.put(context, Constant.SP.mySetting.NOTDISTURBENDTIME_FLAG, time);
            }

        }

    }
}
