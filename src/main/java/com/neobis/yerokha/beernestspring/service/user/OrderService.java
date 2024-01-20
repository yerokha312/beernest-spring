package com.neobis.yerokha.beernestspring.service.user;

import com.neobis.yerokha.beernestspring.dto.CreateOrderDto;
import com.neobis.yerokha.beernestspring.dto.OrderDto;
import com.neobis.yerokha.beernestspring.entity.beer.Beer;
import com.neobis.yerokha.beernestspring.entity.user.Customer;
import com.neobis.yerokha.beernestspring.entity.user.Order;
import com.neobis.yerokha.beernestspring.entity.user.OrderItem;
import com.neobis.yerokha.beernestspring.enums.Status;
import com.neobis.yerokha.beernestspring.exception.CustomerIdDoesNotMatchException;
import com.neobis.yerokha.beernestspring.exception.OrderDoesNotExistException;
import com.neobis.yerokha.beernestspring.exception.OutOfStockException;
import com.neobis.yerokha.beernestspring.exception.UnableToCancelException;
import com.neobis.yerokha.beernestspring.exception.UserDoesNotExistException;
import com.neobis.yerokha.beernestspring.repository.user.OrderRepository;
import com.neobis.yerokha.beernestspring.service.beer.BeerService;
import com.neobis.yerokha.beernestspring.util.OrderMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final BeerService beerService;
    private final UserService userService;

    public static final int PAGE_SIZE = 10;

    public OrderService(OrderRepository orderRepository,
                        BeerService beerService, UserService userService) {
        this.orderRepository = orderRepository;
        this.beerService = beerService;
        this.userService = userService;
    }

    public OrderDto createOrder(Long id, CreateOrderDto dto) {
        Customer customer = userService.getCustomerById(id);
        if (!customer.getId().equals(dto.getCustomerId())) {
            throw new CustomerIdDoesNotMatchException("Customer's id does not match");
        }

        Order order = new Order();

        order.setCustomer(customer);
        order.setCreationDateTime(LocalDateTime.now());
        order.setContactInfo(dto.getContactInfo());

        for (CreateOrderDto.OrderItemDto orderItemDto : dto.getOrderItemDtos()) {
            Beer beer = beerService.getBeerById(orderItemDto.getBeerId());
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setBeer(beer);
            orderItem.setQuantity(orderItemDto.getQuantity());
            order.addOrderItem(orderItem);

            updateBeerStock(orderItemDto, beer);
        }

        order.calculateTotalPrice();
        order.setStatus(Status.PENDING);
        order.setIsDelivered(false);

        return OrderMapper.mapOrderToDto(orderRepository.save(order));
    }

    private void updateBeerStock(CreateOrderDto.OrderItemDto orderItem, Beer beer) {

        int quantityToSubtract = orderItem.getQuantity();

        if (beer.getStockAmount() < quantityToSubtract) {
            throw new OutOfStockException("Ordered quantity exceeds available stock");
        }

        int updatedStock = beer.getStockAmount() - quantityToSubtract;
        beer.setStockAmount(updatedStock);

        long updatedSold = beer.getSoldAmount() + quantityToSubtract;
        beer.setSoldAmount(updatedSold);

        beerService.updateBeer(beer);

    }


    public Page<OrderDto> getAllOrdersByCustomerId(Long customerId, Pageable pageable) {
        try {
            return orderRepository.findAllByCustomerId(customerId, pageable).map(OrderMapper::mapOrderToDto);
        } catch (Exception e) {
            throw new UserDoesNotExistException("Customer with id: " + customerId + " not found");
        }
    }

    public OrderDto getOrderById(Long orderId) {
        return OrderMapper.mapOrderToDto(orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderDoesNotExistException("Order with id: " + orderId + " not found.")));
    }

    public void cancelCustomersOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderDoesNotExistException("Order with id: " + orderId + " not found."));

        if (order.getIsDelivered()) {
            throw new UnableToCancelException("Order is already delivered, try to return order instead");
        }

        order.setStatus(Status.CANCELED);

        orderRepository.save(order);
    }

    public Page<OrderDto> getAllOrdersByCustomer(Long id, Pageable pageable) {
        try {
            return orderRepository.findAllByCustomerId(id, pageable).map(OrderMapper::mapOrderToDto);
        } catch (Exception e) {
            throw new UserDoesNotExistException("Customer with id: " + id + " not found");
        }
    }

    public OrderDto getOneOrder(Long customerId, Long orderId) {
        return OrderMapper.mapOrderToDto(orderRepository
                .findByCustomerIdAndId(customerId, orderId)
                .orElseThrow(() -> new OrderDoesNotExistException("Order with id: " + orderId + " not found.")));
    }

    public void cancelOrder(Long customerId, Long orderId) {
        Order order = orderRepository
                .findByCustomerIdAndId(customerId, orderId)
                .orElseThrow(() -> new OrderDoesNotExistException("Order with id: " + orderId + " not found."));

        if (order.getIsDelivered()) {
            throw new UnableToCancelException("Order is already delivered, try to return order instead");
        }

        order.setStatus(Status.CANCELED);

        orderRepository.save(order);
    }

    public Page<OrderDto> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable).map(OrderMapper::mapOrderToDto);
    }
}
