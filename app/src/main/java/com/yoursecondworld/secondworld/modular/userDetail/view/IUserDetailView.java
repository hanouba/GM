package com.yoursecondworld.secondworld.modular.userDetail.view;

import com.yoursecondworld.secondworld.modular.mvp.view.IBaseView;
import com.yoursecondworld.secondworld.modular.systemInfo.entity.NewUser;
import com.yoursecondworld.secondworld.modular.systemInfo.entity.User;
import com.yoursecondworld.secondworld.modular.systemInfo.entity.UserResult;

/**
 * Created by cxj on 2016/8/11.
 */
public interface IUserDetailView extends IBaseView {

    /**
     * 获取目标用户的id
     *
     * @return
     */
    String getTargetUserId();


    /**
     * 用户信息加载成功
     *
     * @param user
     */
    void onLoadUserInfoSuccess(UserResult entity);

}
