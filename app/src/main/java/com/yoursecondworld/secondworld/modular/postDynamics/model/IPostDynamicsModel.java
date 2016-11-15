package com.yoursecondworld.secondworld.modular.postDynamics.model;

import java.io.File;
import java.util.List;

/**
 * Created by cxj on 2016/8/24.
 */
public interface IPostDynamicsModel {


    /**
     * 对路径的图片信息转化成文件的集合
     *
     * @param images
     * @return
     */
    List<File> converse(List<String> images);

}
