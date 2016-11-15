package com.yoursecondworld.secondworld.modular.mvp.presneter;

import com.yoursecondworld.secondworld.AppConfig;
import com.yoursecondworld.secondworld.common.CallbackAdapter;
import com.yoursecondworld.secondworld.common.Constant;
import com.yoursecondworld.secondworld.common.JsonRequestParameter;
import com.yoursecondworld.secondworld.common.StaticDataStore;
import com.yoursecondworld.secondworld.commonBean.MasterInfo;
import com.yoursecondworld.secondworld.modular.mvp.view.IBaseMasterView;

import okhttp3.RequestBody;
import retrofit2.Call;

/**
 * Created by cxj on 2016/11/8.
 */
public class BaseMasterPresenter {

    protected IBaseMasterView view;

    public BaseMasterPresenter(IBaseMasterView view) {
        this.view = view;
    }

    public void getMasterInfoByMonth(int year, int month) {


        RequestBody body = JsonRequestParameter.build(
                new String[]{Constant.RESULT_SESSION_ID, Constant.RESULT_OBJECT_ID, "year", "month"},
                new Object[]{StaticDataStore.session_id, StaticDataStore.newUser.getUser_id(), year, month}
        );

        Call<MasterInfo> call = AppConfig.netWorkService.master_get_infor_by_month(body);

        call.enqueue(new CallbackAdapter<MasterInfo>(view) {

            @Override
            public void onResponse(MasterInfo masterInfo) {
                view.onLoadMasterInfoSuccess(masterInfo);
            }

        });

    }

}
