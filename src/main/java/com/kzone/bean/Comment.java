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
    @Temporal(TemporalType.TIME)
    @Column(name = "create_time")
    private Date createTime;

    public Comment() {

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

    public int getKTVId() {
        return KTVId;
    }

    public int getUserId() {
        return userId;
    }

    public String getComment() {
        return comment;
    }

    public float getServiceScore() {
        return serviceScore;
    }

    public float getEnvironmentScore() {
        return environmentScore;
    }

    public float getSoundEffectsScore() {
        return soundEffectsScore;
    }

    public Date getCreateTime() {
        return createTime;
    }
}
