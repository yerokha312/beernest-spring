package com.neobis.yerokha.beernestspring.entity.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "customer")
public class Customer extends User {

    public Customer() {
    }

    @Override
    public String toString() {
        return "Customer{" +
                super.toString() +
                "}";
    }
}
