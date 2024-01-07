package com.neobis.yerokha.beernestspring.controller;

import com.neobis.yerokha.beernestspring.dto.CreateCustomerDto;
import com.neobis.yerokha.beernestspring.dto.LoginResponse;
import com.neobis.yerokha.beernestspring.entity.user.Customer;
import com.neobis.yerokha.beernestspring.exception.EmailAlreadyTakenException;
import com.neobis.yerokha.beernestspring.service.user.CustomerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;

@RestController
@RequestMapping("/api/v1/customers/auth")
public class CustomerAuthenticationController {

    private final CustomerService customerService;

    public CustomerAuthenticationController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/register")
    public Customer registerCustomer(@RequestBody CreateCustomerDto dto) {
        return customerService.createCustomer(dto);
    }

/*    @PutMapping("/update/phone")
    public Customer updatePhoneNumber(@RequestBody LinkedHashMap<String, String> body) {

        String email = body.get("email");
        String phone = body.get("phoneNumber");

        Customer customer = customerService.getCustomerByEmail(email);
        customer.setPhoneNumber(phone);

        return customerService.updateCustomer(customer);
    }
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LinkedHashMap<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");
        return new LoginResponse;

    }*/
}