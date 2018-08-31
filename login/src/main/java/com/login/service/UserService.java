package com.login.service;

import com.login.model.User;

public interface UserService {
    User saveUser(User user);
    User findByEmail(String email);
}
