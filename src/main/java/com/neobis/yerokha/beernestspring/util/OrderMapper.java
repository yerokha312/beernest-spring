package com.neobis.yerokha.beernestspring.util;

import com.neobis.yerokha.beernestspring.dto.OrderDto;
import com.neobis.yerokha.beernestspring.dto.OrderItemBeerDto;
import com.neobis.yerokha.beernestspring.entity.user.Order;
import com.neobis.yerokha.beernestspring.entity.user.OrderItem;

import java.util.List;

public class OrderMapper {

    public static OrderDto mapOrderToDto(Order entity) {
        OrderDto dto = new OrderDto();
        String customerName = entity.getCustomer().getFirstName() + " " + entity.getCustomer().getLastName();

        dto.setOrderId(entity.getId());
        dto.setCustomerName(customerName);
        dto.setCreationDateTime(entity.getCreationDateTime());
        dto.setContactInfo(entity.getContactInfo());
        dto.setTotalPrice(entity.getTotalPrice());
        dto.setStatus(entity.getStatus());

        List<OrderDto.OrderItemDto> orderItemDtos = entity.getOrderItems().stream()
                .map(OrderMapper::mapOrderItemToDto)
                .toList();


        dto.setOrderItemDtos(orderItemDtos);

        return dto;
    }

    private static OrderDto.OrderItemDto mapOrderItemToDto(OrderItem entity) {
        OrderDto.OrderItemDto dto = new OrderDto.OrderItemDto();
        dto.setBeerDto(new OrderItemBeerDto(entity.getBeer().getId(), entity.getBeer().getName()));
        dto.setPrice(entity.getBeer().getSellingPrice());
        dto.setQuantity(entity.getQuantity());

        return dto;
    }
}
