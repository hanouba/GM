package com.yoursecondworld.secondworld.test;

import android.graphics.Color;
import android.view.View;

import com.yoursecondworld.secondworld.R;
import com.yoursecondworld.secondworld.common.BaseAct;
import com.yoursecondworld.secondworld.modular.postDynamics.widget.ShowImagesViewForPostDynamics;

import xiaojinzi.annotation.Injection;

/**
 * 测试的界面
 */
@Injection(R.layout.act_test)
public class TestActivity extends BaseAct {

    @Injection(R.id.test)
    private ShowImagesViewForPostDynamics showImagesViewForPostDynamics;

    @Override
    public void initView() {
        super.initView();

        showImagesViewForPostDynamics.setHorizontalIntervalDistance(4);
        showImagesViewForPostDynamics.setVerticalIntervalDistance(4);

        View v = new View(context);
        v.setBackgroundColor(Color.YELLOW);
        showImagesViewForPostDynamics.addView(v);

        View v1 = new View(context);
        v1.setBackgroundColor(Color.GRAY);
        showImagesViewForPostDynamics.addView(v1);

        View v2 = new View(context);
        v2.setBackgroundColor(Color.DKGRAY);
        showImagesViewForPostDynamics.addView(v2);

        View v3 = new View(context);
        v3.setBackgroundColor(Color.BLUE);
        showImagesViewForPostDynamics.addView(v3);

        View v4 = new View(context);
        v4.setBackgroundColor(Color.BLACK);
        showImagesViewForPostDynamics.addView(v4);
    }

    @Override
    public void setOnlistener() {
        super.setOnlistener();
    }

    @Override
    public boolean isRegisterEvent() {
        return false;
    }
}
