package com.yoursecondworld.secondworld.modular.prepareModule.selectGame.entity;

import com.yoursecondworld.secondworld.modular.systemInfo.entity.GameLabel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by cxj on 2016/8/4.
 */
public class GameLabelResultEntity {

    private List<GameLabel> data = new ArrayList<GameLabel>();

    public List<GameLabel> getData() {
        return data;
    }

    public void setData(List<GameLabel> data) {
        this.data = data;
    }
}
