package com.practice.JournalApp.controller;

import com.practice.JournalApp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public")
public class PublicController {

    private final UserService userService;

    @GetMapping("/health-check")
    public ResponseEntity<String> healthCheck(){
        return ResponseEntity.ok("SUCCESS");
    }
    @PostMapping("/register")
    public ResponseEntity<?> createNewUser(@RequestParam String username, @RequestParam String password){
        if (userService.findUserByUsername(username) == null){
            userService.createNewUser(username, password);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>("username already exists. ",HttpStatus.FORBIDDEN);
    }

}
