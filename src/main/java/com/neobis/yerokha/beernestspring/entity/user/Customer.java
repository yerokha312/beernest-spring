package com.neobis.yerokha.beernestspring.entity.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.neobis.yerokha.beernestspring.enums.Role;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "customer")
@Getter
@Setter
public class Customer extends Person {

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "customer_role_junction",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    private Set<Role> authorities;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Order> orders;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "customer_contact_junction",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "contact_info_id")
    )
    private Set<ContactInfo> contactInfo;

    public Customer() {
    }

    @Override
    public String toString() {
        return "Customer{" +
                super.toString() +
                "}";
    }
}
