package com.neobis.yerokha.beernestspring.controller;

import com.neobis.yerokha.beernestspring.dto.CreateOrderDto;
import com.neobis.yerokha.beernestspring.dto.OrderDto;
import com.neobis.yerokha.beernestspring.service.user.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static com.neobis.yerokha.beernestspring.service.user.TokenService.getUserIdFromAuthToken;

@RestController
@RequestMapping("/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/")
    public ResponseEntity<OrderDto> createOrder(Authentication authentication,
                                                @Valid @RequestBody CreateOrderDto dto) {
        OrderDto createdOrder = orderService.createOrder(getUserIdFromAuthToken(authentication), dto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{orderId}")
                .buildAndExpand(createdOrder.getOrderId())
                .toUri();

        return ResponseEntity.created(location).body(createdOrder);
    }

    @GetMapping("/")
    public ResponseEntity<Page<OrderDto>> getCustomersOrders(Authentication authentication, Pageable pageable) {
        Page<OrderDto> orders = orderService.getAllOrdersByCustomer(getUserIdFromAuthToken(authentication), pageable);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> getOrderById(Authentication authentication, @PathVariable Long orderId) {
        OrderDto order = orderService.getOneOrder(getUserIdFromAuthToken(authentication), orderId);
        return ResponseEntity.ok(order);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<String> cancelOrder(Authentication authentication, @PathVariable Long orderId) {
        orderService.cancelOrder(getUserIdFromAuthToken(authentication), orderId);
        return ResponseEntity.ok("Order successfully canceled");
    }
}
