package com.yoursecondworld.secondworld.modular.commonFunction.getSessionId.presenter;

import com.yoursecondworld.secondworld.AppConfig;
import com.yoursecondworld.secondworld.MyApp;
import com.yoursecondworld.secondworld.common.StaticDataStore;
import com.yoursecondworld.secondworld.modular.commonFunction.getSessionId.view.IGetSessionIdView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by cxj on 2016/9/6.
 */
public class GetSessionIdPresenter {


    private IGetSessionIdView view;

    public GetSessionIdPresenter(IGetSessionIdView view) {
        this.view = view;
    }

    public void getSeesionId() {

        view.showDialog("请稍后");

        Call<String> call = AppConfig.netWorkService.aquire_session_id();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                view.closeDialog();
                view.onGetSessionIdSuccess(response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                view.closeDialog();
                view.tip("初始化失败");
            }
        });
    }

}
