package com.yoursecondworld.secondworld.modular.systemInfo.entity;

/**
 * Created by cxj on 2016/8/4.
 */
public class GameLabel {

    /* 主键id */
    private Integer id;

    /* 游戏的名字 */
    private String gameName;

    /* 游戏头像地址 */
    private String avatarAddress;

    /* 游戏头像地址的大图,用于在发布的界面展示 */
    private String bigAvatarAddress;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getAvatarAddress() {
        return avatarAddress;
    }

    public void setAvatarAddress(String avatarAddress) {
        this.avatarAddress = avatarAddress;
    }

    public String getBigAvatarAddress() {
        return bigAvatarAddress;
    }

    public void setBigAvatarAddress(String bigAvatarAddress) {
        this.bigAvatarAddress = bigAvatarAddress;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof GameLabel ? this.id.equals(((GameLabel) obj).id) : false;
    }

}
