package com.neobis.yerokha.beernestspring.dto;

import com.neobis.yerokha.beernestspring.entity.user.ContactInfo;
import lombok.Data;

import java.util.List;

@Data
public class CreateOrderDto {

    private Long customerId;
    private ContactInfo contactInfo;
    private List<OrderItemDto> orderItemDtos;

    @Data
    public static class OrderItemDto {

        private Long beerId;
        private Integer quantity;

        public OrderItemDto() {
        }
    }
}
