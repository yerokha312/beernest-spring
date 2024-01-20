package com.neobis.yerokha.beernestspring.controller.rest.admin;

import com.neobis.yerokha.beernestspring.dto.BeerFullDto;
import com.neobis.yerokha.beernestspring.service.beer.BeerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(
        name = "Admin Beer",
        description = "Admin control panel for beer. ADMIN has full access, and OBSERVER only to GET requests")
@ApiResponses({
        @ApiResponse(responseCode = "401", description = "If user has not signed in", content = @Content),
        @ApiResponse(responseCode = "403", description = "If signed user has role CUSTOMER", content = @Content)
})
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/v1/admin/beers")
public class AdminBeerController {

    private final BeerService beerService;

    @Autowired
    public AdminBeerController(BeerService beerService) {
        this.beerService = beerService;
    }

    @Operation(
            summary = "Create beer object",
            description = "Creates a new beer object if does not exist in used " +
                    "database or refills amount of existing beer in db",
            tags = {"beers", "post"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "New Beer object created successfully " +
                    "or stored amount is fulfilled"),
            @ApiResponse(responseCode = "403", description = "Only ADMIN users has access for POST method")
    })
    @PostMapping("/")
    public ResponseEntity<BeerFullDto> createBeer(@RequestBody BeerFullDto beerFullDto) {
        return new ResponseEntity<>(beerService.createBeer(beerFullDto), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Retrieve all beer",
            description = "Get all beer dto in list from database",
            tags = {"beers", "get"}
    )
    @ApiResponse(responseCode = "200", description = "Success")
    @GetMapping("/all")
    public ResponseEntity<List<BeerFullDto>> getAllBeers() {
        return ResponseEntity.ok(beerService.getAllBeers());
    }

    @Operation(
            summary = "Retrieve a Beer by id",
            description = "Get one beer dto from database specifying it's id. Response is a Beer dto with full info",
            tags = {"beers", "get"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Beer with id: {beerId} not found.", content = @Content)
    })
    @Parameter(name = "beerId", description = "Unique beer object identifier")
    @GetMapping("/{beerId}")
    public ResponseEntity<BeerFullDto> getBeerById(@PathVariable Long beerId) {
        return ResponseEntity.ok(beerService.getBeerFullDtoById(beerId));
    }

    @Operation(
            summary = "Update beer object",
            description = "Update Beer info by id specified in RequestBody",
            tags = {"beers", "put"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Beer object info successfully updated"),
            @ApiResponse(responseCode = "403", description = "Only ADMIN users has access for PUT method", content = @Content),
            @ApiResponse(responseCode = "404", description = "Beer you are trying to update not found.", content = @Content)
    })
    @PutMapping("/")
    public ResponseEntity<BeerFullDto> updateBeer(@RequestBody BeerFullDto dto) {
        return ResponseEntity.ok(beerService.updateBeerFullDto(dto));
    }

    @Operation(
            summary = "Delete Beer object",
            description = "Delete Beer object from database by id specified in PathVariable",
            tags = {"beers", "delete"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Beer object info successfully removed"),
            @ApiResponse(responseCode = "403", description = "Only ADMIN users has access for DELETE method"),
            @ApiResponse(responseCode = "404", description = "Beer you are trying to delete not found.", content = @Content)
    })
    @Parameter(name = "beerId", description = "Unique beer object identifier")
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

