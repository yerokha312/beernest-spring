package com.neobis.yerokha.beernestspring.repository.beer;

import com.neobis.yerokha.beernestspring.entity.beer.Beer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface BeerRepository extends JpaRepository<Beer, Long> {

    Optional<Beer> findBeerByName(String name);

    Optional<Beer> findByBeerCode(String code);
}
