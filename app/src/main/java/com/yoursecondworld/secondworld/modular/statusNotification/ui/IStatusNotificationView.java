package com.yoursecondworld.secondworld.modular.statusNotification.ui;

import com.yoursecondworld.secondworld.modular.mvp.view.IBaseView;
import com.yoursecondworld.secondworld.modular.statusNotification.enity.CommentsListEntity;
import com.yoursecondworld.secondworld.modular.statusNotification.enity.ZanListEntity;

import java.util.List;

/**
 * Created by cxj on 2016/9/19.
 */
public interface IStatusNotificationView extends IBaseView {
    void onLoadCommentsListSuccess(List<CommentsListEntity> commentsListEntities);

    void onLoadMoreCommentsListSuccess(List<CommentsListEntity> commentsListEntities);

    void savePass(String pass);

    String getPass();

    void onLoadZanListSuccess(List<ZanListEntity> zanListEntities);

    void onLoadMoreZanListSuccess(List<ZanListEntity> zanListEntities);

}
