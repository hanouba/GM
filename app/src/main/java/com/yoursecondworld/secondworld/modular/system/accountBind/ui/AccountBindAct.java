package com.yoursecondworld.secondworld.modular.system.accountBind.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yoursecondworld.secondworld.R;
import com.yoursecondworld.secondworld.common.BaseAct;
import com.yoursecondworld.secondworld.common.event.SessionInvalidEvent;
import com.yoursecondworld.secondworld.modular.share.ShareUtil;
import com.yoursecondworld.secondworld.modular.system.accountBind.entity.AccountBindInfoResult;
import com.yoursecondworld.secondworld.modular.system.accountBind.presenter.AccountBindPresenter;

import org.greenrobot.eventbus.Subscribe;

import xiaojinzi.annotation.Injection;

/**
 * 账号绑定的界面
 */
@Injection(R.layout.act_account_bind)
public class AccountBindAct extends BaseAct implements IAccountBindView {

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

    /**
     * 标题栏右边的菜单文本
     */
    @Injection(value = R.id.tv_menu, click = "clickView")
    private TextView tv_menu = null;

    //=============================================================================

    @Injection(value = R.id.rl_act_account_bind_weibo, click = "clickView")
    private RelativeLayout rl_act_account_bind_weibo;

    @Injection(value = R.id.rl_act_account_bind_weixin, click = "clickView")
    private RelativeLayout rl_act_account_bind_weixin;

    @Injection(value = R.id.rl_act_account_bind_qq, click = "clickView")
    private RelativeLayout rl_act_account_bind_qq;

    /**
     * 账号绑定的主持人
     */
    private AccountBindPresenter accountBindPresenter = new AccountBindPresenter(this);


    @Override
    public void initView() {
        super.initView();

        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) rl_titlebar.getLayoutParams();
        lp.topMargin = statusHeight;

        tv_titleName.setText("账号绑定");

//        //针对白色的标题栏需要做的操作
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

    private AccountBindInfoResult info;

    @Override
    public void initData() {
        super.initData();

        //获取绑定的信息
        accountBindPresenter.getThirdBindInfo();

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
            case R.id.rl_act_account_bind_weibo:

                if (info == null) {
                    accountBindPresenter.getThirdBindInfo();
                    return;
                }

                if (info.isWeibo()) { //如果微博已经绑定
                    tip("您已经绑定了微博");
                    return;
                }

                showDialog("正在绑定");

                ShareUtil.loginWeiBo(new ShareUtil.OnLoginListener() {
                    @Override
                    public void onSuccess(String token, String userId, String userName, String userAvatar) {
                        accountBindPresenter.bind_weibo(userId, token);
                    }

                    @Override
                    public void onCancel() {
                        closeDialog();
                    }

                    @Override
                    public void onError() {
                        closeDialog();
                    }
                });

                break;

            case R.id.rl_act_account_bind_weixin:

                if (info == null) {
                    accountBindPresenter.getThirdBindInfo();
                    return;
                }

                if (info.isWechat()) {
                    tip("您已经绑定了微信");
                    return;
                }

                showDialog("正在绑定");

                ShareUtil.loginWeiXin(new ShareUtil.OnLoginListener() {

                    @Override
                    public void onSuccess(String token, String userId, String userName, String userAvatar) {
                        accountBindPresenter.bind_wechat(userId, token);
                    }

                    @Override
                    public void onCancel() {
                        closeDialog();
                    }

                    @Override
                    public void onError() {
                        closeDialog();
                    }

                });

                break;

            case R.id.rl_act_account_bind_qq: //qq登陆的

                if (info == null) {
                    accountBindPresenter.getThirdBindInfo();
                    return;
                }

                if (info.isQq()) { //避免重复绑定
                    tip("您已经绑定了QQ");
                    return;
                }

                showDialog("正在绑定");

                ShareUtil.loginQQ(new ShareUtil.OnLoginListener() {

                    @Override
                    public void onSuccess(String token, String userId, String userName, String userAvatar) {
                        accountBindPresenter.bindQq(userId, token);
                    }

                    @Override
                    public void onCancel() {
                        closeDialog();
                    }

                    @Override
                    public void onError() {
                        closeDialog();
                    }

                });

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
    public void onLoadAccountBindInfoSuccess(AccountBindInfoResult info) {
        this.info = info;
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
