package com.neobis.yerokha.beernestspring.service.beer;

import com.neobis.yerokha.beernestspring.dto.BeerDto;
import com.neobis.yerokha.beernestspring.dto.BeerFullDto;
import com.neobis.yerokha.beernestspring.entity.beer.Beer;
import com.neobis.yerokha.beernestspring.entity.beer.BeerDescription;
import com.neobis.yerokha.beernestspring.entity.beer.Brand;
import com.neobis.yerokha.beernestspring.entity.beer.SubStyle;
import com.neobis.yerokha.beernestspring.enums.Container;
import com.neobis.yerokha.beernestspring.enums.Style;
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
    private final SubStyleService subStyleService;

    @Autowired
    public BeerService(BeerRepository beerRepository, BeerDescriptionService beerDescriptionService, BrandService brandService, SubStyleService subStyleService) {
        this.beerRepository = beerRepository;
        this.beerDescriptionService = beerDescriptionService;
        this.brandService = brandService;
        this.subStyleService = subStyleService;
    }

    public BeerFullDto createBeer(BeerFullDto dto) {
        Beer beer = new Beer();
        Result result = checkFieldDistinction(dto);

        BeerDescription beerDescription = new BeerDescription();
        beerDescription.setDescription(dto.beerDescription());
        BeerDescription savedBeerDescription = beerDescriptionService.createBeerDescription(beerDescription);

        beer.setName(dto.name());
        beer.setStyle(Style.valueOf(dto.style()));
        beer.setSubStyle(result.savedSubStyle());
        beer.setBrand(result.savedBrand());
        beer.setAlcohol(dto.alcohol());
        beer.setContainer(Container.valueOf(dto.container()));
        beer.setSize(dto.size());
        beer.setPurchasePrice(dto.purchasePrice());

        if (dto.sellingPrice() == null) {
            beer.setSellingPrice(dto.purchasePrice().multiply(BigDecimal.valueOf(2)));
        } else {
            beer.setSellingPrice(dto.sellingPrice());
        }

        beer.setCountry(dto.country());
        beer.setBeerDescription(savedBeerDescription);
        beer.generateCode();

        Optional<Beer> existingBeerOpt = beerRepository.findByBeerCode(beer.getBeerCode());

        // check if beer already exists in db, if yes, just refill storage Amount
        if (existingBeerOpt.isPresent()) {
            Beer existingBeer = existingBeerOpt.get();
            int updatedStock = existingBeer.getStockAmount() + dto.stockAmount();
            existingBeer.setStockAmount(updatedStock);

            return BeerMapper.mapToBeerFullDto(beerRepository.save(existingBeer));
        } else {
            return BeerMapper.mapToBeerFullDto(beerRepository.save(beer));
        }
    }

    private Result checkFieldDistinction(BeerFullDto dto) {
        SubStyle savedSubStyle = subStyleService.getSubStyleByName(dto.subStyle()).orElseGet(() -> {
            SubStyle subStyle = new SubStyle();
            subStyle.setName(dto.subStyle());
            subStyle.setStyle(Style.valueOf(dto.style()));
            return subStyleService.createSubStyle(subStyle);
        });

        Brand savedBrand = brandService.getBrandByName(dto.brand()).orElseGet(() -> {
            Brand brand = new Brand();
            brand.setName(dto.brand());
            return brandService.createBrand(brand);
        });

        return new Result(savedSubStyle, savedBrand);

    }

    public void updateBeer(Beer beer) {
        beerRepository.save(beer);
    }

    public BeerDto getBeerDtoById(Long beerId) {
        Beer beer = getBeerById(beerId);
        return BeerMapper.mapToBeerDto(beer);
    }

    private record Result(SubStyle savedSubStyle, Brand savedBrand) {
    }

    public List<BeerDto> getAllBeerDtos() {

        return beerRepository.findAll().stream()
                .map(BeerMapper::mapToBeerDto)
                .collect(Collectors.toList());

    }

    public BeerFullDto getBeerFullDtoById(Long id) {
        return BeerMapper.mapToBeerFullDto(getBeerById(id));

    }

    public List<BeerFullDto> getAllBeers() {

        return beerRepository.findAll().stream()
                .map(BeerMapper::mapToBeerFullDto)
                .collect(Collectors.toList());

    }

    public Beer getBeerById(Long id) {

        return beerRepository.findById(id)
                .orElseThrow(() -> new BeerDoesNotExistException("Beer with id: " + id + " not found."));

    }

    public BeerFullDto updateBeerFullDto(BeerFullDto dto) {
        Beer dbBeer = beerRepository.findById(dto.id())
                .orElseThrow(() -> new BeerDoesNotExistException("Beer you are trying to update not found."));
        BeerMapper.mapToBeerEntity(dbBeer, dto);
        dbBeer.generateCode();

        return BeerMapper.mapToBeerFullDto(beerRepository.save(dbBeer));

    }

    public void deleteBeerById(Long id) {
        Beer beer = beerRepository.findById(id)
                .orElseThrow(() -> new BeerDoesNotExistException("Beer you trying to delete not found."));

        beerRepository.delete(beer);

    }
}