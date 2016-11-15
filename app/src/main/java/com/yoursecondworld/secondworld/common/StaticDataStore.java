package com.yoursecondworld.secondworld.common;

import android.content.Context;


import com.yoursecondworld.secondworld.modular.systemInfo.entity.NewUser;

/**
 * Created by cxj on 2016/9/9.
 * 静态的数据的存放地方
 */
public class StaticDataStore {

    /**
     * 用户的信息
     */
    public static NewUser newUser;

    /**
     * app的sessionID
     */
    public static String session_id = null;

    /**
     * 临时的文件
     */
    public static Object tmpData;

    /**
     * 初始化基础
     *
     */
    static {
        newUser = new NewUser();
    }

    /**
     * 释放资源
     */
    public static void releaseData() {
        newUser = null;
        tmpData = null;
    }

}
