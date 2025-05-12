package org.example.appliancestore.dto;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private Long id;

    @NotNull
    private EmployeeDto employee;

    @NotNull
    private ClientDto client;

    private Set<OrderRowDto> orderRowSetDto = new HashSet<>();

    private Boolean approved = false;

    public BigDecimal getAmount() {
        return orderRowSetDto.stream()
                .map(orderRowDto -> orderRowDto.getAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
