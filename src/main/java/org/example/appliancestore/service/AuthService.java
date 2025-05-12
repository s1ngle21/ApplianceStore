package org.example.appliancestore.service;

import org.example.appliancestore.dto.AuthRequest;
import org.example.appliancestore.dto.RegistrationClient;
import org.example.appliancestore.dto.TokenResponse;
import org.example.appliancestore.model.Client;
import org.springframework.security.core.Authentication;

public interface AuthService {
    TokenResponse login(AuthRequest authRequest);
    Client register(RegistrationClient registrationClient);
    boolean isAuthenticated(Authentication authentication);
}
