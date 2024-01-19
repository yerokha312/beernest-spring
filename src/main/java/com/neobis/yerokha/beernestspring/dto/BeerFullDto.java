package com.neobis.yerokha.beernestspring.dto;

import java.math.BigDecimal;

public record BeerFullDto(Long id,
                          String beerCode,
                          String name,
                          String style,
                          String subStyle,
                          String brand,
                          Double alcohol,
                          String container,
                          Integer size,
                          BigDecimal purchasePrice,
                          BigDecimal sellingPrice,
                          String country,
                          String beerDescription,
                          Long soldAmount,
                          Integer stockAmount) {
}
