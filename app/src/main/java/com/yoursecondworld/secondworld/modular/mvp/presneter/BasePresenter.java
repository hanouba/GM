package com.yoursecondworld.secondworld.modular.mvp.presneter;

import com.yoursecondworld.secondworld.modular.mvp.view.IView;

/**
 * Created by cxj on 2016/10/23.
 */
public class BasePresenter {

    private IView iView;

    public BasePresenter(IView iView) {
        this.iView = iView;
    }



}
