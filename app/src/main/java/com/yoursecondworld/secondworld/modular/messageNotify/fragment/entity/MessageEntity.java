package com.yoursecondworld.secondworld.modular.messageNotify.fragment.entity;

import com.yoursecondworld.secondworld.common.baseResult.BaseEntity;

/**
 * Created by cxj on 2016/9/24.
 */
public class MessageEntity extends BaseEntity {


    /**
     * user_nickname : 番茄你个西红柿
     * time : 2016-09-24 13:47:00.157894
     * event_type : 0
     * user_head_image : http://gm-head-shot.img-cn-hangzhou.aliyuncs.com/57de3083fd91495cf81e972d/8a162236-5289-470c-ad55-c1408a90a9e5.jpg
     * aoc : 57de3083fd91495cf81e972d
     * user_id : 57de3083fd91495cf81e972d
     * content : hdhsj
     * source_article_content : 爸爸妈妈的就是学校
     * source_article_id : 57e5d8a4fd91493e85f53ae9
     */

    private LastEventBean last_event;

    /**
     * last_event : {"user_nickname":"番茄你个西红柿","time":"2016-09-24 13:47:00.157894","event_type":0,"user_head_image":"http://gm-head-shot.img-cn-hangzhou.aliyuncs.com/57de3083fd91495cf81e972d/8a162236-5289-470c-ad55-c1408a90a9e5.jpg","aoc":"57de3083fd91495cf81e972d","user_id":"57de3083fd91495cf81e972d","content":"hdhsj","source_article_content":"爸爸妈妈的就是学校","source_article_id":"57e5d8a4fd91493e85f53ae9"}
     * status : 0
     * number : 33
     */

    private int number;

    public LastEventBean getLast_event() {
        return last_event;
    }

    public void setLast_event(LastEventBean last_event) {
        this.last_event = last_event;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public static class LastEventBean {
        private String user_nickname;
        private String time;
        private int event_type;
        private String user_head_image;
        private String aoc;
        private String user_id;
        private String content;
        private String source_article_content;
        private String source_article_id;

        public String getUser_nickname() {
            return user_nickname;
        }

        public void setUser_nickname(String user_nickname) {
            this.user_nickname = user_nickname;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public int getEvent_type() {
            return event_type;
        }

        public void setEvent_type(int event_type) {
            this.event_type = event_type;
        }

        public String getUser_head_image() {
            return user_head_image;
        }

        public void setUser_head_image(String user_head_image) {
            this.user_head_image = user_head_image;
        }

        public String getAoc() {
            return aoc;
        }

        public void setAoc(String aoc) {
            this.aoc = aoc;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getSource_article_content() {
            return source_article_content;
        }

        public void setSource_article_content(String source_article_content) {
            this.source_article_content = source_article_content;
        }

        public String getSource_article_id() {
            return source_article_id;
        }

        public void setSource_article_id(String source_article_id) {
            this.source_article_id = source_article_id;
        }
    }
}
