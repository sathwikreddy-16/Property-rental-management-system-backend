package com.propertyrental.repository;
import com.propertyrental.model.RentalApplication;
import org.springframework.data.jpa.repository.JpaRepository;
public interface RentalApplicationRepository extends JpaRepository<RentalApplication, Long> {
    long countByStatus(String status);
}
