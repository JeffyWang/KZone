package com.kzone.bean;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Jeffy on 14-5-17
 */
@Entity
@Table(name = "k_district")
public class District implements Serializable {
    private static final long serialVersionUID = -4200002925231788472L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "province", nullable = false, columnDefinition = "varchar(16) default ''")
    private String province;
    @Column(name = "province_code", nullable = false, columnDefinition = "varchar(8) default ''")
    private String provinceCode;
    @Column(name = "municipality", nullable = false, columnDefinition = "varchar(32) default ''")
    private String municipality;
    @Column(name = "municipality_code", nullable = false, columnDefinition = "varchar(8) default ''")
    private String municipalityCode;
    @Column(name = "district", nullable = false, columnDefinition = "varchar(32) default ''")
    private String district;
    @Column(name = "district_code", nullable = false, columnDefinition = "varchar(32) default ''")
    private String districtCode;

    public District() {

    }

    public District(String province, String provinceCode, String municipality, String municipalityCode, String district, String districtCode) {
        this.province = province;
        this.provinceCode = provinceCode;
        this.municipality = municipality;
        this.municipalityCode = municipalityCode;
        this.district = district;
        this.districtCode = districtCode;
    }

    public int getId() {
        return id;
    }

    public String getProvince() {
        return province;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public String getMunicipality() {
        return municipality;
    }

    public String getMunicipalityCode() {
        return municipalityCode;
    }

    public String getDistrict() {
        return district;
    }

    public String getDistrictCode() {
        return districtCode;
    }
}