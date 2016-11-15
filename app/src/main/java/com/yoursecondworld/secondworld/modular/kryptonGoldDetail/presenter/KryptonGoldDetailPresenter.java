package com.yoursecondworld.secondworld.modular.kryptonGoldDetail.presenter;

import com.yoursecondworld.secondworld.modular.kryptonGoldDetail.view.IKryptonGoldDetailView;
import com.yoursecondworld.secondworld.modular.mvp.presneter.BaseMasterPresenter;

/**
 * Created by cxj on 2016/11/9.
 */
public class KryptonGoldDetailPresenter extends BaseMasterPresenter {

    private IKryptonGoldDetailView view;

    public KryptonGoldDetailPresenter(IKryptonGoldDetailView view) {
        super(view);
        this.view = view;
    }

}
