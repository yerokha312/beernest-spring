package com.neobis.yerokha.beernestspring.dto;

import com.neobis.yerokha.beernestspring.entity.user.Customer;
import lombok.Data;

@Data
public class LoginResponse {
    private Customer customer;
}
