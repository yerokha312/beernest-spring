package com.neobis.yerokha.beernestspring.entity.beer;

import com.neobis.yerokha.beernestspring.enums.Style;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "sub_style")
@NoArgsConstructor
@AllArgsConstructor
public class SubStyle {

    @Id
    @Column(name = "sub_style_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "style")
    @Enumerated(EnumType.STRING)
    private Style style;

    @Column(name = "name", unique = true)
    private String name;
}
