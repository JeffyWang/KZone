package com.kzone.bean;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Jeffy on 2014/5/20 0020.
 */
@Entity
@Table(name = "k_area")
public class Area implements Serializable {
    private static final long serialVersionUID = 7800411985486344124L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "area_id", nullable = false, columnDefinition = "varchar(16) default ''")
    private String areaId;
    @Column(name = "area_name", nullable = false, columnDefinition = "varchar(16) default ''")
    private String areaName;
    @Column(name = "area_code", nullable = false, columnDefinition = "varchar(16) default ''")
    private String areaCode;
    @Column(name = "reference", nullable = false, columnDefinition = "varchar(16) default ''")
    private String reference;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAreaId() {
        return areaId;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
}
