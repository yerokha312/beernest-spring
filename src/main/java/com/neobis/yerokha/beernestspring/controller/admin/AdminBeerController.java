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
@RequestMapping("/api/admin/beers")
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

    @GetMapping("/{beerId}")
    public Beer getBeerById(@PathVariable Long beerId) {
        return beerService.getBeerById(beerId);
    }

    @PutMapping("/")
    public Beer updateBeer(@RequestBody Beer beer) {
        return beerService.updateBeer(beer);
    }

    @DeleteMapping("/{beerId}")
    public ResponseEntity<String> deleteBeer(@PathVariable Long beerId) {
        beerService.deleteBeerById(beerId);

        return new ResponseEntity<>("Resource removed successfully", HttpStatus.OK);
    }
}

/*
 * AdminBeerController provides REST endpoints for managing beers.
 *
 * @author yerbolat
 * @version 1.0
 *
 * @RestController - Indicates this as a REST controller.
 * @RequestMapping - Maps URL path /api/admin/beers.
 *
 * @PostMapping - Handles POST request to create a new beer.
 * @RequestBody Beer beer - Beer object to create, passed in request body.
 * @ResponseStatus(HttpStatus.CREATED) - Returns 201 status code.
 *
 * @GetMapping - Handles GET for all beers.
 * Returns List<Beer> of all beers.
 *
 * @GetMapping("/{beerId}") - Handles GET for single beer by id.
 * @PathVariable Long beerId - Beer id passed as path variable.
 * Returns Beer object with matching id.
 *
 * @PutMapping - Handles PUT to update existing beer.
 * @RequestBody Beer beer - Updated Beer object passed in request body.
 *
 * @DeleteMapping("/{beerId}") - Handles DELETE beer by id.
 * @PathVariable Long beerId - Beer id passed as path variable.
 * Returns HTTP 200 status with success message.
 */

