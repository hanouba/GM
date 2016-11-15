package com.yoursecondworld.secondworld.modular.commonFunction.user.presenter;

import android.text.TextUtils;

import com.yoursecondworld.secondworld.AppConfig;
import com.yoursecondworld.secondworld.common.CallbackAdapter;
import com.yoursecondworld.secondworld.common.Constant;
import com.yoursecondworld.secondworld.common.JsonRequestParameter;
import com.yoursecondworld.secondworld.common.StaticDataStore;
import com.yoursecondworld.secondworld.common.baseResult.BaseEntity;
import com.yoursecondworld.secondworld.modular.commonFunction.user.view.IUserView;

import retrofit2.Call;

/**
 * Created by cxj on 2016/9/1.
 */
public class UserPresenter {

    private IUserView view;

    public UserPresenter(IUserView view) {
        this.view = view;
    }

    /**
     * 关注用户
     */
    public void followUser(String userId, final Object... obs) {

        view.showDialog("正在关注");

        if (TextUtils.isEmpty(userId)) {
            view.closeDialog();
            return;
        }

        Call<BaseEntity> call = AppConfig.netWorkService.followUser(JsonRequestParameter.build(
                new String[]{Constant.RESULT_SESSION_ID, Constant.RESULT_OBJECT_ID,"user_id"},
                new Object[]{StaticDataStore.session_id, StaticDataStore.newUser.getUser_id(),userId}));

        call.enqueue(new CallbackAdapter<BaseEntity>(view) {
            @Override
            public void onResponse(BaseEntity entity) {
                view.onFollowSuccess(obs);
            }

            @Override
            public void onFailure(Call<BaseEntity> call, Throwable t) {
                super.onFailure(call, t);
                view.tip("关注失败");
            }
        });

    }

    /**
     * 取消关注
     */
    public void unFollowUser(String userId, final Object... obs) {
        view.showDialog("正在取消关注");

        if (TextUtils.isEmpty(userId)) {
            view.closeDialog();
            return;
        }

        Call<BaseEntity> call = AppConfig.netWorkService.unFollowUser(JsonRequestParameter.build(
                new String[]{Constant.RESULT_SESSION_ID, Constant.RESULT_OBJECT_ID,"user_id"},
                new Object[]{StaticDataStore.session_id, StaticDataStore.newUser.getUser_id(),userId}));

        call.enqueue(new CallbackAdapter<BaseEntity>(view) {
            @Override
            public void onResponse(BaseEntity entity) {
                view.onUnFollowSuccess(obs);
            }

            @Override
            public void onFailure(Call<BaseEntity> call, Throwable t) {
                super.onFailure(call, t);
                view.tip("取消关注失败");
            }
        });
    }

    /**
     * 拉黑用户
     *
     * @param userId
     */
    public void blockUser(String userId) {

        view.showDialog("正在屏蔽该用户");

        Call<BaseEntity> call = AppConfig.netWorkService.blockUser(JsonRequestParameter.build(
                new String[]{Constant.RESULT_SESSION_ID, Constant.RESULT_OBJECT_ID,"user_id"},
                new Object[]{StaticDataStore.session_id, StaticDataStore.newUser.getUser_id(),userId}));

        call.enqueue(new CallbackAdapter<BaseEntity>(view) {
            @Override
            public void onResponse(BaseEntity entity) {
                view.onBlockSuccess();
                view.tip("拉黑用户成功");
            }

            @Override
            public void onFailure(Call<BaseEntity> call, Throwable t) {
                super.onFailure(call, t);
                view.tip("拉黑用户失败");
            }
        });

    }

    public void unblockUser(String userId) {

        view.showDialog("正在屏蔽该用户");

        Call<BaseEntity> call = AppConfig.netWorkService.unblock_user(JsonRequestParameter.build(
                new String[]{Constant.RESULT_SESSION_ID, Constant.RESULT_OBJECT_ID,"user_id"},
                new Object[]{StaticDataStore.session_id, StaticDataStore.newUser.getUser_id(),userId}));

        call.enqueue(new CallbackAdapter<BaseEntity>(view) {
            @Override
            public void onResponse(BaseEntity entity) {
                view.onBlockSuccess();
                view.onUnBlockSuccess();
            }

            @Override
            public void onFailure(Call<BaseEntity> call, Throwable t) {
                super.onFailure(call, t);
                view.tip("取消拉黑用户失败");
            }
        });

    }

}
