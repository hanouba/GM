package com.yoursecondworld.secondworld.modular.postDynamics.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cxj on 2016/8/24.
 */
public class PostDynamicsModel implements IPostDynamicsModel {

    @Override
    public List<File> converse(List<String> images) {
        List<File> files = new ArrayList<File>();
        int size = images.size();
        for (int i = 0; i < size; i++) {
            File f = new File(images.get(i));
            if (f.exists() && f.isFile()) {
                files.add(f);
            }
        }
        return files;
    }


}
