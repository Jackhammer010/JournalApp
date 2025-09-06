package com.practice.JournalApp.service;

import com.practice.JournalApp.entity.User;
import com.practice.JournalApp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User findUserByUsername(String username){
        return userRepository.findByUsername(username);
    }
    public void saveUser(User user){
        userRepository.save(user);
    }
    @Transactional
    public void createNewUser(String username, String password){
        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(username, encodedPassword);
        user.setRoles(List.of("USER"));
        userRepository.insert(user);
    }
    public void updateUser(User user){
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        saveUser(user);
    }
    public void deleteUserByUsername(String username){
        userRepository.deleteByUsername(username);
    }
}
