package com.yoursecondworld.secondworld.modular.main.popupWindow.share;

/**
 * Created by cxj on 2016/7/20.
 * 这个实体对象描述分享的图标和文字
 */
public class ShareEntity {

    private int imageRsd;

    private String name;

    public ShareEntity(int imageRsd, String name) {
        this.imageRsd = imageRsd;
        this.name = name;
    }

    public int getImageRsd() {
        return imageRsd;
    }

    public void setImageRsd(int imageRsd) {
        this.imageRsd = imageRsd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
