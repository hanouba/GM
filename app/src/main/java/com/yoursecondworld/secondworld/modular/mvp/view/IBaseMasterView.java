package com.yoursecondworld.secondworld.modular.mvp.view;

import com.yoursecondworld.secondworld.commonBean.MasterInfo;

/**
 * Created by cxj on 2016/11/8.
 */
public interface IBaseMasterView extends IBaseView {
    void onLoadMasterInfoSuccess(MasterInfo masterInfo);
}
