package com.practice.JournalApp.controller;

import com.practice.JournalApp.service.UserDetailsServiceImpl;
import com.practice.JournalApp.service.UserService;
import com.practice.JournalApp.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/public")
public class PublicController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtUtils jwtUtils;

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
    @GetMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, String password){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            String jwtToken = jwtUtils.generateToken(username);
            return new ResponseEntity<>(jwtToken, HttpStatus.OK);
        } catch (Exception e){
            log.error("Error occurred while creating jwt token", e);
            return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
        }
    }

}
