package com.yoursecondworld.secondworld.modular.selectPostGame.entity;

import com.yoursecondworld.secondworld.common.baseResult.BaseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cxj on 2016/10/24.
 */
public class GamesResult extends BaseEntity {


    private List<String> games = new ArrayList<String>();

    public List<String> getGames() {
        return games;
    }

    public void setGames(List<String> games) {
        this.games = games;
    }

}
