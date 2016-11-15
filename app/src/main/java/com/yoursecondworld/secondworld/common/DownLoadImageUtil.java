package com.yoursecondworld.secondworld.common;

import android.os.Handler;
import android.os.Message;

import java.io.File;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import xiaojinzi.base.java.io.FileUtil;

/**
 * Created by cxj on 2016/9/21.
 */
public class DownLoadImageUtil {

    private Handler h = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            int what = msg.what;
            if (what == 0) { //表示成功
                mListener.onSuccess(bytes);
            } else {
                mListener.onFail();
            }

        }
    };

    private byte[] bytes;

    private OnDownLoadListener mListener;

    public interface OnDownLoadListener {

        void onSuccess(byte[] bytes);

        void onFail();
    }

    public void downLoadImage(final String url, final OnDownLoadListener listener) {

        mListener = listener;

        new Thread() {
            @Override
            public void run() {
                try {
                    OkHttpClient o = new OkHttpClient();
                    //创建请求对象
                    Request request = new Request.Builder()
                            .url(url)
                            .build();

                    //执行请求返回结果
                    Response response = o.newCall(request).execute();
                    //拿到

                    bytes = response.body().bytes();

                    h.sendEmptyMessage(0);


                } catch (Exception e) {
                    h.sendEmptyMessage(1);
                }
            }
        }.start();

    }

}
