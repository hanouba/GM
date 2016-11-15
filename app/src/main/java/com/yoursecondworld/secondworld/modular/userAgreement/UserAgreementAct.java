package com.yoursecondworld.secondworld.modular.userAgreement;

import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yoursecondworld.secondworld.R;
import com.yoursecondworld.secondworld.common.BaseAct;

import java.io.IOException;
import java.io.InputStream;

import xiaojinzi.annotation.Injection;
import xiaojinzi.base.java.common.StringUtil;

/**
 * 协议界面
 */
@Injection(R.layout.act_user_agreement)
public class UserAgreementAct extends BaseAct {

    @Injection(R.id.rl_act_titlebar)
    private RelativeLayout rl_titlebar = null;

    @Injection(value = R.id.iv_back, click = "clickView")
    private ImageView iv_back = null;

    @Injection(R.id.tv_title_name)
    private TextView tv_titleName = null;

    //============================================================================

    @Injection(R.id.web)
    private WebView web = null;

    @Override
    public void initView() {
        super.initView();
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) rl_titlebar.getLayoutParams();
        lp.topMargin = statusHeight;

        tv_titleName.setText("用户协议");

    }

    @Override
    public void initData() {
        super.initData();
        web.loadUrl("file:///android_asset/dist/html/protocol.html");
    }

    /**
     * 点击事件的集中处理
     *
     * @param v
     */
    public void clickView(View v) {

        //获取控件的id
        int id = v.getId();

        //对id的筛选然后做处理
        switch (id) {
            case R.id.iv_back:
                finish();
                break;
        }

    }

    @Override
    public boolean isRegisterEvent() {
        return false;
    }

}
