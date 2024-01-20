package com.neobis.yerokha.beernestspring.controller.rest;

import com.neobis.yerokha.beernestspring.dto.BeerDto;
import com.neobis.yerokha.beernestspring.service.beer.BeerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Beers", description = "Public endpoints for all users to query products")
@RestController
@RequestMapping("/v1/beers")
public class BeerController {

    private final BeerService beerService;

    @Autowired
    public BeerController(BeerService beerService) {
        this.beerService = beerService;

    }

    @Operation(
            summary = "Get all beers",
            description = "Returns List of beer dto from database",
            tags = {"beers", "get"}
    )
    @ApiResponse(responseCode = "200", description = "Success")
    @GetMapping("/all")
    public ResponseEntity<List<BeerDto>> getAllBeers() {

        return ResponseEntity.ok(beerService.getAllBeerDtos());

    }

    @Operation(
            summary = "Get beer by id",
            description = "Get one Beer dto by unique identifier",
            tags = {"beers", "get"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Beer with id: {id} not found.", content = @Content)
    })
    @Parameter(name = "beerId", description = "Beer's unique identifier")
    @GetMapping("/{beerId}")
    public ResponseEntity<BeerDto> getBeerById(@PathVariable Long beerId) {

        return ResponseEntity.ok(beerService.getBeerDtoById(beerId));

    }
}
