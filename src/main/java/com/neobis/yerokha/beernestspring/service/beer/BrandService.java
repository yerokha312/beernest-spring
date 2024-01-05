package com.neobis.yerokha.beernestspring.service.beer;

import com.neobis.yerokha.beernestspring.entity.beer.Brand;
import com.neobis.yerokha.beernestspring.repository.beer.BrandRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BrandService {

    private final BrandRepository brandRepository;

    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    public Brand createBrand(Brand brand) {
        return brandRepository.save(brand);
    }

    public Optional<Brand> getBrandByName(String brandName) {
        return brandRepository.findBrandByName(brandName);
    }
}
