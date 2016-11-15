package com.yoursecondworld.secondworld.modular.commonFunction.searchUserDynamics.view;

import com.yoursecondworld.secondworld.modular.dynamics.entity.NewDynamics;
import com.yoursecondworld.secondworld.modular.mvp.view.IBaseView;

import java.util.List;

/**
 * Created by cxj on 2016/9/12.
 */
public interface ISearchUserDynamicsView extends IBaseView {
    String getSearchContent();

    void disPlay(List<NewDynamics> dynamicses);

    void disPlayMore(List<NewDynamics> dynamicses);

    void savePass(String pass);

    String getPass();
}
