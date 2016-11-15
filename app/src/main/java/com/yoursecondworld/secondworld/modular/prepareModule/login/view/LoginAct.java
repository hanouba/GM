package com.yoursecondworld.secondworld.modular.prepareModule.login.view;

import android.content.Intent;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yoursecondworld.secondworld.MyApp;
import com.yoursecondworld.secondworld.R;
import com.yoursecondworld.secondworld.common.BaseAct;
import com.yoursecondworld.secondworld.common.Constant;
import com.yoursecondworld.secondworld.common.StaticDataStore;
import com.yoursecondworld.secondworld.modular.commonFunction.getSessionId.presenter.GetSessionIdPresenter;
import com.yoursecondworld.secondworld.modular.commonFunction.getSessionId.view.IGetSessionIdView;
import com.yoursecondworld.secondworld.modular.db.userGames.NewUserGamesDao;
import com.yoursecondworld.secondworld.modular.db.userGames.NewUserGamesDb;
import com.yoursecondworld.secondworld.modular.forgetPassword.ForgetPasswordAct;
import com.yoursecondworld.secondworld.modular.main.ui.MainAct;
import com.yoursecondworld.secondworld.modular.prepareModule.improvePersonInfo.view.ImprovePersonInfoAct;
import com.yoursecondworld.secondworld.modular.prepareModule.login.presenter.LoginPresenter;
import com.yoursecondworld.secondworld.modular.prepareModule.register.view.RegisterAct;

import com.yoursecondworld.secondworld.modular.share.ShareUtil;
import com.yoursecondworld.secondworld.modular.userAgreement.UserAgreementAct;

import xiaojinzi.annotation.Injection;
import xiaojinzi.base.android.activity.ActivityUtil;
import xiaojinzi.base.android.log.L;
import xiaojinzi.base.android.os.T;
import xiaojinzi.base.android.store.SPUtil;

/**
 * 登陆界面
 */
@Injection(R.layout.act_login)
public class LoginAct extends BaseAct implements ILoginView, TextWatcher, IGetSessionIdView {

    public static final String PHONENUMBERFLAG = "phoneNumberFlag";

    //=============================================================================

    @Injection(value = R.id.tv_register, click = "clickView")
    private TextView tv_register;

    /**
     * 用户输入的账号
     */
    @Injection(R.id.et_account)
    private EditText et_account = null;

    @Injection(R.id.et_password)
    private EditText et_password = null;

    @Injection(value = R.id.tv_act_login_forget_password, click = "clickView")
    private TextView tv_forgetPassword = null;

    @Injection(value = R.id.bt_act_login_login, click = "clickView")
    private Button bt_login = null;

    @Injection(value = R.id.iv_service_provision, click = "clickView")
    private ImageView iv_service_provision = null;

    //第三方的三个
    @Injection(value = R.id.rl_act_login_other_login_weixin_container, click = "clickView")
    private RelativeLayout rl_weixin = null;

    @Injection(value = R.id.rl_act_login_other_login_weibo_container, click = "clickView")
    private RelativeLayout rl_weibo = null;

    @Injection(value = R.id.rl_act_login_other_login_qq_container, click = "clickView")
    private RelativeLayout rl_qq = null;

    /**
     * 创建一个主持人
     */
    private LoginPresenter loginPresenter = new LoginPresenter(this);

    private GetSessionIdPresenter getSessionIdPresenter = new GetSessionIdPresenter(this);

    @Override
    public void initView() {
        super.initView();

        String account = getIntent().getStringExtra(PHONENUMBERFLAG);

        if (TextUtils.isEmpty(account)) {
            //获取保存的用户账号信息
            account = SPUtil.get(context, Constant.SP.loginAct.USERACCOUNT, "");
        }

        et_account.setText(account);

        //让指针去最后
        et_account.setSelection(account.length());

    }

    private String mUserAvatar;

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
            case R.id.iv_service_provision: //服务条款

                ActivityUtil.startActivity(context, UserAgreementAct.class);

                break;
            case R.id.tv_register:
                ActivityUtil.startActivity(context, RegisterAct.class);
                break;
            case R.id.tv_act_login_forget_password: //忘记密码
                ActivityUtil.startActivity(context, ForgetPasswordAct.class);
                finish();
                break;
            case R.id.bt_act_login_login: //登陆按钮

                getSessionIdPresenter.getSeesionId();

