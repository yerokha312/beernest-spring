package com.neobis.yerokha.beernestspring.dto;

import com.neobis.yerokha.beernestspring.entity.beer.Beer;
import com.neobis.yerokha.beernestspring.entity.user.Customer;
import lombok.Data;

import java.util.List;

@Data
public class OrderDto {

    private Long customerId;
    private List<OrderItemDto> orderItemDtos;

    @Data
    public class OrderItemDto {

        private Beer beer;
        private Integer quantity;
    }
}
