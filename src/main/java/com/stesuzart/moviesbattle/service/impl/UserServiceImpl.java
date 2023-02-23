package com.stesuzart.moviesbattle.service.impl;

import com.stesuzart.moviesbattle.entity.User;
import com.stesuzart.moviesbattle.repository.UserRepository;
import com.stesuzart.moviesbattle.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUser() {
        final var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findUserByUsername(username);
    }
}
