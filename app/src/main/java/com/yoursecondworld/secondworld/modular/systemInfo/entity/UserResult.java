package com.yoursecondworld.secondworld.modular.systemInfo.entity;

import com.yoursecondworld.secondworld.common.baseResult.BaseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cxj on 2016/9/19.
 */
public class UserResult extends BaseEntity {

    private NewUser user;

    private List<NewUser> users = new ArrayList<NewUser>();

    private boolean is_followed;

    private List<String> games = new ArrayList<String>();

    public List<NewUser> getUsers() {
        return users;
    }

    public void setUsers(List<NewUser> users) {
        this.users = users;
    }

    public boolean is_followed() {
        return is_followed;
    }

    public void setIs_followed(boolean is_followed) {
        this.is_followed = is_followed;
    }

    public List<String> getGames() {
        return games;
    }

    public void setGames(List<String> games) {
        this.games = games;
    }

    public NewUser getUser() {
        return user;
    }

    public void setUser(NewUser user) {
        this.user = user;
    }
}
