package com.neobis.yerokha.beernestspring.dto;

import com.neobis.yerokha.beernestspring.entity.user.ContactInfo;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class CreateOrderDto {

    @NotNull
    private ContactInfo contactInfo;

    @NotNull
    @NotEmpty
    private List<OrderItemDto> orderItemDtos;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderItemDto {

        @NotNull
        private Long beerId;
        @NotNull
        @PositiveOrZero
        private Integer quantity;

    }
}
