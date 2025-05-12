package org.example.appliancestore.service.impl;

import lombok.AllArgsConstructor;
import org.example.appliancestore.aspect.Loggable;
import org.example.appliancestore.dto.AuthRequest;
import org.example.appliancestore.dto.RegistrationClient;
import org.example.appliancestore.dto.TokenResponse;
import org.example.appliancestore.exception.UsernameAlreadyExistsException;
import org.example.appliancestore.model.Client;
import org.example.appliancestore.repository.ClientRepository;
import org.example.appliancestore.service.AuthService;
import org.example.appliancestore.service.ClientService;
import org.example.appliancestore.token.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private UserDetailsService userDetailsService;
    private ClientRepository clientRepository;
    private ClientService clientService;
    private JwtUtils jwtUtils;
    private AuthenticationManager authenticationManager;

    @Loggable
    @Override
    public TokenResponse login(AuthRequest loginRequest) {
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            String token = jwtUtils.generateToken(userDetails);
            return new TokenResponse(token);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @Loggable
    @Override
    public Client register(RegistrationClient registrationClient) {
        if (clientRepository.existsByEmail(registrationClient.getEmail())) {
            throw new UsernameAlreadyExistsException(String.format("User with current email %s already exists!",
                    registrationClient.getEmail()));
        }
        Client client = clientService.registerClient(registrationClient);
        return client;
    }

    @Override
    public boolean isAuthenticated(Authentication authentication) {
        return authentication != null && authentication.isAuthenticated();
    }
}
