package com.neobis.yerokha.beernestspring.service.beer;

import com.neobis.yerokha.beernestspring.dto.BeerDto;
import com.neobis.yerokha.beernestspring.entity.beer.Beer;
import com.neobis.yerokha.beernestspring.entity.beer.BeerDescription;
import com.neobis.yerokha.beernestspring.entity.beer.Brand;
import com.neobis.yerokha.beernestspring.entity.beer.Substyle;
import com.neobis.yerokha.beernestspring.exception.BeerDoesNotExistException;
import com.neobis.yerokha.beernestspring.repository.beer.BeerRepository;
import com.neobis.yerokha.beernestspring.util.BeerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
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

    public Beer createBeer(Beer dto) {
        Result result = checkFieldDistinction(dto);

        BeerDescription beerDescription = new BeerDescription();
        beerDescription.setDescription(dto.getBeerDescription().getDescription());
        BeerDescription savedBeerDescription = beerDescriptionService.createBeerDescription(beerDescription);

        dto.setSubstyle(result.savedSubstyle());
        dto.setBrand(result.savedBrand());

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

            return beerRepository.save(existingBeer);

        } else {

            return beerRepository.save(dto);
        }
    }

    private Result checkFieldDistinction(Beer dto) {
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

        return new Result(savedSubstyle, savedBrand);

    }

    private record Result(Substyle savedSubstyle, Brand savedBrand) {
    }

    public List<BeerDto> getAllBeerDtos() {

        return beerRepository.findAll().stream()
                .map(BeerMapper::mapToBeerDto)
                .collect(Collectors.toList());

    }

    public BeerDto getBeerDtoById(Long id) {

        return BeerMapper.mapToBeerDto(beerRepository.findById(id)
                .orElseThrow(() -> new BeerDoesNotExistException("Beer with id: " + id + " not found.")));

    }

    public List<Beer> getAllBeers() {

        return beerRepository.findAll();

    }

    public Beer getBeerById(Long id) {

        return beerRepository.findById(id)
                .orElseThrow(() -> new BeerDoesNotExistException("Beer with id: " + id + " not found."));

    }

    public Beer updateBeer(Beer beer) {
        beerRepository.findById(beer.getId())
                .orElseThrow(() -> new BeerDoesNotExistException("Beer you are trying to update not found."));

        beer.generateCode();

        return beerRepository.save(beer);

    }

    public void deleteBeerById(Long id) {
        Beer beer = beerRepository.findById(id)
                .orElseThrow(() -> new BeerDoesNotExistException("Beer you trying to delete not found."));

        beerRepository.delete(beer);

    }
}