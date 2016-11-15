package com.yoursecondworld.secondworld.modular.system.accountBind.presenter;

import com.yoursecondworld.secondworld.AppConfig;
import com.yoursecondworld.secondworld.common.CallbackAdapter;
import com.yoursecondworld.secondworld.common.Constant;
import com.yoursecondworld.secondworld.common.JsonRequestParameter;
import com.yoursecondworld.secondworld.common.StaticDataStore;
import com.yoursecondworld.secondworld.common.baseResult.BaseEntity;
import com.yoursecondworld.secondworld.modular.system.accountBind.entity.AccountBindInfoResult;
import com.yoursecondworld.secondworld.modular.system.accountBind.ui.IAccountBindView;

import retrofit2.Call;

/**
 * Created by cxj on 2016/9/17.
 */
public class AccountBindPresenter {

    private IAccountBindView view;

    public AccountBindPresenter(IAccountBindView view) {
        this.view = view;
    }

    public void getThirdBindInfo() {

        view.showDialog("正在获取绑定信息");

        Call<AccountBindInfoResult> call = AppConfig.netWorkService.is_third_party_account_binded(JsonRequestParameter.build(
                new String[]{Constant.RESULT_SESSION_ID, Constant.RESULT_OBJECT_ID},
                new Object[]{StaticDataStore.session_id, StaticDataStore.newUser.getUser_id()}));

        call.enqueue(new CallbackAdapter<AccountBindInfoResult>(view) {
            @Override
            public void onResponse(AccountBindInfoResult entity) {
                view.onLoadAccountBindInfoSuccess(entity);
            }
        });

    }

    public void bindQq(String openid, String access_token) {

        Call<BaseEntity> call = AppConfig.netWorkService.bind_qq(JsonRequestParameter.build(
                new String[]{Constant.RESULT_SESSION_ID, Constant.RESULT_OBJECT_ID, "openid", "access_token"},
                new Object[]{StaticDataStore.session_id, StaticDataStore.newUser.getUser_id(), openid, access_token}));

        call.enqueue(new CallbackAdapter<BaseEntity>(view) {
            @Override
            public void onResponse(BaseEntity baseEntity) {
                view.tip("绑定成功");
                view.onBindQQSuccess();
            }
            @Override
            public void onFailure(Call<BaseEntity> call, Throwable t) {
                super.onFailure(call, t);
                view.tip("账号已使用");
            }
        });

    }

    public void bind_wechat(String openid, String access_token) {

        Call<BaseEntity> call = AppConfig.netWorkService.bind_wechat(JsonRequestParameter.build(
                new String[]{Constant.RESULT_SESSION_ID, Constant.RESULT_OBJECT_ID, "openid", "access_token"},
                new Object[]{StaticDataStore.session_id, StaticDataStore.newUser.getUser_id(), openid, access_token}));

        call.enqueue(new CallbackAdapter<BaseEntity>(view) {
            @Override
            public void onResponse(BaseEntity baseEntity) {
                view.tip("绑定成功");
                view.onBindWeiXinSuccess();
            }
            @Override
            public void onFailure(Call<BaseEntity> call, Throwable t) {
                super.onFailure(call, t);
                view.tip("账号已使用");
            }
        });

    }

    public void bind_weibo(String openid, String access_token) {
//        view.showDialog("正在绑定");

        Call<BaseEntity> call = AppConfig.netWorkService.bind_weibo(JsonRequestParameter.build(
                new String[]{Constant.RESULT_SESSION_ID, Constant.RESULT_OBJECT_ID, "uid", "access_token"},
                new Object[]{StaticDataStore.session_id, StaticDataStore.newUser.getUser_id(), openid, access_token}));

        call.enqueue(new CallbackAdapter<BaseEntity>(view) {
            @Override
            public void onResponse(BaseEntity baseEntity) {
                view.tip("绑定成功");
                view.onBindWeiBoSuccess();
            }

            @Override
            public void onFailure(Call<BaseEntity> call, Throwable t) {
                super.onFailure(call, t);
                view.tip("账号已使用");
            }
        });

    }

}
