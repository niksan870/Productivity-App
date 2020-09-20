package com.example.polls.service;

import com.example.polls.exception.ResourceNotFoundException;
import com.example.polls.model.User;
import com.example.polls.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
public class UserPrincipal {

    @Autowired
    private UserRepository userRepository;

    public User getCurrentUserPrincipal() {
        Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();
        String username = currentAuth.getPrincipal().toString();
        return userRepository.findByUsername(currentAuth.getName())
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
    }
}
