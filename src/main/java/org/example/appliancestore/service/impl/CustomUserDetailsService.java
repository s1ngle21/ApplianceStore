package org.example.appliancestore.service.impl;

import lombok.AllArgsConstructor;
import org.example.appliancestore.model.User;
import org.example.appliancestore.repository.ClientRepository;
import org.example.appliancestore.repository.EmployeeRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final ClientRepository clientRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        User user;
        user = clientRepository.findByEmail(email);
        if (user != null) {
            return user;
        }
        user = employeeRepository.findByEmail(email);
        if (user != null) {
            return user;
        }
        throw new UsernameNotFoundException("User with email: " + email + " does not exist");
    }

}