package com.yoursecondworld.secondworld.modular.statusNotification.enity;

import com.yoursecondworld.secondworld.common.baseResult.BaseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cxj on 2016/9/19.
 */
public class ZanListResult extends BaseEntity {

    private List<ZanListEntity> event = new ArrayList<ZanListEntity>();

    public List<ZanListEntity> getEvent() {
        return event;
    }

    public void setEvent(List<ZanListEntity> event) {
        this.event = event;
    }
}
