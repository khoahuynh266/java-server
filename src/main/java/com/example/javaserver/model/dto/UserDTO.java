package com.example.javaserver.model.dto;

import com.example.javaserver.entity.ERole;
import com.example.javaserver.entity.Role;
import com.example.javaserver.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;

    private String username;

    private String fullname;
 
    private String phone;
 
    private String sex;
 
    private String iddepartment;
 
    private String idsalary;
 
    private String avatar;
 
    private String birthday;
 
    private String address;

    private Boolean active;
    private ERole rolename;
    private  Set<Role> roles;
}