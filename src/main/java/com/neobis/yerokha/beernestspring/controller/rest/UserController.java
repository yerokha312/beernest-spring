package com.neobis.yerokha.beernestspring.controller.rest;

import com.neobis.yerokha.beernestspring.dto.UserDto;
import com.neobis.yerokha.beernestspring.entity.user.ContactInfo;
import com.neobis.yerokha.beernestspring.service.user.ContactsService;
import com.neobis.yerokha.beernestspring.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import static com.neobis.yerokha.beernestspring.service.user.TokenService.getUserIdFromAuthToken;


@RestController
@RequestMapping("/v1/users")
public class UserController {

    private final UserService userService;
    private final ContactsService contactsService;

    @Autowired
    public UserController(UserService userService, ContactsService contactsService) {
        this.userService = userService;
        this.contactsService = contactsService;
    }

    @GetMapping("/account")
    public UserDto showProfilePage(Authentication authentication) {
        return userService.getCustomerDto(getUserIdFromAuthToken(authentication));
    }

    @PutMapping("/update")
    public UserDto updateProfile(Authentication authentication, @RequestBody UserDto userDto) {
        return userService.updateProfileInformation(getUserIdFromAuthToken(authentication), userDto);
    }

    @PostMapping("/contacts")
    @ResponseStatus(HttpStatus.CREATED)
    public ContactInfo addContactInfo(Authentication authentication, @RequestBody ContactInfo contactInfo) {
        return userService.addContacts(getUserIdFromAuthToken(authentication), contactInfo);
    }

    @GetMapping("/contacts")
    public List<ContactInfo> getContactInfoList(Authentication authentication) {
        return contactsService.getContacts(getUserIdFromAuthToken(authentication));
    }

    @GetMapping("/contacts/{id}")
    public ContactInfo getContactInfo(Authentication authentication, @PathVariable Long id) {
        return contactsService.getOneContact(getUserIdFromAuthToken(authentication), id);
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
