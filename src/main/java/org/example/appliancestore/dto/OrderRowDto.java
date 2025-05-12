package org.example.appliancestore.dto;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRowDto {

    private Long id;

    private ApplianceDto appliance;

    @Min(value = 1, message = "Please enter the quantity of chosen appliance")
    private Long number;

    @Min(value = 0, message = "Amount must be above 0")
    private BigDecimal amount;
}
