package com.yoursecondworld.secondworld.modular.selectPostGame.view;

import com.yoursecondworld.secondworld.modular.mvp.view.IBaseView;

import java.util.List;

/**
 * Created by cxj on 2016/10/24.
 */
public interface ISearchPostGameView extends IBaseView {

    /**
     * 获取搜索的关键字
     *
     * @return
     */
    String getSearchKey();

    /**
     * 搜索成功
     *
     * @param games
     */
    void onSearchSuccess(List<String> games);


}
