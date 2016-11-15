package com.yoursecondworld.secondworld.modular.forgetPassword;

import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.yoursecondworld.secondworld.AppConfig;
import com.yoursecondworld.secondworld.MyApp;
import com.yoursecondworld.secondworld.R;
import com.yoursecondworld.secondworld.common.BaseAct;
import com.yoursecondworld.secondworld.common.baseResult.BaseEntity;
import com.yoursecondworld.secondworld.common.Constant;
import com.yoursecondworld.secondworld.common.JsonRequestParameter;
import com.yoursecondworld.secondworld.common.ResultStatusFilter;
import com.yoursecondworld.secondworld.common.StaticDataStore;
import com.yoursecondworld.secondworld.modular.commonFunction.getSessionId.presenter.GetSessionIdPresenter;
import com.yoursecondworld.secondworld.modular.commonFunction.getSessionId.view.IGetSessionIdView;
import com.yoursecondworld.secondworld.modular.db.userGames.NewUserGamesDao;
import com.yoursecondworld.secondworld.modular.db.userGames.NewUserGamesDb;
import com.yoursecondworld.secondworld.modular.main.ui.MainAct;
import com.yoursecondworld.secondworld.modular.prepareModule.improvePersonInfo.view.ImprovePersonInfoAct;
import com.yoursecondworld.secondworld.modular.prepareModule.login.view.LoginAct;
import com.yoursecondworld.secondworld.modular.systemInfo.entity.NewUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xiaojinzi.annotation.Injection;
import xiaojinzi.base.android.activity.ActivityUtil;
import xiaojinzi.base.android.adapter.view.TextWatcherAdapter;
import xiaojinzi.base.android.log.L;
import xiaojinzi.base.android.store.SPUtil;

/**
 * 忘记密码的界面
 */
@Injection(R.layout.act_forget_password)
public class ForgetPasswordAct extends BaseAct implements Runnable, IGetSessionIdView {


    //短信发送的时候.这里是表示还要等多久才能再一次返送,默认是60
    private int leaveSecond = 0;

    /**
     * 是否点击了获取短信的按钮
     */
    private boolean isClickGetCheckCodeButton = false;

    /**
     * 手机号码的正则表达式
     */
    private String phoneMatche = "^1[3|4|5|7|8][0-9]\\d{8}$";

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
                    break;
                case 1:
                    //可以响应用户的点击了
                    tv_sendCheckCode.setEnabled(true);
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

    /**
     * 控制线程是否存活,界面开启,线程就启动
     */
    private boolean controlThread = true;

    @Injection(value = R.id.tv_sendCheckCode, click = "clickView")
    private TextView tv_sendCheckCode = null;

    @Injection(value = R.id.bt_act_forget_password_submit, click = "clickView")
    private Button bt_submit = null;

    @Injection(R.id.et_account)
    private EditText et_phone = null;

    @Injection(R.id.et_check_code)
    private EditText et_code = null;

    @Injection(R.id.et_password)
    private EditText et_passwordOne = null;

    @Injection(R.id.iv_password_visiable_flag)
    private ImageView iv_password_visiable_flag;

    @Injection(value = R.id.tv_goback_login, click = "clickView")
    private TextView tv_goback_login = null;


    @Override
    public void initView() {
        super.initView();

        //启动线程
        new Thread(this).start();

    }

