package com.yoursecondworld.secondworld.modular.prepareModule.register.view;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.text.TextUtils;
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
import com.yoursecondworld.secondworld.common.JsonRequestParameter;
import com.yoursecondworld.secondworld.common.StaticDataStore;
import com.yoursecondworld.secondworld.modular.commonFunction.getSessionId.presenter.GetSessionIdPresenter;
import com.yoursecondworld.secondworld.modular.commonFunction.getSessionId.view.IGetSessionIdView;
import com.yoursecondworld.secondworld.modular.db.newUser.NewUserDao;
import com.yoursecondworld.secondworld.modular.db.newUser.NewUserDb;
import com.yoursecondworld.secondworld.modular.db.userGames.NewUserGamesDao;
import com.yoursecondworld.secondworld.modular.db.userGames.NewUserGamesDb;
import com.yoursecondworld.secondworld.modular.main.ui.MainAct;
import com.yoursecondworld.secondworld.modular.prepareModule.improvePersonInfo.view.ImprovePersonInfoAct;
import com.yoursecondworld.secondworld.modular.prepareModule.login.presenter.LoginPresenter;
import com.yoursecondworld.secondworld.modular.prepareModule.login.view.ILoginView;
import com.yoursecondworld.secondworld.modular.prepareModule.login.view.LoginAct;
import com.yoursecondworld.secondworld.modular.prepareModule.register.presenter.RegisterPresenter;
import com.yoursecondworld.secondworld.modular.share.ShareUtil;
import com.yoursecondworld.secondworld.modular.userAgreement.UserAgreementAct;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xiaojinzi.annotation.Injection;
import xiaojinzi.base.android.activity.ActivityUtil;
import xiaojinzi.base.android.os.T;
import xiaojinzi.base.android.store.SPUtil;

/**
 * 注册的页面
 */
@Injection(R.layout.act_register)
public class RegisterAct extends BaseAct implements Runnable, IRegisterView, IGetSessionIdView, ILoginView {

    //=============================================================================

    /**
     * 号码输入框
     */
    @Injection(R.id.et_account)
    private EditText et_phone = null;

    @Injection(value = R.id.tv_sendCheckCode, click = "clickView")
    private TextView tv_sendCheckCode = null;

    /**
     * 验证码的输入框
     */
    @Injection(R.id.et_check_code)
    private EditText et_checkCode = null;

    @Injection(R.id.et_password)
    private EditText et_password = null;


    /**
     * 登陆按钮
     */
    @Injection(value = R.id.bt_act_register_register, click = "clickView")
    private Button bt_register = null;

    @Injection(value = R.id.tv_have_account, click = "clickView")
    private TextView tv_have_account = null;

    @Injection(value = R.id.iv_password_visiable_flag, click = "clickView")
    private ImageView iv_password_visiable_flag = null;

    //第三方的三个
    @Injection(value = R.id.rl_weixin_login_container, click = "clickView")
    private RelativeLayout rl_weixin = null;

    @Injection(value = R.id.rl_weibo_login_container, click = "clickView")
    private RelativeLayout rl_weibo = null;

    @Injection(value = R.id.rl_qq_login_container, click = "clickView")
    private RelativeLayout rl_qq = null;

    /**
     * 用户协议
     */
//    @Injection(value = R.id.tv_act_register_user_agreement, click = "clickView")
//    private TextView tv_userAgreement = null;

    private RegisterPresenter presenter = new RegisterPresenter(this);

    private GetSessionIdPresenter getSessionIdPresenter = new GetSessionIdPresenter(this);

    private LoginPresenter loginPresenter = new LoginPresenter(this);

    /**
     * 控制线程是否存活,界面开启,线程就启动
     */
    private boolean controlThread = true;

    @Override
    public void initView() {
        super.initView();
        initBase();
        //启动线程
        new Thread(this).start();

    }

