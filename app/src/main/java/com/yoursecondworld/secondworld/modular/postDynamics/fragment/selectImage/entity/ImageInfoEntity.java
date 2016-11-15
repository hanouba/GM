package com.yoursecondworld.secondworld.modular.postDynamics.fragment.selectImage.entity;

/**
 * Created by cxj on 2016/7/29.
 * 描述图片的实体对象
 */
public class ImageInfoEntity {

    /**
     * 图片的本地地址
     */
    private String localPath;

    /**
     * 被选中的下标,如果是-1,说明是没有被选中
     * 这个是标志在选中的图片中的第几个
     */
    private int selectedPotion = -1;

    /**
     * 这个的上一个被选中的图片对应的实体对象在集合中的位置
     */
    private int prePosition = -1;

    /**
     * 加入这个图片被选中了之后,下一个图片也被选中了,那么下一张的图片下标会被记录到这个实体对象中
     * 方便取消选中某一张的时候
     */
    private int nextPotion = -1;

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public int getSelectedPotion() {
        return selectedPotion;
    }

    public void setSelectedPotion(int selectedPotion) {
        this.selectedPotion = selectedPotion;
    }

    public int getPrePosition() {
        return prePosition;
    }

    public void setPrePosition(int prePosition) {
        this.prePosition = prePosition;
    }

    public int getNextPotion() {
        return nextPotion;
    }

    public void setNextPotion(int nextPotion) {
        this.nextPotion = nextPotion;
    }

    /**
     * 还原状态
     */
    public void reStore() {
        selectedPotion = -1;
        prePosition = -1;
        nextPotion = -1;
    }

}