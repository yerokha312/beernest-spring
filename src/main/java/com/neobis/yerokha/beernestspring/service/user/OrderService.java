package com.neobis.yerokha.beernestspring.service.user;

import com.neobis.yerokha.beernestspring.dto.OrderDto;
import com.neobis.yerokha.beernestspring.entity.beer.Beer;
import com.neobis.yerokha.beernestspring.entity.user.Customer;
import com.neobis.yerokha.beernestspring.entity.user.Order;
import com.neobis.yerokha.beernestspring.entity.user.OrderItem;
import com.neobis.yerokha.beernestspring.exception.CustomerDoesNotExistException;
import com.neobis.yerokha.beernestspring.repository.user.CustomerRepository;
import com.neobis.yerokha.beernestspring.repository.user.OrderRepository;
import com.neobis.yerokha.beernestspring.service.beer.BeerService;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final BeerService beerService;
    private final CustomerRepository customerRepository;

    public OrderService(OrderRepository orderRepository, BeerService beerService, CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.beerService = beerService;
        this.customerRepository = customerRepository;
    }

    public Order createOrder(OrderDto dto) {
        Order order = new Order();

        Customer customer = customerRepository.
                findById(dto.getCustomerId()).orElseThrow(() ->
                        new CustomerDoesNotExistException("This customer does not exist"));
        order.setCustomer(customer);
        order.setCreationDate(Date.valueOf(LocalDate.now()));

        for (OrderDto.OrderItemDto orderItemDto : dto.getOrderItemDtos()) {
            Beer beer = beerService.getBeerById(orderItemDto.getBeer().getId());
            OrderItem orderItem = new OrderItem();
            orderItem.setBeer(beer);
            orderItem.setQuantity(orderItemDto.getQuantity());
            order.addOrderItem(orderItem);
        }

        order.calculateTotalPrice();

        return orderRepository.save(order);
    }
}
