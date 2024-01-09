package com.neobis.yerokha.beernestspring.controller.admin;

import com.neobis.yerokha.beernestspring.dto.CustomerDto;
import com.neobis.yerokha.beernestspring.service.user.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.neobis.yerokha.beernestspring.service.user.UserService.PAGE_SIZE;

@RestController
@RequestMapping("/api/admin/users")
public class AdminUserController {

    private final UserService userService;

    public AdminUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public Page<CustomerDto> getAllCustomers() {
        return userService.getAllCustomerDtos(Pageable.ofSize(PAGE_SIZE));
    }

    @GetMapping("/{id}")
    public CustomerDto getOneCustomer(@PathVariable Long id) {
        return userService.getCustomerDtoById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long id) {
        userService.deleteCustomerById(id);

        return new ResponseEntity<>("Customer successfully deleted", HttpStatus.OK);
    }
}
