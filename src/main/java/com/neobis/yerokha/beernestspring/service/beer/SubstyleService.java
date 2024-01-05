package com.neobis.yerokha.beernestspring.service.beer;

import com.neobis.yerokha.beernestspring.entity.beer.Substyle;
import com.neobis.yerokha.beernestspring.repository.beer.SubstyleRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SubstyleService {

    private final SubstyleRepository substyleRepository;

    public SubstyleService(SubstyleRepository substyleRepository) {
        this.substyleRepository = substyleRepository;
    }

    public Substyle createSubstyle(Substyle substyle) {
        return substyleRepository.save(substyle);
    }

    public Optional<Substyle> getSubstyleByName(String substyleName) {
        return substyleRepository.findSubstyleByName(substyleName);
    }
}
