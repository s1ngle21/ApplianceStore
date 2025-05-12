package org.example.appliancestore.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {

    @NotEmpty(message = "Email must be provided!")
    private String email;

    @NotEmpty(message = "Password must be provided!")
    private String password;
}