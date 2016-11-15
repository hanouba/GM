package com.yoursecondworld.secondworld.broadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import xiaojinzi.base.android.log.L;
import xiaojinzi.base.android.os.T;

/**
 * Created by cxj on 2016/7/23.
 * 监听屏幕打开或者关闭的
 */
public class NetStateChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobileInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifiInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
//        NetworkInfo activeInfo = manager.getActiveNetworkInfo();
//        Toast.makeText(context, "mobile:" + mobileInfo.isConnected() + "\n" + "wifi:" + wifiInfo.isConnected(), Toast.LENGTH_SHORT).show();

        //创建传递的事件
        NetEvent ne = new NetEvent();

        if (wifiInfo.isConnected()) {
            ne.setNetType(NetEvent.WIFI_NET);
        }
        if (mobileInfo.isConnected()) {
            ne.setNetType(NetEvent.MOBILE_NET);
        }

        //发送消息给其他组建
        EventBus.getDefault().post(ne);

    }

}
