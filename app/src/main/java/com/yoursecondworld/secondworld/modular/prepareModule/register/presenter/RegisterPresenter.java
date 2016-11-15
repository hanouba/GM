package com.yoursecondworld.secondworld.modular.prepareModule.register.presenter;

import android.text.TextUtils;

import com.yoursecondworld.secondworld.AppConfig;
import com.yoursecondworld.secondworld.MyApp;
import com.yoursecondworld.secondworld.common.CallbackAdapter;
import com.yoursecondworld.secondworld.common.Constant;
import com.yoursecondworld.secondworld.common.JsonRequestParameter;
import com.yoursecondworld.secondworld.common.StaticDataStore;
import com.yoursecondworld.secondworld.modular.prepareModule.login.entity.LoginResult;
import com.yoursecondworld.secondworld.modular.prepareModule.register.view.IRegisterView;
import com.yoursecondworld.secondworld.modular.systemInfo.entity.NewUser;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by cxj on 2016/8/11.
 */
public class RegisterPresenter {

    /**
     * 手机号码的正则表达式
     */
    private String phoneMatche = "^1[3|4|5|7|8][0-9]\\d{8}$";

    private IRegisterView registerView;

    public RegisterPresenter(IRegisterView registerView) {
        this.registerView = registerView;
    }

    /**
     * 注册
     */
    public void register() {

        //获取用户输入的手机号
        String phone = registerView.getPhone();

        //健壮性判断
        if (TextUtils.isEmpty(phone)) {
            registerView.tip("手机号不能为空！");
            return;
        }

        //手机号的正则
        boolean matches = phone.matches(phoneMatche);
        if (!matches) { //如果不匹配,提示用户
            registerView.tip("请输入正确的手机号");
            return;
        }

        //拿到密码
        String password = registerView.getPassword();

        //检查输入的密码
        if (TextUtils.isEmpty(password)) {
            registerView.tip("请输入密码");
            return;
        }

        //获取验证码
        String checkCode = registerView.getCheckCode();
        if (TextUtils.isEmpty(checkCode)) {
            registerView.tip("验证码不能为空");
            return;
        }

        registerView.showDialog("正在注册");


        RequestBody body = JsonRequestParameter.build(
                new String[]{Constant.RESULT_SESSION_ID, "phone_number", "password", "code"},
                new Object[]{StaticDataStore.session_id, phone, password, checkCode});


        //注册
        Call<LoginResult> call = AppConfig.netWorkService.register(body);

        call.enqueue(new CallbackAdapter<LoginResult>(registerView) {
            @Override
            public void onResponse(LoginResult loginResult) {
                //保存userId
                StaticDataStore.newUser = loginResult.getUser();
                if (StaticDataStore.newUser == null) {
                    StaticDataStore.newUser = new NewUser();
                    StaticDataStore.newUser.setUser_id(loginResult.getUser_id());
                }
                registerView.tip("注册成功");
                registerView.onRegisterSuccess();
            }
        });

    }

    /**
     * 发送验证码
     */
    public void sendCheckCode() {
        //获取用户输入的手机号
        String phone = registerView.getPhone();

        //健壮性判断
        if (TextUtils.isEmpty(phone)) {
            registerView.tip("手机号不能为空！");
            return;
        }

        //手机号的正则
        boolean matches = phone.matches(phoneMatche);
        if (!matches) { //如果不匹配,提示用户
            registerView.tip("请输入正确的手机号");
            return;
        }

        RequestBody body = JsonRequestParameter.getInstance()
                .addParameter(Constant.RESULT_SESSION_ID, StaticDataStore.session_id)
                .addParameter("phone_number", phone)
                .build();
        Call<String> call = AppConfig.netWorkService.send_resginer_message(body);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    JSONObject jb = new JSONObject(response.body());
                    int states = jb.getInt(Constant.RESULT_STATUS);
                    if (states == 0) {
                        registerView.tip("发送短信息成功,请注意接收");
                    } else if (states == 103) {
                        registerView.tip("手机号已经被使用,如果是您自己注册的,请直接登陆");
                    } else {
                        registerView.tip("发送失败:" + states);
                    }
                } catch (JSONException e) {
                    registerView.tip("发送失败");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                registerView.tip("发送失败");
            }
        });

    }

}
