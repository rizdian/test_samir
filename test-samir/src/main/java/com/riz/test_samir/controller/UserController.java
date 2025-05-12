package com.riz.test_samir.controller;

import com.riz.test_samir.domain.User;
import com.riz.test_samir.dto.AuthRequest;
import com.riz.test_samir.service.JwtService;
import com.riz.test_samir.service.UserService;
import com.riz.test_samir.web.BaseController;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController extends BaseController {

    private UserService userService;
    private JwtService jwtService;
    private AuthenticationManager authenticationManager;

    @Autowired
    public UserController(UserService userService, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/addUser")
    public Object addUser(@RequestBody User user) {
        userService.addUser(user);
        return buildResponseDataCreated();
    }

    @PostMapping("/login")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }
}