    @Override
    public void setOnlistener() {
        super.setOnlistener();

        TextWatcherAdapter textWatcherAdapter = new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                super.afterTextChanged(s);
                changeLoginBtBackDrable();
            }
        };

        et_phone.addTextChangedListener(textWatcherAdapter);
        et_code.addTextChangedListener(textWatcherAdapter);
        et_passwordOne.addTextChangedListener(textWatcherAdapter);

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
            case R.id.iv_back: //如果是返回
                finish();
                break;

            case R.id.tv_goback_login: //如果是返回登陆

                ActivityUtil.startActivity(context, LoginAct.class);

                break;
            case R.id.iv_password_visiable_flag: //控制密码是否可见

                int inputType = et_passwordOne.getInputType();
                if (inputType == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) { //如果是不可见的
                    et_passwordOne.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    iv_password_visiable_flag.setImageResource(R.mipmap.password_invisiable);
                } else {
                    et_passwordOne.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    iv_password_visiable_flag.setImageResource(R.mipmap.password_visiable);
                }
                et_passwordOne.setSelection(et_passwordOne.getText().length());

                break;
            case R.id.tv_sendCheckCode:

                //拿到电话号码
                String phone = et_phone.getText().toString();

                if (TextUtils.isEmpty(phone)) {
                    tip("请填写正确的手机号码");
                    return;
                }

                //手机号的正则
                boolean matches = phone.matches(phoneMatche);
                if (!matches) { //如果不匹配,提示用户
                    tip("请输入正确的手机号");
                    return;
                }

                leaveSecond = 30;
                isClickGetCheckCodeButton = true;

                sendSms();

                break;
            case R.id.bt_act_forget_password_submit: //点击了提交的按钮

                changePassword();

                break;
        }

    }

    /**
     * 提交更改ia
     */
    private void changePassword() {
        //拿到电话号码
        String phone = et_phone.getText().toString();

        if (TextUtils.isEmpty(phone)) {
            tip("请填写正确的手机号码");
            return;
        }

        //手机号的正则
        boolean matches = phone.matches(phoneMatche);
        if (!matches) { //如果不匹配,提示用户
            tip("请输入正确的手机号");
            return;
        }

        String password1 = et_passwordOne.getText().toString();

        if (TextUtils.isEmpty(password1)) {
            tip("密码不能为空");
            return;
        }

        String code = et_code.getText().toString();
        if (TextUtils.isEmpty(code)) {
            tip("验证码不能为空");
            return;
        }

        RequestBody body = JsonRequestParameter.getInstance()
                .addParameter(Constant.RESULT_SESSION_ID, StaticDataStore.session_id)
                .addParameter("phone_number", phone)
                .addParameter("password", password1)
                .addParameter("code", code)
                .build();

        Call<String> call = AppConfig.netWorkService.changePassword(body);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                doSoveLoginReturnData(response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                tip("网络可能有点问题");
            }
        });

    }

    private void doSoveLoginReturnData(String content) {

        L.s(TAG, "content = " + content);

        try {
            //转化成对象
            JSONObject jb = new JSONObject(content);

            int status = jb.getInt(Constant.RESULT_STATUS);
            if (status == Constant.Status.SUCCESS) {
                //拿到用户信息
                String userStr = jb.getString("user");
                StaticDataStore.newUser = AppConfig.gson.fromJson(userStr, NewUser.class);

                //拿到游戏的集合
                String gameArrStr = jb.getString("games");
                List<String> games = AppConfig.gson.fromJson(gameArrStr, new TypeToken<List<String>>() {
                }.getType());
                StaticDataStore.newUser.setGames(games);

                //是否完善信息
                boolean isFullInfo = jb.getBoolean("full_information");

                if (isFullInfo) {
                    saveSessionIdAndObjectId();
                    ActivityUtil.startActivity(context, MainAct.class);
                } else {
                    ActivityUtil.startActivity(context, ImprovePersonInfoAct.class);
                }

                //结束自己
                finish();

            } else {
                tip("修改密码失败");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
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

    /**
     * 在每个文本变化的时候调用即可
     */
    private void changeLoginBtBackDrable() {

        bt_submit.setBackgroundResource(R.drawable.forget_password_submit_bt_disable_bg);

        //拿到电话号码
        String phone = et_phone.getText().toString();

        if (TextUtils.isEmpty(phone)) {
            return;
        }

        //手机号的正则
        boolean matches = phone.matches(phoneMatche);
        if (!matches) { //如果不匹配,提示用户
            return;
        }

        String code = et_code.getText().toString();
        if (TextUtils.isEmpty(code)) {
            return;
        }

        bt_submit.setBackgroundResource(R.drawable.forget_password_submit_bt_able_bg);

    }

    /**
     * 获取sessionId的工具
     */
    private GetSessionIdPresenter getSessionIdPresenter = new GetSessionIdPresenter(this);


    /**
     * 发送短信
     */
    private void sendSms() {
        getSessionIdPresenter.getSeesionId();
    }

    @Override
    public boolean isRegisterEvent() {
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //让线程挂掉
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
    public void onGetSessionIdSuccess(String sessionId) {

        StaticDataStore.session_id = sessionId;

        RequestBody body = JsonRequestParameter.getInstance()
                .addParameter(Constant.RESULT_SESSION_ID, StaticDataStore.session_id)
                .addParameter("phone_number", et_phone.getText().toString())
                .build();

        Call<BaseEntity> call = AppConfig.netWorkService.sendForgetPasswordSms(body);

        call.enqueue(new Callback<BaseEntity>() {
            @Override
            public void onResponse(Call<BaseEntity> call, Response<BaseEntity> response) {
                if (response.body().getStatus() == Constant.Status.SUCCESS) {
                    tip("发送信息成功");
                } else {
                    tip("发送信息失败");
                }
                ResultStatusFilter.filter(response.body());
            }

            @Override
            public void onFailure(Call<BaseEntity> call, Throwable t) {
                tip("发送信息失败");
            }
        });

    }

}
