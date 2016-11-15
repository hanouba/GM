package com.yoursecondworld.secondworld.modular.statusNotification.enity;

/**
 * Created by cxj on 2016/9/19.
 */
public class CommentsListEntity {

    /**
     * “_id”:”1234567abc”, //事件id
     “event_type”:0,
     “user_id”:”3412adfda”,
     “user_nickname”:”阿布”,
     “content”：“好啊好啊”
     “user_head_image”:”用户头像”,
     “time”:””,//时间
     “source_article_id”:”1234567abc”,//原文章id
     “source_article_content”:”” //原文章内容
     */

    //事件id
    private String _id;

    private Integer event_type;

    private String user_id;

    private String user_nickname;
    private String content;
    private String user_head_image;
    private String time;
    private String source_article_id;
    private String source_article_content;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Integer getEvent_type() {
        return event_type;
    }

    public void setEvent_type(Integer event_type) {
        this.event_type = event_type;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUser_head_image() {
        return user_head_image;
    }

    public void setUser_head_image(String user_head_image) {
        this.user_head_image = user_head_image;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSource_article_id() {
        return source_article_id;
    }

    public void setSource_article_id(String source_article_id) {
        this.source_article_id = source_article_id;
    }

    public String getSource_article_content() {
        return source_article_content;
    }

    public void setSource_article_content(String source_article_content) {
        this.source_article_content = source_article_content;
    }
}
