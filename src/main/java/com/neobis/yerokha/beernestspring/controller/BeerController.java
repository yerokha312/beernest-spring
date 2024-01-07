package com.neobis.yerokha.beernestspring.controller;

import com.neobis.yerokha.beernestspring.dto.BeerDto;
import com.neobis.yerokha.beernestspring.service.beer.BeerService;
import com.neobis.yerokha.beernestspring.util.BeerMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/beers")
public class BeerController {

    private final BeerService beerService;

    public BeerController(BeerService beerService) {
        this.beerService = beerService;
    }

    @GetMapping("/")
    public Set<BeerDto> getAllBeers() {
        Set<BeerDto> beerDtoSet = beerService.getAllBeers().stream()
                .map(BeerMapper::mapToBeerDto)
                .collect(Collectors.toSet());

        return beerDtoSet;
    }

    @GetMapping("/{id}")
    public BeerDto getBeerById(@PathVariable Long id) {
        BeerDto dto = BeerMapper.mapToBeerDto(beerService.getBeerById(id));

        return dto;
    }
}
