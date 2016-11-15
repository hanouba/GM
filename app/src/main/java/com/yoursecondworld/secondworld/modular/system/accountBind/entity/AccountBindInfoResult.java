package com.yoursecondworld.secondworld.modular.system.accountBind.entity;

import com.yoursecondworld.secondworld.common.baseResult.BaseEntity;

/**
 * Created by cxj on 2016/9/17.
 */
public class AccountBindInfoResult extends BaseEntity {

    private boolean wechat;
    private boolean weibo;
    private boolean qq;
    private boolean phone;

    public boolean isWechat() {
        return wechat;
    }

    public void setWechat(boolean wechat) {
        this.wechat = wechat;
    }

    public boolean isWeibo() {
        return weibo;
    }

    public void setWeibo(boolean weibo) {
        this.weibo = weibo;
    }

    public boolean isQq() {
        return qq;
    }

    public void setQq(boolean qq) {
        this.qq = qq;
    }

    public boolean isPhone() {
        return phone;
    }

    public void setPhone(boolean phone) {
        this.phone = phone;
    }
}
