package com.yoursecondworld.secondworld.modular.dynamics.entity;

import com.yoursecondworld.secondworld.modular.systemInfo.entity.GameLabel;
import com.yoursecondworld.secondworld.modular.systemInfo.entity.User;

/**
 * Created by cxj on 2016/7/10.
 * 动态内容的对应的实体对象
 */
public class DynamicsContentEntity {

    /**
     * 六个话题
     */
    public static final String[] TOPICS = {"攻略心得", "游戏八卦", "心情吐槽", "show time", "剁手时刻", "游戏测评"};

    /**
     * 标志这个动态是没有任何类型的
     */
    public static final int NO_TYPE_DYNAMICS = 0;

    /**
     * 标志这个类型是图片的动态
     */
    public static final int IMAGE_TYPE_DYNAMICS = 1;

    /**
     * 标志这个动态是视频的动态
     */
    public static final int VIDEO_TYPE_DYNAMICS = 2;

    /**
     * 标志这个动态是游戏长文的动态
     */
    public static final int GAMELONGTEXT_TYPE_DYNAMICS = 3;


    /**
     * 图片路径之间的分隔符
     */
    public static final String IMAGE_SPLIT_CHAR = ";";

    /**
     * 动态的流水id
     */
    private Integer id;

    /**
     * 动态类型<br/>
     * 0:表示只有问题的很普通的动态{@link DynamicsContentEntity#NO_TYPE_DYNAMICS}<br/>
     * 1:表示展示图片的{@link DynamicsContentEntity#IMAGE_TYPE_DYNAMICS}<br/>
     * 2:展示视频的{@link DynamicsContentEntity#VIDEO_TYPE_DYNAMICS}<br/>
     * 3:展示游戏长文的{@link DynamicsContentEntity#GAMELONGTEXT_TYPE_DYNAMICS}<br/>
     * 这个数值不是由客户端传过来的时候,而是服务端自己判断然后赋值的<br/>
     */
    private Integer dynamicsType = NO_TYPE_DYNAMICS;

    /**
     * 发布动态的人
     */
    private User user;

    /**
     * 动态的游戏标签
     */
    private GameLabel gameLabel;

    /**
     * 动态的话题<br/>
     * 1:攻略心得<br/>
     * 2:游戏八卦<br/>
     * 3:心情吐槽<br/>
     * 4:show time <br/>
     * 5:剁手时刻<br/>
     * 6:游戏测评<br/>
     */
    private Integer topic;

    /**
     * 剁手的金额
     */
    private Integer money;

    /**
     * 发布的动态的内容
     */
    private String content;

    /**
     * 动态的图片<br/>
     * 每个图片用";"隔开,一个变量表示多个图片地址
     * 如果用在草稿箱中,这里存放的是多个图片的本地路径
     */
    private String images;

    /**
     * 视频的地址,和图片只能有一个,有了图片就不能有视频,反之有视频就不能有图片
     * 如果用在草稿箱中,这里存放的是本地的视频的路径
     */
    private String videoUrl;

    /**
     * 发布的时间
     */
    private long postTime;

    /**
     * 点赞数量
     */
    private int zanNum;

    /**
     * 评论数量
     */
    private int commentNum;

    /**
     * 额外的信息
     */
    private String extraInfo;

    //==========================================================================

    /**
     * 该用户是否收藏了这个动态
     * -1表示没有收藏,反之不是-1表示已经收藏了,并且是关联表的id
     */
    private int isCollect = -1;

    /**
     * 是否点赞
     * -1表示没有点赞,反之不是-1表示已经收点赞了,并且是关联表的id
     */
    private int isZan = -1;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDynamicsType() {
        return dynamicsType;
    }

    public void setDynamicsType(Integer dynamicsType) {
        this.dynamicsType = dynamicsType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public GameLabel getGameLabel() {
        return gameLabel;
    }

    public void setGameLabel(GameLabel gameLabel) {
        this.gameLabel = gameLabel;
    }

    public Integer getTopic() {
        return topic;
    }

    public void setTopic(Integer topic) {
        this.topic = topic;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public long getPostTime() {
        return postTime;
    }

    public void setPostTime(long postTime) {
        this.postTime = postTime;
    }

    public int getZanNum() {
        return zanNum;
    }

    public void setZanNum(int zanNum) {
        this.zanNum = zanNum;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }

    //=====================================================================================

    public int getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(int isCollect) {
        this.isCollect = isCollect;
    }

    public int getIsZan() {
        return isZan;
    }

    public void setIsZan(int isZan) {
        this.isZan = isZan;
    }

}
