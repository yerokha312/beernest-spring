package com.neobis.yerokha.beernestspring.service.user;

import com.neobis.yerokha.beernestspring.dto.OrderDto;
import com.neobis.yerokha.beernestspring.dto.OrderRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PaymentService {

    private final RestTemplate restTemplate;
    private final OrderService orderService;

    @Value("${payment.service.url}")
    private String paymentServiceUrl;
    @Value("${payment.service.username}")
    private String username;
    @Value("${payment.service.password}")
    private String password;

    public PaymentService(RestTemplate restTemplate, OrderService orderService) {
        this.restTemplate = restTemplate;
        this.orderService = orderService;
    }

    public String processPayment(Long orderId) {
        OrderDto order = orderService.getOrderById(orderId);
        OrderRequest request = new OrderRequest(
                order.getOrderId(),
                "Beernest company",
                order.getTotalPrice()
        );
        return createOrderRequest(request);
    }

    private String createOrderRequest(OrderRequest request) {
        HttpHeaders headers = new HttpHeaders();

        headers.set("Content-Type", "application/json");
        headers.setBasicAuth(username, password);

        HttpEntity<OrderRequest> requestEntity = new HttpEntity<>(request, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                paymentServiceUrl + "/orders/create",
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        return responseEntity.getBody();

    }
}
