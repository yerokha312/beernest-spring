package com.neobis.yerokha.beernestspring.repository;

import com.neobis.yerokha.beernestspring.entity.beer.Beer;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BeerRepository extends JpaRepository<Beer, Long> {
}
