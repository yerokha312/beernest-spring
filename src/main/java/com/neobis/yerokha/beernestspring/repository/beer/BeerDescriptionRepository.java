package com.neobis.yerokha.beernestspring.repository.beer;

import com.neobis.yerokha.beernestspring.entity.beer.BeerDescription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BeerDescriptionRepository extends JpaRepository<BeerDescription, Long> {
}
