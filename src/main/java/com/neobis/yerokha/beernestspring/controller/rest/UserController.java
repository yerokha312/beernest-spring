package com.neobis.yerokha.beernestspring.controller.rest;

import com.neobis.yerokha.beernestspring.dto.UserDto;
import com.neobis.yerokha.beernestspring.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.neobis.yerokha.beernestspring.service.user.TokenService.getUserIdFromAuthToken;


@RestController
@RequestMapping("/v1/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/account")
    public ResponseEntity<UserDto> showProfilePage(Authentication authentication) {
        return ResponseEntity.ok().body(userService.getCustomerDto(getUserIdFromAuthToken(authentication)));
    }

    @PutMapping("/update")
    public ResponseEntity<UserDto> updateProfile(Authentication authentication, @RequestBody UserDto userDto) {
        return ResponseEntity.ok().body(userService
                .updateProfileInformation(getUserIdFromAuthToken(authentication), userDto));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteAccount(Authentication authentication, @RequestBody Map<String, String> body) {
        userService.setActiveFalse(getUserIdFromAuthToken(authentication), body);
        return new ResponseEntity<>("Your account successfully deleted", HttpStatus.OK);
    }

    @PutMapping("/recovery")
    public ResponseEntity<String> restoreAccount(@RequestBody Map<String, String> body) {
        userService.setActiveTrue(body);
        return new ResponseEntity<>("Your account successfully restored", HttpStatus.OK);
    }
}
