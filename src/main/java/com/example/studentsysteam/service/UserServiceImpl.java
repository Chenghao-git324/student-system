package com.example.studentsysteam.service;

import com.example.studentsysteam.entity.User;
import com.example.studentsysteam.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    // BCrypt 加密器：encode() 加密，matches() 验证
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public User login(User user) {
        // 第 1 步：按用户名查出用户（拿到库里存的加密密码）
        User dbUser = userMapper.findByUsername(user.getUsername());

        // 第 2 步：用户名不存在
        if (dbUser == null) {
            return null;
        }

        // 第 3 步：用 BCrypt 比对（用户输入的明文 vs 库里的密文）
        boolean ok = encoder.matches(user.getPassword(), dbUser.getPassword());
        if (!ok) {
            return null;
        }

        // 第 4 步：都通过 → 登录成功
        return dbUser;
    }

    @Override
    public String register(User user) {
        // 第 1 步：检查用户名是否已存在
        User exist = userMapper.findByUsername(user.getUsername());
        if (exist != null) {
            return "用户名已存在";
        }

        // 第 2 步：把明文密码加密成哈希（关键！）
        String hash = encoder.encode(user.getPassword());
        user.setPassword(hash);

        // 第 3 步：存进数据库（存的是哈希，不是明文）
        userMapper.insert(user);

        return "注册成功";
    }
}


