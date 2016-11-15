package com.yoursecondworld.secondworld.modular.im.popupMenu;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.view.View;
import android.widget.TextView;

import com.yoursecondworld.secondworld.AppConfig;
import com.yoursecondworld.secondworld.R;
import com.yoursecondworld.secondworld.common.CallbackAdapter;
import com.yoursecondworld.secondworld.common.Constant;
import com.yoursecondworld.secondworld.common.JsonRequestParameter;
import com.yoursecondworld.secondworld.common.StaticDataStore;
import com.yoursecondworld.secondworld.common.baseResult.BaseEntity;
import com.yoursecondworld.secondworld.modular.commonFunction.user.presenter.UserPresenter;
import com.yoursecondworld.secondworld.modular.commonFunction.user.view.IUserView;
import com.yoursecondworld.secondworld.modular.im.ConversationAct;

import retrofit2.Call;
import xiaojinzi.base.android.os.T;

/**
 * Created by cxj on 2016/7/24.
 * 弹出分享的界面,这个用android自带的轻量级组件弹出
 */
public class PopupMenu extends BottomSheetDialog implements IUserView, View.OnClickListener {


    /**
     * 弹出的视图
     */
    private View contentView;

    private Context mContext;

    private UserPresenter userPresenter = new UserPresenter(this);

    /**
     * 创建弹出的界面
     *
     * @param context
     */
    public PopupMenu(@NonNull Context context) {
        super(context);
        this.mContext = context;
        contentView = View.inflate(context, R.layout.act_im_converst_popup_menu, null);

        initView(contentView);

        setContentView(contentView);

        setOnListener();
    }

    private TextView tv_shield;
    private TextView tv_report;

    /**
     * 初始化控件
     *
     * @param contentView
     */
    private void initView(View contentView) {
        tv_shield = (TextView) contentView.findViewById(R.id.tv_shield);
        tv_report = (TextView) contentView.findViewById(R.id.tv_report);
    }

    /**
     * 设置条目的监听
     */
    private void setOnListener() {
        tv_shield.setOnClickListener(this);
        tv_report.setOnClickListener(this);
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
    public void onFollowSuccess(Object... obs) {
    }

    @Override
    public void onUnFollowSuccess(Object... obs) {
    }

    @Override
    public void onBlockSuccess() {
    }

    @Override
    public void onUnBlockSuccess() {
    }

    @Override
    public void showDialog(String content) {
    }

    @Override
    public void closeDialog() {
    }

    @Override
    public void tip(String content) {
        T.showShort(mContext, content);
    }

    @Override
    public void onSessionInvalid() {

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.tv_shield: //如果是屏蔽这个人
                userPresenter.blockUser(ConversationAct.targetUserId);
                break;
            case R.id.tv_report: //如果是举报

                Call<BaseEntity> call = AppConfig.netWorkService.report_user(JsonRequestParameter.build(
                        new String[]{Constant.RESULT_SESSION_ID, Constant.RESULT_OBJECT_ID, "user_id", "reason"},
                        new Object[]{StaticDataStore.session_id, StaticDataStore.newUser.getUser_id(), ConversationAct.targetUserId, "no"}));

                call.enqueue(new CallbackAdapter<BaseEntity>(this) {
                    @Override
                    public void onResponse(BaseEntity entity) {
                        tip("举报成功");
                    }
                });

                break;
        }
        dismiss();
    }
}
