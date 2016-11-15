package com.yoursecondworld.secondworld.common;

/**
 * Created by cxj on 2016/9/7.
 * 用户信息保存和获取的工具类
 * 同时用户信息也从MyApp中迁移到这里
 */
public class UserInfoStoreUtil {

    private static UserInfoStoreUtil util;

    public static UserInfoStoreUtil getInstance() {
        if (util == null) {
            util = new UserInfoStoreUtil();
        }
        return util;
    }

//    public void save

}
