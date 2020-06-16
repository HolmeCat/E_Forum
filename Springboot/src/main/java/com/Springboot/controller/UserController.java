package com.Springboot.controller;

import com.Springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {//用户表控制层，主要承接前后端
    @Autowired
    private UserService userService;
    @CrossOrigin
    @GetMapping("/checkByUserName")
    public boolean checkByUserName(@RequestParam("name") String name){//查找用户是否存在
        return userService.checkByUserName(name);
    }
    @CrossOrigin
    @GetMapping("/checkUser")
    public boolean checkUser(@RequestParam("name") String name,
                             @RequestParam("password") String password) { //用户名密码验证
        return userService.checkUser(name,password);
    }
    @CrossOrigin
    @GetMapping("/insertOne")
    public boolean insertOne(@RequestParam("name") String name,
                             @RequestParam("password") String password) { //添加用户
        return userService.saveOne(name,password);
    }
    @CrossOrigin
    @GetMapping("/updatePassword")
    public boolean updatePassword(@RequestParam("name")String name,
                                  @RequestParam("password") String password) { //修改密码
        return userService.saveOne(name,password);
    }
}
