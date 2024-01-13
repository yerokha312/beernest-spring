package com.neobis.yerokha.beernestspring.service.beer;

import com.neobis.yerokha.beernestspring.entity.beer.SubStyle;
import com.neobis.yerokha.beernestspring.repository.beer.SubStyleRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SubStyleService {

    private final SubStyleRepository subStyleRepository;

    public SubStyleService(SubStyleRepository subStyleRepository) {
        this.subStyleRepository = subStyleRepository;
    }

    public SubStyle createSubStyle(SubStyle subStyle) {
        return subStyleRepository.save(subStyle);
    }

    public Optional<SubStyle> getSubStyleByName(String subStyleName) {
        return subStyleRepository.findSubStyleByName(subStyleName);
    }
}
