package org.example.appliancestore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenResponse {
    private String token;

    public String getToken() {
    return this.token;
    }
}
