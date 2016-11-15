package com.yoursecondworld.secondworld.modular.commonFunction.fansAndFollow.view;

import com.yoursecondworld.secondworld.modular.mvp.view.IBaseView;
import com.yoursecondworld.secondworld.modular.systemInfo.entity.NewUser;

import java.util.List;

/**
 * Created by cxj on 2016/9/9.
 */
public interface IFansAndFollowView extends IBaseView {


    void onLoadFollowEachOtherSuccess(List<NewUser> newUsers);


    void onLoadUnFollowFansSuccess(List<NewUser> newUsers);

    void onLoadMoreUnFollowFansSuccess(List<NewUser> users);

    void savePass(String pass);

    String getPass();
}
