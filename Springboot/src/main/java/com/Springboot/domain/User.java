package com.Springboot.domain;

import javax.persistence.*;

@Entity
@Table(name = "User")
public class User { //用户表
    @Id //主键
    private String name; //用户名
    @Column(nullable = false)
    private String password; //密码
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return password;
    }
}
