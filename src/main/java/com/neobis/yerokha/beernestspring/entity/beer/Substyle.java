package com.neobis.yerokha.beernestspring.entity.beer;

import com.neobis.yerokha.beernestspring.enums.Style;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "substyle")
public class Substyle {

    @Id
    @Column(name = "substyle_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "style")
    @Enumerated(EnumType.STRING)
    private Style style;

    @Column(name = "name", unique = true)
    private String name;

    //    @JsonManagedReference
    @OneToMany(mappedBy = "substyle", fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<Beer> beerSet = new HashSet<>();

    public void addBeer(Beer beer) {
        if (beerSet == null) {
            beerSet = new HashSet<>();
        }
        beerSet.add(beer);
    }

}
