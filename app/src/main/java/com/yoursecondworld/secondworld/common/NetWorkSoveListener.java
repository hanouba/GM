package com.yoursecondworld.secondworld.common;

/**
 * Created by cxj on 2016/9/19.
 */
public interface NetWorkSoveListener<T> {

    void success(T t);

    void fail(String msg);

}
