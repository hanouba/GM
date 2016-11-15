package com.yoursecondworld.secondworld.common;

import android.content.Context;

/**
 * Created by cxj on 2016/7/11.
 * 颜色的工具类,主要是用了过时的方法,怕到时候改的时候改很多地方
 */
public class ColorUtil {

    /**
     * 返回颜色值
     *
     * @param context
     * @param resId
     * @return
     */
    public static int getColor(Context context, int resId) {
        //封装一层有利于解耦
        return context.getResources().getColor(resId);
    }

}
