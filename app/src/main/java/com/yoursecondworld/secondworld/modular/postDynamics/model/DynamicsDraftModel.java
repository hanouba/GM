package com.yoursecondworld.secondworld.modular.postDynamics.model;

import com.yoursecondworld.secondworld.common.StaticDataStore;
import com.yoursecondworld.secondworld.modular.db.dynamicsDraft.DynamicsDraftDao;
import com.yoursecondworld.secondworld.modular.dynamics.entity.NewDynamics;

import java.util.List;

/**
 * Created by cxj on 2016/9/12.
 */
public class DynamicsDraftModel implements IDynamicsDraftModel {

    @Override
    public void save(DynamicsDraftDao draftDao, List<String> images, String videoPath, String dynamicsContent, String topic, String gameTag, String saveTime) {

        NewDynamics newDynamics = new NewDynamics();
        if (images != null) {
            newDynamics.getPicture_list().addAll(images);
        }

        if (videoPath != null) {
            newDynamics.getVideo_list().add(videoPath);
        }

        newDynamics.setContent(dynamicsContent);
        newDynamics.setType_tag(topic);
        newDynamics.setGame_tag(gameTag);
        newDynamics.setTime(saveTime);

        draftDao.save(StaticDataStore.newUser.getUser_id(), newDynamics);

    }

    @Override
    public void update(DynamicsDraftDao draftDao, int draftId, List<String> images, String videoPath, String dynamicsContent, String topic, String gameTag, String saveTime) {

        NewDynamics newDynamics = new NewDynamics();

        newDynamics.setId(draftId);

        if (images != null) {
            newDynamics.getPicture_list().addAll(images);
        }

        if (videoPath != null) {
            newDynamics.getVideo_list().add(videoPath);
        }

        newDynamics.setContent(dynamicsContent);
        newDynamics.setType_tag(topic);
        newDynamics.setGame_tag(gameTag);
        newDynamics.setTime(saveTime);

        draftDao.update(newDynamics);
    }

}
