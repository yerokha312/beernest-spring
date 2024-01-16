package com.neobis.yerokha.beernestspring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
