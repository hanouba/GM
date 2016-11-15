package com.yoursecondworld.secondworld.modular.dynamics.entity;

import com.yoursecondworld.secondworld.modular.systemInfo.entity.NewUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cxj on 2016/8/29.
 */
public class NewDynamics {

    /**
     * 这个id目前在数据库中使用
     */
    private Integer id;

    /**
     * 动态id
     */
    private String _id;

    /**
     * 游戏名称
     */
    private String game_tag;

    /**
     * 游戏类型
     */
    private String type_tag;

    /**
     * 浏览次数
     */
    private int view_number;

    /**
     * 评论数量
     */
    private int comment_number;

    /**
     * 点赞数量
     */
    private int liked_number;

    /**
     * 是否收藏
     */
    private boolean is_collected;

    /**
     * 是否点赞
     */
    private boolean is_liked;

    /**
     * 内容
     */
    private String content;

    /**
     * 图片的额外信息
     */
    private String infor_type;

    /**
     * 发布的人
     */
    private NewUser user;

    /**
     * 视频播放地址
     */
    private String video_url;

    /**
     * 第一针的图片
     */
    private String video_cover_picture;

    /**
     * 视频的地址,用于上传用,播放地址不是这个
     */
    private List<String> video_list = new ArrayList<String>();

    /**
     * 图片列表
     */
    private List<String> picture_list = new ArrayList<String>();

    /**
     * 剁手的时候的金额
     */
    private Integer money;

    /**
     * 时间戳
     */
    private String time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getGame_tag() {
        return game_tag;
    }

    public void setGame_tag(String game_tag) {
        this.game_tag = game_tag;
    }

    public String getType_tag() {
        return type_tag;
    }

    public void setType_tag(String type_tag) {
        this.type_tag = type_tag;
    }

    public int getView_number() {
        return view_number;
    }

    public void setView_number(int view_number) {
        this.view_number = view_number;
    }

    public int getComment_number() {
        return comment_number;
    }

    public void setComment_number(int comment_number) {
        this.comment_number = comment_number;
    }

    public int getLiked_number() {
        return liked_number;
    }

    public void setLiked_number(int liked_number) {
        this.liked_number = liked_number;
    }

    public boolean is_collected() {
        return is_collected;
    }

    public void setIs_collected(boolean is_collected) {
        this.is_collected = is_collected;
    }

    public boolean is_liked() {
        return is_liked;
    }

    public void setIs_liked(boolean is_liked) {
        this.is_liked = is_liked;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getInfor_type() {
        return infor_type;
    }

    public void setInfor_type(String infor_type) {
        this.infor_type = infor_type;
    }

    public NewUser getUser() {
        return user;
    }

    public void setUser(NewUser user) {
        this.user = user;
    }

    public List<String> getVideo_list() {
        return video_list;
    }

    public void setVideo_list(List<String> video_list) {
        this.video_list = video_list;
    }

    public List<String> getPicture_list() {
        return picture_list;
    }

    public void setPicture_list(List<String> picture_list) {
        this.picture_list = picture_list;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public String getVideo_cover_picture() {
        return video_cover_picture;
    }

    public void setVideo_cover_picture(String video_cover_picture) {
        this.video_cover_picture = video_cover_picture;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }
}
