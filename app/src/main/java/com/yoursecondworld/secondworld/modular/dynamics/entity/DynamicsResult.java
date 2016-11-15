package com.yoursecondworld.secondworld.modular.dynamics.entity;

import com.yoursecondworld.secondworld.common.baseResult.BaseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cxj on 2016/9/19.
 */
public class DynamicsResult extends BaseEntity {

    private NewDynamics article;

    public NewDynamics getArticle() {
        return article;
    }

    public void setArticle(NewDynamics article) {
        this.article = article;
    }

    private List<NewDynamics> articles = new ArrayList<NewDynamics>();

    public List<NewDynamics> getArticles() {
        return articles;
    }

    public void setArticles(List<NewDynamics> articles) {
        this.articles = articles;
    }

}
