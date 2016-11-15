package com.yoursecondworld.secondworld.common;

import com.yoursecondworld.secondworld.common.baseResult.BaseEntity;
import com.yoursecondworld.secondworld.modular.mvp.view.IBaseView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by cxj on 2016/11/1.
 * 统一处理请求返回的结果
 */
public abstract class CallbackAdapter<T extends BaseEntity> implements Callback<T> {

    private IBaseView view;

    public CallbackAdapter(IBaseView view) {
        this.view = view;
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        view.closeDialog();
        view.tip("网络有点问题");
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {

        //拿到返回的数据
        T body = response.body();
        //如果为空,就不做处理
        if (body == null) {
            view.closeDialog();
            return;
        }
        //拿到状态码
        Integer status = body.getStatus();
        if (status == 0) {
            view.closeDialog();
            onResponse(body);
        } else if (status == 2) {
            view.tip("您已经在另一台设备上登陆,被迫下线");
            view.closeDialog();
            view.onSessionInvalid();
        } else {
            view.closeDialog();
            onOtherStatusResponse(status);
        }

    }

    /**
     * 处理真正的对象
     *
     * @param t
     */
    public abstract void onResponse(T t);

    public void onOtherStatusResponse(int status) {
    }

}
