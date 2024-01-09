package com.neobis.yerokha.beernestspring.service.user;

import com.neobis.yerokha.beernestspring.entity.user.ContactInfo;
import com.neobis.yerokha.beernestspring.exception.ContactInfoException;
import com.neobis.yerokha.beernestspring.exception.CustomerDoesNotExistException;
import com.neobis.yerokha.beernestspring.repository.user.ContactInfoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactsService {

    private final ContactInfoRepository contactInfoRepository;

    public ContactsService(ContactInfoRepository contactInfoRepository) {
        this.contactInfoRepository = contactInfoRepository;
    }


    public List<ContactInfo> getContacts(Long id) {
        try {
            return contactInfoRepository.findAllByCustomersId(id);
        } catch (Exception e) {
            throw new CustomerDoesNotExistException("Customer with id: " + id + " not found.");
        }
    }

    public ContactInfo getOneContact(Long customerId, Long id) {
        return contactInfoRepository.findContactInfoByCustomersIdAndId(customerId, id)
                .orElseThrow(() -> new ContactInfoException("Contact info not found"));
    }
}
