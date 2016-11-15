package com.yoursecondworld.secondworld.modular.dynamicsDetail.entity;

import com.yoursecondworld.secondworld.modular.systemInfo.entity.NewUser;
import com.yoursecondworld.secondworld.modular.systemInfo.entity.User;

/**
 * Created by cxj on 2016/7/13.
 * 封装详情页的评论信息,对应列表中的一个条目,头部的动态详情不算
 */
public class DynamicsComment {

    /**
     * 评论的id
     */
    private String comment_id;

    /**
     * 评论的内容
     */
    private String content;

    /**
     * 评论者
     */
    private NewUser user;

    /**
     * 评论针对的用户,可能是楼主,可能是层主,如果是楼主没有@xxx
     */
    private User targetUser;

    /**
     * 点赞数量
     */
    private Integer liked_number;

    /**
     * 是否点赞
     */
    private boolean is_liked;

    /**
     * 评论时间
     */
    private String time;

    public String getComment_id() {
        return comment_id;
    }

    public void setComment_id(String comment_id) {
        this.comment_id = comment_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public NewUser getUser() {
        return user;
    }

    public void setUser(NewUser user) {
        this.user = user;
    }

    public User getTargetUser() {
        return targetUser;
    }

    public void setTargetUser(User targetUser) {
        this.targetUser = targetUser;
    }

    public Integer getLiked_number() {
        return liked_number;
    }

    public void setLiked_number(Integer liked_number) {
        this.liked_number = liked_number;
    }

    public boolean is_liked() {
        return is_liked;
    }

    public void setIs_liked(boolean is_liked) {
        this.is_liked = is_liked;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
