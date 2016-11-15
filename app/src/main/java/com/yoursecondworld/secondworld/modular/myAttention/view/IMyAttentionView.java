package com.yoursecondworld.secondworld.modular.myAttention.view;

import com.yoursecondworld.secondworld.modular.mvp.view.IBaseView;
import com.yoursecondworld.secondworld.modular.systemInfo.entity.NewUser;
import com.yoursecondworld.secondworld.modular.systemInfo.entity.User;

import java.util.List;

/**
 * Created by cxj on 2016/8/18.
 */
public interface IMyAttentionView extends IBaseView {

    /**
     * 加载数据成功的回掉
     *
     * @param users
     */
    void onLoadDataSuccess(List<NewUser> newUsers);

    void onLoadMoreDataSuccess(List<NewUser> users);

    void onLoadSearchDataSuccess(List<NewUser> users);

    String getPass();

    void savePass(String pass);

    String getSearchKey();
}
