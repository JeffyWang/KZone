package com.kzone.bean;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Jeffy on 14-5-17
 */
@Entity
@Table(name = "k_statistics")
public class Statistics implements Serializable {
    private static final long serialVersionUID = 6489902284170546873L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "user_id", nullable = true, columnDefinition = "int default 0")
    private int userId;

    @Temporal(TemporalType.TIME)
    @Column(name = "create_time")
    private Date createTime;
    @Temporal(TemporalType.TIME)
    @Column(name = "update_time")
    private Date updateTime;

    public Statistics() {

    }

    public Statistics(int userId) {
        this.userId = userId;
        this.createTime = new Date();
        this.updateTime = new Date();
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
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
