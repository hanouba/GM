package com.yoursecondworld.secondworld.modular.main.find.fragment;

import com.yoursecondworld.secondworld.modular.dynamics.entity.DynamicsResult;
import com.yoursecondworld.secondworld.modular.dynamics.entity.NewDynamics;


import com.yoursecondworld.secondworld.modular.main.find.entity.AdvResult;
import com.yoursecondworld.secondworld.modular.mvp.view.IBaseView;

import com.yoursecondworld.secondworld.modular.systemInfo.entity.UserResult;

import java.util.List;

/**
 * Created by cxj on 2016/8/17.
 */
public interface IFindView extends IBaseView {

    void onLoadAdvSuccess(AdvResult advResult);

    void onLoadPopupLarStarSuccess(UserResult userResult);

    void onloadHotDynamicsSuccess(DynamicsResult entity);

    void onloadMoreHotDynamicsSuccess(DynamicsResult entity);

    String getPass();

    void savePass(String pass);
}
