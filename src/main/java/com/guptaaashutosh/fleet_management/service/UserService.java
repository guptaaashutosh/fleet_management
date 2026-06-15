package com.guptaaashutosh.fleet_management.service;

import com.guptaaashutosh.fleet_management.model.User;

public class UserService {
    private UserRepository repository;
    public User findUser(Long id) {
        return repository.findById(id).orElseThrow();
    }
}
