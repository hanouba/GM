package com.yoursecondworld.secondworld.modular.commonFunction.postComment.presenter;

import android.text.TextUtils;

import com.yoursecondworld.secondworld.AppConfig;
import com.yoursecondworld.secondworld.common.CallbackAdapter;
import com.yoursecondworld.secondworld.common.Constant;
import com.yoursecondworld.secondworld.common.JsonRequestParameter;
import com.yoursecondworld.secondworld.common.StaticDataStore;
import com.yoursecondworld.secondworld.common.baseResult.BaseEntity;
import com.yoursecondworld.secondworld.modular.commonFunction.postComment.view.IpostCommentView;

import okhttp3.RequestBody;
import retrofit2.Call;

/**
 * Created by cxj on 2016/9/20.
 */
public class PostCommentPresenter {

    private IpostCommentView view;

    public PostCommentPresenter(IpostCommentView view) {
        this.view = view;
    }

    /**
     * 发布动态的评论
     */
    public void postDynamicsComment() {

        String commentContent = view.getCommentContent();

        if (TextUtils.isEmpty(commentContent)) {
            view.tip("评论内容不能为空");
            return;
        }

        commentContent = commentContent.trim();

        if (commentContent.length() > 120) {
            view.tip("评论内容过长");
            return;
        }

        view.showDialog("正在发布评论");

        RequestBody body = JsonRequestParameter.build(
                new String[]{Constant.RESULT_SESSION_ID, Constant.RESULT_OBJECT_ID, "article_id", "comment", "userId"},
                new Object[]{StaticDataStore.session_id, StaticDataStore.newUser.getUser_id(), view.getDynamicsId(), commentContent.trim(), view.getComentTargetUserId()}
        );

        Call<BaseEntity> call = AppConfig.netWorkService.postDynamicsComment(body);

        //发布评论
        call.enqueue(new CallbackAdapter<BaseEntity>(view) {
            @Override
            public void onResponse(BaseEntity entity) {
                view.clearCommentContent();
                view.onCommentSuccess();
            }

            @Override
            public void onFailure(Call<BaseEntity> call, Throwable t) {
                super.onFailure(call, t);
                view.tip("加载评论列表失败");
            }
        });


    }

}
