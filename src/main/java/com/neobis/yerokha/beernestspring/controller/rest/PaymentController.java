package com.neobis.yerokha.beernestspring.controller.rest;

import com.neobis.yerokha.beernestspring.dto.OrderResponse;
import com.neobis.yerokha.beernestspring.service.user.PaymentService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }


    @PostMapping("/process/{orderId}")
    public String processPayment(@PathVariable Long orderId) {
        return paymentService.processPayment(orderId);
    }

    @PostMapping("/report")
    public void reportPayment(@RequestBody OrderResponse response) {
        paymentService.reportPayment(response);
    }
}
