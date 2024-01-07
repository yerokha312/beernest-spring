package com.neobis.yerokha.beernestspring.repository.user;

import com.neobis.yerokha.beernestspring.entity.user.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByEmail(String email);
}
