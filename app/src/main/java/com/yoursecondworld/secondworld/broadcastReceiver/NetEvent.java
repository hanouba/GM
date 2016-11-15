package com.yoursecondworld.secondworld.broadcastReceiver;

/**
 * Created by cxj on 2016/9/1.
 * 网络变化发送的消息实体对象
 */
public class NetEvent {

    /**
     * 网络无连接
     */
    public static final int NO_NET = 0;

    /**
     * 移动网络
     */
    public static final int MOBILE_NET = 1;

    /**
     * wifi网络
     */
    public static final int WIFI_NET = 2;

    /**
     * 网络的类型
     */
    private int netType = NO_NET;

    public int getNetType() {
        return netType;
    }

    public void setNetType(int netType) {
        this.netType = netType;
    }
}
