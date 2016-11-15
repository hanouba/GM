package com.yoursecondworld.secondworld.modular.system.accountManager.ui;


import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yoursecondworld.secondworld.R;
import com.yoursecondworld.secondworld.common.BaseAct;
import com.yoursecondworld.secondworld.common.event.SessionInvalidEvent;
import com.yoursecondworld.secondworld.modular.system.accountManager.presenter.AccountManagerPresenter;

import org.greenrobot.eventbus.Subscribe;

import xiaojinzi.annotation.Injection;

/**
 * 账号管理的界面
 */
@Injection(R.layout.act_account_manager)
public class AccountManagerAct extends BaseAct implements AccountManagerView {

    /**
     * 标题栏的容器
     */
    @Injection(R.id.rl_act_titlebar_container)
    private RelativeLayout rl_titlebarContainer = null;

    /**
     * 标题栏
     */
    @Injection(R.id.rl_act_titlebar)
    private RelativeLayout rl_titlebar = null;

    /**
     * 返回按钮
     */
    @Injection(value = R.id.iv_back, click = "clickView")
    private ImageView iv_back = null;

    /**
     * 标题栏的文本
     */
    @Injection(R.id.tv_title_name)
    private TextView tv_titleName = null;

    @Injection(value = R.id.tv_menu, click = "clickView")
    private TextView tv_menu = null;

    //=============================================================================

    @Injection(R.id.et_phone)
    private EditText et_phone;

    @Injection(R.id.et_checkcode)
    private EditText et_checkcode;

    @Injection(R.id.et_password_one)
    private EditText et_passOne;

    @Injection(value = R.id.bt_getcheckcode, click = "clickView")
    private Button bt_getcheckcode;

    @Injection(value = R.id.bt_confirm_bind, click = "clickView")
    private Button bt_confirm_bind;

    /**
     * 这个标识是为了让时间倒计时
     */
    private boolean flag;

    /**
     * 剩余时间,也就是距离下次发送验证码还剩多久
     * 一次30秒
     */
    private int restTime;

    private Handler h = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (restTime <= 0) {
                flag = false;
            }
            restTime--;
            if (flag) {
                bt_getcheckcode.setEnabled(false); //让用户不能点击
                bt_getcheckcode.setText("重新发送(" + restTime + ")");
                h.sendEmptyMessageDelayed(0, 1000);
            } else {
                bt_getcheckcode.setEnabled(true); //让用户能点击
                bt_getcheckcode.setText("重新发送");
            }
        }
    };

    private AccountManagerPresenter accountManagerPresenter = new AccountManagerPresenter(this);

    @Override
    public void initView() {
        super.initView();

        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) rl_titlebar.getLayoutParams();
        lp.topMargin = statusHeight;

        tv_titleName.setText("账号管理");

        //针对白色的标题栏需要做的操作
//        boolean b = SystemUtil.FlymeSetStatusBarLightMode(getWindow(), true);
//        if (!b) {
//            b = SystemUtil.MIUISetStatusBarLightMode(getWindow(), true);
//            if (!b) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//                } else {
//                    //设置标题栏背景为黑色
//                    rl_titlebarContainer.setBackgroundColor(Color.BLACK);
//                }
//            }
//        }

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
            case R.id.bt_getcheckcode: //获取验证码的按钮

                flag = true;
                restTime = 60;
                h.sendEmptyMessage(0);

                //发送验证码
                accountManagerPresenter.send_bind_phone_number_message();

                break;

            case R.id.bt_confirm_bind: //点击了确定绑定的按钮

                String code = et_checkcode.getText().toString().trim();
                String p1 = et_passOne.getText().toString().trim();

                if (TextUtils.isEmpty(code)) {
                    tip("验证码不能为空");
                    return;
                }

                if (TextUtils.isEmpty(p1)) {
                    tip("密码不能为空");
                    return;
                }

                accountManagerPresenter.auth_bind_phone_number_message();

                break;
        }
    }


    @Override
    public boolean isRegisterEvent() {
        return true;
    }

    /**
     * 当sessionId失效了
     */
    @Subscribe
    public void onSessionIdInvalid(SessionInvalidEvent event) {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        restTime = 0;
        flag = false;
    }

    @Override
    public String getPhoneNumber() {
        return et_phone.getText().toString().trim();
    }

    @Override
    public void onSendMessageSuccess() {
    }

    @Override
    public String getPassword() {
        return et_passOne.getText().toString().trim();
    }

    @Override
    public String getCheckCode() {
        return et_checkcode.getText().toString().trim();
    }

    @Override
    public void onBindPhoneSuccess() {
        finish();
    }

}
