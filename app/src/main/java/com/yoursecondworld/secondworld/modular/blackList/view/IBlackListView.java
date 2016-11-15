package com.yoursecondworld.secondworld.modular.blackList.view;

import com.yoursecondworld.secondworld.modular.blackList.entity.BlockUser;
import com.yoursecondworld.secondworld.modular.mvp.view.IBaseView;

import java.util.List;

/**
 * Created by cxj on 2016/9/12.
 */
public interface IBlackListView extends IBaseView {

    void onLoadBlackSuccess(List<BlockUser> list);

}
