package com.yoursecondworld.secondworld.modular.im;

/**
 * Created by cxj on 2016/7/16.
 * 即时通讯里面的用户概念的信息
 */
public class ImUserInfo {

    //对应每一个userID的标识
    private String token;

    //名称
    private String name;

    //头像地址
    private String portraitUri;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPortraitUri() {
        return portraitUri;
    }

    public void setPortraitUri(String portraitUri) {
        this.portraitUri = portraitUri;
    }

}
