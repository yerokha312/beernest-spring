package com.neobis.yerokha.beernestspring.controller.rest;

import com.neobis.yerokha.beernestspring.dto.ContactInfoDto;
import com.neobis.yerokha.beernestspring.entity.user.ContactInfo;
import com.neobis.yerokha.beernestspring.service.user.ContactsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Contacts", description = "Controller for addresses and phone numbers of customers")
@ApiResponse(responseCode = "401", description = "User is not authorized")
@ApiResponse(responseCode = "403", description = "User is not CUSTOMER or accessing not own data", content = @Content)
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/v1/contacts")
public class ContactController {

    private final ContactsService contactsService;

    public ContactController(ContactsService contactsService) {
        this.contactsService = contactsService;
    }

    @Operation(
            summary = "Add new contact",
            description = "Create a new contact object with phone number and address",
            tags = {"contacts", "post"}
    )
    @ApiResponse(responseCode = "201", description = "Success")
    @PostMapping("/create")
    public ResponseEntity<ContactInfoDto> addContactInfo(Authentication authentication,
                                                         @RequestBody ContactInfo contactInfo) {
        return new ResponseEntity<>(contactsService.addContacts(getUserIdFromAuthToken(authentication),
                contactInfo), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get all contacts",
            description = "Get all contacts related to querying customer. Can't get other user's contacts",
            tags = {"contacts", "get"}
    )
    @ApiResponse(responseCode = "200", description = "Success")
    @GetMapping("/all")
    public ResponseEntity<List<ContactInfoDto>> getContactInfoList(Authentication authentication) {
        return ResponseEntity.ok().body(contactsService.getContacts(getUserIdFromAuthToken(authentication)));
    }

    @Operation(
            summary = "Get contact by contactId",
            description = "Get one contact by contact contactId. Can query only own contacts",
            tags = {"contacts", "get"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Contact info with given contactId not found", content = @Content)
    })
    @Parameter(name = "contactId", description = "Unique identifier of contact info object")
    @GetMapping("/{contactId}")
    public ResponseEntity<ContactInfoDto> getContactInfo(Authentication authentication, @PathVariable Long contactId) {
        return ResponseEntity.ok().body(contactsService.getOneContact(getUserIdFromAuthToken(authentication), contactId));
    }

    @Operation(
            summary = "Update contact info",
            description = "Update contact info specified by contactId in RequestBody",
            tags = {"contacts", "put"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Contact info with given contactId not found", content = @Content)
    })
    @PutMapping("/update")
    public ResponseEntity<ContactInfoDto> modifyContactInfo(Authentication authentication, @RequestBody ContactInfoDto dto) {
        return ResponseEntity.ok().body(contactsService.updateContactInfo(getUserIdFromAuthToken(authentication), dto));
    }

    @Operation(
            summary = "Delete contact by contactId",
            description = "Delete one contact by contact contactId. Can modify only own contacts",
            tags = {"contacts", "delete"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Contact info successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Contact info with given contactId not found", content = @Content)
    })
    @Parameter(name = "contactId", description = "Unique identifier of contact info object")
    @DeleteMapping("/delete/{contactId}")
    public ResponseEntity<String> deleteContactInfo(Authentication authentication, @PathVariable Long contactId) {
        contactsService.setActiveFalse(getUserIdFromAuthToken(authentication), contactId);
        return ResponseEntity.ok().body("Contact info successfully deleted");
    }

}
