package com.yoursecondworld.secondworld.modular.commonFunction.feedback.presenter;

import com.yoursecondworld.secondworld.AppConfig;
import com.yoursecondworld.secondworld.common.CallbackAdapter;
import com.yoursecondworld.secondworld.common.Constant;
import com.yoursecondworld.secondworld.common.JsonRequestParameter;
import com.yoursecondworld.secondworld.common.StaticDataStore;
import com.yoursecondworld.secondworld.common.baseResult.BaseEntity;
import com.yoursecondworld.secondworld.modular.commonFunction.feedback.view.IFeedbackView;

import retrofit2.Call;

/**
 * Created by cxj on 2016/9/16.
 */
public class FeedbackPresenter {

    private IFeedbackView view;

    public FeedbackPresenter(IFeedbackView view) {
        this.view = view;
    }

    /**
     * 发送反馈
     *
     * @param content
     */
    public void post_feedback(String content) {

        view.showDialog("正在提交");

        Call<BaseEntity> call = AppConfig.netWorkService.post_feedback(JsonRequestParameter.build(
                new String[]{Constant.RESULT_SESSION_ID, Constant.RESULT_OBJECT_ID,"feedback"},
                new Object[]{StaticDataStore.session_id, StaticDataStore.newUser.getUser_id(),content}));

        call.enqueue(new CallbackAdapter<BaseEntity>(view) {
            @Override
            public void onResponse(BaseEntity entity) {
                view.tip("提交成功");
            }

            @Override
            public void onFailure(Call<BaseEntity> call, Throwable t) {
                super.onFailure(call, t);
                view.tip("提交失败");
            }
        });

    }

}
