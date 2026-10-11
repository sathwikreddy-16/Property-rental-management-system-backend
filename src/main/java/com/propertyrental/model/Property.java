package com.propertyrental.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "properties")
public class Property {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank private String title;
    @NotBlank private String location;
    @NotBlank private String city;
    @NotBlank private String propertyType;
    @NotNull @DecimalMin("0.0") private BigDecimal monthlyRent;
    @Min(0) private int bedrooms;
    @Min(0) private int bathrooms;
    @Column(length = 2000) private String description;
    private String imageUrl;
    private boolean available = true;
    private String ownerName;
    private LocalDateTime createdAt = LocalDateTime.now();

    public Property() {}
    @PrePersist void onCreate() { if (createdAt == null) createdAt = LocalDateTime.now(); }
    public Long getId(){return id;} public void setId(Long v){id=v;}
    public String getTitle(){return title;} public void setTitle(String v){title=v;}
    public String getLocation(){return location;} public void setLocation(String v){location=v;}
    public String getCity(){return city;} public void setCity(String v){city=v;}
    public String getPropertyType(){return propertyType;} public void setPropertyType(String v){propertyType=v;}
    public BigDecimal getMonthlyRent(){return monthlyRent;} public void setMonthlyRent(BigDecimal v){monthlyRent=v;}
    public int getBedrooms(){return bedrooms;} public void setBedrooms(int v){bedrooms=v;}
    public int getBathrooms(){return bathrooms;} public void setBathrooms(int v){bathrooms=v;}
    public String getDescription(){return description;} public void setDescription(String v){description=v;}
    public String getImageUrl(){return imageUrl;} public void setImageUrl(String v){imageUrl=v;}
    public boolean isAvailable(){return available;} public void setAvailable(boolean v){available=v;}
    public String getOwnerName(){return ownerName;} public void setOwnerName(String v){ownerName=v;}
    public LocalDateTime getCreatedAt(){return createdAt;} public void setCreatedAt(LocalDateTime v){createdAt=v;}
}
