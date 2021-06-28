package com.example.javaserver.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Entity //xác định lớp hiện tại là một entity.
@Table(name = "user") // xác định tên bảng ánh xạ sang.
@Data
@Getter
@Setter
@SecondaryTables({
        @SecondaryTable(name = "role", pkJoinColumns = @PrimaryKeyJoinColumn(name = "idrole")),
        @SecondaryTable(name = "department", pkJoinColumns = @PrimaryKeyJoinColumn(name = "iddepartment"))
})
public class User implements Serializable {
    private static final long serialVersionUID = -297553281792804396L;

    @Id //xác định thuộc tính hiện tại là ID trong bảng CSDL.
    @GeneratedValue(strategy = GenerationType.IDENTITY) //xác định kiểu sinh khóa chính, ở đây là AUTO_INCREMENT.
    private int id;
    @Column //xác định thuộc tính hiện tại là một cột trong CSDL.
    private String username;
    @Column
    private String password;
    @Column
    private String fullname;
    @Column
    private String phone;
    @Column
    private String sex;
    @Column
    private String iddepartment;
    @Column
    private String idsalary;
    @Column
    private String avatar;
    @Column
    private String birthday;
    @Column
    private boolean active;
    @Column
    private String resetPasswordToken;
    @Column
    private String Address;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }


    @Column(name = "departmentname", table = "department")
    private String departmentname;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "id")
            , inverseJoinColumns = @JoinColumn(name = "idrole")
    )
    private Set<Role> roles = new HashSet<>();

    public User() {
    }

    //
    public User(String username, String fullname, String password) {
        this.username = username;
        this.fullname = fullname;
        this.password = password;
        this.idsalary = "1";
        this.active = true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

}