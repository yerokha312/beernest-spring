package com.neobis.yerokha.beernestspring.service.beer;

import com.neobis.yerokha.beernestspring.dto.BeerDto;
import com.neobis.yerokha.beernestspring.entity.beer.Beer;
import com.neobis.yerokha.beernestspring.entity.beer.BeerDescription;
import com.neobis.yerokha.beernestspring.entity.beer.Brand;
import com.neobis.yerokha.beernestspring.entity.beer.Substyle;
import com.neobis.yerokha.beernestspring.exception.BeerDoesNotExistException;
import com.neobis.yerokha.beernestspring.repository.beer.BeerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BeerService {

    private final BeerRepository beerRepository;
    private final BeerDescriptionService beerDescriptionService;
    private final BrandService brandService;
    private final SubstyleService substyleService;

    @Autowired
    public BeerService(BeerRepository beerRepository, BeerDescriptionService beerDescriptionService, BrandService brandService, SubstyleService substyleService) {
        this.beerRepository = beerRepository;

        this.beerDescriptionService = beerDescriptionService;
        this.brandService = brandService;
        this.substyleService = substyleService;
    }

    public void createBeer(BeerDto dto) {

        Substyle savedSubstyle = substyleService.getSubstyleByName(dto.substyle().getName()).orElseGet(() -> {
            Substyle substyle = new Substyle();
            substyle.setName(dto.substyle().getName());
            substyle.setStyle(dto.style());
            return substyleService.createSubstyle(substyle);
        });

        Brand savedBrand = brandService.getBrandByName(dto.brand().getName()).orElseGet(() -> {
            Brand brand = new Brand();
            brand.setName(dto.brand().getName());
            return brandService.createBrand(brand);
        });

        BeerDescription beerDescription = new BeerDescription();
        beerDescription.setDescription(dto.beerDescription().getDescription());
        BeerDescription savedBeerDescription = beerDescriptionService.createBeerDescription(beerDescription);

        Beer beer = new Beer();

        beer.setName(dto.name());
        beer.setStyle(dto.style());
        beer.setSubstyle(savedSubstyle);
        beer.setBrand(savedBrand);
        beer.setAlcohol(dto.alcohol());
        beer.setContainer(dto.container());
        beer.setSize(dto.size());
        beer.setPurchasePrice(dto.price());
        beer.setSellingPrice(dto.price().multiply(BigDecimal.valueOf(1.5)));
        beer.setCountry(dto.country());
        beer.setBeerDescription(savedBeerDescription);
        beer.generateCode();

        Optional<Beer> existingBeerOpt = beerRepository.findByBeerCode(beer.getBeerCode());

        if (existingBeerOpt.isPresent()) {
            // Beer exists, update stock amount
            Beer existingBeer = existingBeerOpt.get();
            int updatedStock = existingBeer.getStockAmount() + dto.stockAmount();
            existingBeer.setStockAmount(updatedStock);
            beerRepository.save(existingBeer);

        } else {
            beer.setStockAmount(dto.stockAmount());
            beerRepository.save(beer);
        }
    }

    public List<BeerDto> getAllBeers() {
        List<BeerDto> beerDtos = beerRepository.findAll()
                .stream()
                .map(beer -> new BeerDto(beer.getName(), beer.getStyle(), beer.getSubstyle(), beer.getBrand(),
                        beer.getAlcohol(), beer.getContainer(), beer.getSize(), beer.getSellingPrice(), beer.getCountry(),
                        beer.getBeerDescription(), beer.getStockAmount()))
                .collect(Collectors.toList());

        return beerDtos;
    }

    public BeerDto getBeerById(Long id) {
        Beer beer = beerRepository.findById(id)
                .orElseThrow(() -> new BeerDoesNotExistException("Beer not found with id: " + id));

        BeerDto dto = new BeerDto(beer.getName(), beer.getStyle(), beer.getSubstyle(), beer.getBrand(),
                beer.getAlcohol(), beer.getContainer(), beer.getSize(), beer.getSellingPrice(), beer.getCountry(),
                beer.getBeerDescription(), beer.getStockAmount());

        return dto;
    }
}
/*
* public static BeerDto toBeerDto(Beer beer) {
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
