package org.example.appliancestore.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Entity
@Table
public class OrderRow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "appliance_id")
    private Appliance appliance;

    @Min(value = 1, message = "Please enter the quantity of chosen appliance")
    private Long number;

    @Min(value = 0, message = "Amount must be above 0")
    private BigDecimal amount;

    public OrderRow(Long id, Appliance appliance, Long number, BigDecimal amount) {
        this.id = id;
        this.appliance = appliance;
        this.number = number;
        this.amount = amount;
    }
}
