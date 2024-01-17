package com.neobis.yerokha.beernestspring.util;

import com.neobis.yerokha.beernestspring.dto.ContactInfoDto;
import com.neobis.yerokha.beernestspring.entity.user.ContactInfo;

public class ContactMapper {

    public static ContactInfoDto entityToDto(ContactInfo entity) {
        return new ContactInfoDto(entity.getId(), entity.getPhoneNumber(), entity.getAddress());
    }

    public static ContactInfo dtoToEntity(ContactInfoDto dto, ContactInfo entity) {
        entity.setPhoneNumber(dto.phoneNumber());
        entity.setAddress(dto.address());
        return entity;
    }
}
