package com.yoursecondworld.secondworld.modular.im.selectContacts.entity;

/**
 * Created by cxj on 2016/7/21.
 * 描述联系人的实体对象
 */
public class ImContactsEntity {

    /**
     * 当有这个属性的时候,其他属性都没有,反之
     * 如果这个属性没有,应该有其他属性
     */
    private String tagName;

    /**
     * 头像地址
     */
    private String aVatarAddress;

    /**
     * 联系人的名字
     */
    private String name;

    public ImContactsEntity() {
    }

    public ImContactsEntity(String tagName, String aVatarAddress, String name) {
        this.tagName = tagName;
        this.aVatarAddress = aVatarAddress;
        this.name = name;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getaVatarAddress() {
        return aVatarAddress;
    }

    public void setaVatarAddress(String aVatarAddress) {
        this.aVatarAddress = aVatarAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
