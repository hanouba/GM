package com.yoursecondworld.secondworld.modular.dynamicsDetail.entity;

import com.yoursecondworld.secondworld.common.baseResult.BaseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cxj on 2016/9/19.
 */
public class DynamicsCommentResult extends BaseEntity {

    private List<DynamicsComment> comments = new ArrayList<DynamicsComment>();

    public List<DynamicsComment> getComments() {
        return comments;
    }

    public void setComments(List<DynamicsComment> comments) {
        this.comments = comments;
    }
}
