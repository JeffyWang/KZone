package com.kzone.bean;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Jeffy on 2014/5/20 0020.
 */
@Entity
@Table(name = "k_city")
public class City implements Serializable {
    private static final long serialVersionUID = -238533214876931980L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "city_id", nullable = false, columnDefinition = "varchar(16) default ''")
    private String cityId;
    @Column(name = "city_name", nullable = false, columnDefinition = "varchar(16) default ''")
    private String cityName;
    @Column(name = "reference", nullable = false, columnDefinition = "varchar(16) default ''")
    private String reference;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
