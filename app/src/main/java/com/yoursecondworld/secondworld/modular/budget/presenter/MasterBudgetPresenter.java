package com.yoursecondworld.secondworld.modular.budget.presenter;

import com.yoursecondworld.secondworld.AppConfig;
import com.yoursecondworld.secondworld.common.CallbackAdapter;
import com.yoursecondworld.secondworld.common.Constant;
import com.yoursecondworld.secondworld.common.JsonRequestParameter;
import com.yoursecondworld.secondworld.common.StaticDataStore;
import com.yoursecondworld.secondworld.common.baseResult.BaseEntity;
import com.yoursecondworld.secondworld.modular.budget.view.IMasterBudgetView;
import com.yoursecondworld.secondworld.modular.mvp.presneter.BaseMasterPresenter;
import com.yoursecondworld.secondworld.modular.mvp.view.IBaseMasterView;

import okhttp3.RequestBody;
import retrofit2.Call;

/**
 * Created by cxj on 2016/11/8.
 */
public class MasterBudgetPresenter extends BaseMasterPresenter {

    private IMasterBudgetView view;

    public MasterBudgetPresenter(IMasterBudgetView view) {
        super(view);
        this.view = view;
    }

    /**
     * 设置Master的金额
     *
     * @param year
     * @param month
     */
    public void setMasterMoney(int year, int month) {

        int money = view.getMoney();

        if (money >= 1000000) {
            view.tip("上限为一百万");
            return;
        }

        RequestBody body = JsonRequestParameter.build(
                new String[]{Constant.RESULT_SESSION_ID, Constant.RESULT_OBJECT_ID, "year", "month", "money"},
                new Object[]{StaticDataStore.session_id, StaticDataStore.newUser.getUser_id(), year, month, money}
        );

        Call<BaseEntity> call = AppConfig.netWorkService.master_set_money(body);

        call.enqueue(new CallbackAdapter<BaseEntity>(view) {
            @Override
            public void onResponse(BaseEntity baseEntity) {
                view.tip("设置成功");
            }

            @Override
            public void onOtherStatusResponse(int status) {
                super.onOtherStatusResponse(status);
                if (status == 301) {
                    view.tip("一个月只能设置一次哦");
                }
            }
        });

    }

    public void setMasterTime(int year, int month) {

        int time = view.getTime();

        if (time >= 720 * 60) {
            view.tip("不能超过720小时");
            return;
        }

        RequestBody body = JsonRequestParameter.build(
                new String[]{Constant.RESULT_SESSION_ID, Constant.RESULT_OBJECT_ID, "year", "month", "time"},
                new Object[]{StaticDataStore.session_id, StaticDataStore.newUser.getUser_id(), year, month, time}
        );

        Call<BaseEntity> call = AppConfig.netWorkService.master_set_time(body);

        call.enqueue(new CallbackAdapter<BaseEntity>(view) {
            @Override
            public void onResponse(BaseEntity baseEntity) {
                view.tip("设置时间成功");
            }

            @Override
            public void onOtherStatusResponse(int status) {
                super.onOtherStatusResponse(status);
                if (status == 301) {
                    view.tip("一个月只能设置一次哦");
                }
            }
        });
    }

}
