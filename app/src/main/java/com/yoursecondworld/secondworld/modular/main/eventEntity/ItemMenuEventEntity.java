package com.yoursecondworld.secondworld.modular.main.eventEntity;

/**
 * Created by cxj on 2016/7/25.
 * 点击item中的下拉菜单传递的事件的实体
 */
public class ItemMenuEventEntity {

    //主页
    public static final int FROM_HOME = 1;

    //发现页
    public static final int FROM_FIND = 2;

    //收藏页面
    public static final int FROM_COLLECT = 3;

    //所有发布
    public static final int FROM_ALL_RELEASE = 4;

    //动态详情
    public static final int FROM_DYNAMICS_DETAIL = 5;

    /**
     * 事件从哪里来
     */
    public int from;

    /**
     * 动态的id,用户点击菜单条目的时候的处理
     * 举报动态的时候用
     */
    public String dynamicsId;

    /**
     * 举报用户用的
     */
    public String userId;
}
