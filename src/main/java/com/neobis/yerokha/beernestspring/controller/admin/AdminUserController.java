package com.neobis.yerokha.beernestspring.controller.admin;

import com.neobis.yerokha.beernestspring.dto.OrderDto;
import com.neobis.yerokha.beernestspring.dto.UserDto;
import com.neobis.yerokha.beernestspring.service.user.OrderService;
import com.neobis.yerokha.beernestspring.service.user.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.neobis.yerokha.beernestspring.service.user.UserService.PAGE_SIZE;

@RestController
@RequestMapping("/v1/admin/users")
public class AdminUserController {

    private final UserService userService;
    private final OrderService orderService;

    public AdminUserController(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    @GetMapping("/")
    public Page<UserDto> getAllCustomers() {
        return userService.getAllCustomerDtos(Pageable.ofSize(PAGE_SIZE));
    }

    @GetMapping("/{customerId}")
    public UserDto getOneCustomer(@PathVariable Long customerId) {
        return userService.getCustomerDtoById(customerId);
    }

    @PutMapping("/")
    public UserDto updateCustomer(@RequestBody UserDto dto) {
        return userService.updateCustomer(dto);
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long customerId) {
        userService.deleteCustomerById(customerId);

        return new ResponseEntity<>("Customer successfully deleted", HttpStatus.OK);
    }

    @GetMapping("/{customerId}/orders")
    public Page<OrderDto> getAllOrdersByCustomerId(@PathVariable Long customerId) {
        return orderService.getAllOrdersByCustomerId(customerId, Pageable.ofSize(OrderService.PAGE_SIZE));
    }

}
