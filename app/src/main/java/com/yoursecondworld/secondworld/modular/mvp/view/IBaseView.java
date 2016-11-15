package com.yoursecondworld.secondworld.modular.mvp.view;

/**
 * Created by cxj on 2016/8/15.
 * 基本的MVP的V视图层
 */
public interface IBaseView {

    /**
     * 弹出进度条对话框
     *
     * @param content
     */
    void showDialog(String content);

    /**
     * 关闭对话框
     */
    void closeDialog();

    /**
     * 提示用户的信息
     *
     * @param content
     */
    void tip(String content);

    /**
     * Session失效啦
     */
    void onSessionInvalid();

}
