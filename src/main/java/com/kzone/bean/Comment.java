package com.kzone.bean;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Jeffy on 14-5-17
 */
@Entity
@Table(name = "k_comment")
public class Comment implements Serializable {
    private static final long serialVersionUID = 5963756913391108116L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "ktv_id", nullable = true, columnDefinition = "varchar(16) default ''")
    private String KTVId;
    @Column(name = "user_id", nullable = true, columnDefinition = "varchar(16) default ''")
    private String userId;
    @Column(name = "user_name", nullable = true, columnDefinition = "varchar(16) default ''")
    private String userName;
    @Column(name = "comment", nullable = true, columnDefinition = "varchar(20480) default ''")
    private String comment;
    @Column(name = "score", nullable = true, columnDefinition = "varchar(32) default 0.0")
    private String score;
    @Column(name = "service_score", nullable = true, columnDefinition = "varchar(32) default 0.0")
    private String serviceScore;
    @Column(name = "environment_score", nullable = true, columnDefinition = "varchar(32) default 0.0")
    private String environmentScore;
    @Column(name = "sound_effects_score", nullable = true, columnDefinition = "varchar(32) default 0.0")
    private String soundEffectsScore;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time")
    private Date createTime;

    public Comment() {
        this.createTime = new Date();
        this.score = "0";
        this.serviceScore = "0";
        this.environmentScore = "0";
        this.soundEffectsScore = "0";
    }

    public Comment(String KTVId, String userId,String userName, String comment,String score, String serviceScore, String environmentScore, String soundEffectsScore) {
        this.KTVId = KTVId;
        this.userId = userId;
        this.userName = userName;
        this.comment = comment;
        this.score = score;
        this.serviceScore = serviceScore;
        this.environmentScore = environmentScore;
        this.soundEffectsScore = soundEffectsScore;
        this.createTime = new Date();
    }

    public int getId() {
        return id;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKTVId() {
        return KTVId;
    }

    public void setKTVId(String KTVId) {
        this.KTVId = KTVId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getServiceScore() {
        return serviceScore;
    }

    public void setServiceScore(String serviceScore) {
        this.serviceScore = serviceScore;
    }

    public String getEnvironmentScore() {
        return environmentScore;
    }

    public void setEnvironmentScore(String environmentScore) {
        this.environmentScore = environmentScore;
    }

    public String getSoundEffectsScore() {
        return soundEffectsScore;
    }

    public void setSoundEffectsScore(String soundEffectsScore) {
        this.soundEffectsScore = soundEffectsScore;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
