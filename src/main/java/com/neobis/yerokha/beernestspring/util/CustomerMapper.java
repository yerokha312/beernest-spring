package com.neobis.yerokha.beernestspring.util;

import com.neobis.yerokha.beernestspring.dto.CreateCustomerDto;
import com.neobis.yerokha.beernestspring.entity.user.Customer;

public class CustomerMapper {

    public static Customer mapToCustomerEntity(CreateCustomerDto dto) {
        Customer entity = new Customer();

        entity.setFirstName(dto.firstName());
        entity.setLastName(dto.LastName());
        entity.setBirthDate(dto.dob());
        entity.setEmail(dto.email());
        entity.setPassword(dto.password());

        return entity;
    }
}
