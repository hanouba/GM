package com.yoursecondworld.secondworld.modular.commonFunction.loadMyDynamics.view;

import com.yoursecondworld.secondworld.modular.dynamics.entity.NewDynamics;
import com.yoursecondworld.secondworld.modular.mvp.view.IBaseView;

import java.util.List;

/**
 * Created by cxj on 2016/9/2.
 * 加载我的动态的视图层
 */
public interface ILoadMyDynamicsView extends IBaseView {

    /**
     * 获取目标用户的id
     *
     * @return
     */
    String getTargetUserId();

    /**
     * 加载成功调用
     *
     * @param newDynamicses
     */
    void onLoadMyDynamicsSuccess(List<NewDynamics> newDynamicses);

    /**
     * 删除动态成功
     *
     * @param postion
     */
    void onDeleteDynamicsSuccess(int postion);

    /**
     * 保存分页标识
     *
     * @param pass
     */
    void savePass(String pass);

    /**
     * 获取分页的标识
     *
     * @return
     */
    String getPass();

    void onLoadMoreMyDynamicsSuccess(List<NewDynamics> newDynamicses);
}
