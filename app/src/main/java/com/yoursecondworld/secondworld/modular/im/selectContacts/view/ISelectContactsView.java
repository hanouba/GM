package com.yoursecondworld.secondworld.modular.im.selectContacts.view;

import com.yoursecondworld.secondworld.modular.mvp.view.IBaseView;
import com.yoursecondworld.secondworld.modular.systemInfo.entity.User;

import java.util.List;

/**
 * Created by cxj on 2016/8/17.
 */
public interface ISelectContactsView extends IBaseView {

    /**
     * 数据加载成功的处理
     *
     * @param users
     */
    void onLoadSuccess(List<User> users);

}
