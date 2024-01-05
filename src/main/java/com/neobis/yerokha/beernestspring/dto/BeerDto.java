package com.neobis.yerokha.beernestspring.dto;

import com.neobis.yerokha.beernestspring.entity.beer.BeerDescription;
import com.neobis.yerokha.beernestspring.entity.beer.Brand;
import com.neobis.yerokha.beernestspring.entity.beer.Substyle;
import com.neobis.yerokha.beernestspring.enums.Container;
import com.neobis.yerokha.beernestspring.enums.Style;

import java.math.BigDecimal;

public record BeerDto(String name,
                      Style style,
                      Substyle substyle,
                      Brand brand,
                      Double alcohol,
                      Container container,
                      Integer size,
                      BigDecimal price,
                      String country,
                      BeerDescription beerDescription,
                      Integer stockAmount) {

    /*public static BeerDto toBeerDto(Beer beer) {
        BeerDto dto = new BeerDto(beer.getName(), beer.getStyle(), beer.getSubstyle(), beer.getBrand(),
                beer.getAlcohol(), beer.getContainer(), beer.getSize(), beer.getCountry(),
                beer.getBeerDescription(), beer.getStockAmount());

        return dto;
    }

    public static Beer fromBeerDto(BeerDto dto) {
        Beer beer = new Beer();

        beer.setName(dto.name());
        beer.setStyle(dto.style());
        beer.setSubstyle(dto.substyle());
        beer.setBrand(dto.brand());
        beer.setAlcohol(dto.alcohol());
        beer.setContainer(dto.container());
        beer.setSize(dto.size());
        beer.setCountry(dto.country());
        beer.setBeerDescription(dto.beerDescription());
        beer.setStockAmount(dto.stockAmount());

        return beer;
    }*/
}
