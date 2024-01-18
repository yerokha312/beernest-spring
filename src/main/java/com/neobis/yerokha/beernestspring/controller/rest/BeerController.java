package com.neobis.yerokha.beernestspring.controller.rest;

import com.neobis.yerokha.beernestspring.dto.BeerDto;
import com.neobis.yerokha.beernestspring.service.beer.BeerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/beers")
public class BeerController {

    private final BeerService beerService;

    @Autowired
    public BeerController(BeerService beerService) {
        this.beerService = beerService;

    }

    @GetMapping("/all")
    public ResponseEntity<List<BeerDto>> getAllBeers() {

        return ResponseEntity.ok(beerService.getAllBeerDtos());

    }

    @GetMapping("/{beerId}")
    public ResponseEntity<BeerDto> getBeerById(@PathVariable Long beerId) {

        return ResponseEntity.ok(beerService.getBeerDtoById(beerId));

    }
}
