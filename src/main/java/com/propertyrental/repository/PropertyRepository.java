package com.propertyrental.repository;
import com.propertyrental.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;
import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Long> {
    @Query("select p from Property p where " +
      "(:search is null or lower(p.title) like lower(concat('%',:search,'%')) or lower(p.location) like lower(concat('%',:search,'%')) or lower(p.city) like lower(concat('%',:search,'%'))) and " +
      "(:city is null or lower(p.city)=lower(:city)) and " +
      "(:type is null or lower(p.propertyType)=lower(:type)) and " +
      "(:maxRent is null or p.monthlyRent <= :maxRent)")
    List<Property> search(@Param("search") String search, @Param("city") String city,
      @Param("type") String type, @Param("maxRent") BigDecimal maxRent);
    long countByAvailableTrue();
}
