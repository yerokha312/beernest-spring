package com.neobis.yerokha.beernestspring.controller.rest;

import com.neobis.yerokha.beernestspring.service.user.OrderService;
import com.neobis.yerokha.beernestspring.service.user.PaymentService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/payments")
public class PaymentController {

    private final PaymentService paymentService;
    private final OrderService orderService;

    public PaymentController(PaymentService paymentService, OrderService orderService) {
        this.paymentService = paymentService;
        this.orderService = orderService;
    }


    @PostMapping("/process/{orderId}")
    public String processPayment(@PathVariable Long orderId) {
        return paymentService.processPayment(orderId);
    }
}
