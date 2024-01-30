package com.neobis.yerokha.beernestspring.dto;

import com.neobis.yerokha.beernestspring.enums.Status;

public record OrderResponse(Long orderId, Status status) {
}
