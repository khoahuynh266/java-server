package com.example.javaserver.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity //xác định lớp hiện tại là một entity.
@Table(name = "department") // xác định tên bảng ánh xạ sang.
@Data
public class Department implements Serializable {
    private static final long serialVersionUID = -297553281792804396L;

    @Id //xác định thuộc tính hiện tại là ID trong bảng CSDL.
    @GeneratedValue(strategy = GenerationType.IDENTITY) //xác định kiểu sinh khóa chính, ở đây là AUTO_INCREMENT.
    private String iddepartment;


    @Column
    private String departmentname;
    @Column
    private String managerid;
    @Column
    private String departmentphone;
    @Column
    private String location;

}
