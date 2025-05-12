package org.example.appliancestore.repository;

import org.example.appliancestore.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrdersRepository extends JpaRepository<Order, Long> {
    Page<Order> findAll(Pageable pageable);

    @Query("SELECT o FROM Order o WHERE o.client.id = :clientId")
    Page<Order> findAllByClientId(@Param("clientId") Long clientId, Pageable pageable);
}






