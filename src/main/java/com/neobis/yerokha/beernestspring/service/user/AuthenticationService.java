package com.neobis.yerokha.beernestspring.service.user;

import com.neobis.yerokha.beernestspring.dto.Credentials;
import com.neobis.yerokha.beernestspring.exception.InvalidCredentialsException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AuthenticationService(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    public String login(Credentials credentials) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(credentials.username(), credentials.password()));
            return tokenService.generateToken(authentication);
        } catch (
                AuthenticationException e) {
            throw new InvalidCredentialsException("Username or password is invalid");
        }
    }
}
