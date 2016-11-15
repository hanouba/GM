package com.yoursecondworld.secondworld.common.resultMsg;

import com.yoursecondworld.secondworld.common.StatusCodeConstant;

/**
 * 返回的每个json外面都包了一个这个对象
 */
public class Msg {

    public static final String OK = "ok";

    public static final String ERROR = "error";


    /**
     * 信息的文本
     */
    private String msgText = OK;

    /**
     * 状态码,默认是表示一切正常<br>
     * {@link StatusCodeConstant#NORMAL}<br>
     * {@link StatusCodeConstant#NORMAL_ERROR}<br>
     * {@link StatusCodeConstant#TOKENINVALID}<br>
     */
    private int statusCode = StatusCodeConstant.NORMAL;

    /**
     * 真实的数据
     */
    private Object data;

    public Msg() {
        super();
    }

    public Msg(int statusCode) {
        this.statusCode = statusCode;
    }

    public Msg(String msgText, int statusCode) {
        this.msgText = msgText;
        this.statusCode = statusCode;
    }

    public Msg(String msgText, int statusCode, Object data) {
        this.msgText = msgText;
        this.statusCode = statusCode;
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsgText() {
        return msgText;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void setMsgText(String msgText) {
        this.msgText = msgText;
    }

    @Override
    public String toString() {
        return "Msg{" +
                "msgText='" + msgText + '\'' +
                ", statusCode=" + statusCode +
                ", data=" + data +
                '}';
    }

}
