package com.example.javaserver.entity;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idrole;

    @Enumerated(EnumType.STRING)
    @Column()
    private ERole rolename;

    public Role() {

    }

    public Role(ERole rolename) {
        this.rolename = rolename;
    }

    public Integer getId() {
        return idrole;
    }

    public void setId(Integer id) {
        this.idrole = id;
    }

    public ERole getName() {
        return rolename;
    }

    public void setName(ERole rolename) {
        this.rolename = rolename;
    }
}