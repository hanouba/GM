package com.yoursecondworld.secondworld.common;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.RequestBody;

/**
 * Created by cxj on 2016/8/10.
 */
public class JsonRequestParameter {

    private JSONObject jb = new JSONObject();

    private static JsonRequestParameter jsonRequestParameter = null;

    /**
     * 构造函数私有化
     */
    private JsonRequestParameter() {
    }

    /**
     * 获取单利的对象
     *
     * @return
     */
    public static JsonRequestParameter getInstance() {
        if (jsonRequestParameter == null) {
            jsonRequestParameter = new JsonRequestParameter();
        }
        return jsonRequestParameter;
    }

    /**
     * 添加参数
     *
     * @param key
     * @param value
     */
    public JsonRequestParameter addParameter(String key, String value) {
        try {
            if (key != null && value != null) {
                jb.put(key, value);
            }
        } catch (Exception e) {
        }
        return this;
    }

    public JsonRequestParameter addParameter(String key, int value) {
        try {
            if (key != null) {
                jb.put(key, value);
            }
        } catch (Exception e) {
        }
        return this;
    }

    public JsonRequestParameter addParameter(String key, String[] values) {
        try {
            if (key != null && values != null) {
                JSONArray ja = new JSONArray();
                for (int i = 0; values != null && i < values.length; i++) {
                    if (values[i] != null) {
                        ja.put(values[i]);
                    }
                }
                jb.put(key, ja);
            }
        } catch (Exception e) {
        }
        return this;
    }

    /**
     * 构建出json字符串
     *
     * @return
     */
    public RequestBody build() {
        String s = jb.toString();
//        System.out.println("json请求体:" + s);
        jb = new JSONObject();
        RequestBody requestBody = RequestBody.create(Constant.mediaType, s);
        return requestBody;
    }

    /**
     * 快速构建一个请求对象
     *
     * @param keys
     * @param values
     * @return
     */
    public static RequestBody build(String[] keys, Object[] values) {
        if (keys == null || values == null) {
            throw new NullPointerException("the parameter can not be null");
        }
        JSONObject jb = new JSONObject();
        try {
            for (int i = 0; i < keys.length; i++) {
                Object value = values[i];
                if (value != null) {
                    jb.put(keys[i], value);
                }
            }
        } catch (Exception e) {
        }
        String s = jb.toString();
//        System.out.println("json请求体:" + s);
        RequestBody requestBody = RequestBody.create(Constant.mediaType, s);
        return requestBody;
    }

}
