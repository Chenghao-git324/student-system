package com.example.studentsysteam.controller;

import com.example.studentsysteam.entity.User;
import com.example.studentsysteam.service.UserService;
import com.example.studentsysteam.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        User result = userService.login(user);

        if (result == null) {
            return "账号或密码错误";
        }

        // 登录成功 → 拿用户名生成一个 token 返回
        return jwtUtil.createToken(result.getUsername(), result.getRole());
    }

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        return userService.register(user);
    }

}

