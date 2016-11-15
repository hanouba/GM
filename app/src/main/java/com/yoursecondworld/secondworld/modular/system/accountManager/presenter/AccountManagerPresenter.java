package com.yoursecondworld.secondworld.modular.system.accountManager.presenter;

import android.text.TextUtils;

import com.yoursecondworld.secondworld.AppConfig;
import com.yoursecondworld.secondworld.common.CallbackAdapter;
import com.yoursecondworld.secondworld.common.Constant;
import com.yoursecondworld.secondworld.common.JsonRequestParameter;
import com.yoursecondworld.secondworld.common.StaticDataStore;
import com.yoursecondworld.secondworld.modular.messageNotify.fragment.entity.MessageEntity;
import com.yoursecondworld.secondworld.modular.system.accountManager.ui.AccountManagerView;

import retrofit2.Call;

/**
 * Created by cxj on 2016/9/27.
 */
public class AccountManagerPresenter {

    /**
     * 手机号码的正则表达式
     */
    private String phoneMatche = "^1[3|4|5|7|8][0-9]\\d{8}$";

    private AccountManagerView view;

    public AccountManagerPresenter(AccountManagerView view) {
        this.view = view;

    }

    public void send_bind_phone_number_message() {

        String phoneNumber = view.getPhoneNumber();

        if (TextUtils.isEmpty(phoneNumber)) {
            view.tip("电话号码不能为空");
            return;
        }

        //手机号的正则
        boolean matches = phoneNumber.matches(phoneMatche);
        if (!matches) { //如果不匹配,提示用户
            view.tip("请输入正确的手机号");
            return;
        }

        Call<MessageEntity> call = AppConfig.netWorkService.send_bind_phone_number_message(JsonRequestParameter.build(
                new String[]{Constant.RESULT_SESSION_ID, Constant.RESULT_OBJECT_ID, "phone_number"},
                new Object[]{StaticDataStore.session_id, StaticDataStore.newUser.getUser_id(), phoneNumber}));

        call.enqueue(new CallbackAdapter<MessageEntity>(view) {
            @Override
            public void onResponse(MessageEntity entity) {
                view.tip("短信发送成功");
                view.onSendMessageSuccess();
            }

            @Override
            public void onOtherStatusResponse(int status) {
                super.onOtherStatusResponse(status);
                if (105 == status) {
                    view.tip("手机号已经被绑定,请更换手机号");
                }
            }

            @Override
            public void onFailure(Call<MessageEntity> call, Throwable t) {
                super.onFailure(call, t);
                view.tip("发送失败");
            }
        });

    }


    public void auth_bind_phone_number_message() {
        String phoneNumber = view.getPhoneNumber();

        if (TextUtils.isEmpty(phoneNumber)) {
            view.tip("电话号码不能为空");
            return;
        }

        //手机号的正则
        boolean matches = phoneNumber.matches(phoneMatche);
        if (!matches) { //如果不匹配,提示用户
            view.tip("请输入正确的手机号");
            return;
        }

        Call<MessageEntity> call = AppConfig.netWorkService.send_bind_phone_number_message(JsonRequestParameter.build(
                new String[]{Constant.RESULT_SESSION_ID, Constant.RESULT_OBJECT_ID, "phone_number", "password", "code"},
                new Object[]{StaticDataStore.session_id, StaticDataStore.newUser.getUser_id(), phoneNumber, view.getPassword(), view.getCheckCode()}));

        call.enqueue(new CallbackAdapter<MessageEntity>(view) {
            @Override
            public void onResponse(MessageEntity entity) {
                view.tip("绑定成功成功");
                view.onBindPhoneSuccess();
            }

            @Override
            public void onFailure(Call<MessageEntity> call, Throwable t) {
                super.onFailure(call, t);
                view.tip("发送失败");
            }
        });

    }

}
