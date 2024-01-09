package com.neobis.yerokha.beernestspring.util;

import com.neobis.yerokha.beernestspring.dto.CreateCustomerDto;
import com.neobis.yerokha.beernestspring.dto.CustomerDto;
import com.neobis.yerokha.beernestspring.entity.user.Customer;

public class CustomerMapper {

    public static Customer mapToCustomerEntity(CreateCustomerDto dto) {
        Customer entity = new Customer();

        entity.setFirstName(dto.firstName());
        entity.setLastName(dto.lastName());
        entity.setBirthDate(dto.dob());
        entity.setEmail(dto.email());
        entity.setPassword(dto.password());

        return entity;
    }

    public static CustomerDto mapToCustomerDto(Customer customer) {

        return new CustomerDto(
                customer.getId(), customer.getFirstName(), customer.getLastName(),
                customer.getBirthDate(), customer.getEmail());
    }

    public static void mapToCustomerEntity(CustomerDto dto, Customer entity) {

        entity.setFirstName(dto.firstName());
        entity.setLastName(dto.lastName());
        entity.setBirthDate(dto.dob());
        entity.setEmail(dto.email());

    }
}
