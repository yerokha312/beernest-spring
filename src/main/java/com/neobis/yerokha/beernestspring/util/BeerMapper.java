package com.neobis.yerokha.beernestspring.util;

import com.neobis.yerokha.beernestspring.dto.BeerDto;
import com.neobis.yerokha.beernestspring.dto.BeerFullDto;
import com.neobis.yerokha.beernestspring.entity.beer.Beer;
import com.neobis.yerokha.beernestspring.entity.beer.BeerDescription;
import com.neobis.yerokha.beernestspring.enums.Container;
import com.neobis.yerokha.beernestspring.enums.Style;

public class BeerMapper {

    public static BeerFullDto mapToBeerFullDto(Beer beer) {

        return new BeerFullDto(beer.getId(), beer.getBeerCode(), beer.getName(), beer.getStyle().toString(),
                beer.getSubStyle().getName(), beer.getBrand().getName(), beer.getAlcohol(),
                beer.getContainer().toString(), beer.getSize(), beer.getPurchasePrice(), beer.getSellingPrice(),
                beer.getCountry(), beer.getBeerDescription().getDescription(), beer.getSoldAmount(), beer.getStockAmount());
    }


    public static BeerDto mapToBeerDto(Beer beer) {

        return new BeerDto(beer.getId(), beer.getName(), beer.getStyle().toString(),
                beer.getSubStyle().getName(), beer.getBrand().getName(), beer.getAlcohol(),
                beer.getContainer().toString(), beer.getSize(), beer.getSellingPrice(),
                beer.getCountry(), beer.getBeerDescription().getDescription(), beer.getStockAmount());
    }

    /*
    * ong id,
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
                              Integer stockAmount) {*/
    public static void mapToBeerEntity(Beer entity, BeerFullDto dto) {
        entity.setId(entity.getId());
        entity.setBeerCode(dto.beerCode());
        entity.setName(dto.name());
        entity.setStyle(Style.valueOf(dto.style()));
        entity.setAlcohol(dto.alcohol());
        entity.setContainer(Container.valueOf(dto.container()));
        entity.setSize(dto.size());
        entity.setPurchasePrice(dto.purchasePrice());
        entity.setSellingPrice(dto.sellingPrice());
        entity.setCountry(dto.country());
        BeerDescription description = new BeerDescription();
        description.setDescription(dto.beerDescription());
        entity.setBeerDescription(description);
        entity.setSoldAmount(dto.soldAmount());
        entity.setStockAmount(dto.stockAmount());
    }
}
