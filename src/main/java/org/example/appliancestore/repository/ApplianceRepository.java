package org.example.appliancestore.repository;

import org.example.appliancestore.model.Appliance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ApplianceRepository extends JpaRepository<Appliance, Long> {
    Page<Appliance> findAll(Pageable pageable);
}
