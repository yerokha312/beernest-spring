package com.neobis.yerokha.beernestspring.controller;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Hidden
@Controller
public class DemoController {

    @GetMapping("/home")
    public String showHomePage() {
        return "home";
    }

    @GetMapping("/customers")
    public String showCustomersPage() {
        return "customers";
    }

    @GetMapping("/admin")
    public String showAdminPage() {
        return "admin";
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/home";
    }
}
