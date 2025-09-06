package com.practice.JournalApp.controller;

import com.practice.JournalApp.entity.User;
import com.practice.JournalApp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<User> findByUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return new ResponseEntity<>(userService.findUserByUsername(username), HttpStatus.OK);
    }
    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        if (userService.findUserByUsername(user.getUsername()) == null){
            User existingUser = userService.findUserByUsername(username);
            existingUser.setUsername(user.getUsername());
            existingUser.setPassword(user.getPassword());
            userService.updateUser(existingUser);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Username must be unique. Provided username already exists.", HttpStatus.FORBIDDEN);
        }
    }
}
