package com.neobis.yerokha.beernestspring.entity.user;

import com.neobis.yerokha.beernestspring.enums.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "employee")
@Getter
@Setter
public class Employee extends Person {

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "employee_role_junction",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    private Set<Role> authorities;

    public Employee() {
    }

    @Override
    public String toString() {
        return "Employee{" +
                super.toString() +
                "}";
    }
}
