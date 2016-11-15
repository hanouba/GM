package com.yoursecondworld.secondworld.common.baseResult;

/**
 * Created by cxj on 2016/9/19.
 * 每一个json返回的实体对象都应该继承的一个类
 */
public class BaseEntity {

    private Integer status;

    private String pass;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "status=" + status +
                ", pass='" + pass + '\'' +
                '}';
    }

}
