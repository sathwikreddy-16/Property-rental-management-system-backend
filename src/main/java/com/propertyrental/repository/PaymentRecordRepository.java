package com.propertyrental.repository;
import com.propertyrental.model.PaymentRecord;
import org.springframework.data.jpa.repository.JpaRepository;
public interface PaymentRecordRepository extends JpaRepository<PaymentRecord, Long> {}
