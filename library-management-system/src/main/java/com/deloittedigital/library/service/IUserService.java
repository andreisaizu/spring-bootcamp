package com.deloittedigital.library.service;

import com.deloittedigital.library.model.domain.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    Optional<User> get(Long id);
    List<User> getAll();
    User add(User user);
}
