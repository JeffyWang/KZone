package com.kzone.bean;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Jeffy on 14-5-17
 */
@Entity
@Table(name = "k_information")
public class Information implements Serializable {
    private static final long serialVersionUID = 607898763447181238L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "title", nullable = true, columnDefinition = "varchar(128) default ''")
    private String title;
    @Column(name = "article", nullable = true, columnDefinition = "varchar(10240) default ''")
    private String article;
    @Column(name = "pictures", nullable = true, columnDefinition = "varchar(4096) default ''")
    private String pictures;
    @Column(name = "link", nullable = true, columnDefinition = "varchar(256) default ''")
    private String link;
    @Temporal(TemporalType.TIME)
    @Column(name = "create_time")
    private Date createTime;
    @Temporal(TemporalType.TIME)
    @Column(name = "update_time")
    private Date updateTime;

    public Information() {

    }

    public Information(String title, String article, String pictures, String link) {
        this.title = title;
        this.article = article;
        this.pictures = pictures;
        this.link = link;
        this.createTime = new Date();
        this.updateTime = new Date();
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getPictures() {
        return pictures;
    }

    public void setPictures(String pictures) {
        this.pictures = pictures;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}