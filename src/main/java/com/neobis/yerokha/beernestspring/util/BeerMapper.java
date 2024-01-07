package com.neobis.yerokha.beernestspring.util;

import com.neobis.yerokha.beernestspring.dto.BeerDto;
import com.neobis.yerokha.beernestspring.entity.beer.Beer;

public class BeerMapper {

    public static BeerDto mapToBeerDto(Beer beer) {
        BeerDto dto = new BeerDto(beer.getId(), beer.getName(), beer.getStyle().toString(),
                beer.getSubstyle().getName(), beer.getBrand().getName(), beer.getAlcohol(),
                beer.getContainer().toString(), beer.getSize(), beer.getSellingPrice(),
                beer.getCountry(), beer.getBeerDescription().getDescription(), beer.getStockAmount());

        return dto;
    }
}
