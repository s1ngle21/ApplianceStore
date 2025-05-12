package org.example.appliancestore.repository;

import org.example.appliancestore.model.Employee;
import org.example.appliancestore.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Page<Employee> findAll(Pageable pageable);
    User findByEmail(String email);
}
