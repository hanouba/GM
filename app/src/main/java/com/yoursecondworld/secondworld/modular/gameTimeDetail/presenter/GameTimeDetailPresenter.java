package com.yoursecondworld.secondworld.modular.gameTimeDetail.presenter;

import com.yoursecondworld.secondworld.modular.gameTimeDetail.view.IGameTimeDetailView;
import com.yoursecondworld.secondworld.modular.mvp.presneter.BaseMasterPresenter;

/**
 * Created by cxj on 2016/11/9.
 */
public class GameTimeDetailPresenter extends BaseMasterPresenter {

    private IGameTimeDetailView view;

    public GameTimeDetailPresenter(IGameTimeDetailView view) {
        super(view);
        this.view = view;
    }

}
