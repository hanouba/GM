package com.yoursecondworld.secondworld.modular.main.eventEntity;

/**
 * Created by cxj on 2016/7/20.
 * 在分享的菜单弹出来之后,选择了一个平台分享之后,这个事件用于popupWindow向MainAct中传递点击是哪一个平台
 */
public class SharePlatformEntity {

    public static final String FROM_HOME = "from_home";
    public static final String FROM_FIND = "from_find";
    public static final String FROM_COLLECT = "from_collect";
    public static final String FROM_USERDETAIL = "from_userdetail";

    /**
     * 复制链接
     */
    public static final int DEFAULT_PLATFORM = 0;

    /**
     * 微信的平台
     */
    public static final int WEIXIN_PLATFORM = 1;

    /**
     * 微博的平台
     */
    public static final int WEIBO_PLATFORM = 2;

    /**
     * QQ的平台
     */
    public static final int QQ_PLATFORM = 3;

    /**
     * 默认是复制链接的行为
     */
    private int platform = DEFAULT_PLATFORM;

    public String from;

    public SharePlatformEntity() {
    }

    public SharePlatformEntity(int platform) {
        this.platform = platform;
    }

    public int getPlatform() {
        return platform;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }
}
