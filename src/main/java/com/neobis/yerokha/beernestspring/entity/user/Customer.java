package com.neobis.yerokha.beernestspring.entity.user;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "customer")
public class Customer extends User {

    @OneToMany(mappedBy = "customer")
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
