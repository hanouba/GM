package com.yoursecondworld.secondworld.modular.messageNotify.fragment.presenter;

import com.yoursecondworld.secondworld.AppConfig;
import com.yoursecondworld.secondworld.common.CallbackAdapter;
import com.yoursecondworld.secondworld.common.Constant;
import com.yoursecondworld.secondworld.common.JsonRequestParameter;
import com.yoursecondworld.secondworld.common.StaticDataStore;
import com.yoursecondworld.secondworld.modular.messageNotify.fragment.entity.MessageEntity;
import com.yoursecondworld.secondworld.modular.messageNotify.fragment.view.IMessageFragmentView;

import retrofit2.Call;

/**
 * Created by cxj on 2016/9/24.
 */
public class MessageFragmentPresenter {

    private IMessageFragmentView view;


    public MessageFragmentPresenter(IMessageFragmentView view) {
        this.view = view;

    }

    public void mes_new_message_exist(){

        Call<MessageEntity> call = AppConfig.netWorkService.mes_new_message_exist(JsonRequestParameter.build(
                new String[]{Constant.RESULT_SESSION_ID, Constant.RESULT_OBJECT_ID},
                new Object[]{StaticDataStore.session_id, StaticDataStore.newUser.getUser_id()}));

        call.enqueue(new CallbackAdapter<MessageEntity>(view) {

            @Override
            public void onResponse(MessageEntity messageEntity) {
                view.onloadNewMessageSuccess(messageEntity);
            }
        });

    }

}
