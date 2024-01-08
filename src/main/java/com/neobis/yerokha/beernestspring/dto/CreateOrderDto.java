package com.neobis.yerokha.beernestspring.dto;

import lombok.Data;

import java.util.List;

@Data
public class CreateOrderDto {

    private Long customerId;
    private List<OrderItemDto> orderItemDtos;

    @Data
    public static class OrderItemDto {

        private Long beerId;
        private Integer quantity;

        public OrderItemDto() {
        }
    }
}
