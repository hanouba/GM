package com.yoursecondworld.secondworld.modular.statusNotification.enity;

import com.yoursecondworld.secondworld.common.baseResult.BaseEntity;

import java.util.List;

/**
 * Created by cxj on 2016/9/19.
 */
public class CommentsListResult extends BaseEntity {

    private List<CommentsListEntity> event;

    public List<CommentsListEntity> getEvent() {
        return event;
    }

    public void setEvent(List<CommentsListEntity> event) {
        this.event = event;
    }
}
