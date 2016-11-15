package com.yoursecondworld.secondworld.modular.prepareModule.selectPlayer.entity;

/**
 * Created by cxj on 2016/7/7.
 */
public class PlayerEntity {

    //用户名
    private String name;

    //介绍
    private String desc;

    //头像地址
    private String avatar;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
