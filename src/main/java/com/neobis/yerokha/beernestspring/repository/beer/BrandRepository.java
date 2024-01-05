package com.neobis.yerokha.beernestspring.repository.beer;

import com.neobis.yerokha.beernestspring.entity.beer.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand, Long> {
    Optional<Brand> findBrandByName(String brandName);
}
