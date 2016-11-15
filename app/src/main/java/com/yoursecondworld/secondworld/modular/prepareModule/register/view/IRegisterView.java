package com.yoursecondworld.secondworld.modular.prepareModule.register.view;

import com.yoursecondworld.secondworld.modular.mvp.view.IBaseView;

/**
 * Created by cxj on 2016/8/11.
 */
public interface IRegisterView extends IBaseView {

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
     * 获取验证码
     *
     * @return
     */
    String getCheckCode();



    /**
     * 注册成功
     */
    void onRegisterSuccess();

}
