package com.kzone.bean;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Jeffy on 14-4-24.
 */
@Entity
@Table(name = "k_user")
public class User implements Serializable {
    private static final long serialVersionUID = 7650194935594742094L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "uuid", nullable = false, columnDefinition = "varchar(100) default ''")
    private String uuid;
    @Column(name = "user_name", nullable = false, columnDefinition = "varchar(16) default ''", unique = true)
    private String userName;
    @Column(name = "password", nullable = false, columnDefinition = "varchar(32) default ''")
    private String password;
    @Temporal(TemporalType.TIME)
    @Column(name = "create_time")
    private Date createTime;
    @Temporal(TemporalType.TIME)
    @Column(name = "update_time")
    private Date updateTime;

    public User() {

    }

    public User(String uuid, String userName, String password) {
        this.uuid = uuid;
        this.userName = userName;
        this.password = password;
        this.createTime = new Date();
        this.updateTime = new Date();
    }

    public int getId() {
        return id;
    }

    public String getUuid() {
        return uuid;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
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
