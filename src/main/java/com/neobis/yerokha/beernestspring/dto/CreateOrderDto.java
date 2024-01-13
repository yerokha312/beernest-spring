package com.neobis.yerokha.beernestspring.dto;

import com.neobis.yerokha.beernestspring.entity.user.ContactInfo;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.util.List;

@Data
public class CreateOrderDto {

    @NotNull
    private Long customerId;
    @NotNull
    private ContactInfo contactInfo;
    @NotNull
    @NotEmpty
    private List<OrderItemDto> orderItemDtos;

    @Data
    public static class OrderItemDto {

        @NotNull
        private Long beerId;
        @NotNull
        @PositiveOrZero
        private Integer quantity;

        public OrderItemDto() {
        }
    }
}
