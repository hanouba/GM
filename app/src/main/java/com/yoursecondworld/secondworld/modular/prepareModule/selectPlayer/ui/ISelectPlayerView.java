package com.yoursecondworld.secondworld.modular.prepareModule.selectPlayer.ui;

import com.yoursecondworld.secondworld.modular.mvp.view.IBaseView;
import com.yoursecondworld.secondworld.modular.systemInfo.entity.NewUser;

import java.util.List;

/**
 * Created by cxj on 2016/9/18.
 */
public interface ISelectPlayerView extends IBaseView {

    //加载用户信息成功的时候调用
    void onLoadUsersSuccess(List<NewUser> list);

}
