package com.neobis.yerokha.beernestspring.service.user;

import com.neobis.yerokha.beernestspring.dto.CreateCustomerDto;
import com.neobis.yerokha.beernestspring.entity.user.Customer;
import com.neobis.yerokha.beernestspring.exception.CustomerDoesNotExistException;
import com.neobis.yerokha.beernestspring.exception.EmailAlreadyTakenException;
import com.neobis.yerokha.beernestspring.exception.PhoneNumberAlreadyTakenException;
import com.neobis.yerokha.beernestspring.repository.user.CustomerRepository;
import com.neobis.yerokha.beernestspring.util.CustomerMapper;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer createCustomer(CreateCustomerDto dto) {
        Customer customer = CustomerMapper.mapToCustomerEntity(dto);
        try {
            return customerRepository.save(customer);
        } catch (Exception e) {
            throw new EmailAlreadyTakenException("The email provided is already taken");
        }
    }

    public Customer getCustomerByEmail(String email) {
        Customer customer = customerRepository.findByEmail(email).orElseThrow(() ->
                new CustomerDoesNotExistException("Customer with provided email address does not exist"));

        return customer;
    }

    public Customer updateCustomer(Customer customer) {
        try {
            return customerRepository.save(customer);
        } catch (Exception e) {
            throw new PhoneNumberAlreadyTakenException("The phone number provided is already taken");
        }
    }
}
