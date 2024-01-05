package com.neobis.yerokha.beernestspring.repository.beer;

import com.neobis.yerokha.beernestspring.entity.beer.Substyle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubstyleRepository extends JpaRepository<Substyle, Long> {

    Optional<Substyle> findSubstyleByName(String name);
}
