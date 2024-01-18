package com.neobis.yerokha.beernestspring.controller.rest;

import com.neobis.yerokha.beernestspring.dto.CreateCustomerDto;
import com.neobis.yerokha.beernestspring.dto.Credentials;
import com.neobis.yerokha.beernestspring.dto.UserDto;
import com.neobis.yerokha.beernestspring.service.user.AuthenticationService;
import com.neobis.yerokha.beernestspring.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<UserDto> registerCustomer(@RequestBody CreateCustomerDto dto) {
        return new ResponseEntity<>(userService.registerCustomer(dto), HttpStatus.CREATED);
    }

    @PostMapping("/token")
    public ResponseEntity<String> login(@RequestBody Credentials credentials) {
        return ResponseEntity.ok().body(authenticationService.login(credentials));
    }
}