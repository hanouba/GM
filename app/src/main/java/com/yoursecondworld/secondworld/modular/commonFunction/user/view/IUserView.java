package com.yoursecondworld.secondworld.modular.commonFunction.user.view;

import com.yoursecondworld.secondworld.modular.mvp.view.IBaseView;

/**
 * Created by cxj on 2016/9/1.
 */
public interface IUserView extends IBaseView {

    /**
     * 关注成功
     *
     * @param obs
     */
    void onFollowSuccess(Object... obs);

    /**
     * 取消关注成功
     *
     * @param obs
     */
    void onUnFollowSuccess(Object... obs);

    /**
     * 拉黑用户成功
     */
    void onBlockSuccess();

    void onUnBlockSuccess();
}
