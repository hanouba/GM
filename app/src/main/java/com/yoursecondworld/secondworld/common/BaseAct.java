package com.yoursecondworld.secondworld.common;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.WindowManager;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import xiaojinzi.activity.BaseActivity;
import xiaojinzi.base.android.os.ProgressDialogUtil;
import xiaojinzi.base.android.os.ScreenUtils;
import xiaojinzi.base.android.os.T;


/**
 * Created by cxj on 2016/7/4.
 */
public abstract class BaseAct extends BaseActivity {

    /**
     * 进度条对话框
     */
    protected ProgressDialog dialog = null;

    /**
     * 状态栏的高度
     */
    protected int statusHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //创建对话框
        dialog = ProgressDialogUtil.create(this, ProgressDialog.STYLE_SPINNER, false);

        super.onCreate(savedInstanceState);

        //如果使用沉浸式状态栏
        if (isTranslucentNavigation()) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
//          getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

    }

    @Override
    public boolean isFullScreen() {
        return false;
    }

    /**
     * 是否使用沉浸式状态栏
     *
     * @return
     */
    public boolean isTranslucentNavigation() {
        return true;
    }

    @Override
    public void initView() {
//        statusHeight = ScreenUtils.getStatusHeight(context);
    }

    /**
     * 显示对话框
     *
     * @param text 要显示的文本
     */
    public void showDialog(String text) {
        if (!TextUtils.isEmpty(text)) {
            dialog.setMessage(text);
        }
        //让对话框显示
        dialog.show();
    }

    /**
     * 关闭对话框
     */
    public void closeDialog() {
        dialog.dismiss();
    }

    public void tip(String content) {
        T.showShort(context, content);
    }

    /**
     * session失效的默认解决办法
     */
    public void onSessionInvalid() {
        finish();
    }

}
