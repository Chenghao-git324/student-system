package com.example.studentsysteam.service;

import com.example.studentsysteam.entity.User;

public interface UserService {
    User login(User user);

    String register(User user);

}
