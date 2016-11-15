package com.yoursecondworld.secondworld.modular.messageNotify.fragment.view;

import com.yoursecondworld.secondworld.modular.messageNotify.fragment.entity.MessageEntity;
import com.yoursecondworld.secondworld.modular.mvp.view.IBaseView;

/**
 * Created by cxj on 2016/9/24.
 */
public interface IMessageFragmentView extends IBaseView {
    void onloadNewMessageSuccess(MessageEntity messageEntity);
}
