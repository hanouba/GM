package com.yoursecondworld.secondworld.modular.budget.view;

import com.yoursecondworld.secondworld.modular.mvp.view.IBaseMasterView;

/**
 * Created by cxj on 2016/11/8.
 */
public interface IMasterBudgetView extends IBaseMasterView {

    /**
     * 获取输入的金额
     *
     * @return
     */
    int getMoney();

    /**
     * 获取输入的时间
     *
     * @return
     */
    int getTime();
}
