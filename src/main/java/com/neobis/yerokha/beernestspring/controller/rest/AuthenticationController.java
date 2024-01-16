package com.neobis.yerokha.beernestspring.controller.rest;

import com.neobis.yerokha.beernestspring.dto.CreateCustomerDto;
import com.neobis.yerokha.beernestspring.dto.UserDto;
import com.neobis.yerokha.beernestspring.service.user.AuthenticationService;
import com.neobis.yerokha.beernestspring.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;

@RestController
@RequestMapping("/v1")
public class AuthenticationController {

    private final UserService userService;
    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(UserService userService,
                                    AuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public UserDto registerCustomer(@RequestBody CreateCustomerDto dto) {
        return userService.registerCustomer(dto);
    }

    @PostMapping("/token")
    public String login(@RequestBody LinkedHashMap<String, String> body) {
        return authenticationService.login(body);


    }
}