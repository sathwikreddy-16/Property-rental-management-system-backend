package com.propertyrental.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "rental_applications")
public class RentalApplication {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @NotNull private Long propertyId;
    @NotBlank private String applicantName;
    @Email @NotBlank private String email;
    private String phone;
    @Column(length = 1000) private String message;
    private String status = "PENDING";
    private LocalDateTime appliedAt = LocalDateTime.now();

    public RentalApplication() {}
    @PrePersist void onCreate(){ if(appliedAt==null) appliedAt=LocalDateTime.now(); if(status==null) status="PENDING"; }
    public Long getId(){return id;} public void setId(Long v){id=v;}
    public Long getPropertyId(){return propertyId;} public void setPropertyId(Long v){propertyId=v;}
    public String getApplicantName(){return applicantName;} public void setApplicantName(String v){applicantName=v;}
    public String getEmail(){return email;} public void setEmail(String v){email=v;}
    public String getPhone(){return phone;} public void setPhone(String v){phone=v;}
    public String getMessage(){return message;} public void setMessage(String v){message=v;}
    public String getStatus(){return status;} public void setStatus(String v){status=v;}
    public LocalDateTime getAppliedAt(){return appliedAt;} public void setAppliedAt(LocalDateTime v){appliedAt=v;}
}
