package com.yoursecondworld.secondworld.modular.main.find.entity;

/**
 * Created by cxj on 2016/8/17.
 */
public class Adv {

    /**
     * 流水id
     */
    private Integer id;

    /**
     * 广告图片地址
     */
    private String image;

    /**
     * 链接地址
     */
    private String href;


    /**
     * 文章Id
     */
    private String article_id;

    private String title;

    private String hyper_text;

    /**
     * 简单的描述
     */
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getArticle_id() {
        return article_id;
    }

    public void setArticle_id(String article_id) {
        this.article_id = article_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHyper_text() {
        return hyper_text;
    }

    public void setHyper_text(String hyper_text) {
        this.hyper_text = hyper_text;
    }
}
