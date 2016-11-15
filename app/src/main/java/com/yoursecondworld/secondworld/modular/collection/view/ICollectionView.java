package com.yoursecondworld.secondworld.modular.collection.view;

import com.yoursecondworld.secondworld.modular.dynamics.entity.DynamicsContentEntity;
import com.yoursecondworld.secondworld.modular.dynamics.entity.NewDynamics;
import com.yoursecondworld.secondworld.modular.mvp.view.IBaseView;

import java.util.List;

/**
 * Created by cxj on 2016/8/12.
 */
public interface ICollectionView extends IBaseView {

    /**
     * 加载数据成功的处理
     *
     * @param newDynamicses
     */
    void onLoadDataSuccess(List<NewDynamics> newDynamicses);

    String getPass();

    void onLoadMoreDataSuccess(List<NewDynamics> newDynamicses);

    void savePass(String pass);
}
