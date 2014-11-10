package com.kzone.bean;

import com.wordnik.swagger.annotations.ApiModelProperty;

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
    @ApiModelProperty(hidden = true)
    private int id;
    @Column(name = "uuid", nullable = false, columnDefinition = "varchar(100) default ''")
    @ApiModelProperty(hidden = true)
    private String uuid;
    @Column(name = "device_id", nullable = true, columnDefinition = "varchar(16) default ''")
    private String deviceId;
    @Column(name = "user_name", nullable = false, columnDefinition = "varchar(16) default ''", unique = true)
    private String userName;
    @Column(name = "password", nullable = false, columnDefinition = "varchar(32) default ''")
    private String password;
    @Column(name = "favorite", nullable = true, columnDefinition = "varchar(128) default ''")
    private String favorite;
    @Column(name = "phone_number", nullable = true, columnDefinition = "varchar(256) default ''")
    private String phoneNumber;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time")
    @ApiModelProperty(hidden = true)
    private Date createTime;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_time")
    @ApiModelProperty(hidden = true)
    private Date updateTime;

    public User() {
        this.createTime = new Date();
        this.updateTime = new Date();
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

    public void setId(int id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFavorite() {
        return favorite;
    }

    public void setFavorite(String favorite) {
        this.favorite = favorite;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", uuid='" + uuid + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", favorite='" + favorite + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
