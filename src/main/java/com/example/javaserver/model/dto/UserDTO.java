package com.example.javaserver.model.dto;

import com.example.javaserver.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;

    private String username;

    private String fullname;

    private int role;

    private String phone;

    private String sex;

    private String idsalary;

    private String avatar;

    private String birthday;

    private String rolename;

    private String departmentname;
}