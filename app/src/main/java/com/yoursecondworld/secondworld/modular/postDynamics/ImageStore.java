package com.yoursecondworld.secondworld.modular.postDynamics;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cxj on 2016/9/1.
 * 这个类用于存储选择好的图片
 * 当选择图片的fragment选择好
 * 之后,或者第三方的相册选择
 * 好之后,都应该存储在这,方便
 * 大家读取
 */
public class ImageStore {

    /**
     * 请求选择图片的请求码
     */
    public static final int REQUEST_SELECT_IMAGE_CODE = 111 * 6;

    /**
     * 最大的图片选择数量
     */
    public static final int MAX_IMAGE_SIZE = 6;

    private static ImageStore my = null;

    public static ImageStore getInstance() {
        if (my == null) {
            my = new ImageStore();
        }
        return my;
    }

    private List<String> images = new ArrayList<String>();

    public void save(List<String> images) {
        this.images.clear();
        this.images.addAll(images);
    }

    public void append(List<String> images) {
        this.images.addAll(images);
    }


    public void append(String image) {
        this.images.add(image);
    }

    public void remove(String image) {
        this.images.remove(image);
    }

    public void remove(int position) {
        this.images.remove(position);
    }


    public List<String> getImages() {
        return images;
    }

    public void clear() {
        this.images.clear();
    }
}
