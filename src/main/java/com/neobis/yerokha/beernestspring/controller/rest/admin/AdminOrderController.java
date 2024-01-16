package com.neobis.yerokha.beernestspring.controller.rest.admin;

import com.neobis.yerokha.beernestspring.dto.OrderDto;
import com.neobis.yerokha.beernestspring.service.user.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.neobis.yerokha.beernestspring.service.user.OrderService.PAGE_SIZE;

@RestController
@RequestMapping("/v1/admin/orders")
public class AdminOrderController {

    private final OrderService orderService;

    public AdminOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/")
    public Page<OrderDto> getAllOrders() {
        return orderService.getAllOrders(Pageable.ofSize(PAGE_SIZE));
    }

    @GetMapping("/{orderId}")
    public OrderDto getOrderById(@PathVariable Long orderId) {
        return orderService.getOrderById(orderId);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<String> cancelOrder(@PathVariable Long orderId) {
        orderService.cancelCustomersOrder(orderId);

        return new ResponseEntity<>("Order successfully canceled", HttpStatus.OK);
    }
}
