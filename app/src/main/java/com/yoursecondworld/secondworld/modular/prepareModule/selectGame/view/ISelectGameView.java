package com.yoursecondworld.secondworld.modular.prepareModule.selectGame.view;

import com.yoursecondworld.secondworld.modular.systemInfo.entity.GameLabel;

import java.util.List;

/**
 * Created by cxj on 2016/8/15.
 */
public interface ISelectGameView {

    /**
     * 显示进度条
     *
     * @param content
     */
    void showDialog(String content);

    /**
     * 关闭对话框
     */
    void closeDialog();

    void disPlayData(List<String> gameLabels);

    /**
     * 提示用户
     *
     * @param content
     */
    void tip(String content);

    /**
     * 获取用户选择的游戏标签的id数组
     *
     * @return
     */
    String[] getSelectGameLabelIds();

    /**
     * 成功之后
     *
     */
    void onSuccess(String gamaLabelId[]);


}
