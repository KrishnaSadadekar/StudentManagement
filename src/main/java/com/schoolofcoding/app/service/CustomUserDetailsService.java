package com.schoolofcoding.app.service;


import com.schoolofcoding.app.exception.UserNotFoundException;
import com.schoolofcoding.app.model.Admin;
import com.schoolofcoding.app.model.Student;
import com.schoolofcoding.app.model.User;
import com.schoolofcoding.app.repository.AdminRepository;
import com.schoolofcoding.app.repository.StudentRepository;
import com.schoolofcoding.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("user not found "));

        System.out.println("In loadbyuser " + user.getEmail());
        System.out.println("Role " + user.getRole().name());
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), List.of(new SimpleGrantedAuthority(user.getRole().name())
        )
        );
    }

}

