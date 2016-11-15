package com.yoursecondworld.secondworld;

import android.app.Application;

import xiaojinzi.base.android.log.L;

/**
 * Created by cxj on 2016/7/4.
 * 项目的app类
 */
public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //初始化项目需要的组建
        AppConfig.init(this);

    }

}
