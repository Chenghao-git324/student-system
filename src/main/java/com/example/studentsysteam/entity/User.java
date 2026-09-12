package com.example.studentsysteam.entity;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String username;
    private String password;
    private String role;   // 角色：admin=管理员 / user=普通用户
}
