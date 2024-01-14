package com.neobis.yerokha.beernestspring.controller;

import com.neobis.yerokha.beernestspring.dto.CreateCustomerDto;
import com.neobis.yerokha.beernestspring.dto.CustomerDto;
import com.neobis.yerokha.beernestspring.dto.LoginResponse;
import com.neobis.yerokha.beernestspring.exception.InvalidCredentialsException;
import com.neobis.yerokha.beernestspring.service.user.TokenService;
import com.neobis.yerokha.beernestspring.service.user.UserService;
import com.neobis.yerokha.beernestspring.util.CustomerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;

@RestController
@RequestMapping("/v1")
public class CustomerAuthenticationController {

    private final UserService userService;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public CustomerAuthenticationController(UserService userService, TokenService tokenService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public CustomerDto registerCustomer(@RequestBody CreateCustomerDto dto) {
        return userService.registerCustomer(dto);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LinkedHashMap<String, String> body) {

        String username = body.get("username");
        String password = body.get("password");

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));
            String token = tokenService.generateToken(authentication);
            return new LoginResponse(CustomerMapper.mapToCustomerDto(userService.getCustomerByEmail(username)), token);
        } catch (AuthenticationException e) {
            throw new InvalidCredentialsException("Username or password is incorrect");
        }
    }
}