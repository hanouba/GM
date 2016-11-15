package com.yoursecondworld.secondworld.modular.editGameArticle.ui;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yoursecondworld.secondworld.MyApp;
import com.yoursecondworld.secondworld.R;
import com.yoursecondworld.secondworld.common.BaseAct;

import xiaojinzi.annotation.Injection;
import xiaojinzi.autolayout.utils.AutoUtils;
import xiaojinzi.base.android.os.ScreenUtils;
import xiaojinzi.base.android.os.T;

/**
 * 编辑游戏文章界面
 */
@Injection(R.layout.act_edit_game_article)
public class EditGameArticleAct extends BaseAct {

    @Injection(R.id.rl_root)
    private RelativeLayout rl_root = null;

    @Injection(R.id.rl_content)
    private RelativeLayout rl_content = null;

    /**
     * 标题栏
     */
    @Injection(R.id.rl_act_titlebar)
    private RelativeLayout rl_titlebar = null;

    /**
     * 标题栏中左边的文本
     */
    @Injection(value = R.id.tv_back, click = "clickView")
    private TextView tv_cancel = null;

    /**
     * 标题栏中的标题
     */
    @Injection(R.id.tv_title_name)
    private TextView tv_titleName = null;

    /**
     * 屏幕高度
     */
    private int screenHeight;

    /**
     * 状态栏的高度
     */
    private int stateHeight;

    /**
     * 标题栏高度
     */
    private int titlebarHeight;

    //==============================================================================================

    @Injection(R.id.wv)
    private WebView wv = null;

    @Override
    public void initView() {
        super.initView();
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) rl_titlebar.getLayoutParams();
        lp.topMargin = statusHeight;

        tv_titleName.setText("编辑游戏文章");

    }

    @Override
    public void initData() {
        super.initData();

        initBase();

        controlKeyboardLayout(rl_root, rl_content);

    }

    /**
     * 初始化基本的
     */
    private void initBase() {
        //获取屏幕和状态栏和标题栏的高度
        screenHeight = ScreenUtils.getScreenHeightPixels(context);
        stateHeight = ScreenUtils.getStatusHeight(context);
        titlebarHeight = (int) getResources().getDimension(R.dimen.titlebar_height);
        titlebarHeight = AutoUtils.getPercentHeightSize(titlebarHeight);
    }

    @Override
    public boolean isRegisterEvent() {
        return false;
    }

    /**
     * 点击事件的集中处理
     *
     * @param v
     */
    public void clickView(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv_back:
                finish();
                break;
        }
    }

    /**
     * 在弹出输入法的时候,会动态的调整布局的大小,不会导致我们的试图因为挤压而变形
     *
     * @param root             最外层布局
     * @param needToScrollView 要滚动的布局,就是说在键盘弹出的时候,你需要试图滚动上去的View,在键盘隐藏的时候,他又会滚动到原来的位置的布局
     */
    private void controlKeyboardLayout(final View root, final View needToScrollView) {
        root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            private Rect r = new Rect();

            @Override
            public void onGlobalLayout() {
                //获取当前界面可视部分
                EditGameArticleAct.this.getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
                //获取屏幕的高度
                int screenHeight = EditGameArticleAct.this.getWindow().getDecorView().getRootView().getHeight();
                //此处就是用来获取键盘的高度的， 在键盘没有弹出的时候 此高度为0 键盘弹出的时候为一个正数
                int heightDifference = screenHeight - r.bottom;
                ViewGroup.LayoutParams layoutParams = needToScrollView.getLayoutParams();
                int height = screenHeight - titlebarHeight - heightDifference - stateHeight;
                if (height != layoutParams.height) {
                    layoutParams.height = height;
                    root.requestLayout();
                    if (heightDifference != 0) { //如果此次是要弹出输入法的,那么隐藏fragment

                    }
                }
            }
        });
    }

}
