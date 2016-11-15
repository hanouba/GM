package com.yoursecondworld.secondworld.modular.blackList.entity;

/**
 * Created by cxj on 2016/9/12.
 */
public class BlockUser {

    private String user_id;
    private String user_introduction;
    private String user_nickname;
    private String blocked_time;
    private String user_head_image;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_introduction() {
        return user_introduction;
    }

    public void setUser_introduction(String user_introduction) {
        this.user_introduction = user_introduction;
    }

    public String getUser_nickname() {
        return user_nickname;
    }

    public void setUser_nickname(String user_nickname) {
        this.user_nickname = user_nickname;
    }

    public String getBlocked_time() {
        return blocked_time;
    }

    public void setBlocked_time(String blocked_time) {
        this.blocked_time = blocked_time;
    }

    public String getUser_head_image() {
        return user_head_image;
    }

    public void setUser_head_image(String user_head_image) {
        this.user_head_image = user_head_image;
    }
}
