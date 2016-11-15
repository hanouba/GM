package com.yoursecondworld.secondworld.modular.myFans.entity;

/**
 * Created by cxj on 2016/7/15.
 * 粉丝对应的实体对象
 */
public class FansEntity {

    //当有这个属性的时候不会有其它的属性
    private String tag;

    //头像地址
    private String avatarAddress;

    //粉丝的名称
    private String name;

    //粉丝的简介
    private String desc;

    //是否关注这个粉丝了
    private boolean isAttention;

    public FansEntity() {
    }

    public FansEntity(boolean isAttention, String avatarAddress, String name, String desc) {
        this.isAttention = isAttention;
        this.avatarAddress = avatarAddress;
        this.name = name;
        this.desc = desc;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getAvatarAddress() {
        return avatarAddress;
    }

    public void setAvatarAddress(String avatarAddress) {
        this.avatarAddress = avatarAddress;
    }

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

    public boolean isAttention() {
        return isAttention;
    }

    public void setAttention(boolean attention) {
        isAttention = attention;
    }
}
