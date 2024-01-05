package com.neobis.yerokha.beernestspring.service.beer;

import com.neobis.yerokha.beernestspring.entity.beer.BeerDescription;
import com.neobis.yerokha.beernestspring.repository.beer.BeerDescriptionRepository;
import org.springframework.stereotype.Service;

@Service
public class BeerDescriptionService {

    private final BeerDescriptionRepository beerDescriptionRepository;

    public BeerDescriptionService(BeerDescriptionRepository beerDescriptionRepository) {
        this.beerDescriptionRepository = beerDescriptionRepository;
    }

    public BeerDescription createBeerDescription(BeerDescription description) {
        return beerDescriptionRepository.save(description);
    }
}
