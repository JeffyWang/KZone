package com.kzone.bean;

import javax.persistence.*;

/**
 * Created by Jeffy on 14-4-24.
 */
@Entity
@Table(name = "k_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "uuid", nullable = false, columnDefinition = "varchar(100) default ''")
    private String uuid;
    @Column(name = "name", nullable = false, columnDefinition = "varchar(16) default ''", unique = true)
    private String name;
    @Column(name = "password", nullable = false, columnDefinition = "varchar(32) default ''")
    private String password;

    public int getId() {
        return id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
