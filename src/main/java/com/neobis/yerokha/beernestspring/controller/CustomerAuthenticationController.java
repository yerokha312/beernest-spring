package com.neobis.yerokha.beernestspring.controller;

import com.neobis.yerokha.beernestspring.dto.CreateCustomerDto;
import com.neobis.yerokha.beernestspring.dto.CustomerDto;
import com.neobis.yerokha.beernestspring.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class CustomerAuthenticationController {

    private final UserService userService;

    @Autowired
    public CustomerAuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public CustomerDto registerCustomer(@RequestBody CreateCustomerDto dto) {
        return userService.registerCustomer(dto);
    }
//
//    @PostMapping("/login")
//    public LoginResponse login(@RequestBody LinkedHashMap<String, String> body) {
//        String email = body.get("email");
//        String password = body.get("password");
//        return new LoginResponse;
//
//    }
}