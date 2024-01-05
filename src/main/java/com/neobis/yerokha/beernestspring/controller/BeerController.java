package com.neobis.yerokha.beernestspring.controller;

import com.neobis.yerokha.beernestspring.dto.BeerDto;
import com.neobis.yerokha.beernestspring.service.beer.BeerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/beers")
public class BeerController {

    private final BeerService beerService;

    @Autowired
    public BeerController(BeerService beerService) {
        this.beerService = beerService;
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public void createBeer(@RequestBody BeerDto dto) {
        beerService.createBeer(dto);
    }

    @GetMapping("/")
    public List<BeerDto> getAllBeers() {
        return beerService.getAllBeers();
    }

    @GetMapping("/{id}")
    public BeerDto getBeerById(@PathVariable Long id) {
        return beerService.getBeerById(id);
    }
}
