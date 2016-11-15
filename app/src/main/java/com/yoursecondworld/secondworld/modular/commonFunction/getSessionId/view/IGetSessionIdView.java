package com.yoursecondworld.secondworld.modular.commonFunction.getSessionId.view;

import com.yoursecondworld.secondworld.modular.mvp.view.IBaseView;

/**
 * Created by cxj on 2016/9/6.
 */
public interface IGetSessionIdView extends IBaseView {

    /**
     * 获取sessionId成功
     *
     * @param sessionId
     */
    void onGetSessionIdSuccess(String sessionId);

}
