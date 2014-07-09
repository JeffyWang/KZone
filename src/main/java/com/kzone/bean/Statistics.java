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
    @Column(name = "os_type", nullable = false, columnDefinition = "varchar(128) default ''")
    private String osType;
    @Column(name = "os_version", nullable = false, columnDefinition = "varchar(128) default ''")
    private String osVersion;
    @Column(name = "app_version", nullable = false, columnDefinition = "varchar(128) default ''")
    private String appVersion;
    @Column(name = "app_name", nullable = false, columnDefinition = "varchar(128) default ''")
    private String appName;
    @Column(name = "token", nullable = false, columnDefinition = "varchar(128) default ''")
    private String token;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time")
    private Date createTime;

    public Statistics() {
        this.createTime = new Date();
    }

    public Statistics(int userId) {
        this.userId = userId;
        this.createTime = new Date();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getOsType() {
        return osType;
    }

    public void setOsType(String osType) {
        this.osType = osType;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "Statistics{" +
                "id=" + id +
                ", userId=" + userId +
                ", osType='" + osType + '\'' +
                ", osVersion='" + osVersion + '\'' +
                ", appVersion='" + appVersion + '\'' +
                ", appName='" + appName + '\'' +
                ", token='" + token + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
