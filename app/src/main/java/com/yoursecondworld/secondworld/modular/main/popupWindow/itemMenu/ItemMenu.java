package com.yoursecondworld.secondworld.modular.main.popupWindow.itemMenu;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yoursecondworld.secondworld.R;
import com.yoursecondworld.secondworld.modular.commonFunction.user.presenter.UserPresenter;
import com.yoursecondworld.secondworld.modular.commonFunction.user.view.IUserView;
import com.yoursecondworld.secondworld.modular.dynamics.entity.NewDynamics;
import com.yoursecondworld.secondworld.modular.dynamics.presenter.DynamicsPresenter;
import com.yoursecondworld.secondworld.modular.dynamics.view.IDynamicsView;
import com.yoursecondworld.secondworld.modular.main.eventEntity.BlockUserEventEntity;
import com.yoursecondworld.secondworld.modular.main.eventEntity.ItemMenuEventEntity;
import com.yoursecondworld.secondworld.modular.main.eventEntity.ShareEventEntity;
import com.yoursecondworld.secondworld.modular.main.eventEntity.SharePlatformEntity;
import com.yoursecondworld.secondworld.modular.main.popupWindow.share.ShareAdapter;
import com.yoursecondworld.secondworld.modular.main.popupWindow.share.ShareEntity;
import com.yoursecondworld.secondworld.modular.main.popupWindow.share.SharePopupWindowSpaceItemDecoration;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import xiaojinzi.base.android.adapter.recyclerView.CommonRecyclerViewAdapter;
import xiaojinzi.base.android.os.T;

/**
 * Created by cxj on 2016/7/24.
 * 弹出分享的界面,这个用android自带的轻量级组件弹出
 */
public class ItemMenu extends BottomSheetDialog implements View.OnClickListener, IUserView, IDynamicsView {

    /**
     * 上下文
     */
    private Context context;

    /**
     * 弹出的视图
     */
    private View contentView;

    private ItemMenuEventEntity item;

    private UserPresenter userPresenter = new UserPresenter(this);

    private DynamicsPresenter dynamicsPresenter = new DynamicsPresenter(this);

    /**
     * 创建弹出的界面
     *
     * @param context
     */
    public ItemMenu(@NonNull Context context, @NonNull ItemMenuEventEntity item) {
        super(context);
        this.context = context;
        this.item = item;
        contentView = View.inflate(context, R.layout.act_main_popup_for_dynamics_item_menu, null);

        initView(contentView);

        setContentView(contentView);

        setOnListener();

    }

    /**
     * 初始化控件
     *
     * @param contentView
     */
    private void initView(View contentView) {

        TextView tv_shield = (TextView) contentView.findViewById(R.id.tv_shield); //举报
        TextView tv_report = (TextView) contentView.findViewById(R.id.tv_report); //屏蔽
        TextView tv_cancel = (TextView) contentView.findViewById(R.id.tv_cancel); //取消

        tv_shield.setOnClickListener(this);
        tv_report.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
    }

    /**
     * 设置条目的监听
     */
    private void setOnListener() {
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
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv_shield: //举报
//                T.showShort(context, "举报");
                dynamicsPresenter.report_article(item.dynamicsId);
                break;
            case R.id.tv_report: //屏蔽:
//                T.showShort(context, "屏蔽");
//                BlockUserEventEntity b = new BlockUserEventEntity();
//                b.userId = item.userId;
//                EventBus.getDefault().post(b);
                userPresenter.blockUser(item.userId);
                break;
            case R.id.tv_cancel: //取消
                break;
        }
        dismiss();
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
        T.showShort(context, content);
    }

    @Override
    public void onSessionInvalid() {

    }

    @Override
    public void onZanSuccess(TextView tv_zan, ImageView iv_zan, NewDynamics dynamics, int liked_number) {
    }

    @Override
    public void onCancleZanSuccess(TextView tv_zan, ImageView iv_zan, NewDynamics dynamics, int liked_number) {
    }

    @Override
    public void onCollectSuccess(ImageView iv_collect, NewDynamics dynamics) {
    }

    @Override
    public void onUnCollectSuccess(ImageView iv_collect, NewDynamics dynamics) {
    }

    @Override
    public void onReportDynamicsSuccess() {
    }
}
