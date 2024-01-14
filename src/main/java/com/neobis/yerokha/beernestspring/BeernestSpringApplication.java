package com.neobis.yerokha.beernestspring;

import com.neobis.yerokha.beernestspring.config.RSAKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(RSAKeyProperties.class)
public class BeernestSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(BeernestSpringApplication.class, args);
    }

}
