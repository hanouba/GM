package com.yoursecondworld.secondworld.modular.statusNotification.sheetDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.yoursecondworld.secondworld.R;
import com.yoursecondworld.secondworld.modular.commonFunction.postComment.presenter.PostCommentPresenter;
import com.yoursecondworld.secondworld.modular.commonFunction.postComment.view.IpostCommentView;
import com.yoursecondworld.secondworld.modular.dynamicsDetail.presenter.DynamicsDetailPresenter;
import com.yoursecondworld.secondworld.modular.dynamicsDetail.view.IDynamicsDetailView;

import xiaojinzi.base.android.os.KeyBoardUtils;
import xiaojinzi.base.android.os.T;

/**
 * Created by cxj on 2016/7/24.
 * 弹出分享的界面,这个用android自带的轻量级组件弹出
 */
public class PopupReply extends BottomSheetDialog implements IpostCommentView {

    /**
     * 上下文
     */
    private Context context;

    /**
     * 弹出的视图
     */
    private View contentView;

    private String user_id;
    private String userName;

    private String article_id;

    private PostCommentPresenter postCommentPresenter = new PostCommentPresenter(this);

    /**
     * 创建弹出的界面
     *
     * @param context
     */
    public PopupReply(@NonNull Context context, String user_id, String userName, String article_id) {
        super(context);
        this.context = context;
        this.user_id = user_id;
        this.userName = userName;
        this.article_id = article_id;
        contentView = View.inflate(context, R.layout.act_status_notifi_reply, null);
        setContentView(contentView);

        initView(contentView);

        setOnListener();

    }

    private TextView tv_send;
    private EditText et_content;

    /**
     * 初始化控件
     *
     * @param contentView
     */
    private void initView(View contentView) {
        tv_send = (TextView) findViewById(R.id.tv_send);

        et_content = (EditText) findViewById(R.id.et_content);
        et_content.setHint("回复:" + userName);
    }

    /**
     * 设置条目的监听
     */
    private void setOnListener() {
        tv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = et_content.getText().toString();
                if (!TextUtils.isEmpty(content)) {
                    postCommentPresenter.postDynamicsComment();
                } else {
                    tip("评论内容不能为空");
                }
            }
        });
    }

    /**
     * 获取视图
     *
     * @return
     */
    public View getContentView() {
        return contentView;
    }

    @Override
    public void clearCommentContent() {
    }

    @Override
    public void onCommentSuccess() {
        tip("评论成功");
        dismiss();
    }

    @Override
    public String getComentTargetUserId() {
        return user_id;
    }

    @Override
    public String getDynamicsId() {
        return article_id;
    }

    @Override
    public String getCommentContent() {
        return "回复 @" + userName + ": " + et_content.getText().toString();
    }

    @Override
    public void showDialog(String content) {

    }

    @Override
    public void closeDialog() {

    }

    @Override
    public void tip(String content) {
        T.showShort(context, content);
    }

    @Override
    public void onSessionInvalid() {

    }
}
