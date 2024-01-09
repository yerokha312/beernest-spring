package com.neobis.yerokha.beernestspring.dto;

import java.time.LocalDate;

public record CreateCustomerDto(String firstName, String lastName, LocalDate dob, String email, String password) {
}
