package com.kzone.bo;

/**
 * Created by jeffy on 2014/6/8 0008.
 */
public class Article {
    private int informationId;
    private String article;

    public Article(int informationId, String article) {
        this.informationId = informationId;
        this.article = article;
    }

    public int getInformationId() {
        return informationId;
    }

    public void setInformationId(int informationId) {
        this.informationId = informationId;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }
}
