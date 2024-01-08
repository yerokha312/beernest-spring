package com.neobis.yerokha.beernestspring.controller;

import com.neobis.yerokha.beernestspring.dto.BeerDto;
import com.neobis.yerokha.beernestspring.service.beer.BeerService;
import com.neobis.yerokha.beernestspring.util.BeerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/beers")
public class BeerController {

    private final BeerService beerService;

    @Autowired
    public BeerController(BeerService beerService) {
        this.beerService = beerService;

    }

    @GetMapping("/")
    public List<BeerDto> getAllBeers() {

        return beerService.getAllBeerDtos();

    }

    @GetMapping("/{id}")
    public BeerDto getBeerById(@PathVariable Long id) {

        return beerService.getBeerDtoById(id);

    }
}
