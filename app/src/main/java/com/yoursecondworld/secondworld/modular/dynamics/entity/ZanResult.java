package com.yoursecondworld.secondworld.modular.dynamics.entity;

import com.yoursecondworld.secondworld.common.baseResult.BaseEntity;

/**
 * Created by cxj on 2016/9/19.
 */
public class ZanResult extends BaseEntity {

    /**
     * 点赞的数量
     */
    private int liked_number;

    public int getLiked_number() {
        return liked_number;
    }

    public void setLiked_number(int liked_number) {
        this.liked_number = liked_number;
    }
}
