package com.neobis.yerokha.beernestspring.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@ConfigurationProperties(prefix = "rsa")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RSAKeyProperties {

    public RSAPublicKey publicKey;
    private RSAPrivateKey privateKey;
}
