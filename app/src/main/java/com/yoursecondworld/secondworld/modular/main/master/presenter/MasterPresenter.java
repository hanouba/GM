package com.yoursecondworld.secondworld.modular.main.master.presenter;

import com.yoursecondworld.secondworld.modular.main.master.fragment.IMasterFragmentView;
import com.yoursecondworld.secondworld.modular.mvp.presneter.BaseMasterPresenter;

/**
 * Created by cxj on 2016/11/9.
 */
public class MasterPresenter extends BaseMasterPresenter {

    private IMasterFragmentView view;

    public MasterPresenter(IMasterFragmentView view) {
        super(view);
        this.view = view;
    }

    @Override
    public void getMasterInfoByMonth(int year, int month) {
        super.getMasterInfoByMonth(year, month);
    }
}
