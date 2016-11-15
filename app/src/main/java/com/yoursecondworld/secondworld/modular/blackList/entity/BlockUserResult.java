package com.yoursecondworld.secondworld.modular.blackList.entity;

import com.yoursecondworld.secondworld.common.baseResult.BaseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cxj on 2016/9/19.
 */
public class BlockUserResult extends BaseEntity {

    private List<BlockUser> users = new ArrayList<BlockUser>();

    public List<BlockUser> getUsers() {
        return users;
    }

    public void setUsers(List<BlockUser> users) {
        this.users = users;
    }
}
