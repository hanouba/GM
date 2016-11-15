package com.yoursecondworld.secondworld.modular.system.accountManager.ui;

import com.yoursecondworld.secondworld.modular.mvp.view.IBaseView;

/**
 * Created by cxj on 2016/9/27.
 */
public interface AccountManagerView extends IBaseView {

    String getPhoneNumber();

    void onSendMessageSuccess();

    String getPassword();

    String getCheckCode();

    void onBindPhoneSuccess();
}
