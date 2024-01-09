package com.neobis.yerokha.beernestspring.service.user;

import com.neobis.yerokha.beernestspring.dto.CreateCustomerDto;
import com.neobis.yerokha.beernestspring.dto.CustomerDto;
import com.neobis.yerokha.beernestspring.entity.user.ContactInfo;
import com.neobis.yerokha.beernestspring.entity.user.Customer;
import com.neobis.yerokha.beernestspring.exception.CustomerDoesNotExistException;
import com.neobis.yerokha.beernestspring.exception.EmailAlreadyTakenException;
import com.neobis.yerokha.beernestspring.repository.user.ContactInfoRepository;
import com.neobis.yerokha.beernestspring.repository.user.CustomerRepository;
import com.neobis.yerokha.beernestspring.util.CustomerMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {

    private final CustomerRepository customerRepository;
    private final ContactInfoRepository contactInfoRepository;

    public static final int PAGE_SIZE = 10;

    public UserService(CustomerRepository customerRepository, ContactInfoRepository contactInfoRepository) {
        this.customerRepository = customerRepository;
        this.contactInfoRepository = contactInfoRepository;
    }

    public Customer createCustomer(CreateCustomerDto dto) {
        Customer customer = CustomerMapper.mapToCustomerEntity(dto);
        customer.setRegistrationTime(LocalDateTime.now());

        try {
            return customerRepository.save(customer);
        } catch (Exception e) {
            throw new EmailAlreadyTakenException("The email provided is already taken");
        }
    }

    public Page<CustomerDto> getAllCustomerDtos(Pageable pageable) {
        return customerRepository.findAll(pageable).map(CustomerMapper::mapToCustomerDto);
    }

    private Customer getCustomerById(Long id) {

        return customerRepository.findById(id).orElseThrow(() ->
                new CustomerDoesNotExistException("Customer with id: " + id + " not found"));

    }

    public CustomerDto getCustomerDtoById(Long id) {
        return CustomerMapper.mapToCustomerDto(
                customerRepository.findById(id)
                        .orElseThrow(() ->
                                new CustomerDoesNotExistException("Customer with id: " + id + " not found.")));
    }

    public CustomerDto updateCustomer(CustomerDto customerDto) {
        Customer dbCustomer = getCustomerById(customerDto.id());

        CustomerMapper.mapToCustomerEntity(customerDto, dbCustomer);

        customerRepository.save(dbCustomer);

        return customerDto;
    }

    public ContactInfo addContacts(Long id, ContactInfo contactInfo) {
        Customer customer = getCustomerById(id);
        customer.getContactInfo().add(contactInfo);
        customerRepository.save(customer);
        return contactInfo;
    }
}