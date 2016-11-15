package com.yoursecondworld.secondworld.modular.systemInfo.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by cxj on 2016/7/16.
 * 作为应用的描述用户的信息
 */
public class User {

    /**
     * 表示用户在app中的唯一标识
     */
    private Integer id;

    /**
     * 用于即时聊天的token
     */
    private String imToken;

    /**
     * 客户端应该持有的token,这个token会代表这个用户<br/>
     * token在登陆成功后交给用户,如果app退出了,应该调用loginOut方法来登出来销毁当前的生成的token<br/>
     * 为了防止恶心的对服务器进行攻击,所以设计了这个,token在登陆成功的时候自动生成<br/>
     * 是一个和时间戳有关的加密48位字符串,攻击者肯定拿不到这个值得,同时也是可以利用这个token防止在多个设备上登陆<br/>
     * 如果用户退出app是被强制杀死的,那么肯定不会调用登出的方法,下次登陆token会使用之前生成的那个token
     */
    private String clientToken;

    /**
     * 电话号码
     */
    private String phoneNumber;

    /**
     * 用户名
     */
    private String name;

    /**
     * 用户的简介
     */
    private String description;

    /**
     * 性别<br/>
     * 0:男性<br/>
     * 1:女性
     */
    private String sex;

    /**
     * 密码
     */
    private String password;

    /**
     * 头像
     */
    private String avatarAddress;

    /**
     * qq的唯一标识
     */
    private String qqId;

    /**
     * 微博唯一标识
     */
    private String weiboId;

    /**
     * 微信唯一标识
     */
    private String weixinId;

    /**
     * 用户选择的游戏标签
     */
    private List<GameLabel> gameLabels = new ArrayList<GameLabel>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImToken() {
        return imToken;
    }

    public void setImToken(String imToken) {
        this.imToken = imToken;
    }

    public String getClientToken() {
        return clientToken;
    }

    public void setClientToken(String clientToken) {
        this.clientToken = clientToken;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatarAddress() {
        return avatarAddress;
    }

    public void setAvatarAddress(String avatarAddress) {
        this.avatarAddress = avatarAddress;
    }

    public String getQqId() {
        return qqId;
    }

    public void setQqId(String qqId) {
        this.qqId = qqId;
    }

    public String getWeiboId() {
        return weiboId;
    }

    public void setWeiboId(String weiboId) {
        this.weiboId = weiboId;
    }

    public String getWeixinId() {
        return weixinId;
    }

    public void setWeixinId(String weixinId) {
        this.weixinId = weixinId;
    }

    public List<GameLabel> getGameLabels() {
        return gameLabels;
    }

    public void setGameLabels(List<GameLabel> gameLabels) {
        this.gameLabels = gameLabels;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", imToken='" + imToken + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", avatarAddress='" + avatarAddress + '\'' +
                ", qqId='" + qqId + '\'' +
                ", weiboId='" + weiboId + '\'' +
                ", weixinId='" + weixinId + '\'' +
                '}';
    }
}
