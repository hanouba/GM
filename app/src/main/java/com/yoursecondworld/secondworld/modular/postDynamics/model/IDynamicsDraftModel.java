package com.yoursecondworld.secondworld.modular.postDynamics.model;

import com.yoursecondworld.secondworld.modular.db.dynamicsDraft.DynamicsDraftDao;

import java.util.List;

/**
 * Created by cxj on 2016/9/12.
 */
public interface IDynamicsDraftModel {

    /**
     * 保存到草稿数据库
     *
     * @param draftDao
     * @param images
     * @param videoPath
     * @param dynamicsContent
     * @param topic
     * @param gameTag
     * @param saveTime
     */
    void save(DynamicsDraftDao draftDao, List<String> images, String videoPath, String dynamicsContent, String topic, String gameTag, String saveTime);


    /**
     * 更新一个草稿
     *
     * @param draftDao
     * @param draftId
     * @param images
     * @param videoPath
     * @param dynamicsContent
     * @param topic
     * @param gameTag
     * @param saveTime
     */
    void update(DynamicsDraftDao draftDao, int draftId, List<String> images, String videoPath, String dynamicsContent, String topic, String gameTag, String saveTime);

}
