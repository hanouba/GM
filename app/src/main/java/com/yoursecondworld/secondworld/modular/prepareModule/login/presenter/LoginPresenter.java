package com.yoursecondworld.secondworld.modular.prepareModule.login.presenter;

import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.yoursecondworld.secondworld.AppConfig;
import com.yoursecondworld.secondworld.MyApp;
import com.yoursecondworld.secondworld.common.CallbackAdapter;
import com.yoursecondworld.secondworld.common.Constant;
import com.yoursecondworld.secondworld.common.JsonRequestParameter;
import com.yoursecondworld.secondworld.common.StaticDataStore;
import com.yoursecondworld.secondworld.modular.prepareModule.login.entity.LoginResult;
import com.yoursecondworld.secondworld.modular.prepareModule.login.view.ILoginView;
import com.yoursecondworld.secondworld.modular.systemInfo.entity.NewUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by cxj on 2016/8/11.
 * 登陆界面的主持人
 */
public class LoginPresenter {

    private ILoginView iLoginView;

    public LoginPresenter(ILoginView iLoginView) {
        this.iLoginView = iLoginView;
    }

    /**
     * 实现登陆
     */
    public void login() {

        //显示正在登陆进度条
        iLoginView.showDialog("正在登陆");

        //拿到账号和密码
        String phone_number = iLoginView.getPhone();
        String password = iLoginView.getPassword();

        //获取用户输入的号码
        final String account = phone_number;

        if (TextUtils.isEmpty(account) || TextUtils.isEmpty(password)) {
            iLoginView.tip("用户名或者密码不能为空");
            iLoginView.closeDialog();
            return;
        }


        RequestBody body = JsonRequestParameter.build(
                new String[]{Constant.RESULT_SESSION_ID, "phone_number", "password"},
                new Object[]{StaticDataStore.session_id, account, password});

        //执行登陆

        Call<LoginResult> call = AppConfig.netWorkService.login(body);

        call.enqueue(new CallbackAdapter<LoginResult>(iLoginView) {
            @Override
            public void onResponse(LoginResult loginResult) {
                doSoveLoginReturnData(loginResult);
            }
        });

    }

    /**
     * 微信登陆
     */
    public void weiXinLogin(final String access_token, final String openid) {

//        iLoginView.showProgress();

        Call<String> call = AppConfig.netWorkService.aquire_session_id();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                StaticDataStore.session_id = response.body();
                doWeiXinLogin(access_token, openid);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                iLoginView.closeDialog();
                iLoginView.tip("初始化失败");
            }

        });


    }

    private void doWeiXinLogin(String access_token, String openid) {

        RequestBody body = JsonRequestParameter.build(
                new String[]{Constant.RESULT_SESSION_ID, "access_token", "openid"},
                new Object[]{StaticDataStore.session_id, access_token, openid});

        Call<LoginResult> call = AppConfig.netWorkService.wechat_login(body);
        call.enqueue(new CallbackAdapter<LoginResult>(iLoginView) {
            @Override
            public void onResponse(LoginResult loginResult) {
                doSoveLoginReturnData(loginResult);
            }
        });
    }

    /**
     * 微博登录
     *
     * @param access_token
     * @param openid
     */
    public void weiBoLogin(final String access_token, final String openid) {

        Call<String> call = AppConfig.netWorkService.aquire_session_id();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                StaticDataStore.session_id = response.body();
                doWeiBoLogin(access_token, openid);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                iLoginView.closeDialog();
                iLoginView.tip("初始化失败");
            }

        });


    }

    private void doWeiBoLogin(String access_token, String uid) {


        RequestBody body = JsonRequestParameter.build(
                new String[]{Constant.RESULT_SESSION_ID, "access_token", "uid"},
                new Object[]{StaticDataStore.session_id, access_token, uid});

        Call<LoginResult> call = AppConfig.netWorkService.weibo_login(body);

        call.enqueue(new CallbackAdapter<LoginResult>(iLoginView) {
            @Override
            public void onResponse(LoginResult loginResult) {
                doSoveLoginReturnData(loginResult);
            }
        });
    }

    public void qqLogin(final String access_token, final String openid) {

        Call<String> call = AppConfig.netWorkService.aquire_session_id();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                StaticDataStore.session_id = response.body();
                doQqLogin(access_token, openid);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                iLoginView.closeDialog();
                iLoginView.tip("初始化失败");
            }

        });


    }

    private void doQqLogin(String access_token, String openid) {

        RequestBody body = JsonRequestParameter.build(
                new String[]{Constant.RESULT_SESSION_ID, "access_token", "openid"},
                new Object[]{StaticDataStore.session_id, access_token, openid});

        Call<LoginResult> call = AppConfig.netWorkService.qq_login(body);
        call.enqueue(new CallbackAdapter<LoginResult>(iLoginView) {
            @Override
            public void onResponse(LoginResult loginResult) {
                doSoveLoginReturnData(loginResult);
            }
        });
    }

    /**
     * 处理登陆返回的结果
     *
     * @param loginResult
     */
    private void doSoveLoginReturnData(LoginResult loginResult) {
        StaticDataStore.newUser = loginResult.getUser();
        //是否完善信息
        boolean isFullInfo = loginResult.isFull_information();
        iLoginView.loginSuccess(isFullInfo);
    }


}
