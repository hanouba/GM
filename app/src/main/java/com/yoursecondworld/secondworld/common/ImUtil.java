package com.yoursecondworld.secondworld.common;

import android.content.Context;
import android.text.TextUtils;

import com.yoursecondworld.secondworld.AppConfig;
import com.yoursecondworld.secondworld.MyApp;
import com.yoursecondworld.secondworld.common.baseResult.BaseEntity;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xiaojinzi.base.android.log.L;
import xiaojinzi.base.android.store.SPUtil;

/**
 * Created by cxj on 2016/7/16.
 * 即时通讯的工具类
 */
public class ImUtil {

    /**
     * 类的标识
     */
    public static final String TAG = "ImUtil";

    /**
     * 连接服务器,如果失败了,可能是因为token的失效,那么应该重新获取token
     */
    public static void connect(final Context context, String imToken) {

//        String imToken = SPUtil.get(context, Constant.SP.im.TOKEN, "");
//        String imToken = "AHkkE4c2rO5VMEROx4kBTjd7VmBnpzSvGcw6np/c6EVlju7TgXJkw0zPmyuZOY6jEXO7LJ8Y0kMSjeg7UeWALYDycFU0/+jesvumNnLMMwVyUViRRrySsw==";

        if (TextUtils.isEmpty(imToken)) {
            getToken(context);
            return;
        }

        //如果不是连接状态,那么就去连接
        RongIMClient.ConnectionStatusListener.ConnectionStatus status = RongIM.getInstance().getCurrentConnectionStatus();
        if (status != RongIMClient.ConnectionStatusListener.ConnectionStatus.CONNECTED) {
            /**
             * IMKit SDK调用第二步
             *
             * 建立与服务器的连接
             *
             */
            RongIM.connect(imToken, new RongIMClient.ConnectCallback() {
                @Override
                public void onTokenIncorrect() {
                    //Connect Token 失效的状态处理，需要重新获取 Token
                    L.s(TAG, "token失效,需要重新获取");
//                    IMTokenManager.getInstance().reGetToken(context);
                    getToken(context);
                }

                @Override
                public void onSuccess(String userId) {
                    L.s(TAG, "连接成功");
                }

                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    L.s(TAG, "失败");
                }
            });
        } else {
            L.s(TAG, "本来就连接的");
        }

    }

    /**
     * 登出
     */
    public static void loginOut() {

    }


    private static void getToken(final Context context) {

        RequestBody body = JsonRequestParameter.getInstance()
                .addParameter(Constant.RESULT_SESSION_ID, StaticDataStore.session_id)
                .addParameter(Constant.RESULT_OBJECT_ID, StaticDataStore.newUser.getUser_id())
                .build();

        Call<TokenResult> call = AppConfig.netWorkService.get_rc_token(body);

        call.enqueue(new Callback<TokenResult>() {
            @Override
            public void onResponse(Call<TokenResult> call, Response<TokenResult> response) {

                //获取token
                String token = response.body().getToken();
                if (token != null) {
                    SPUtil.put(context, Constant.SP.im.TOKEN, token);
                    connect(context, token);
                }

            }

            @Override
            public void onFailure(Call<TokenResult> call, Throwable t) {
                String imToken = SPUtil.get(context, Constant.SP.im.TOKEN, "");
                connect(context, imToken);
            }
        });

    }

    /**
     * 是否已经链接
     *
     * @return
     */
    public static boolean isConnected() {
        RongIMClient.ConnectionStatusListener.ConnectionStatus status = RongIM.getInstance().getCurrentConnectionStatus();
        return status == RongIMClient.ConnectionStatusListener.ConnectionStatus.CONNECTED;
    }

    /**
     * 默认都先和15857913627聊天
     *
     * @param context
     */
    public static void startChat(Context context) {
//        RongIM.getInstance().startPrivateChat(context, "15857913627", null);
//        RongIM.getInstance().startPrivateChat(context, "13735721441", null);
//        RongIM.getInstance().startConversationList(context,null);
    }

    public static class TokenResult extends BaseEntity {

        private String token;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

    }

}