    /**
     * 初始化基本信息
     */
    private void initBase() {

        //给用户协议几个字加粗
//        tv_userAgreement.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
//        tv_userAgreement.getPaint().setFakeBoldText(true);//加粗
//        tv_userAgreement.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
//        tv_userAgreement.getPaint().setAntiAlias(true);//抗锯齿
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
            case R.id.tv_have_account: //如果已经有账号了

                //跳转到登陆界面
                ActivityUtil.startActivity(context, LoginAct.class);
                finish();

                break;
            case R.id.iv_back: //点击了返回键
                finish();
                break;

            case R.id.iv_password_visiable_flag: //控制密码是否可见

                int inputType = et_password.getInputType();
                if (inputType == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) { //如果是不可见的
                    et_password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    iv_password_visiable_flag.setImageResource(R.mipmap.password_invisiable);
                } else {
                    et_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    iv_password_visiable_flag.setImageResource(R.mipmap.password_visiable);
                }
                et_password.setSelection(et_password.getText().length());

                break;
            case R.id.tv_sendCheckCode: //点击了发送验证码的按钮

                action = 1;
                getSessionIdPresenter.getSeesionId();

                break;
//            case R.id.tv_act_register_user_agreement: //点击了用户守则
//                ActivityUtil.startActivity(context, UserAgreementAct.class);
//                break;
            case R.id.bt_act_register_register: //点击了注册的按钮

                if (TextUtils.isEmpty(StaticDataStore.session_id)) {
                    action = 2;
                    getSessionIdPresenter.getSeesionId();
                } else {
                    presenter.register();
                }


                break;

            case R.id.rl_weixin_login_container: //微信登陆
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
            case R.id.rl_weibo_login_container: //微博登陆
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
            case R.id.rl_qq_login_container: //qq登陆
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

    //短信发送的时候.这里是表示还要等多久才能再一次返送,默认是60
    private int leaveSecond = 0;

    //是否已经点击过了获取验证码
    private boolean isClickGetCheckCodeButton = false;

    /**
     * 消息的传送
     */
    private Handler h = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;

            switch (what) {
                case 0: //表示在等待
                    tv_sendCheckCode.setEnabled(false); //让用户不能点击
                    tv_sendCheckCode.setText(leaveSecond + "");
                    //设置灰色的背景,表示现在不是能点击的
//                    tv_sendCheckCode.setBackgroundResource(R.drawable.invalid_getcheckcode_bt_bg);
                    break;
                case 1:
                    //可以响应用户的点击了
                    tv_sendCheckCode.setEnabled(true);
//                    tv_sendCheckCode.setBackgroundResource(R.drawable.getcheckcode_bt_bg);
                    //如果已经点过按钮了,那么这里显示的就是再次发送
                    if (isClickGetCheckCodeButton) {
                        tv_sendCheckCode.setText("重新发送");
                    } else {
                        tv_sendCheckCode.setText("发送验证");
                    }
                    break;
            }

        }
    };

    @Override
    public boolean isRegisterEvent() {
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        controlThread = false;
    }

    /**
     * 用来实现短信发送的倒计时
     */
    @Override
    public void run() {
        while (controlThread) {
            //休眠一秒钟
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            if (leaveSecond > 0) {
                leaveSecond--;
                h.sendEmptyMessage(0);
            } else {
                h.sendEmptyMessage(1);
            }
        }
    }

    @Override
    public String getPhone() {
        return et_phone.getText().toString();
    }

    @Override
    public String getPassword() {
        return et_password.getText().toString();
    }

    @Override
    public String getCheckCode() {
        return et_checkCode.getText().toString();
    }

    @Override
    public void loginSuccess(boolean isFullInfo) {
        //跳转到完善信息的界面
        if (isFullInfo) { //如果完善了
            saveSessionIdAndObjectId();
            ActivityUtil.startActivity(context, MainAct.class);
            //ActivityUtil.startActivity(context, SelectGameAct.class);
        } else {
            Intent i = new Intent(context, ImprovePersonInfoAct.class);
            i.putExtra(ImprovePersonInfoAct.USERAVATAR_FLAG, mUserAvatar);
            context.startActivity(i);
        }
        //结束自己
        finish();
    }

    @Override
    public void onRegisterSuccess() {

        //跳转到完善信息界面
        Intent i = new Intent(context, ImprovePersonInfoAct.class);
        i.putExtra(ImprovePersonInfoAct.FROM_FLAG, TAG);
        startActivity(i);
        finish();

    }

    private int action = 0;

    @Override
    public void onGetSessionIdSuccess(String sessionId) {
        StaticDataStore.session_id = sessionId;
        if (action == 1) {
            //发送短信
            presenter.sendCheckCode();
            leaveSecond = 30;
            isClickGetCheckCodeButton = true;
        } else if (action == 2) {
            presenter.register(); //注册的逻辑
        }
    }

    /**
     * 保存登陆用的信息
     */
    private void saveSessionIdAndObjectId() {
        SPUtil.put(context, Constant.RESULT_SESSION_ID, StaticDataStore.session_id);
        SPUtil.put(context, Constant.RESULT_OBJECT_ID, StaticDataStore.newUser.getUser_id());
        //NewUserDao userDao = new NewUserDao(new NewUserDb(this));
        //保存最新的用户信息
        //userDao.deleteByUserId();
        //userDao.insert(MyApp.newUser);
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
