package com.yoursecondworld.secondworld.common;

import com.yoursecondworld.secondworld.common.baseResult.BaseEntity;
import com.yoursecondworld.secondworld.common.event.SessionInvalidEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by cxj on 2016/9/19.
 */
public class ResultStatusFilter {

    /**
     * 如果是sessionId
     *
     * @param entity
     */
    public static void filter(BaseEntity entity) {
        if (entity != null && entity.getStatus() != null && entity.getStatus() == 2) {
            //如果是被顶了,就发送一个通知
            EventBus.getDefault().post(new SessionInvalidEvent());
        }
    }

}
