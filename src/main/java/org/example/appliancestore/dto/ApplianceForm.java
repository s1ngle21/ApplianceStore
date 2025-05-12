package org.example.appliancestore.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApplianceForm {

    @NotNull(message = "Please choose an appliance")
    Integer applianceId;

    @NotNull(message = "Please enter the quantity of chosen appliance")
    @Min(value = 1, message = "Quantity of chosen appliance must be greater or equal to 1")
    Integer quantity;
}
