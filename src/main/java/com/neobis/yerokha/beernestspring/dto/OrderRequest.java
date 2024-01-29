package com.neobis.yerokha.beernestspring.dto;

import java.math.BigDecimal;

public record OrderRequest(Long requestId, String requester, BigDecimal amount) {
}
