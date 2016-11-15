package com.yoursecondworld.secondworld.modular.postDynamics.presenter;

import com.yoursecondworld.secondworld.modular.postDynamics.model.DynamicsDraftModel;
import com.yoursecondworld.secondworld.modular.postDynamics.model.IDynamicsDraftModel;
import com.yoursecondworld.secondworld.modular.postDynamics.view.IDynamicsDraftView;

import java.util.Date;

import xiaojinzi.base.java.util.DateUtil;

/**
 * Created by cxj on 2016/9/12.
 */
public class DynamicsDraftPresenter {

    private DateUtil d = new DateUtil(DateUtil.CHINADATE_STYLE_1 + " " + DateUtil.CHINATIME_STYLE2);

    private IDynamicsDraftView view;

    private IDynamicsDraftModel model = new DynamicsDraftModel();

    public DynamicsDraftPresenter(IDynamicsDraftView view) {
        this.view = view;
    }

    /**
     * 保存动态到草稿箱
     */
    public void saveDynamicsToDraft() {
        model.save(view.getDynamicsDraftDao(), view.getSelectImages(), view.getVideoPath(), view.getDynamicsContent(),
                view.getTopic(), view.getGameLabel(), d.formatDate(new Date(System.currentTimeMillis())));
    }

    /**
     * 更新一个草稿到数据库
     */
    public void updateDynamicsToDraft() {
        model.update(view.getDynamicsDraftDao(), view.getDraftId(), view.getSelectImages(), view.getVideoPath(),
                view.getDynamicsContent(), view.getTopic(), view.getGameLabel(), d.formatDate(new Date(System.currentTimeMillis())));
    }

}
