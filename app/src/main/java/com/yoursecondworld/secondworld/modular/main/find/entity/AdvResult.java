package com.yoursecondworld.secondworld.modular.main.find.entity;

import com.yoursecondworld.secondworld.common.baseResult.BaseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cxj on 2016/9/19.
 */
public class AdvResult extends BaseEntity{

    private List<Adv> banner = new ArrayList<Adv>();

    public List<Adv> getBanner() {
        return banner;
    }

    public void setBanner(List<Adv> banner) {
        this.banner = banner;
    }
}
