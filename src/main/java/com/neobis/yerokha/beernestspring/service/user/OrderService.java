package com.neobis.yerokha.beernestspring.service.user;

import com.neobis.yerokha.beernestspring.dto.CreateOrderDto;
import com.neobis.yerokha.beernestspring.entity.beer.Beer;
import com.neobis.yerokha.beernestspring.entity.user.Customer;
import com.neobis.yerokha.beernestspring.entity.user.Order;
import com.neobis.yerokha.beernestspring.entity.user.OrderItem;
import com.neobis.yerokha.beernestspring.exception.CustomerDoesNotExistException;
import com.neobis.yerokha.beernestspring.exception.OrderDoesNotExistException;
import com.neobis.yerokha.beernestspring.repository.user.CustomerRepository;
import com.neobis.yerokha.beernestspring.repository.user.OrderRepository;
import com.neobis.yerokha.beernestspring.service.beer.BeerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final BeerService beerService;
    private final CustomerRepository customerRepository;

    public static final int PAGE_SIZE = 10;

    public OrderService(OrderRepository orderRepository, BeerService beerService, CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.beerService = beerService;
        this.customerRepository = customerRepository;
    }

    public Order createOrder(CreateOrderDto dto) {
        Order order = new Order();

        Customer customer = customerRepository.
                findById(dto.getCustomerId()).orElseThrow(() ->
                        new CustomerDoesNotExistException("This customer does not exist"));

        order.setCustomer(customer);
        order.setCreationDateTime(LocalDateTime.now());

        for (CreateOrderDto.OrderItemDto orderItemDto : dto.getOrderItemDtos()) {
            Beer beer = beerService.getBeerById(orderItemDto.getBeerId());
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setBeer(beer);
            orderItem.setQuantity(orderItemDto.getQuantity());
            order.addOrderItem(orderItem);
        }

        order.calculateTotalPrice();

        return orderRepository.save(order);
    }

    public Page<Order> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    public Page<Order> getAllOrdersByCustomerId(Long customerId, Pageable pageable) {
        try {
            return orderRepository.findAllByCustomerId(customerId, pageable);
        } catch (Exception e) {
            throw new CustomerDoesNotExistException("Customer with id: " + customerId + " not found");
        }
    }

    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderDoesNotExistException("Order with id: " + orderId + " not found."));
    }
}
