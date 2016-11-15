package com.yoursecondworld.secondworld.modular.search.ui;

import com.yoursecondworld.secondworld.modular.dynamics.entity.NewDynamics;
import com.yoursecondworld.secondworld.modular.mvp.view.IBaseView;
import com.yoursecondworld.secondworld.modular.systemInfo.entity.NewUser;

import java.util.List;

/**
 * Created by cxj on 2016/9/3.
 */
public interface ISearchView extends IBaseView {

    /**
     * 获取搜索的内容
     *
     * @return
     */
    String getSearchContent();

    /**
     * 搜索动态成功之后调用
     *
     * @param newDynamicses
     */
    void onSearchDynamicsSuccess(List<NewDynamics> newDynamicses);

    void onSearchMoreDynamicsSuccess(List<NewDynamics> newDynamicses);


    /**
     * 搜索用户成功调用
     *
     * @param newUsers
     */
    void onSearchUsersSuccess(List<NewUser> newUsers);

    void onSearchMoreUsersSuccess(List<NewUser> newUsers);

    void savePass(String pass);

    String getPass();
}
