package com.neobis.yerokha.beernestspring.controller;

import com.neobis.yerokha.beernestspring.entity.user.Customer;
import com.neobis.yerokha.beernestspring.service.user.UserService;
import com.neobis.yerokha.beernestspring.util.CustomerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login-form";
    }

    @GetMapping("/registration")
    public String showRegisterForm(Model model) {
        Customer customer = new Customer();
        model.addAttribute("customer", customer);

        return "registration";
    }

    @GetMapping("/access-denied")
    public String showAccessDenied() {

        return "access-denied";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute Customer customer) {
        userService.registerCustomer(CustomerMapper.mapEntityToCCD(customer));
        return "redirect:/home";
    }
}
