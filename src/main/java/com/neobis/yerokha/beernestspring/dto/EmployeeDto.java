package com.neobis.yerokha.beernestspring.dto;

import com.neobis.yerokha.beernestspring.enums.Role;

import java.time.LocalDate;
import java.util.Set;

public record EmployeeDto(Long id, String firstName, String lastName, LocalDate birthDate, String email,
                          String password, Set<Role> authorities) {
}
