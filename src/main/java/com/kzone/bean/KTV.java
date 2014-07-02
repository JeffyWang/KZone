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
    @Column(name = "district_id", nullable = true, columnDefinition = "varchar(128) default ''")
    private String districtId;
    @Column(name = "name", nullable = false, columnDefinition = "varchar(128) default ''")
    private String name;
    @Column(name = "address", nullable = true, columnDefinition = "varchar(512) default ''")
    private String address;
    @Column(name = "phone_number", nullable = true, columnDefinition = "varchar(16) default ''")
    private String phoneNumber;
    @Column(name = "introduction", nullable = true, columnDefinition = "varchar(20480) default ''")
    private String introduction;
    @Column(name = "average_price", nullable = true, columnDefinition = "int default 0")
    private int averagePrice;
    @Column(name = "score", nullable = true, columnDefinition = "varchar(32) default 0.0")
    private String  score;
    @Column(name = "price", nullable = true, columnDefinition = "varchar(32) default 0.0")
    private String  price;
    @Column(name = "pictures", nullable = true, columnDefinition = "text")
    private String pictures;
    @Column(name = "geographic_information", nullable = true, columnDefinition = "varchar(32) default ''")
    private String geographicInformation;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time")
    private Date createTime;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_time")
    private Date updateTime;

    public KTV() {
        this.createTime = new Date();
        this.updateTime = new Date();
        this.score = "0";
    }

    public KTV(String districtId, String name, String address, String phoneNumber, String introduction, int averagePrice, String score, String pictures, String geographicInformation) {
        this.districtId = districtId;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.introduction = introduction;
        this.averagePrice = averagePrice;
        this.score = score;
        this.pictures = pictures;
        this.geographicInformation = geographicInformation;
        this.createTime = new Date();
        this.updateTime = new Date();
    }

    public KTV(String districtId, String name, String address, String phoneNumber) {
        this.districtId = districtId;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.createTime = new Date();
        this.updateTime = new Date();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
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

    public int getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(int averagePrice) {
        this.averagePrice = averagePrice;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getPictures() {
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

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}