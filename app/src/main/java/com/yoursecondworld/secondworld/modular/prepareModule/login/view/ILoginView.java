package com.yoursecondworld.secondworld.modular.prepareModule.login.view;

import com.yoursecondworld.secondworld.modular.mvp.view.IBaseView;

/**
 * Created by cxj on 2016/8/11.
 */
public interface ILoginView extends IBaseView {

    /**
     * 获取电话号码
     *
     * @return
     */
    String getPhone();

    /**
     * 获取密码
     *
     * @return
     */
    String getPassword();

    /**
     * 登陆成功
     */
    void loginSuccess(boolean isFullInfo);

}
