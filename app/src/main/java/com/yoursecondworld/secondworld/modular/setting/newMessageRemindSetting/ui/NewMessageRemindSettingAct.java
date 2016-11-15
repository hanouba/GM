package com.yoursecondworld.secondworld.modular.setting.newMessageRemindSetting.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yoursecondworld.secondworld.R;
import com.yoursecondworld.secondworld.common.BaseAct;
import com.yoursecondworld.secondworld.modular.setting.functionRemindSetting.ui.FunctionRemindSettingAct;
import com.yoursecondworld.secondworld.modular.setting.notDisturbSetting.ui.NotDisturbSettingAct;

import xiaojinzi.annotation.Injection;
import xiaojinzi.base.android.activity.ActivityUtil;

/**
 * 关于设置新消息提醒界面
 * 没有这个界面了
 */
@Injection(R.layout.act_setting_new_message_remind)
public class NewMessageRemindSettingAct extends BaseAct {


    @Injection(R.id.rl_act_titlebar)
    private RelativeLayout rl_titlebar = null;

    @Injection(value = R.id.iv_back, click = "clickView")
    private ImageView iv_back = null;

    @Injection(R.id.tv_title_name)
    private TextView tv_titleName = null;

    //========================================================================================

    @Injection(value = R.id.rl_act_new_message_remind_function_remind, click = "clickView")
    private RelativeLayout rl_functionRemind = null;

    @Injection(value = R.id.rl_act_new_message_remind_not_disturb, click = "clickView")
    private RelativeLayout rl_notDisturb = null;

    @Override
    public void initView() {
        super.initView();
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) rl_titlebar.getLayoutParams();
        lp.topMargin = statusHeight;

        tv_titleName.setText("新消息通知");

    }


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
            case R.id.rl_act_new_message_remind_function_remind:
                ActivityUtil.startActivity(context, FunctionRemindSettingAct.class);
                break;
            case R.id.rl_act_new_message_remind_not_disturb:
                ActivityUtil.startActivity(context, NotDisturbSettingAct.class);
                break;
        }

    }

    @Override
    public boolean isRegisterEvent() {
        return false;
    }


}
