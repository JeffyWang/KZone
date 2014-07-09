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

    public void setId(int id) {
        this.id = id;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public String getMunicipalityCode() {
        return municipalityCode;
    }

    public void setMunicipalityCode(String municipalityCode) {
        this.municipalityCode = municipalityCode;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    @Override
    public String toString() {
        return "District{" +
                "id=" + id +
                ", province='" + province + '\'' +
                ", provinceCode='" + provinceCode + '\'' +
                ", municipality='" + municipality + '\'' +
                ", municipalityCode='" + municipalityCode + '\'' +
                ", district='" + district + '\'' +
                ", districtCode='" + districtCode + '\'' +
                '}';
    }
}