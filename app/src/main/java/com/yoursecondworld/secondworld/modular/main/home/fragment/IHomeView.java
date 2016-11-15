package com.yoursecondworld.secondworld.modular.main.home.fragment;

import com.yoursecondworld.secondworld.modular.dynamics.entity.DynamicsResult;
import com.yoursecondworld.secondworld.modular.dynamics.entity.NewDynamics;
import com.yoursecondworld.secondworld.modular.mvp.view.IBaseView;

import java.util.List;

/**
 * Created by cxj on 2016/8/29.
 */
public interface IHomeView extends IBaseView {

    /**
     * 获取游戏标识
     *
     * @return
     */
    String getGameTag();

    void onLoadDynamicsSuccess(DynamicsResult dynamicsResult);

    void onLoadFollowDynamicsSuccess(DynamicsResult dynamicsResult);

    /**
     * 获取分页的标识
     *
     * @return
     */
    String getPass();

    void onLoadMoreDynamicsSuccess(DynamicsResult dynamicsResult);

    void onLoadMoreFollowDynamicsSuccess(DynamicsResult dynamicsResult);

    void savePass(String pass);
}
