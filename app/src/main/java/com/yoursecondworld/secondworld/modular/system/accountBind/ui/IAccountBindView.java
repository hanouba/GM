package com.yoursecondworld.secondworld.modular.system.accountBind.ui;

import com.yoursecondworld.secondworld.modular.mvp.view.IBaseView;
import com.yoursecondworld.secondworld.modular.system.accountBind.entity.AccountBindInfoResult;

/**
 * Created by cxj on 2016/9/17.
 */
public interface IAccountBindView extends IBaseView {
    void onLoadAccountBindInfoSuccess(AccountBindInfoResult info);

    void onBindQQSuccess();
    void onBindWeiXinSuccess();
    void onBindWeiBoSuccess();
}
