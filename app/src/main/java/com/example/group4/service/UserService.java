package com.example.group4.service;

import com.example.group4.entity.User;

public interface UserService {
    public Boolean login(String username,String password);

    public Boolean register(String username,String password);

    public User getUserByName(String username);
}
