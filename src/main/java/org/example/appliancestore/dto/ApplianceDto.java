package org.example.appliancestore.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.appliancestore.model.Category;
import org.example.appliancestore.model.Manufacturer;
import org.example.appliancestore.model.PowerType;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplianceDto {

    private Long id;

    @NotEmpty(message = "Name must not be empty")
    private String name;

    @Enumerated(EnumType.STRING)
    private Category category;

    @NotEmpty(message = "Model must not be empty")
    private String model;

    private Manufacturer manufacturer;

    private PowerType powerType;

    @NotEmpty(message = "Characteristic must not be empty")
    private String characteristic;

    @NotEmpty(message = "Description must not be empty")
    private String description;

    @NotNull(message = "Power must not be empty")
    @Min(value = 0, message = "Power must be above 0")
    private Integer power;

    @NotNull(message = "Price must not be empty")
    @Min(value = 0, message = "Price must be above 0")
    private BigDecimal price;

}
