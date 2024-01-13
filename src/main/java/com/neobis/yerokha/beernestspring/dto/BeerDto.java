package com.neobis.yerokha.beernestspring.dto;

import java.math.BigDecimal;

public record BeerDto(Long id,
                      String name,
                      String style,
                      String subStyle,
                      String brand,
                      Double alcohol,
                      String container,
                      Integer size,
                      BigDecimal price,
                      String country,
                      String beerDescription,
                      Integer stockAmount) {
}
