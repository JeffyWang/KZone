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
    @Column(name = "ktv_id", nullable = true, columnDefinition = "int default 0")
    private int KTVId;
    @Column(name = "user_id", nullable = true, columnDefinition = "int default 0")
    private int userId;
    @Column(name = "comment", nullable = true, columnDefinition = "varchar(20480) default ''")
    private String comment;
    @Column(name = "service_score", nullable = true, columnDefinition = "float default 0.0")
    private float serviceScore;
    @Column(name = "environment_score", nullable = true, columnDefinition = "float default 0.0")
    private float environmentScore;
    @Column(name = "sound_effects_score", nullable = true, columnDefinition = "float default 0.0")
    private float soundEffectsScore;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time")
    private Date createTime;

    public Comment() {
        this.createTime = new Date();
    }

    public Comment(int KTVId, int userId, String comment, float serviceScore, float environmentScore, float soundEffectsScore) {
        this.KTVId = KTVId;
        this.userId = userId;
        this.comment = comment;
        this.serviceScore = serviceScore;
        this.environmentScore = environmentScore;
        this.soundEffectsScore = soundEffectsScore;
        this.createTime = new Date();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getKTVId() {
        return KTVId;
    }

    public void setKTVId(int KTVId) {
        this.KTVId = KTVId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public float getServiceScore() {
        return serviceScore;
    }

    public void setServiceScore(float serviceScore) {
        this.serviceScore = serviceScore;
    }

    public float getEnvironmentScore() {
        return environmentScore;
    }

    public void setEnvironmentScore(float environmentScore) {
        this.environmentScore = environmentScore;
    }

    public float getSoundEffectsScore() {
        return soundEffectsScore;
    }

    public void setSoundEffectsScore(float soundEffectsScore) {
        this.soundEffectsScore = soundEffectsScore;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
