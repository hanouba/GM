package com.yoursecondworld.secondworld;

import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.Gson;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.MobclickAgent;
import com.yoursecondworld.secondworld.common.Constant;
import com.yoursecondworld.secondworld.modular.main.eventEntity.ReFreshIMUserInfoEvent;
import com.yoursecondworld.secondworld.netWorkService.NetWorkService;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import cn.jpush.android.api.JPushInterface;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import xiaojinzi.base.android.store.SPUtil;

/**
 * Created by cxj on 2016/10/21.
 * 整体项目的配置文件,里面有直接可以引用的组建,从MyApp类中移到这里了
 */
public class AppConfig {

    /**
     * 项目的访问根路径
     */
    public static final String APPPREFIX = "http://114.55.110.208:8888/";

    /**
     * 项目的网络对象
     */
    public static NetWorkService netWorkService = null;

    /**
     * 网络请求框架
     */
    public static Retrofit retrofit;

    /**
     * 是否初始化了
     */
    private static boolean isInit = false;

    /**
     * json解析器
     */
    public static Gson gson;

    /**
     * 初始化项目的组建
     *
     * @param context 上下文
     */
    public static void init(Context context) {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(APPPREFIX)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        if (netWorkService == null) {
            //让框架自动实现我们的请求接口,让我们的请求接口可以被调用
            netWorkService = retrofit.create(NetWorkService.class);
        }

        if (gson == null) {
            //初始化json解析框架
            gson = new Gson();
        }

        if (!isInit) { //如果没有初始化过

            //友盟统计
            MobclickAgent.setScenarioType(context, MobclickAgent.EScenarioType.E_UM_NORMAL);

//            String channel = AnalyticsConfig.getChannel(context);
//            System.out.println("channel = " + channel);

            //bugly的报错统计
            CrashReport.initCrashReport(context, "70e46ec158", false);

            //初始化图片加载框架
            Fresco.initialize(context);

            //初始化分享的
            ShareSDK.initSDK(context);

            /**
             * 初始化融云
             */
            RongIM.init(context);

            /**
             * 设置用户信息的提供者，供 RongIM 调用获取用户名称和头像信息。
             * 回传的userId是聊天列表中的用户的id,然后去服务器请求我们的头像的名字
             *
             * @param userInfoProvider 用户信息提供者。
             * @param isCacheUserInfo  设置是否由 IMKit 来缓存用户信息。<br>
             *                         如果 App 提供的 UserInfoProvider
             *                         每次都需要通过网络请求用户数据，而不是将用户数据缓存到本地内存，会影响用户信息的加载速度；<br>
             *                         此时最好将本参数设置为 true，由 IMKit 将用户信息缓存到本地内存中。
             * @see UserInfoProvider
             */
            RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {

                /**
                 * 拿着userId去服务器请求这个用户的基本信息
                 *
                 * @param userId
                 * @return
                 */
                @Override
                public UserInfo getUserInfo(String userId) {
                    EventBus.getDefault().post(new ReFreshIMUserInfoEvent(userId));
                    //先返回一个默认的用户信息
                    return null;
                }

            }, true);

            isInit = true;

        }


    }

    /**
     * 退出app
     *
     * @param context
     */
    public static void exitApp(Context context) {
        //清空信息
        SPUtil.put(context, Constant.SP.im.TOKEN, "");
        SPUtil.put(context, Constant.RESULT_SESSION_ID, "");
        SPUtil.put(context, Constant.RESULT_OBJECT_ID, "");
        //融云的要断开连接
        RongIM.getInstance().disconnect();
        //极光也要停止推送
        JPushInterface.stopPush(context);
        Platform platform = ShareSDK.getPlatform(Wechat.NAME);
        platform.removeAccount();
        platform = ShareSDK.getPlatform(SinaWeibo.NAME);
        platform.removeAccount();
        platform = ShareSDK.getPlatform(QQ.NAME);
        platform.removeAccount();
    }

}
