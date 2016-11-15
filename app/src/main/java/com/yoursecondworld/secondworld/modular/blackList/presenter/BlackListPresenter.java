package com.yoursecondworld.secondworld.modular.blackList.presenter;

import com.yoursecondworld.secondworld.AppConfig;
import com.yoursecondworld.secondworld.common.CallbackAdapter;
import com.yoursecondworld.secondworld.common.Constant;
import com.yoursecondworld.secondworld.common.JsonRequestParameter;
import com.yoursecondworld.secondworld.common.StaticDataStore;
import com.yoursecondworld.secondworld.modular.blackList.entity.BlockUserResult;
import com.yoursecondworld.secondworld.modular.blackList.view.IBlackListView;

import retrofit2.Call;

/**
 * Created by cxj on 2016/9/12.
 */
public class BlackListPresenter {

    private IBlackListView view;

    public BlackListPresenter(IBlackListView view) {
        this.view = view;
    }

    /**
     * 获取黑名单
     */
    public void getBlackList() {

        view.showDialog("正在加载");

        Call<BlockUserResult> call = AppConfig.netWorkService.get_user_blocked_list(JsonRequestParameter.build(
                new String[]{Constant.RESULT_SESSION_ID, Constant.RESULT_OBJECT_ID},
                new Object[]{StaticDataStore.session_id, StaticDataStore.newUser.getUser_id()}));

        call.enqueue(new CallbackAdapter<BlockUserResult>(view) {

            @Override
            public void onResponse(BlockUserResult blockUserResult) {
                view.onLoadBlackSuccess(blockUserResult.getUsers());
            }
        });

    }

}
