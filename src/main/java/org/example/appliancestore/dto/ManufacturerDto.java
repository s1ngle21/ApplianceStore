package org.example.appliancestore.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManufacturerDto {

    private Long id;

    @NotEmpty(message = "Name must not be empty")
    private String name;
}
