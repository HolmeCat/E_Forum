package com.Springboot.service;

public interface UserService {
    boolean checkByUserName(String name);//查找用户是否存在
    boolean checkUser(String name,String password);//用户名密码验证
    boolean saveOne(String name,String password);//增改用户
}
