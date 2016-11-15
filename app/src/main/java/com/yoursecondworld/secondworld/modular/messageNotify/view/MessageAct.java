package com.yoursecondworld.secondworld.modular.messageNotify.view;

import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.yoursecondworld.secondworld.R;
import com.yoursecondworld.secondworld.common.BaseFragmentAct;
import com.yoursecondworld.secondworld.modular.messageNotify.fragment.view.MessageFragment;

import xiaojinzi.annotation.Injection;

/**
 * 消息的界面
 */
@Injection(R.layout.act_message)
public class MessageAct extends BaseFragmentAct {

    @Injection(R.id.fl)
    private FrameLayout fl;

    @Override
    public void initView() {
        super.initView();

        FragmentTransaction fs = getSupportFragmentManager().beginTransaction();
        fs.replace(R.id.fl, new MessageFragment());
        fs.commit();

    }

    @Override
    public boolean isRegisterEvent() {
        return false;
    }


}
