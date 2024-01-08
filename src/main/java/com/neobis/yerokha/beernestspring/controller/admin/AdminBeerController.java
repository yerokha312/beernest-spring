package com.neobis.yerokha.beernestspring.controller.admin;

import com.neobis.yerokha.beernestspring.entity.beer.Beer;
import com.neobis.yerokha.beernestspring.service.beer.BeerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v8/beers")
public class AdminBeerController {

    private final BeerService beerService;

    @Autowired
    public AdminBeerController(BeerService beerService) {
        this.beerService = beerService;
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public Beer createBeer(@RequestBody Beer beer) {
        return beerService.createBeer(beer);
    }

    @GetMapping("/")
    public List<Beer> getAllBeers() {
        return beerService.getAllBeers();
    }

    @GetMapping("/{id}")
    public Beer getBeerById(@PathVariable Long id) {
        return beerService.getBeerById(id);
    }

    @PutMapping("/")
    public Beer updateBeer(@RequestBody Beer beer) {
        return beerService.updateBeer(beer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBeer(@PathVariable Long id) {
        beerService.deleteBeerById(id);

        return new ResponseEntity<>("Resource removed successfully", HttpStatus.OK);
    }
}
