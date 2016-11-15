package com.yoursecondworld.secondworld.modular.prepareModule.login.entity;

import com.yoursecondworld.secondworld.common.baseResult.BaseEntity;
import com.yoursecondworld.secondworld.modular.systemInfo.entity.NewUser;

/**
 * Created by cxj on 2016/11/5.
 */
public class LoginResult extends BaseEntity {

    private NewUser user;

    private boolean full_information;

    private String user_id;

    private String session_id;

    public NewUser getUser() {
        return user;
    }

    public void setUser(NewUser user) {
        this.user = user;
    }

    public boolean isFull_information() {
        return full_information;
    }

    public void setFull_information(boolean full_information) {
        this.full_information = full_information;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }
}
