package org.example.appliancestore.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationClient {

    @Pattern(regexp = "[A-Z][a-z]+",
            message = "Must start with a capital letter followed by one or more lowercase letters!")
    @Column(nullable = false)
    private String name;

    @Email(message = "Please enter a valid e-mail address!")
    @NotEmpty(message = "Please enter an e-mail address!")
    @Column(unique = true)
    private String email;

    @Pattern(regexp = "(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}",
            message = "Must be minimum 6 characters and have at least one letter and one number!")
    private String password;

    @Pattern(regexp = "[0-9]+", message = "Card number, must contain only numbers!")
    private String card;
}
