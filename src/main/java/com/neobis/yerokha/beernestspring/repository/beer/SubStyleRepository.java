package com.neobis.yerokha.beernestspring.repository.beer;

import com.neobis.yerokha.beernestspring.entity.beer.SubStyle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubStyleRepository extends JpaRepository<SubStyle, Long> {

    Optional<SubStyle> findSubStyleByName(String name);
}
