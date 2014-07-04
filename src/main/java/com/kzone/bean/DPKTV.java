package com.kzone.bean;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Jeffy on 2014/7/4 0004.
 */
@Entity
@Table(name = "dp_ktv")
public class DpKTV implements Serializable {
    private static final long serialVersionUID = 2402079727904009394L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "price")
    private String price;
    @Column(name = "businessid")
    private String businessid;
    @Column(name = "shopname")
    private String shopname;
    @Column(name = "region")
    private String region;
    @Column(name = "shopadd")
    private String shopadd;
    @Column(name = "bookingtel")
    private String bookingtel;
    @Column(name = "cityid")
    private String cityid;
    @Column(name = "businessarea")
    private String businessarea;
    @Column(name = "datapoi")
    private String datapoi;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBusinessid() {
        return businessid;
    }

    public void setBusinessid(String businessid) {
        this.businessid = businessid;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getShopadd() {
        return shopadd;
    }

    public void setShopadd(String shopadd) {
        this.shopadd = shopadd;
    }

    public String getBookingtel() {
        return bookingtel;
    }

    public void setBookingtel(String bookingtel) {
        this.bookingtel = bookingtel;
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    public String getBusinessarea() {
        return businessarea;
    }

    public void setBusinessarea(String businessarea) {
        this.businessarea = businessarea;
    }

    public String getDatapoi() {
        return datapoi;
    }

    public void setDatapoi(String datapoi) {
        this.datapoi = datapoi;
    }
}
