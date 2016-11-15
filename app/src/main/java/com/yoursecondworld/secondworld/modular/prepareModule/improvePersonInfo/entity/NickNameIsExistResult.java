package com.yoursecondworld.secondworld.modular.prepareModule.improvePersonInfo.entity;

import com.yoursecondworld.secondworld.common.baseResult.BaseEntity;

/**
 * Created by cxj on 2016/9/20.
 */
public class NickNameIsExistResult extends BaseEntity {

    private boolean nickname_exist;

    public boolean isNickname_exist() {
        return nickname_exist;
    }

    public void setNickname_exist(boolean nickname_exist) {
        this.nickname_exist = nickname_exist;
    }
}
