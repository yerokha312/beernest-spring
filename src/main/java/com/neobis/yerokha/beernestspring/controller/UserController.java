package com.neobis.yerokha.beernestspring.controller;

import com.neobis.yerokha.beernestspring.dto.CustomerDto;
import com.neobis.yerokha.beernestspring.entity.user.ContactInfo;
import com.neobis.yerokha.beernestspring.service.user.ContactsService;
import com.neobis.yerokha.beernestspring.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.neobis.yerokha.beernestspring.service.user.UserService.PAGE_SIZE;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final ContactsService contactsService;

    @Autowired
    public UserController(UserService userService, ContactsService contactsService) {
        this.userService = userService;
        this.contactsService = contactsService;
    }

    @GetMapping("/{id}")
    public CustomerDto getCustomerById(@PathVariable Long id) {
        return userService.getCustomerDtoById(id);
    }

    @GetMapping("/")
    public Page<CustomerDto> getAllCustomers() {
        return userService.getAllCustomerDtos(Pageable.ofSize(PAGE_SIZE));
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
    private List<ContactInfo> getContacts(@PathVariable Long id) {
        return contactsService.getContacts(id);
    }

    @GetMapping("/{customerId}/contacts/{id}")
    private ContactInfo getContactInfo(@PathVariable Long customerId, @PathVariable Long id) {
        return contactsService.getOneContact(customerId, id);
    }
}
