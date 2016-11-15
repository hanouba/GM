package com.yoursecondworld.secondworld.modular.systemInfo.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cxj on 2016/8/29.
 * 为了适应返回的数据
 */
public class NewUser {

    //用户的id
    private String user_id;

    //用户的昵称
    private String user_nickname;

    //用户的性别
    private String user_sex;

    //用户的头像
    private String user_head_image;

    //用户的简介
    private String user_introduction;

    //用户的生日
    private String user_birthday;

    /**
     * 关注数量j
     */
    private int user_follow_number;

    /**
     * 文章的数量
     */
    private int user_article_number;

    /**
     * 用户收藏的文章数量
     */
    private int user_collected_article_number;

    /**
     * 用户粉丝数量
     */
    private int user_fans_number;

    //扩展字段
    @SerializedName("is_followed")
    private boolean isFollow;

    /**
     * 当成isFans用就行了,表示是不是我的粉丝
     */
    private boolean is_friend;

    public boolean is_friend() {
        return is_friend;
    }

    public void setIs_friend(boolean is_friend) {
        this.is_friend = is_friend;
    }

    /**
     * 用户的游戏标签
     */
    private List<String> games = new ArrayList<String>();

    public NewUser() {
    }

    public NewUser(String user_id) {
        this.user_id = user_id;
    }

    public NewUser(String user_id, String user_nickname, String user_sex, String user_head_image) {
        this.user_id = user_id;
        this.user_nickname = user_nickname;
        this.user_sex = user_sex;
        this.user_head_image = user_head_image;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_nickname() {
        return user_nickname;
    }

    public void setUser_nickname(String user_nickname) {
        this.user_nickname = user_nickname;
    }

    public String getUser_sex() {
        return user_sex;
    }

    public void setUser_sex(String user_sex) {
        this.user_sex = user_sex;
    }

    public String getUser_head_image() {
        return user_head_image;
    }

    public void setUser_head_image(String user_head_image) {
        this.user_head_image = user_head_image;
    }

    public int getUser_follow_number() {
        return user_follow_number;
    }

    public void setUser_follow_number(int user_follow_number) {
        this.user_follow_number = user_follow_number;
    }

    public int getUser_article_number() {
        return user_article_number;
    }

    public void setUser_article_number(int user_article_number) {
        this.user_article_number = user_article_number;
    }

    public int getUser_fans_number() {
        return user_fans_number;
    }

    public void setUser_fans_number(int user_fans_number) {
        this.user_fans_number = user_fans_number;
    }

    public int getUser_collected_article_number() {
        return user_collected_article_number;
    }

    public void setUser_collected_article_number(int user_collected_article_number) {
        this.user_collected_article_number = user_collected_article_number;
    }

    public String getUser_introduction() {
        return user_introduction;
    }

    public void setUser_introduction(String user_introduction) {
        this.user_introduction = user_introduction;
    }

    public String getUser_birthday() {
        return user_birthday;
    }

    public void setUser_birthday(String user_birthday) {
        this.user_birthday = user_birthday;
    }

    public boolean isFollow() {
        return isFollow;
    }

    public void setFollow(boolean follow) {
        isFollow = follow;
    }

    public List<String> getGames() {
        return games;
    }

    public void setGames(List<String> games) {
        this.games = games;
    }
}
