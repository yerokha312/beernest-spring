package com.neobis.yerokha.beernestspring.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.neobis.yerokha.beernestspring.entity.user.ContactInfo;
import com.neobis.yerokha.beernestspring.enums.Status;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDto {

    private Long orderId;
    private String customerName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime creationDateTime;
    private ContactInfo contactInfo;
    private BigDecimal totalPrice;
    private Status status;
    private List<OrderItemDto> orderItemDtos;

    @Data
    public static class OrderItemDto {

        private OrderItemBeerDto beerDto;
        private BigDecimal price;
        private Integer quantity;

        public OrderItemDto() {
        }
    }
}
