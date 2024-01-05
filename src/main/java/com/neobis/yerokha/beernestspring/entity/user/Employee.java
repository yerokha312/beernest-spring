package com.neobis.yerokha.beernestspring.entity.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "employee")
public class Employee extends User {

    public Employee() {
    }

    @Override
    public String toString() {
        return "Employee{" +
                super.toString() +
                "}";
    }
}