                break;
            case R.id.rl_act_login_other_login_weixin_container: //微信登陆
                showDialog("正在使用微信登陆");
                ShareUtil.loginWeiXin(new ShareUtil.OnLoginListener() {
                    @Override
                    public void onSuccess(String token, String userId, String userName, String userAvatar) {
                        mUserAvatar = userAvatar;
                        loginPresenter.weiXinLogin(token, userId);
                    }

                    @Override
                    public void onCancel() {
                        tip("取消微信登陆");
                        closeDialog();
                    }

                    @Override
                    public void onError() {
                        tip("微信登陆错误");
                        closeDialog();
                    }
                });
                break;
            case R.id.rl_act_login_other_login_weibo_container: //微博登陆
                showDialog("正在使用微博登陆");
                ShareUtil.loginWeiBo(new ShareUtil.OnLoginListener() {
                    @Override
                    public void onSuccess(String token, String userId, String userName, String userAvatar) {
                        mUserAvatar = userAvatar;
                        loginPresenter.weiBoLogin(token, userId);
                    }

                    @Override
                    public void onCancel() {
                        tip("取消微博登陆");
                        closeDialog();
                    }

                    @Override
                    public void onError() {
                        tip("微博登陆错误");
                        closeDialog();
                    }
                });
                break;
            case R.id.rl_act_login_other_login_qq_container: //qq登陆
                showDialog("正在使用QQ登陆");
                ShareUtil.loginQQ(new ShareUtil.OnLoginListener() {
                    @Override
                    public void onSuccess(String token, String userId, String userName, String userAvatar) {
                        mUserAvatar = userAvatar;
                        loginPresenter.qqLogin(token, userId);
                    }

                    @Override
                    public void onCancel() {
                        tip("取消QQ登陆");
                        closeDialog();
                    }

                    @Override
                    public void onError() {
                        tip("QQ登陆错误");
                        closeDialog();
                    }
                });
                break;
        }

    }

    @Override
    public void setOnlistener() {
        super.setOnlistener();
        //账号
        et_account.addTextChangedListener(this);
        et_password.addTextChangedListener(this);
    }

    @Override
    public boolean isRegisterEvent() {
        return false;
    }

    @Override
    public String getPhone() {
        return et_account.getText().toString();
    }

    @Override
    public String getPassword() {
        return et_password.getText().toString();
    }

    @Override
    public void loginSuccess(boolean isFullInfo) {

        //登陆成功之后的逻辑实现
        //保存账号
        SPUtil.put(context, Constant.SP.loginAct.USERACCOUNT, getPhone());

        //跳转到完善信息的界面
        if (isFullInfo) { //如果完善了
            saveSessionIdAndObjectId();
            ActivityUtil.startActivity(context, MainAct.class);
//            ActivityUtil.startActivity(context, SelectGameAct.class);
        } else {
            Intent i = new Intent(context, ImprovePersonInfoAct.class);
            i.putExtra(ImprovePersonInfoAct.USERAVATAR_FLAG, mUserAvatar);
            context.startActivity(i);
        }

        //结束自己
        finish();

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (!TextUtils.isEmpty(getPhone()) && !TextUtils.isEmpty(getPassword())) { //如果账号和密码都不是空的
            bt_login.setBackgroundResource(R.drawable.login_login_bt_eable_bg);
            bt_login.setTextColor(Color.WHITE);
        } else {
            bt_login.setBackgroundResource(R.drawable.login_login_bt_diseable_bg);
            bt_login.setTextColor(Color.parseColor("#1E2229"));
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {
    }

    @Override
    public void onGetSessionIdSuccess(String sessionId) {
        StaticDataStore.session_id = sessionId;
        //登陆的逻辑
        loginPresenter.login();
    }

    /**
     * 保存登陆用的信息
     */
    private void saveSessionIdAndObjectId() {

        SPUtil.put(context, Constant.RESULT_SESSION_ID, StaticDataStore.session_id);
        SPUtil.put(context, Constant.RESULT_OBJECT_ID, StaticDataStore.newUser.getUser_id());

//        NewUserDao userDao = new NewUserDao(new NewUserDb(this));

        //保存最新的用户信息
//        userDao.deleteByUserId();
//        userDao.insert(MyApp.newUser);

        //保存游戏标签
        NewUserGamesDao userGamesDao = new NewUserGamesDao(new NewUserGamesDb(this));
        userGamesDao.save(StaticDataStore.newUser.getGames());

    }

    @Override
    public void showDialog(String text) {
        super.showDialog(text);
        dialog.setCancelable(true);
    }
}
