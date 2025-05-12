package org.example.appliancestore.repository;

import org.example.appliancestore.model.Client;
import org.example.appliancestore.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;



public interface ClientRepository extends JpaRepository<Client, Long> {
    User findByEmail(String email);
    Page<Client> findAll(Pageable pageable);
    boolean existsByEmail(String email);
}



