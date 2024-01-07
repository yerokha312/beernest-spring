package com.neobis.yerokha.beernestspring.service.beer;

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

    public void createBeer(Beer dto) {

        Substyle savedSubstyle = substyleService.getSubstyleByName(dto.getSubstyle().getName()).orElseGet(() -> {
            Substyle substyle = new Substyle();
            substyle.setName(dto.getSubstyle().getName());
            substyle.setStyle(dto.getStyle());
            return substyleService.createSubstyle(substyle);
        });

        Brand savedBrand = brandService.getBrandByName(dto.getBrand().getName()).orElseGet(() -> {
            Brand brand = new Brand();
            brand.setName(dto.getBrand().getName());
            return brandService.createBrand(brand);
        });

        BeerDescription beerDescription = new BeerDescription();
        beerDescription.setDescription(dto.getBeerDescription().getDescription());
        BeerDescription savedBeerDescription = beerDescriptionService.createBeerDescription(beerDescription);



        dto.setSubstyle(savedSubstyle);
        dto.setBrand(savedBrand);
        if (dto.getSellingPrice() == null) {
            dto.setSellingPrice(dto.getPurchasePrice().multiply(BigDecimal.valueOf(2)));
        }
        dto.setBeerDescription(savedBeerDescription);
        dto.generateCode();

        Optional<Beer> existingBeerOpt = beerRepository.findByBeerCode(dto.getBeerCode());

        // check if beer already exists in db, if yes, just refill storage Amount
        if (existingBeerOpt.isPresent()) {
            Beer existingBeer = existingBeerOpt.get();
            int updatedStock = existingBeer.getStockAmount() + dto.getStockAmount();
            existingBeer.setStockAmount(updatedStock);
            beerRepository.save(existingBeer);

        } else {

            beerRepository.save(dto);
        }

    }

    public List<Beer> getAllBeers() {
        List<Beer> beerList = beerRepository.findAll();

        return beerList;
    }

    public Beer getBeerById(Long id) {
        Beer beer = beerRepository.findById(id)
                .orElseThrow(() -> new BeerDoesNotExistException("Beer not found with id: " + id));

        return beer;
    }

    public void updateBeer(Beer beer) {
        beer.generateCode();

        beerRepository.save(beer);
    }

    public void removeBeer(Beer beer) {
        beerRepository.delete(beer);
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
