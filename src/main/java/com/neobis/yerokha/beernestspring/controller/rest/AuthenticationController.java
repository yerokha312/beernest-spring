package com.neobis.yerokha.beernestspring.controller.rest;

import com.neobis.yerokha.beernestspring.dto.CreateCustomerDto;
import com.neobis.yerokha.beernestspring.dto.Credentials;
import com.neobis.yerokha.beernestspring.dto.UserDto;
import com.neobis.yerokha.beernestspring.service.user.AuthenticationService;
import com.neobis.yerokha.beernestspring.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication", description = "Public entry point of the application")
@ApiResponse(responseCode = "403", description = "Only anonymous users have access", content = @Content)
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

    @Operation(
            summary = "Registration",
            description = "Endpoint for customer to register a new account. Requires a body",
            tags = {"users", "post"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Customer successfully registered"),
            @ApiResponse(responseCode = "409", description = "The email provided is already taken", content = @Content)
    })
    @PostMapping("/register")
    public ResponseEntity<UserDto> registerCustomer(@RequestBody CreateCustomerDto dto) {
        return new ResponseEntity<>(userService.registerCustomer(dto), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Login",
            description = "Endpoint for getting tokens after login",
            tags = {"users", "post"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully returned a token"),
            @ApiResponse(responseCode = "401", description = "Username or password is invalid", content = @Content)
    })
    @PostMapping("/token")
    public ResponseEntity<String> login(@RequestBody Credentials credentials) {
        return ResponseEntity.ok().body(authenticationService.login(credentials));
    }
}