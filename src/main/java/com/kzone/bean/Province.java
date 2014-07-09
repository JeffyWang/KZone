package com.kzone.bean;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Jeffy on 2014/5/20 0020.
 */
@Entity
@Table(name = "k_province")
public class Province implements Serializable {
    private static final long serialVersionUID = -4505853608068080507L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "province_id", nullable = false, columnDefinition = "varchar(16) default ''")
    private String provinceId;
    @Column(name = "province_name", nullable = false, columnDefinition = "varchar(16) default ''")
    private String provinceName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    @Override
    public String toString() {
        return "Province{" +
                "id=" + id +
                ", provinceId='" + provinceId + '\'' +
                ", provinceName='" + provinceName + '\'' +
                '}';
    }
}
