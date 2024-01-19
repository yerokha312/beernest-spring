package com.neobis.yerokha.beernestspring.entity.beer;

import com.neobis.yerokha.beernestspring.enums.Container;
import com.neobis.yerokha.beernestspring.enums.Style;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Objects;

@Data
@Entity
@Table(name = "beer")
public class Beer {

    @Id
    @Column(name = "beer_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "beer_code", unique = true)
    private String beerCode;

    @Column(name = "name")
    private String name;

    @Column(name = "style")
    @Enumerated(EnumType.STRING)
    private Style style;

    @ManyToOne
    @JoinColumn(name = "sub_style_id")
    private SubStyle subStyle;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @Column(name = "alcohol")
    private Double alcohol;

    @Column(name = "container")
    @Enumerated(EnumType.STRING)
    private Container container;

    @Column(name = "size")
    private Integer size;

    @Column(name = "purchase_price")
    private BigDecimal purchasePrice;

    @Column(name = "selling_price")
    private BigDecimal sellingPrice;

    @Column(name = "country")
    private String country;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "description_id")
    private BeerDescription beerDescription;

    @Column(name = "sold_amount")
    private long soldAmount;

    @Column(name = "stock_amount")
    private int stockAmount;

    public Beer() {

    }

    public void generateCode() {
        StringBuilder builder = new StringBuilder();
        builder
                .append(brand.getName().length() > 4 ? brand.getName().substring(0, 3) : brand)
                .append("-")
                .append(style.toString(), 0, 1)
                .append("-");

        String[] subArray = subStyle.getName().split(" ");
        for (String s : subArray) {
            builder.append(s.charAt(0));
        }

        builder
                .append("-")
                .append(((int) (alcohol * 10)))
                .append(container.toString().charAt(0))
                .append(size)
                .append(country, 0, 3);


        this.beerCode = String.valueOf(builder).toUpperCase();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Beer beer = (Beer) o;
        return Objects.equals(name, beer.name) && style == beer.style && Objects.equals(subStyle, beer.subStyle) &&
                Objects.equals(brand, beer.brand) && Objects.equals(alcohol, beer.alcohol) &&
                container == beer.container && Objects.equals(size, beer.size) && Objects.equals(country, beer.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, style, subStyle, brand, alcohol, container, size, country);
    }
}
