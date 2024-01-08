package com.neobis.yerokha.beernestspring.entity.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "customer")
@Getter @Setter
public class Customer extends User {

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Order> orders;

    public Customer() {
    }

    @Override
    public String toString() {
        return "Customer{" +
                super.toString() +
                "}";
    }
}
