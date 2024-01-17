package com.neobis.yerokha.beernestspring.controller.rest;

import com.neobis.yerokha.beernestspring.dto.ContactInfoDto;
import com.neobis.yerokha.beernestspring.entity.user.ContactInfo;
import com.neobis.yerokha.beernestspring.service.user.ContactsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.neobis.yerokha.beernestspring.service.user.TokenService.getUserIdFromAuthToken;

@RestController
@RequestMapping("/v1/contacts")
public class ContactController {

    private final ContactsService contactsService;

    public ContactController(ContactsService contactsService) {
        this.contactsService = contactsService;
    }

    @PostMapping("/create")
    public ResponseEntity<ContactInfoDto> addContactInfo(Authentication authentication,
                                                         @RequestBody ContactInfo contactInfo) {
        return new ResponseEntity<>(contactsService.addContacts(getUserIdFromAuthToken(authentication),
                contactInfo), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ContactInfoDto>> getContactInfoList(Authentication authentication) {
        return ResponseEntity.ok().body(contactsService.getContacts(getUserIdFromAuthToken(authentication)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactInfoDto> getContactInfo(Authentication authentication, @PathVariable Long id) {
        return ResponseEntity.ok().body(contactsService.getOneContact(getUserIdFromAuthToken(authentication), id));
    }

    @PutMapping("/update")
    public ResponseEntity<ContactInfoDto> modifyContactInfo(Authentication authentication, @RequestBody ContactInfoDto dto) {
        return ResponseEntity.ok().body(contactsService.updateContactInfo(getUserIdFromAuthToken(authentication), dto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteContactInfo(Authentication authentication, @PathVariable Long id) {
        contactsService.setActiveFalse(getUserIdFromAuthToken(authentication), id);
        return ResponseEntity.ok().body("Contact info successfully deleted");
    }

}
