package com.neobis.yerokha.beernestspring.dto;

import java.sql.Date;

public record CreateCustomerDto(String firstName, String LastName, Date dob, String email, String password) {
}
