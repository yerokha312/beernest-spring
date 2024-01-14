package com.neobis.yerokha.beernestspring.controller;

import com.neobis.yerokha.beernestspring.dto.CustomerDto;
import com.neobis.yerokha.beernestspring.entity.user.ContactInfo;
import com.neobis.yerokha.beernestspring.service.user.ContactsService;
import com.neobis.yerokha.beernestspring.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


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
    public CustomerDto getCustomer(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        return userService.getCustomerDto(token);
    }

    @PutMapping("/")
    public CustomerDto updateCustomer(@RequestBody CustomerDto customerDto) {
        return userService.updateCustomer(customerDto);
    }

    @PostMapping("/{id}/contacts")
    @ResponseStatus(HttpStatus.CREATED)
    public ContactInfo addContactInfo(@PathVariable Long id, @RequestBody ContactInfo contactInfo) {
        return userService.addContacts(id, contactInfo);
    }

    @GetMapping("/{id}/contacts")
    public List<ContactInfo> getContacts(@PathVariable Long id) {
        return contactsService.getContacts(id);
    }

    @GetMapping("/{customerId}/contacts/{id}")
    public ContactInfo getContactInfo(@PathVariable Long customerId, @PathVariable Long id) {
        return contactsService.getOneContact(customerId, id);
    }

    @DeleteMapping("/")
    public ResponseEntity<String> deleteAccount(@RequestBody Map<String, String> body) {
        userService.setActiveFalse(body);
        return new ResponseEntity<>("Your account successfully deleted", HttpStatus.OK);
    }

    @PutMapping("/recovery")
    public ResponseEntity<String> restoreAccount(@RequestBody Map<String, String> body) {
        userService.setActiveTrue(body);
        return new ResponseEntity<>("Your account successfully restored", HttpStatus.OK);
    }
}
