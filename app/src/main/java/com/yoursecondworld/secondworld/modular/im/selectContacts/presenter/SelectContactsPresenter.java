package com.yoursecondworld.secondworld.modular.im.selectContacts.presenter;

import android.os.Handler;
import android.os.Message;

import com.yoursecondworld.secondworld.modular.im.selectContacts.view.ISelectContactsView;
import com.yoursecondworld.secondworld.modular.systemInfo.entity.User;

import java.util.List;

/**
 * Created by cxj on 2016/8/17.
 */
public class SelectContactsPresenter {

    private Handler h = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            selectContactsView.onLoadSuccess(users);
        }
    };

    private ISelectContactsView selectContactsView;

    /**
     * 待整理,整理好后才调用ISelectContactsView中的显示方法
     */
    private List<User> users;

    public SelectContactsPresenter(ISelectContactsView selectContactsView) {
        this.selectContactsView = selectContactsView;
    }

    /**
     * 获取互相关注的列表
     */
    public void getFollowEachOther() {

    }

}
