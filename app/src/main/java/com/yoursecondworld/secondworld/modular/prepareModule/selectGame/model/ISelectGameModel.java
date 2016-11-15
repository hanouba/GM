package com.yoursecondworld.secondworld.modular.prepareModule.selectGame.model;


import com.yoursecondworld.secondworld.common.baseResult.BaseEntity;
import com.yoursecondworld.secondworld.common.NetWorkSoveListener;

/**
 * Created by cxj on 2016/8/15.
 */
public interface ISelectGameModel {

    /**
     * 完善选择的游戏标签
     *
     * @param sessionId
     * @param objectId
     * @param game_name
     * @param listener
     */
    void updateGameLabel(String sessionId, String objectId, String[] game_name, NetWorkSoveListener<BaseEntity> listener);

}
