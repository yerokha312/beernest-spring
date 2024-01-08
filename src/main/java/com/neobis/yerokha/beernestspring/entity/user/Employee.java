package com.neobis.yerokha.beernestspring.entity.user;

import com.neobis.yerokha.beernestspring.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "employee")
@Getter @Setter
public class Employee extends User {

    @Enumerated(EnumType.STRING)
    @Column(name = "authority")
    private Role role;

    public Employee() {
    }

    @Override
    public String toString() {
        return "Employee{" +
                super.toString() +
                this.getRole() +
                "}";
    }
}
