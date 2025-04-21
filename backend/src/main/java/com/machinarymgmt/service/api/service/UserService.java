package com.machinarymgmt.service.api.service;

import com.machinarymgmt.service.api.data.model.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByUsername(String username);
}

