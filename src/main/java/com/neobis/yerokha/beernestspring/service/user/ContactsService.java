package com.neobis.yerokha.beernestspring.service.user;

import com.neobis.yerokha.beernestspring.dto.ContactInfoDto;
import com.neobis.yerokha.beernestspring.entity.user.ContactInfo;
import com.neobis.yerokha.beernestspring.entity.user.Customer;
import com.neobis.yerokha.beernestspring.exception.ContactInfoException;
import com.neobis.yerokha.beernestspring.exception.UserDoesNotExistException;
import com.neobis.yerokha.beernestspring.repository.user.ContactInfoRepository;
import com.neobis.yerokha.beernestspring.repository.user.CustomerRepository;
import com.neobis.yerokha.beernestspring.util.ContactMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.neobis.yerokha.beernestspring.util.ContactMapper.dtoToEntity;
import static com.neobis.yerokha.beernestspring.util.ContactMapper.entityToDto;

@Service
public class ContactsService {

    private final ContactInfoRepository contactInfoRepository;
    private final UserService userService;
    private final CustomerRepository customerRepository;

    public ContactsService(ContactInfoRepository contactInfoRepository, UserService userService, CustomerRepository customerRepository) {
        this.contactInfoRepository = contactInfoRepository;
        this.userService = userService;
        this.customerRepository = customerRepository;
    }

    public ContactInfoDto addContacts(Long id, ContactInfo contactInfo) {
        Customer customer = userService.getCustomerById(id);
        contactInfo.setIsActive(true);
        customer.getContactInfo().add(contactInfo);
        customerRepository.save(customer);
        return entityToDto(contactInfo);
    }

    public List<ContactInfoDto> getContacts(Long id) {
        try {
            return contactInfoRepository.findAllByCustomersId(id).stream()
                    .map(ContactMapper::entityToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new UserDoesNotExistException("Customer with id: " + id + " not found.");
        }
    }

    public ContactInfoDto getOneContact(Long customerId, Long id) {
        return entityToDto(contactInfoRepository.findByCustomersIdAndId(customerId, id)
                .orElseThrow(() -> new ContactInfoException("Contact info not found")));
    }

    public ContactInfoDto updateContactInfo(Long customerId, ContactInfoDto dto) {
        ContactInfo contactInfo = contactInfoRepository.findByCustomersIdAndId(customerId, dto.id())
                .orElseThrow(() -> new ContactInfoException("Contact info not found"));
        dtoToEntity(dto, contactInfo);
        return entityToDto(contactInfoRepository.save(contactInfo));
    }

    public void setActiveFalse(Long customerId, Long id) {
        ContactInfo contactInfo = contactInfoRepository.findByCustomersIdAndId(customerId, id).orElseThrow(() ->
                new ContactInfoException("Contact info not found"));
        contactInfo.setIsActive(false);
        contactInfoRepository.save(contactInfo);
    }
}
