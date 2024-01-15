package com.neobis.yerokha.beernestspring.dto;

import java.time.LocalDate;

public record UserDto(Long id, String firstName, String lastName, LocalDate dob, String email) {
}
