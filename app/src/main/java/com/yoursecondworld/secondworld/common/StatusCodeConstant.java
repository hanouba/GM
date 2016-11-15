package com.yoursecondworld.secondworld.common;

/**
 * Created by xiaojinzi on 2016/8/25.
 * desc:状态码的集中解释
 */
public class StatusCodeConstant {

    /**
     * 表示请求正常
     */
    public static final int NORMAL = 0;

    /**
     * 普通的错误,比如账号密码错误之类的
     */
    public static final int NORMAL_ERROR = 1;

    /**
     * Token失效的错误,是一个特殊错误
     * 此时客户端需要退出登录
     */
    public static final int TOKENINVALID = 2;

    /**
     * 禁止登陆
     */
    public static final int NOACCESS = 3;

}
