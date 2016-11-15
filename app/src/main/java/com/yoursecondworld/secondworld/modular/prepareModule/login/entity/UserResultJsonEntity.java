package com.yoursecondworld.secondworld.modular.prepareModule.login.entity;

import com.yoursecondworld.secondworld.modular.systemInfo.entity.User;

/**
 * Created by cxj on 2016/8/4.
 * 包裹User对象的json数据对应的实体对象
 */
public class UserResultJsonEntity {

    private User data;

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }

}
