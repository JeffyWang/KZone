package com.kzone.bean;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Jeffy on 14-5-17
 */
@Entity
@Table(name = "k_ktv")
public class KTV implements Serializable {
    private static final long serialVersionUID = 530433493257041219L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "district_id", nullable = true, columnDefinition = "int default 0")
    private int districtId;
    @Column(name = "name", nullable = false, columnDefinition = "varchar(128) default ''")
    private String name;
    @Column(name = "address", nullable = true, columnDefinition = "varchar(256) default ''")
    private String address;
    @Column(name = "phone_number", nullable = true, columnDefinition = "varchar(16) default ''")
    private String phoneNumber;
    @Column(name = "introduction", nullable = true, columnDefinition = "varchar(10240) default ''")
    private String introduction;
    @Column(name = "score", nullable = true, columnDefinition = "float default 0.0")
    private float score;
    @Column(name = "pictures", nullable = true, columnDefinition = "varchar(4096) default ''")
    private String pictures;
    @Column(name = "geographic_information", nullable = true, columnDefinition = "varchar(32) default ''")
    private String geographicInformation;
    @Temporal(TemporalType.TIME)
    @Column(name = "create_time")
    private Date createTime;
    @Temporal(TemporalType.TIME)
    @Column(name = "update_time")
    private Date updateTime;

    public KTV() {

    }

    public KTV(int districtId, String name, String address, String phoneNumber, String introduction, float score, String pictures, String geographicInformation) {
        this.districtId = districtId;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.introduction = introduction;
        this.score = score;
        this.pictures = pictures;
        this.geographicInformation = geographicInformation;
        this.createTime = new Date();
        this.updateTime = new Date();
    }

    public int getId() {
        return id;
    }

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public Object getPictures() {
        return pictures;
    }

    public void setPictures(String pictures) {
        this.pictures = pictures;
    }

    public String getGeographicInformation() {
        return geographicInformation;
    }

    public void setGeographicInformation(String geographicInformation) {
        this.geographicInformation = geographicInformation;
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