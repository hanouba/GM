package com.yoursecondworld.secondworld.modular.setting.ui;


import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.yoursecondworld.secondworld.MyApp;
import com.yoursecondworld.secondworld.R;
import com.yoursecondworld.secondworld.common.BaseAct;
import com.yoursecondworld.secondworld.common.Constant;
import com.yoursecondworld.secondworld.common.event.SessionInvalidEvent;
import com.yoursecondworld.secondworld.modular.blackList.view.BlacklistAct;
import com.yoursecondworld.secondworld.modular.commonFunction.feedback.presenter.FeedbackPresenter;
import com.yoursecondworld.secondworld.modular.commonFunction.feedback.view.IFeedbackView;
import com.yoursecondworld.secondworld.modular.system.accountBind.entity.AccountBindInfoResult;
import com.yoursecondworld.secondworld.modular.system.accountBind.presenter.AccountBindPresenter;
import com.yoursecondworld.secondworld.modular.system.accountBind.ui.AccountBindAct;
import com.yoursecondworld.secondworld.modular.system.accountBind.ui.IAccountBindView;
import com.yoursecondworld.secondworld.modular.system.accountManager.ui.AccountManagerAct;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import cn.jpush.android.api.JPushInterface;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import io.rong.imkit.RongIM;
import xiaojinzi.annotation.Injection;
import xiaojinzi.base.android.activity.ActivityUtil;
import xiaojinzi.base.android.os.T;
import xiaojinzi.base.android.store.SPUtil;


/**
 * 设置的界面
 */
@Injection(R.layout.act_setting)
public class SettingAct extends BaseAct implements IFeedbackView, IAccountBindView {

    @Injection(R.id.rl_act_titlebar)
    private RelativeLayout rl_titlebar = null;

    @Injection(value = R.id.iv_back, click = "clickView")
    private ImageView iv_back = null;

    @Injection(R.id.tv_title_name)
    private TextView tv_titleName = null;

    //============================================================

    @Injection(R.id.s_news_notify)
    private Switch s_newNotify;

    @Injection(R.id.s_shengliuliang)
    private Switch s_shengliuliang;

    @Injection(value = R.id.rl_act_setting_new_message_remind, click = "clickView")
    private RelativeLayout rl_newMessageRemind = null;

    @Injection(value = R.id.rl_act_setting_video_play, click = "clickView")
    private RelativeLayout rl_videoPlay = null;

    @Injection(value = R.id.rl_black_list, click = "clickView")
    private RelativeLayout rl_blackList = null;

    @Injection(value = R.id.rl_account_manager, click = "clickView")
    private RelativeLayout rl_accountManager = null;

    @Injection(value = R.id.rl_account_bind, click = "clickView")
    private RelativeLayout rl_bind = null;

    @Injection(value = R.id.rl_clear_cache, click = "clickView")
    private RelativeLayout rl_cleatCache = null;

    @Injection(value = R.id.rl_message_our, click = "clickView")
    private RelativeLayout rl_mseeageOur = null;

    private FeedbackPresenter feedbackPresenter = new FeedbackPresenter(this);

    private AccountBindPresenter accountBindPresenter = new AccountBindPresenter(this);

    @Override
    public void initView() {
        super.initView();
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) rl_titlebar.getLayoutParams();
        lp.topMargin = statusHeight;

        tv_titleName.setText("设置");

        //初始化开关的状态
        boolean b = SPUtil.get(context, Constant.SP.mySetting.NEW_MESSAGE_NOTIFY_FLAG, false);
        s_newNotify.setChecked(b);
        b = SPUtil.get(context, Constant.SP.mySetting.MOBILE_NETWORK_TRAFFIC_FLAG, false);
        s_shengliuliang.setChecked(b);

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
            case R.id.rl_act_setting_new_message_remind: //跳转到新消息提醒的界面
//                ActivityUtil.startActivity(context, NewMessageRemindSettingAct.class);
                break;
            case R.id.rl_act_setting_video_play: //跳转到视频播放的设置界面
//                ActivityUtil.startActivity(context, VideoPlaySettingAct.class);
                break;
            case R.id.rl_black_list: //跳转到黑名单界面
                ActivityUtil.startActivity(context, BlacklistAct.class);
                break;
            case R.id.rl_account_manager: //跳转到账号管理界面
                accountBindPresenter.getThirdBindInfo();
                break;
            case R.id.rl_account_bind: //跳转到账号绑定界面
                ActivityUtil.startActivity(context, AccountBindAct.class);
                break;
            case R.id.rl_clear_cache: //清楚缓存
                T.showShort(context, "清楚缓存成功");
                break;
            case R.id.rl_message_our: //私信我们
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View view = View.inflate(context, R.layout.act_setting_input_fankui, null);
                final EditText et_content = (EditText) view.findViewById(R.id.et_content);

                builder.setView(view);

                builder.setPositiveButton("完成", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String content = et_content.getText().toString();
                        if (TextUtils.isEmpty(content.trim())) { //内容为空
                            tip("内容不能为空");
                        } else {
                            //发送反馈
                            feedbackPresenter.post_feedback(content.trim());
                        }
                    }
                });

                final AlertDialog dialog = builder.show();
                break;
        }

    }

    @Override
    public void setOnlistener() {
        super.setOnlistener();

        //记录下新消息是否提醒
        s_newNotify.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SPUtil.put(context, Constant.SP.mySetting.NEW_MESSAGE_NOTIFY_FLAG, b);
            }
        });

        //记录下是否移动网络下省流量
        s_shengliuliang.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SPUtil.put(context, Constant.SP.mySetting.MOBILE_NETWORK_TRAFFIC_FLAG, b);
            }
        });

    }

    /**
     * 推出app,在xml中填写的点击属性调用的
     *
     * @param view
     */
    public void exitApp(View view) {
        EventBus.getDefault().post(new SessionInvalidEvent());
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

    /**
     * 在点击账号管理之前判断是否可以进去
     *
     * @param info
     */
    @Override
    public void onLoadAccountBindInfoSuccess(AccountBindInfoResult info) {
        //如果电话已经绑定
        if (info.isPhone()) {
            tip("您已经绑定了手机");
        } else {
            ActivityUtil.startActivity(context, AccountManagerAct.class);
        }
    }

    @Override
    public void onBindQQSuccess() {
    }

    @Override
    public void onBindWeiXinSuccess() {
    }

    @Override
    public void onBindWeiBoSuccess() {
    }
}
