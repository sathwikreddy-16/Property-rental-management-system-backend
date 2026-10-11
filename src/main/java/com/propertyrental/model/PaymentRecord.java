package com.propertyrental.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name="payment_records")
public class PaymentRecord {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    private Long propertyId;
    private String payerName;
    private BigDecimal amount;
    private String status = "DEMO_RECORDED";
    private LocalDateTime createdAt = LocalDateTime.now();
    public PaymentRecord(){}
    @PrePersist void onCreate(){if(createdAt==null) createdAt=LocalDateTime.now();}
    public Long getId(){return id;} public void setId(Long v){id=v;}
    public Long getPropertyId(){return propertyId;} public void setPropertyId(Long v){propertyId=v;}
    public String getPayerName(){return payerName;} public void setPayerName(String v){payerName=v;}
    public BigDecimal getAmount(){return amount;} public void setAmount(BigDecimal v){amount=v;}
    public String getStatus(){return status;} public void setStatus(String v){status=v;}
    public LocalDateTime getCreatedAt(){return createdAt;} public void setCreatedAt(LocalDateTime v){createdAt=v;}
}
