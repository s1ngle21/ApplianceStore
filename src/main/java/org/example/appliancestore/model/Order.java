package org.example.appliancestore.model;

import jakarta.persistence.*;
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
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    @NotNull
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "client_id")
    @NotNull
    private Client client;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "order_order_row",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "order_row_id")
    )
    private Set<OrderRow> orderRowSet = new HashSet<>();

    private Boolean approved = false;


    public BigDecimal getAmount() {
        return orderRowSet.stream()
                .map(orderRow -> orderRow.getAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
